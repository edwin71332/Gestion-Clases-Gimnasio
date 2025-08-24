package gn.gimnasio.inscripcion.controlador;

import gn.gimnasio.inscripcion.dto.InscripcionIdClaseDTO;
import gn.gimnasio.inscripcion.servicio.InscripcionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "https://alesky-gym-rosa.web.app")
@RestController
@RequestMapping("/gimnasio-app/inscripcion")
public class InscripcionControlador {

    @Autowired
    private InscripcionServicio inscripcionServicio;

    // DTO para recibir JSON
    public static class InscripcionRequest {
        public Integer id_usuario;
        public Integer id_clase;
    }

    // Inscribir un usuario a la clase
    @PostMapping
    public ResponseEntity<?> inscribirUsuario(@RequestBody InscripcionRequest request) {
        try {
            inscripcionServicio.inscribirUsuario(request.id_usuario, request.id_clase);
            return ResponseEntity.ok(Map.of("mensaje", "Inscripción exitosa"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error inesperado: " + e.getMessage()));
        }
    }

    //extraer las inscripciones en las clases 
    @GetMapping("/ids")
    public ResponseEntity<List<InscripcionIdClaseDTO>> obtenerInscripcionesIds() {
        List<InscripcionIdClaseDTO> lista = inscripcionServicio.obtenerInscripcionesIds();
        return ResponseEntity.ok(lista);
    }

}
