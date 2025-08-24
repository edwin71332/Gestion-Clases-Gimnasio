package gn.gimnasio.usuario.modelo;

import gn.gimnasio.inscripcion.modelo.Inscripcion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id_usuario;
    String cedula;
    String nombre;
    String apellido;
    String telefono;
    String correo;

    //Relacion con la entidad Inscripcion
    @OneToMany(mappedBy = "usuario")
    private List<Inscripcion> inscripciones = new ArrayList<>();;
}
