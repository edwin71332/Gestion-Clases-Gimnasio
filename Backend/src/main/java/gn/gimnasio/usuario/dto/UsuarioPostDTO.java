package gn.gimnasio.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioPostDTO {
    Integer id_usuario;
    @NotBlank(message = "la cedula no puede estar vacío")
    String cedula;
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    String apellido;
    @Pattern(regexp = "\\d{9,15}", message = "El teléfono debe tener entre 9 y 15 dígitos")
    String telefono;
    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo no puede estar vacío")
    String correo;
}
