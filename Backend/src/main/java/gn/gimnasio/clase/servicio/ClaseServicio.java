// ClaseServicio es la implementación del servicio para las clases en el gimnasio.
// Refactorizado para usar el patrón Adapter para conversiones DTO.

package gn.gimnasio.clase.servicio;

import gn.gimnasio.inscripcion.repositorio.InscripcionRepositorio;
import gn.gimnasio.clase.adapter.ClaseDTOAdapter;
import gn.gimnasio.clase.adapter.ClaseDispoDTOAdarter;
import gn.gimnasio.clase.dto.ClaseDTO;
import gn.gimnasio.clase.dto.ClaseDisponibleDTO;
import gn.gimnasio.clase.dto.ClaseRegisterDTO;
import gn.gimnasio.clase.dto.HorarioDTO;
import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.clase.modelo.Estado;
import gn.gimnasio.clase.repositorio.ClaseRepositorio;
import gn.gimnasio.instructor.repositorio.InstructorRepositorio;
import gn.gimnasio.instructor.modelo.Instructor;
import gn.gimnasio.inscripcion.dto.InscripcionIdClaseDTO;
import gn.gimnasio.sala.modelo.Sala;
import gn.gimnasio.sala.repositorio.SalaRepositorio;
import gn.gimnasio.categoria.modelo.Categoria;
import gn.gimnasio.categoria.repositorio.CategoriaRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service // marcar esta clase como un componente de servicio en Spring.
public class ClaseServicio implements IClaseServicio {

    private static final Logger logger = LoggerFactory.getLogger(ClaseServicio.class);

    @Autowired // Inyeccion de dependencia
    private ClaseRepositorio claseRepositorio;

    @Autowired
    private InstructorRepositorio instructorRepositorio;

    @Autowired
    private SalaRepositorio salaRepositorio;

    @Autowired
    private InscripcionRepositorio inscripcionRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ClaseDTOAdapter claseDTOAdapter;

    @Autowired
    private ClaseDispoDTOAdarter claseDispoDTOAdarter;

    private ClaseDTO convertirEntidadADTO(Clase clase) {
        try {
            return claseDTOAdapter.entidadADTO(clase);
        } catch (Exception e) {
            logger.error("Error al convertir entidad Clase a DTO. ID: {}, Error: {}",
                    clase.getId_clase(), e.getMessage(), e);
            throw new RuntimeException("Error en conversión de entidad a DTO", e);
        }
    }

    // Metodo convierte una clase en claseDisponibleDTO usando adapter
    private ClaseDisponibleDTO EntidadAClaseDisponibleDTO(Clase clase) {
        try {
            return claseDispoDTOAdarter.entidadClaseADTO(clase);
        } catch (Exception e) {
            logger.error("Error al convertir entidad Clase a ClaseDisponibleDTO. ID: {}, Error: {}",
                    clase.getId_clase(), e.getMessage(), e);
            throw new RuntimeException("Error en conversión de entidad a ClaseDisponibleDTO", e);
        }
    }

    // Metodo para registrar una nueva clase
    @Override
    public Clase crearClase(ClaseRegisterDTO dto) {
        logger.info("Registrando nueva clase: {}", dto.getNombre());

        try {
            // Buscar las entidades relacionadas por ID
            Instructor instructor = instructorRepositorio.findById(dto.getId_instructor())
                    .orElseThrow(() -> {
                        logger.error("Instructor no encontrado con ID: {}", dto.getId_instructor());
                        return new EntityNotFoundException(
                                "Instructor no encontrado con ID: " + dto.getId_instructor());
                    });

            Categoria categoria = categoriaRepositorio.findById(dto.getId_categoria())
                    .orElseThrow(() -> {
                        logger.error("Categoría no encontrada con ID: {}", dto.getId_categoria());
                        return new EntityNotFoundException("Categoría no encontrada con ID: " + dto.getId_categoria());
                    });

            Sala sala = salaRepositorio.findById(dto.getId_sala())
                    .orElseThrow(() -> {
                        logger.error("Sala no encontrada con ID: {}", dto.getId_sala());
                        return new EntityNotFoundException("Sala no encontrada con ID: " + dto.getId_sala());
                    });

            // Crear la entidad Clase con las relaciones
            Clase nuevaClase = Clase.fromDTO(dto, instructor, categoria, sala);
            Clase claseGuardada = claseRepositorio.save(nuevaClase);

            logger.info("Clase registrada exitosamente con ID: {}", claseGuardada.getId_clase());
            return claseGuardada;

        } catch (EntityNotFoundException e) {
            throw e; // Re-lanzar excepciones de entidad no encontrada
        } catch (Exception e) {
            logger.error("Error inesperado al registrar clase: {}", e.getMessage(), e);
            throw new RuntimeException("Error al registrar la clase", e);
        }
    }

    // Metodo obtener clases disponibles
    @Override
    public List<ClaseDisponibleDTO> ObtenerClaseDisponibles() {
        return claseRepositorio.findByEstado(Estado.Disponible).stream()
                .map(this::EntidadAClaseDisponibleDTO)
                .collect(Collectors.toList());
    }

    // Metodo obtener clases paginadas
    @Override
    public Page<ClaseDTO> obtenerClasesPaginadas(Pageable pageable) {
        logger.info("Listando clases paginadas. Página: {}, Tamaño: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            return claseRepositorio.findAll(pageable).map(this::convertirEntidadADTO);
        } catch (Exception e) {
            logger.error("Error al listar clases paginadas: {}", e.getMessage(), e);
            throw new RuntimeException("Error al obtener clases paginadas", e);
        }
    }

    // Metodo para asignar horarios a una clase
    @Override
    public void asignarHorarioAClase(HorarioDTO dto) {
        Clase clase = claseRepositorio.findById(dto.getIdClase())
                .orElseThrow(() -> new EntityNotFoundException("Clase no encontrada con ID: " + dto.getIdClase()));

        // Si usas lista de fechas:
        LocalDate fechaInicio = LocalDate.parse(dto.getFechaInicio());
        LocalDate fechaFinal = LocalDate.parse(dto.getFechaFinal());

        LocalTime horaInicio = LocalTime.parse(dto.getHoraInicio());
        LocalTime horaFinal = LocalTime.parse(dto.getHoraFin());

        clase.setFechaInicio(fechaInicio);
        clase.setFechaFinal(fechaFinal);
        clase.setHora_inicio(horaInicio);
        clase.setHora_final(horaFinal);
        clase.setDuracion(dto.getDuracion());
        clase.setEstado(Estado.Disponible);

        claseRepositorio.save(clase);
    }

    // Cambiar estado segun la capacidad
    private void actualizarEstadoPorCapacidad(Clase clase) {
        // 1. Asegúrate de que la clase tiene sala y capacidad
        if (clase.getSala() == null || clase.getCapacidad() == null)
            return;

        Integer maxSala = clase.getSala().getCapacidad();
        if (clase.getCapacidad().equals(maxSala)) {
            clase.setEstado(Estado.Llena);
        } else {
            clase.setEstado(Estado.Disponible);
        }
    }

    @Service
    public class InscripcionServicio {

        public List<InscripcionIdClaseDTO> obtenerInscripcionesIds() {
            return inscripcionRepositorio.findAll().stream()
                    .map(inscripcion -> new InscripcionIdClaseDTO(
                            inscripcion.getId_inscripcion(),
                            inscripcion.getClase().getId_clase()))
                    .toList();
        }
    }
}