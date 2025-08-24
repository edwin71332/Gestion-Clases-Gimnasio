package gn.gimnasio.instructor.mapper;



import gn.gimnasio.instructor.dto.InstructorBasicoDTO;
import gn.gimnasio.instructor.dto.NuevoInstructorDTO;
import gn.gimnasio.instructor.modelo.Instructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InstructorMapper {

    public InstructorBasicoDTO toBasicoDTO(Instructor instructor) {
        return new InstructorBasicoDTO(instructor);
    }

    public NuevoInstructorDTO toNuevoDTO(Instructor instructor) {
        return new NuevoInstructorDTO(instructor);
    }

    public List<InstructorBasicoDTO> toListaBasicoDTO(List<Instructor> instructores) {
        return instructores.stream()
                .map(this::toBasicoDTO)
                .collect(Collectors.toList());
    }

    public List<NuevoInstructorDTO> toListaNuevoDTO(List<Instructor> instructores) {
        return instructores.stream()
                .map(this::toNuevoDTO)
                .collect(Collectors.toList());
    }
}
