package gn.gimnasio.clase.controlador;

import gn.gimnasio.clase.dto.ClaseDTO;
import gn.gimnasio.clase.dto.ClaseDisponibleDTO;
import gn.gimnasio.clase.dto.ClaseRegisterDTO;
import gn.gimnasio.clase.dto.HorarioDTO;
import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.clase.servicio.IClaseServicio;
  import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@CrossOrigin(origins = "https://alesky-gym-rosa.web.app")
@RestController
@RequestMapping("/gimnasio-app/clases")
public class ClaseControlador {
    private static final Logger logger = LoggerFactory.getLogger(ClaseControlador.class);

    @Autowired
    private IClaseServicio claseServicio;

    // obtener datos pageable de la claseDTO
    @GetMapping
    public ResponseEntity<Page<ClaseDTO>> listar(Pageable pageable) {
        // Automatismo paginado: resuelve page, size, sort...
        Page<ClaseDTO> page = claseServicio.obtenerClasesPaginadas(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/crear")
    public ResponseEntity<Clase> crearClase(@RequestBody ClaseRegisterDTO dto) {
        Clase claseCreada = claseServicio.crearClase(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(claseCreada);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ClaseDisponibleDTO>> obtenerClasesDisponibles() {
        try {
            List<ClaseDisponibleDTO> clasesDisponibles = claseServicio.ObtenerClaseDisponibles();
            return ResponseEntity.ok(clasesDisponibles);
        } catch (Exception e) {
            // También podrías usar logs si quieres rastrear el error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @PostMapping("/asignar-horario")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void asignarHorario(@RequestBody HorarioDTO dto) {
        claseServicio.asignarHorarioAClase(dto);
    }
}
