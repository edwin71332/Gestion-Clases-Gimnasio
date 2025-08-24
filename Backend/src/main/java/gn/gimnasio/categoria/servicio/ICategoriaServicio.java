package gn.gimnasio.categoria.servicio;

import gn.gimnasio.categoria.modelo.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoriaServicio {
    // obtener lista categorias
    List<Categoria> obtenerCategoria();

    // Guardar una categoria
    Categoria guardarCategoria(Categoria categoria);

    // obtener lista Categorias Paginadas
    Page<Categoria> obtenerCategoriasPaginados(Pageable pageable);
}