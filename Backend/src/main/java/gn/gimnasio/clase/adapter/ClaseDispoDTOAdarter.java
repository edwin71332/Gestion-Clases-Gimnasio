package gn.gimnasio.clase.adapter;

import gn.gimnasio.clase.dto.ClaseDisponibleDTO;
import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.common.adapter.impl.IConversionCDAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClaseDispoDTOAdarter implements IConversionCDAdapter<Clase, ClaseDisponibleDTO> {
    private static final Logger logger = LoggerFactory.getLogger(ClaseDispoDTOAdarter.class);

    // Convertir la entidad clase a ClaseDisponibleDTO
    @Override
    public ClaseDisponibleDTO entidadClaseADTO(Clase entidad) {
        validarEntidad(entidad);

        try {
            ClaseDisponibleDTO dto = new ClaseDisponibleDTO();
            dto.setId(entidad.getId_clase());
            dto.setNombre(entidad.getNombre());
            dto.setDescripcion(entidad.getDescripcion());
            dto.setFechaInicio(entidad.getFechaInicio());
            dto.setFechaFinal(entidad.getFechaFinal());
            dto.setHora_inicio(entidad.getHora_inicio());
            dto.setHora_final(entidad.getHora_final());
            dto.setDuracion(entidad.getDuracion());
            dto.setCapacidad(entidad.getCapacidad());
            dto.setEstado(entidad.getEstado() != null ? entidad.getEstado().name() : "");

            // Conversión de entidades relacionadas a strings
            dto.setInstructor(construirNombreCompleto(entidad));
            dto.setCategoria(entidad.getCategoria() != null ? entidad.getCategoria().getNombre() : "");
            dto.setSala(entidad.getSala() != null ? entidad.getSala().getNombre() : "");

            logger.debug("Entidad Clase convertida a ClaseDisponibleDTO exitosamente. ID: {}", entidad.getId_clase());
            return dto;

        } catch (Exception e) {
            logger.error("Error al convertir entidad Clase a ClaseDisponibleDTO. ID: {}, Error: {}",
                    entidad.getId_clase(), e.getMessage(), e);
            throw new RuntimeException("Error en conversión entidad a ClaseDisponibleDTO", e);
        }
    }

    // Válida que la entidad no sea nula y tenga los campos mínimos requeridos.
    private void validarEntidad(Clase entidad) {
        if (entidad == null) {
            throw new IllegalArgumentException("La entidad Clase no puede ser nula");
        }

        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la clase es requerido");
        }
    }

    // Construye el nombre completo del instructor concatenando nombre y apellido.
    public String construirNombreCompleto(Clase clase) {
        if (clase.getInstructor() == null) {
            return "";
        }

        String nombre = clase.getInstructor().getNombre();
        String apellido = clase.getInstructor().getApellido();

        if (nombre == null)
            nombre = "";
        if (apellido == null)
            apellido = "";

        return (nombre + " " + apellido).trim();
    }

}
