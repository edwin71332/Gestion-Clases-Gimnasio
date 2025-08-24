package gn.gimnasio.clase.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDisponibleDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_final")
    private LocalDate fechaFinal;
    @Column(name = "hora_inicio")
    private LocalTime hora_inicio;
    @Column(name = "hora_final")
    LocalTime hora_final;
    private Integer duracion;
    private Integer capacidad;
    private String estado;

    private String instructor;
    private String categoria;
    private String sala;

}
