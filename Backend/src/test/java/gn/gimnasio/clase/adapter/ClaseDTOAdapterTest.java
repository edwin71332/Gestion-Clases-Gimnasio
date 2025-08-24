package gn.gimnasio.clase.adapter;

import gn.gimnasio.clase.dto.ClaseDTO;
import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.clase.modelo.Estado;
import gn.gimnasio.instructor.modelo.Instructor;
import gn.gimnasio.categoria.modelo.Categoria;
import gn.gimnasio.sala.modelo.Sala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para ClaseDTOAdapter - Patrón Adapter en Sistema de Gimnasio")
class ClaseDTOAdapterTest {

    private ClaseDTOAdapter adapter;
    private Clase ejemploClase;
    private ClaseDTO ejemploDTO;
    private Instructor instructorEjemplo;
    private Categoria categoriaEjemplo;
    private Sala salaEjemplo;

    @BeforeEach
    @DisplayName("Configuración inicial de datos de prueba")
    void configurarDatosPrueba() {
        adapter = new ClaseDTOAdapter();
        
        // Crear instructor de ejemplo
        instructorEjemplo = new Instructor();
        instructorEjemplo.setNombre("Juan Carlos");
        instructorEjemplo.setApellido("Pérez Gómez");
        
        // Crear categoría de ejemplo
        categoriaEjemplo = new Categoria();
        categoriaEjemplo.setNombre("Cardio Intensivo");

        // Crear sala de ejemplo
        salaEjemplo = new Sala();
        salaEjemplo.setNombre("Sala Principal A");
        
        // Crear clase de ejemplo
        ejemploClase = new Clase();
        ejemploClase.setId_clase(1);
        ejemploClase.setNombre("Clase de Spinning");
        ejemploClase.setCapacidad(25);
        ejemploClase.setDuracion(45);
        ejemploClase.setEstado(Estado.Disponible);
        ejemploClase.setInstructor(instructorEjemplo);
        ejemploClase.setCategoria(categoriaEjemplo);
        ejemploClase.setSala(salaEjemplo);

        // Crear DTO de ejemplo correspondiente
        ejemploDTO = new ClaseDTO();
        ejemploDTO.setId(1);
        ejemploDTO.setNombre("Clase de Spinning");
        ejemploDTO.setCapacidad(25);
        ejemploDTO.setInstructor("Juan Carlos Pérez Gómez");
        ejemploDTO.setCategoria("Cardio Intensivo");
        ejemploDTO.setSala("Sala Principal A");
        ejemploDTO.setDuracion(45);
        ejemploDTO.setEstado("Disponible");
    }

    @Test
    @DisplayName("Debe convertir entidad Clase a ClaseDTO correctamente")
    void debeConvertirEntidadADTO() {
        // When - Cuando se convierte la entidad a DTO
        ClaseDTO dtoResultado = adapter.entidadADTO(ejemploClase);
        
        // Then - Entonces el DTO debe contener los datos correctos
        assertNotNull(dtoResultado, "El DTO no debe ser nulo");
        assertEquals(ejemploClase.getId_clase(), dtoResultado.getId(), "ID debe coincidir");
        assertEquals(ejemploClase.getNombre(), dtoResultado.getNombre(), "Nombre debe coincidir");
        assertEquals(ejemploClase.getCapacidad(), dtoResultado.getCapacidad(), "Capacidad debe coincidir");
        assertEquals(ejemploClase.getDuracion(), dtoResultado.getDuracion(), "Duración debe coincidir");
        assertEquals("Juan Carlos Pérez Gómez", dtoResultado.getInstructor(), "Nombre completo del instructor debe coincidir");
        assertEquals("Cardio Intensivo", dtoResultado.getCategoria(), "Categoría debe coincidir");
        assertEquals("Sala Principal A", dtoResultado.getSala(), "Sala debe coincidir");
        assertEquals("Disponible", dtoResultado.getEstado(), "Estado debe coincidir");
    }

