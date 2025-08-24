package gn.gimnasio.categoria.servicio;

import gn.gimnasio.categoria.modelo.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CategoriaServicio implements ICategoriaServicio{

    private final CategoriaRepositorioPort  categoriaRepositorioport;
    // inyeccion de dependencia
    public CategoriaServicio( CategoriaRepositorioPort categoriaRepositorioport) {
        this.categoriaRepositorioport=categoriaRepositorioport;
    }

    // Obtiene lista de categorias
    @Override
    public List<Categoria> obtenerCategoria() {
        return categoriaRepositorioport.obtener();
    }

    // Guardar una categoria
    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        // si la categoria ya viene con un color, lo dejamos
        if(categoria.getColor()==null || categoria.getColor().isEmpty()){
            // obtener colores ya usados
            List<String> coloresUsuados = categoriaRepositorioport.obtener().stream()
                    .map(Categoria:: getColor)
                    .toList();
            // Generar un color aleatorio
            String color;
            do{
                color = generarColorAleatorio();
            }while(coloresUsuados.contains(color));
            categoria.setColor(color);
        }
        return categoriaRepositorioport.guardar(categoria);
    }

    // Obtener lista paginada de categorias
    @Override
    public Page<Categoria> obtenerCategoriasPaginados(Pageable pageable) {
        return categoriaRepositorioport.obtenerPaginado(pageable);
    }

    // Metodo para generar un color hexadecimal aleatorio
    private String generarColorAleatorio() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
