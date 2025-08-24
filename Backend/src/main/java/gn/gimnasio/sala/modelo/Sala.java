package gn.gimnasio.sala.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id_sala;
    String nombre;
    String descripcion;
    Integer capacidad;
    @Enumerated(EnumType.STRING)
    EstadoSala estado;
    Integer id_categoria;
}