    @Test
    @DisplayName("Debe convertir ClaseDTO a entidad Clase correctamente")
    void debeConvertirDTOAEntidad() {
        // When - Cuando se convierte el DTO a entidad
        Clase entidadResultado = adapter.dtoAEntidad(ejemploDTO);
        
        // Then - Entonces la entidad debe contener los datos básicos correctos
        assertNotNull(entidadResultado, "La entidad no debe ser nula");
        assertEquals(ejemploDTO.getId(), entidadResultado.getId_clase(), "ID debe coincidir");
        assertEquals(ejemploDTO.getNombre(), entidadResultado.getNombre(), "Nombre debe coincidir");
        assertEquals(ejemploDTO.getCapacidad(), entidadResultado.getCapacidad(), "Capacidad debe coincidir");
        assertEquals(ejemploDTO.getDuracion(), entidadResultado.getDuracion(), "Duración debe coincidir");
    }

    @Test
    @DisplayName("Debe actualizar entidad existente desde DTO correctamente")
    void debeActualizarEntidadDesdeDTO() {
        // Given - Dada una entidad existente con datos diferentes
        Clase entidadExistente = new Clase();
        entidadExistente.setId_clase(999);
        entidadExistente.setNombre("Nombre Anterior");
        entidadExistente.setCapacidad(10);
        entidadExistente.setDuracion(30);
        
        // When - Cuando se actualiza la entidad desde el DTO
        Clase entidadActualizada = adapter.actualizarEntidadDesdeDTO(entidadExistente, ejemploDTO);
        
        // Then - Entonces la entidad debe tener los nuevos valores
        assertNotNull(entidadActualizada, "La entidad actualizada no debe ser nula");
        assertEquals(ejemploDTO.getNombre(), entidadActualizada.getNombre(), "Nombre debe haberse actualizado");
        assertEquals(ejemploDTO.getCapacidad(), entidadActualizada.getCapacidad(), "Capacidad debe haberse actualizada");
        assertEquals(ejemploDTO.getDuracion(), entidadActualizada.getDuracion(), "Duración debe haberse actualizada");
        assertEquals(999, entidadActualizada.getId_clase(), "ID no debe cambiar en actualización");
    }
    
    @Test
    @DisplayName("Debe lanzar excepción cuando entidad es nula")
    void debeLanzarExcepcionCuandoEntidadEsNula() {
        // When & Then - Cuando se pasa entidad nula, debe lanzar excepción
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> adapter.entidadADTO(null),
            "Debe lanzar IllegalArgumentException cuando la entidad es nula"
        );
        
        assertTrue(excepcion.getMessage().contains("no puede ser nula"), 
                  "El mensaje debe indicar que la entidad no puede ser nula");
    }
    
    @Test
    @DisplayName("Debe lanzar excepción cuando DTO es nulo")
    void debeLanzarExcepcionCuandoDTOEsNulo() {
        // When & Then - Cuando se pasa DTO nulo, debe lanzar excepción
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> adapter.dtoAEntidad(null),
            "Debe lanzar IllegalArgumentException cuando el DTO es nulo"
        );
        
        assertTrue(excepcion.getMessage().contains("no puede ser nulo"), 
                  "El mensaje debe indicar que el DTO no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debe manejar instructor nulo en conversión a DTO")
    void debeManejarInstructorNuloEnConversion() {
        // Given - Dada una clase sin instructor
        ejemploClase.setInstructor(null);
        
        // When - Cuando se convierte a DTO
        ClaseDTO dtoResultado = adapter.entidadADTO(ejemploClase);
        
        // Then - Entonces el campo instructor debe estar vacío
        assertNotNull(dtoResultado, "El DTO no debe ser nulo");
        assertEquals("", dtoResultado.getInstructor(), "Instructor debe ser string vacío cuando es nulo");
    }
    
    @Test
    @DisplayName("Debe validar nombre requerido en entidad")
    void debeValidarNombreRequeridoEnEntidad() {
        // Given - Dada una clase sin nombre
        ejemploClase.setNombre(null);
        
        // When & Then - Cuando se convierte, debe lanzar excepción
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> adapter.entidadADTO(ejemploClase),
            "Debe lanzar excepción cuando el nombre de la clase es nulo"
        );
        
        assertTrue(excepcion.getMessage().contains("requerido"), 
                  "El mensaje debe indicar que el nombre es requerido");
    }
}

