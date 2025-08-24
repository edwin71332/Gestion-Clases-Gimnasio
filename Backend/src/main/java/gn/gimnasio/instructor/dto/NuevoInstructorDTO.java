package gn.gimnasio.instructor.dto;

import gn.gimnasio.instructor.modelo.Instructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class NuevoInstructorDTO {
    private Integer id_instructor;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String cedula;
    private List<Map<String, Object>> instructorEspecialidades;

    public NuevoInstructorDTO(Instructor instructor) {
        this.id_instructor = instructor.getId_instructor();
        this.nombre = instructor.getNombre();
        this.apellido = instructor.getApellido();
        this.telefono = instructor.getTelefono();
        this.correo = instructor.getCorreo();
        this.cedula = instructor.getCedula();

        this.instructorEspecialidades = instructor.getInstructorEspecialidades().stream()
                .map(ie -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("especialidad", Map.of(
                            "nombre", ie.getEspecialidad().getNombre()
                    ));
                    return map;
                })
                .collect(Collectors.toList());
    }

}
