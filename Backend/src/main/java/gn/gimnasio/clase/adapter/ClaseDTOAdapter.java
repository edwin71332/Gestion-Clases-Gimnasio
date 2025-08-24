package gn.gimnasio.clase.adapter;

import gn.gimnasio.clase.dto.ClaseDTO;
import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.common.adapter.IConversionDTOAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Adaptador para la conversión entre entidades Clase y ClaseDTO.
 * Centraliza la lógica de transformación y garantiza consistencia en las conversiones.
 * 
 * @author Sistema Gimnasio
 */
@Component
public class ClaseDTOAdapter implements IConversionDTOAdapter<Clase, ClaseDTO> {

    private static final Logger logger = LoggerFactory.getLogger(ClaseDTOAdapter.class);

    // Convertir la entidad clase a ClaseDTO
    @Override
    public ClaseDTO entidadADTO(Clase entidad) {
        validarEntidad(entidad);

        try {
            ClaseDTO dto = new ClaseDTO();
            dto.setId(entidad.getId_clase());
            dto.setNombre(entidad.getNombre());
            dto.setCapacidad(entidad.getCapacidad());
            dto.setDuracion(entidad.getDuracion());
            dto.setFechaInicio(entidad.getFechaInicio());
            dto.setFechaFinal(entidad.getFechaFinal());
            dto.setEstado(entidad.getEstado() != null ? entidad.getEstado().name() : "");

            // Conversión de entidades relacionadas a strings
            dto.setInstructor(construirNombreCompleto(entidad));
            dto.setCategoria(entidad.getCategoria() != null ? entidad.getCategoria().getNombre() : "");
            dto.setSala(entidad.getSala() != null ? entidad.getSala().getNombre() : "");

            logger.debug("Entidad Clase convertida a DTO exitosamente. ID: {}", entidad.getId_clase());
            return dto;

        } catch (Exception e) {
            logger.error("Error al convertir entidad Clase a DTO. ID: {}, Error: {}",
                    entidad.getId_clase(), e.getMessage(), e);
            throw new RuntimeException("Error en conversión entidad a DTO", e);
        }
    }

    // Convertir el ClaseDTO a la entidad clase
    @Override
    public Clase dtoAEntidad(ClaseDTO dto) {
        validarDTO(dto);

        try {
            Clase entidad = new Clase();
            entidad.setId_clase(dto.getId());
            entidad.setNombre(dto.getNombre());
            entidad.setCapacidad(dto.getCapacidad());
            entidad.setDuracion(dto.getDuracion());

            logger.debug("DTO ClaseDTO convertido a entidad exitosamente");
            return entidad;

        } catch (Exception e) {
            logger.error("Error al convertir DTO a entidad Clase. Error: {}", e.getMessage(), e);
            throw new RuntimeException("Error en conversión DTO a entidad", e);
        }
    }

    @Override
    public Clase actualizarEntidadDesdeDTO(Clase entidadExistente, ClaseDTO dto) {
        validarEntidad(entidadExistente);
        validarDTO(dto);

        try {
            if (dto.getNombre() != null && !dto.getNombre().trim().isEmpty()) {
                entidadExistente.setNombre(dto.getNombre().trim());
            }

            if (dto.getCapacidad() != null && dto.getCapacidad() > 0) {
                entidadExistente.setCapacidad(dto.getCapacidad());
            }

            if (dto.getDuracion() != null && dto.getDuracion() > 0) {
                entidadExistente.setDuracion(dto.getDuracion());
            }

            logger.debug("Entidad Clase actualizada desde DTO exitosamente. ID: {}",
                    entidadExistente.getId_clase());
            return entidadExistente;

        } catch (Exception e) {
            logger.error("Error al actualizar entidad Clase desde DTO. ID: {}, Error: {}",
                    entidadExistente.getId_clase(), e.getMessage(), e);
            throw new RuntimeException("Error al actualizar entidad desde DTO", e);
        }
    }

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

    public void validarEntidad(Clase entidad) {
        if (entidad == null) {
            throw new IllegalArgumentException("La entidad Clase no puede ser nula");
        }

        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la clase es requerido");
        }
    }

    private void validarDTO(ClaseDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO Clase no puede ser nulo");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la clase es requerido en el DTO");
        }
    }
}
