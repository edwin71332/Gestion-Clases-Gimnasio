package gn.gimnasio.usuario.dto;

import gn.gimnasio.usuario.modelo.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioModalDTO {
    Integer id_usuario;

    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    String apellido;

    @NotBlank(message = "la cedula no puede estar vacío")
    String cedula;


    // Constructor para el usuario de inscripción
    public UsuarioModalDTO(Usuario usuario){
        this.id_usuario =usuario.getId_usuario();
        this.nombre = usuario.getNombre();
        this.apellido=usuario.getApellido();
        this.cedula = usuario.getCedula();

    }

}
