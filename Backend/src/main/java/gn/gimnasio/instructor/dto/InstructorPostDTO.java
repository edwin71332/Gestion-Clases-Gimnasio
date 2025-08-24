package gn.gimnasio.instructor.dto;

//  * Representa a un instructor en el sistema de gimnasio.
//  * Utiliza JPA para la persistencia y Lombok para reducir código.
// DTO para manejar una vista completa de los instructores

 import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

 import java.util.List;

@Data
public class InstructorPostDTO {
    private Integer idinstructor;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;

    @Pattern(regexp = "\\d{9,15}", message = "El teléfono debe tener entre 9 y 15 dígitos")
    private String telefono;

    @NotBlank(message = "La cédula no puede estar vacía")
    private String cedula;

    private List<Integer> especialidadesIds; // IDs de especialidad seleccionadas

    public InstructorPostDTO(){}


}
