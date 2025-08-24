package gn.gimnasio.inscripcion.dto;

import lombok.Data;

@Data

public class InscripcionIdClaseDTO {

    private Integer idInscripcion;
    private Integer idClase;

    public InscripcionIdClaseDTO(Integer idInscripcion, Integer idClase) {
        this.idInscripcion = idInscripcion;
        this.idClase = idClase;
    }

}
