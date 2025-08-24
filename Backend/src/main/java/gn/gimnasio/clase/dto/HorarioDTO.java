package gn.gimnasio.clase.dto;

import lombok.Data;

import java.util.List;

@Data
public class HorarioDTO {
    private Integer idClase;
    private String fechaInicio;
    private String fechaFinal;
    private String horaInicio;
    private String horaFin;
    private int duracion;
}