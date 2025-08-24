package gn.gimnasio.inscripcion.servicio;

import gn.gimnasio.inscripcion.modelo.Inscripcion;

public interface IInscripcionServicio {
    Inscripcion inscribirUsuario(Integer id_usuario, Integer id_clase);
}
