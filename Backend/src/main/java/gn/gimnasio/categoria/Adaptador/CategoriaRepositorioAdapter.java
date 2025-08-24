package gn.gimnasio.categoria.Adaptador;

import gn.gimnasio.categoria.modelo.Categoria;
import gn.gimnasio.categoria.repositorio.CategoriaRepositorio;
import gn.gimnasio.categoria.servicio.CategoriaRepositorioPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

// Representa Nuestro adaptador saliente, implementa el puerto con jpa
@Repository
public class CategoriaRepositorioAdapter implements CategoriaRepositorioPort {

    private final CategoriaRepositorio categoriaRepositorio;

    //Inyeccion de dependencia
    public CategoriaRepositorioAdapter(CategoriaRepositorio categoriaRepositorio) {
        this.categoriaRepositorio=categoriaRepositorio;
    }

    // metodo guardar una categoria
    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    // Obtener categorias
    @Override
    public List<Categoria> obtener() {
        return categoriaRepositorio.findAll();
    }

    // Obtener categorias paginadas
    @Override
    public Page<Categoria> obtenerPaginado(Pageable pageable) {
        return categoriaRepositorio.findAll(pageable);
    }
}
