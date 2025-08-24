
package gn.gimnasio.lincenciaInstructor.controlador;

import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import gn.gimnasio.lincenciaInstructor.servicio.*;
import gn.gimnasio.lincenciaInstructor.modelo.*;
import gn.gimnasio.lincenciaInstructor.dto.LicenciaDTO;

@CrossOrigin(origins = "https://alesky-gym-rosa.web.app")
@RestController
@RequestMapping("/gimnasio-app/instructores/{id}/licencias")
public class LicenciaControlador {

    private final LicenciaServicio service;

    public LicenciaControlador(LicenciaServicio service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LicenciaDTO> upload(
            @PathVariable("id") Integer id,
            @RequestParam("licencia") MultipartFile file) throws IOException {

        Licencia saved = service.guardar(id, file);
        LicenciaDTO dto = mapToDto(saved);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<LicenciaDTO>> list(@PathVariable("id") Integer id) {
        List<LicenciaDTO> dtos = service.listarPorInstructor(id)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Método privado para convertir entidad a DTO
    private LicenciaDTO mapToDto(Licencia lic) {
        return new LicenciaDTO(
                lic.getId(),
                lic.getInstructor().getId_instructor(),
                lic.getFilename(),
                lic.getFilepath(),
                lic.getUploadedAt());
    }
}