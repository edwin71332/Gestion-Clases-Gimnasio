package gn.gimnasio.common.adapter;

/**
 * Adaptador genérico para la conversión entre entidades y DTOs en el sistema de gimnasio.
 * Proporciona métodos estándar para la transformación bidireccional de datos.
 * 
 * @param <E> Tipo de la entidad
 * @param <D> Tipo del DTO
 * @author Sistema Gimnasio
 */
public interface IConversionDTOAdapter<E, D> {

    // Convierte una entidad a su DTO correspondiente.
    D entidadADTO(E entidad);
    
    //Convierte un DTO a su entidad correspondiente.
    E dtoAEntidad(D dto);

    //Actualiza una entidad existente con los datos de un DTO.
    E actualizarEntidadDesdeDTO(E entidadExistente, D dto);
}
