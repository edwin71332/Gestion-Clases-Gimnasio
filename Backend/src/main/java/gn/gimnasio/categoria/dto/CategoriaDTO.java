package gn.gimnasio.categoria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDTO {
    private Integer id_categoria;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    //Constructor
    public CategoriaDTO(Integer id_categoria, String nombre){
        this.id_categoria=id_categoria;
        this.nombre=nombre;
    }
}
