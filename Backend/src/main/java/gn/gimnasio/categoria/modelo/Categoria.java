package gn.gimnasio.categoria.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id_categoria;
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre;
    @Column(name="color", length = 7)
    private String color;
}