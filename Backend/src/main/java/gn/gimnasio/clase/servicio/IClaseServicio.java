//claseServicio es la interfaz que define los métodos para manejar las clases en el gimnasio.
// ClaseServicio es la implementación del servicio para las clases en el gimnasio.

package gn.gimnasio.clase.servicio;
import gn.gimnasio.clase.dto.ClaseDTO;
import gn.gimnasio.clase.dto.ClaseDisponibleDTO;
import gn.gimnasio.clase.dto.ClaseRegisterDTO;
import gn.gimnasio.clase.dto.HorarioDTO;
import gn.gimnasio.clase.modelo.Clase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClaseServicio {

    // obtener clases Paginadas
    Page<ClaseDTO> obtenerClasesPaginadas(Pageable pageable);

    // Crear clase
    Clase crearClase(ClaseRegisterDTO dto);

    // Obtiene todas las clases cuyo estado = disponibles
    List<ClaseDisponibleDTO> ObtenerClaseDisponibles();

    // Asigna los horarios a una clase
    void asignarHorarioAClase(HorarioDTO dto);

}