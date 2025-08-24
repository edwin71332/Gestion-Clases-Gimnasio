// ClaseDTO es un objeto de transferencia de datos que representa una clase en el gimnasio.
// Utiliza Lombok para generar automáticamente los métodos getter, setter, constructor sin argumentos y constructor con todos los argumentos.
// Este DTO es útil para enviar y recibir datos de clases a través de la API REST del gimnasio.

package gn.gimnasio.clase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDTO {
    private Integer id;
    private String nombre;
    private Integer capacidad;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private String instructor;
    private String categoria;
    private String sala;
    private Integer duracion;
    private String estado;

}
