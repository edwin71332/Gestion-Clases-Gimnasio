package gn.gimnasio.inscripcion.modelo;

import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.usuario.modelo.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_inscripcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_clase")
    private Clase clase;

    private LocalDateTime fecha_inscripcion = LocalDateTime.now();
}
