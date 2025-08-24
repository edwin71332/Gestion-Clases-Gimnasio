package gn.gimnasio.instructor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditarInstructorDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @Email(message = "Formato de email inválido")
    private String correo;
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Teléfono inválido")
    private String telefono;
    @NotBlank(message = "La cedula es obligatorio")
    private String cedula;
}
