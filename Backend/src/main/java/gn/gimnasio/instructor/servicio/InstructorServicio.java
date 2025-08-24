package gn.gimnasio.instructor.servicio;

import gn.gimnasio.especialidad.modelo.Especialidad;
import gn.gimnasio.especialidad.repositorio.EspecialidadRepositorio;
import gn.gimnasio.instructor.dto.*;
import gn.gimnasio.instructor.modelo.Instructor;
import gn.gimnasio.instructor.dto.InstructorBasicoDTO;
import gn.gimnasio.instructor.modelo.InstructorEspecialidad;
import gn.gimnasio.instructor.repositorio.InstructorEspecialidadRepositorio;
import gn.gimnasio.instructor.repositorio.InstructorRepositorio;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de instructores en el gimnasio.
 * Proporciona métodos para listar, agregar y paginar instructores.
 */

@Service
public class InstructorServicio implements IInstructorServicio {


    private final InstructorRepositorio instructorRepositorio;
    private final EspecialidadRepositorio especialidadRepositorio;
    private final InstructorEspecialidadRepositorio ieRepositorio;

    /** Inyección de dependencias del repositorio de instructores. */
    @Autowired
    public InstructorServicio(InstructorRepositorio instructorRepositorio, EspecialidadRepositorio especialidadRepositorio, InstructorEspecialidadRepositorio ieRepositorio) {
        this.instructorRepositorio = instructorRepositorio;
        this.especialidadRepositorio = especialidadRepositorio;
        this.ieRepositorio = ieRepositorio;
    }

    @Override
    public List<Instructor> obtenerEspecialidad() {
        return instructorRepositorio.findAll();
    }

    /** Guarda un nuevo instructor en la base de datos. */
    @Override
    @Transactional
    public Instructor agregarInstructor(InstructorPostDTO dto) {
        // 1. Crear Instructor desde DTO
        Instructor instructor = new Instructor(dto);

        // 2. Guardar instructor base primero para generar ID
        Instructor instructorGuardado = instructorRepositorio.save(instructor);

        // 3. Agregar especialidades
        if (dto.getEspecialidadesIds() != null) {
            dto.getEspecialidadesIds().forEach(idEspecialidad -> {
                Especialidad especialidad = especialidadRepositorio.findById(idEspecialidad)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Especialidad no encontrada: " + idEspecialidad));

                InstructorEspecialidad ie = new InstructorEspecialidad();
                ie.setInstructor(instructorGuardado);
                ie.setEspecialidad(especialidad);

                // Guardar relación en la base
                ieRepositorio.save(ie);

                // Añadir relación en memoria
                instructorGuardado.getInstructorEspecialidades().add(ie);
            });
        }

        return instructorGuardado;
    }

    /** Obtiene una lista paginada de instructores. */
    @Override
    public Page<Instructor> listarInstructoresPaginados(Pageable pageable) {
        return instructorRepositorio.findAll((pageable));
    }

    @Override
    public List<InstructorBasicoDTO> listarInstructorBasico() {
        return instructorRepositorio.findAll()
                .stream()
                .map(i -> {
                    // Convertir cada Instructor en InstructorBasicDTO
                    InstructorBasicoDTO dto = new InstructorBasicoDTO(i);
                    return dto;
                })
                .toList();
    }

    @Override
    public Page<NuevoInstructorDTO> obtenerInstructoresPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Instructor> instructoresPage = instructorRepositorio.findAllWithEspecialidades(pageable);

        // Convertir a DTO
        List<NuevoInstructorDTO> dtos = instructoresPage.getContent()
                .stream()
                .map(NuevoInstructorDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, instructoresPage.getTotalElements());
    }

    @Override
    @Transactional
    public Instructor editarInstructor(Integer id, EditarInstructorDTO dto) {
        Instructor instructor = instructorRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor no encontrado"));

        instructor.actualizarDesdeDTO(dto);
        return instructorRepositorio.save(instructor);
    }
}
