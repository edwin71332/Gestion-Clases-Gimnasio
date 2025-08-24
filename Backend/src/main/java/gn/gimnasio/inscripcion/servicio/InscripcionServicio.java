package gn.gimnasio.inscripcion.servicio;

import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.clase.repositorio.ClaseRepositorio;
import gn.gimnasio.inscripcion.dto.InscripcionIdClaseDTO;
import gn.gimnasio.inscripcion.modelo.Inscripcion;
import gn.gimnasio.inscripcion.repositorio.InscripcionRepositorio;
import gn.gimnasio.usuario.modelo.Usuario;
import gn.gimnasio.usuario.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServicio implements IInscripcionServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ClaseRepositorio claseRepositorio;

    @Autowired
    private InscripcionRepositorio inscripcionRepositorio;

    // Metodo para inscribir un usuario o cliente en una clase
    @Transactional
    @Override
    public Inscripcion inscribirUsuario(Integer id_usuario, Integer id_clase) {

        Optional<Inscripcion> existente = inscripcionRepositorio.findByUsuarioIdAndClaseId(id_usuario, id_clase);
        if (existente.isPresent()) {
            throw new RuntimeException("El usuario ya está inscrito en esta clase.");
        }

        Usuario usuario = usuarioRepositorio.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Clase clase = claseRepositorio.findById(id_clase)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setClase(clase);
        return inscripcionRepositorio.save(inscripcion);
    }

    // Metodo para obtener lista con solo ids de inscripcion y clase
    public List<InscripcionIdClaseDTO> obtenerInscripcionesIds() {
        return inscripcionRepositorio.findAll().stream()
                .map(inscripcion -> new InscripcionIdClaseDTO(
                        inscripcion.getId_inscripcion(), // Ajusta según tu entidad Inscripcion
                        inscripcion.getClase().getId_clase()))
                .toList();
    }
}
