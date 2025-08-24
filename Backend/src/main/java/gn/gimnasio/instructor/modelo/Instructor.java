package gn.gimnasio.instructor.modelo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import gn.gimnasio.especialidad.modelo.Especialidad;
import gn.gimnasio.instructor.dto.EditarInstructorDTO;
import gn.gimnasio.instructor.dto.InstructorPostDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Representa a un instructor en el sistema de gimnasio.
 * Utiliza JPA para la persistencia y Lombok para reducir código.
 */
@Entity
@Data // Genera automáticamente métodos como toString, equals, y hashCode
@NoArgsConstructor // Constructor sin argumentos requerido por JPA
@AllArgsConstructor // Constructor con todos los atributos

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id_instructor;
    String nombre;
    String apellido;
    String telefono;
    String correo;
    String cedula;


    public Instructor(InstructorPostDTO dto) {
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.correo = dto.getCorreo();
        this.telefono = dto.getTelefono();
        this.cedula = dto.getCedula();
    }

    public void actualizarDesdeDTO(EditarInstructorDTO dto) {
        if (dto.getNombre() != null) this.nombre = dto.getNombre();
        if (dto.getApellido() != null) this.apellido = dto.getApellido();
        if (dto.getCorreo() != null) this.correo = dto.getCorreo();
        if (dto.getTelefono() != null) this.telefono = dto.getTelefono();
        if (dto.getCedula()!= null) this.cedula =dto.getCedula();
    }

    public void addEspecialidad(Especialidad esp) {
        InstructorEspecialidad ie = new InstructorEspecialidad();
        ie.setInstructor(this);
        ie.setEspecialidad(esp);
        this.instructorEspecialidades.add(ie);
    }

    //Relación con la entidad Especialidad
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    @JsonManagedReference("instructor_especialidad")
    private List<InstructorEspecialidad> instructorEspecialidades = new ArrayList<>();


    @JsonGetter("instructorEspecialidades")
    public List<Map<String, Object>> getInstructorEspecialidadesForJson() {
        if (instructorEspecialidades == null) {
            return Collections.emptyList();
        }
        return instructorEspecialidades.stream().map(ie -> {
            Map<String, Object> map = new HashMap<>();
            map.put("especialidad", ie.getEspecialidad());
            return map;
        }).collect(Collectors.toList());
    }
}
