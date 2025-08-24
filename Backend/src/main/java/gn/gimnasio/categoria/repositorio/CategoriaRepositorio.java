package gn.gimnasio.categoria.repositorio;

import gn.gimnasio.categoria.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria,Integer> { }
