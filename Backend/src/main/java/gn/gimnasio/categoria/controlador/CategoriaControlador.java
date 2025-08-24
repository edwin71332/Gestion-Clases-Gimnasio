package gn.gimnasio.categoria.controlador;

import gn.gimnasio.categoria.modelo.Categoria;
import gn.gimnasio.categoria.servicio.ICategoriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://alesky-gym-rosa.web.app")
@RestController
@RequestMapping("/gimnasio-app/categorias")
public class CategoriaControlador {

    @Autowired
    private ICategoriaServicio categoriaServicio;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaControlador.class);

    // Lista de todas las categorias
    @GetMapping
    public List<Categoria> obtener() {
        return categoriaServicio.obtenerCategoria();
    }

    // Guardar una categoria
    @PostMapping
    public Categoria guardar (@RequestBody Categoria categoria){
        logger.info("Categoria a Registra:{} ",categoria);
        return  categoriaServicio.guardarCategoria(categoria);
    }

    // Obtener categoria paginadas
    @GetMapping("/Paginados")
    public ResponseEntity<Page<Categoria>> obtenerPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {

        // Configurar ordenamiento
        Sort.Direction direction = Sort.Direction.ASC;
        if(sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy != null ? sortBy : "nombre")
        );
        return ResponseEntity.ok(categoriaServicio.obtenerCategoriasPaginados(pageable));
    }

}


 