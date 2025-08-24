package gn.gimnasio.instructor.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import gn.gimnasio.especialidad.modelo.Especialidad;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InstructorEspecialidad {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id_Instruespecial;

        @ManyToOne
        @JoinColumn(name = "id_instructor")
        @JsonBackReference("instructor-especialidad") // Mismo nombre que en Instructor // Lado "hijo"
        private Instructor instructor;

        @ManyToOne
        @JoinColumn(name = "id_especialidad")
        @JsonBackReference("especialidad-instructor") // Lado "hijo"
        private Especialidad especialidad;
}
