package gn.gimnasio.instructor.servicio;

import gn.gimnasio.instructor.dto.EditarInstructorDTO;
import  gn.gimnasio.instructor.dto.InstructorBasicoDTO;
import gn.gimnasio.instructor.dto.InstructorPostDTO;
import gn.gimnasio.instructor.dto.NuevoInstructorDTO;
import gn.gimnasio.instructor.modelo.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IInstructorServicio {

    List<Instructor> obtenerEspecialidad();

    // Metodo para agregar un instructor necesario para el formulario
    Instructor agregarInstructor(InstructorPostDTO dto);

    // Nuevo metodo para lista Instructores Paginados
    Page<Instructor> listarInstructoresPaginados(Pageable pageable);

    // Para poblar el <select>: solo id y nombre
    List<InstructorBasicoDTO> listarInstructorBasico();

    // Editar un instructor
    Instructor editarInstructor(Integer id, EditarInstructorDTO dto);

    Page<NuevoInstructorDTO> obtenerInstructoresPaginados(int page, int size);
}
