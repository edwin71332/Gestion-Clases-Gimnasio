package gn.gimnasio.especialidad.servicio;

import gn.gimnasio.especialidad.modelo.Especialidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEspecialidadServicio {

    // Obtiene la lista de especialidades
    List<Especialidad> obtenerEspecialidades();

    // Crear una nueva especialidad
    Especialidad crearEspecialidad(Especialidad especialidad);

    // Obtiene la lista de especialidades
    Page<Especialidad> obtenerEspecialidadesPaginados(Pageable pageable);

}
