package gn.gimnasio.common.adapter.impl;

public interface IConversionCDAdapter<E,D> {
    D entidadClaseADTO(E entidad);
}
