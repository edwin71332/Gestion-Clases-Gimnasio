package gn.gimnasio.clase.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClaseRegisterDTO {
    private Integer id;

    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "La capacidad no puede estar vacía")
    private Integer capacidad;
    @NotBlank(message = "la descripcion no puede estar vacio")
    private String descripcion;
    @NotBlank(message = "El id de instructor no puede estar vacio")
    private Integer id_instructor;
    @NotBlank(message = "El id de la categoria no puede estar vacio")
    private  Integer id_categoria;
    @NotBlank(message = "El id de la sala no puede estar vacio")
    private Integer id_sala;

    public ClaseRegisterDTO(){}
}
