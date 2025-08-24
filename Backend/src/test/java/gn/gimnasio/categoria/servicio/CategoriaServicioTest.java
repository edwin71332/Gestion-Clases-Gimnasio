package gn.gimnasio.categoria.servicio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

// Tu modelo
import gn.gimnasio.categoria.modelo.Categoria;

// Tu servicio
import gn.gimnasio.categoria.servicio.CategoriaServicio;
import gn.gimnasio.categoria.servicio.ICategoriaServicio;


// Tu puerto (adaptador)
import gn.gimnasio.categoria.servicio.CategoriaRepositorioPort;

@ExtendWith(MockitoExtension.class)
public class CategoriaServicioTest {
    @Mock
    private CategoriaRepositorioPort categoriaRepositorioPort;

    @InjectMocks
    private CategoriaServicio categoriaServicio;

    @Test
    void testGuardarCategoria() {
        Categoria mockCategoria = new Categoria(1, "Funcional", "#10b981");
        when(categoriaRepositorioPort.guardar(mockCategoria)).thenReturn(mockCategoria);

        Categoria resultado = categoriaServicio.guardarCategoria(mockCategoria);

        assertNotNull(resultado);
        assertEquals("Funcional", resultado.getNombre());
        assertEquals(1,resultado.getId_categoria());
        assertEquals("#10b981", resultado.getColor());
        verify(categoriaRepositorioPort).guardar(mockCategoria);
    }

    @Test
    void testListarCategoria() {
        List<Categoria> mockLista = Arrays.asList(
                new Categoria(1, "Relajación","#10b986"),
                new Categoria(2, "Intensidad","#14b985")
        );
        when(categoriaRepositorioPort.obtener()).thenReturn(mockLista);

        List<Categoria> resultado = categoriaServicio.obtenerCategoria();

        assertEquals(2, resultado.size());
        verify(categoriaRepositorioPort).obtener();
    }

    @Test
    void testListarCategoriasPaginados() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Categoria> mockPage = new PageImpl<>(
                List.of(new Categoria(1, "Cardio", "#10b989"))
        );
        when(categoriaRepositorioPort.obtenerPaginado(pageable)).thenReturn(mockPage);

        Page<Categoria> resultado = categoriaServicio.obtenerCategoriasPaginados(pageable);

        assertEquals(1, resultado.getTotalElements());
        verify(categoriaRepositorioPort).obtenerPaginado(pageable);
    }

}
