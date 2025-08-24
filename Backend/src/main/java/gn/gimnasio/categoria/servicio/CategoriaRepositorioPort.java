package gn.gimnasio.categoria.servicio;

import gn.gimnasio.categoria.modelo.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// Define el contrato que el núcleo necesita para persistir categorías
// Adaptador Saliente --> representa lo que mi dominio espera de cualquier mecanismo de persistencia
public interface CategoriaRepositorioPort {
    Categoria guardar(Categoria categoria);
    List<Categoria> obtener();
    Page<Categoria> obtenerPaginado(Pageable pageable);
}
