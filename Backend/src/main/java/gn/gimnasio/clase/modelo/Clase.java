package gn.gimnasio.clase.modelo;

import gn.gimnasio.inscripcion.modelo.Inscripcion;
import gn.gimnasio.clase.dto.ClaseRegisterDTO;
import gn.gimnasio.instructor.modelo.Instructor;
import gn.gimnasio.sala.modelo.Sala;
import gn.gimnasio.categoria.modelo.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id_clase;
    String nombre;
    String descripcion;
    @Column(name = "fecha_inicio")
    LocalDate fechaInicio;
    @Column(name = "fecha_final")
    LocalDate fechaFinal;
    @Column(name = "hora_inicio")
    LocalTime hora_inicio;

    @Column(name = "hora_final")
    LocalTime hora_final;
    Integer duracion;
    Integer capacidad;
    @Enumerated(EnumType.STRING)
    Estado estado = Estado.Sin_horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor")
    private Instructor instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala sala;

    // relación con la tabla inscripción
    @OneToMany(mappedBy = "clase")
    private List<Inscripcion> inscripciones;

    // Metodo estatico para crear una instancia de clases
    public static Clase fromDTO(ClaseRegisterDTO dto, Instructor instructor, Categoria categoria, Sala sala) {
        Clase clase = new Clase();
        clase.setNombre(dto.getNombre());
        clase.setCapacidad(dto.getCapacidad());
        clase.setDescripcion(dto.getDescripcion());
        clase.setInstructor(instructor);
        clase.setCategoria(categoria);
        clase.setEstado(Estado.Sin_horario);
        clase.setSala(sala);

        return clase;
    }
}
