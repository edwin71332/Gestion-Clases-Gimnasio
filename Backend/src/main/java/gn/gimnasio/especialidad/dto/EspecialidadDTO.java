package gn.gimnasio.especialidad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EspecialidadDTO {
    private Integer id_especialidad;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "Máximo 60 caracteres")
    private String nombre;

    //Constructor
    public EspecialidadDTO(){}

    //Constructor para registrar especialidad sin lista
    public EspecialidadDTO(Integer id_especialidad, String nombre){
        this.id_especialidad = id_especialidad;
        this.nombre=nombre;
    }

}
