package gn.gimnasio.common.adapter.impl;

import gn.gimnasio.common.adapter.IAlmacenamientoArchivoAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para AlmacenamientoLocalAdapter.
 * Verifica la correcta implementación del patrón Adapter para almacenamiento de archivos.
 */
@DisplayName("Pruebas para AlmacenamientoLocalAdapter - Patrón Adapter en Sistema de Gimnasio")
class AlmacenamientoLocalAdapterTest {

    private IAlmacenamientoArchivoAdapter adapter;
    private MultipartFile archivoEjemplo;
    
    @TempDir
    Path directorioTemporal;

    @BeforeEach
    @DisplayName("Configuración inicial de adaptador y archivos de prueba")
    void configurarAdaptadorYArchivos() {
        // Crear adaptador con directorio temporal
        adapter = new AlmacenamientoLocalAdapter(directorioTemporal.toString());
        
        // Crear archivo de ejemplo para las pruebas
        String contenidoArchivo = "Contenido de prueba para licencia de instructor";
        archivoEjemplo = new MockMultipartFile(
            "licencia",
            "licencia-instructor.pdf",
            "application/pdf",
            contenidoArchivo.getBytes()
        );
    }

    @Test
    @DisplayName("Debe guardar archivo correctamente en el sistema local")
    void debeGuardarArchivoCorrectamente() throws IOException {
        // When - Cuando se guarda el archivo
        String rutaGuardado = adapter.guardarArchivo(archivoEjemplo, "licencia-instructor-123");
        
        // Then - Entonces el archivo debe existir y tener el contenido correcto
        assertNotNull(rutaGuardado, "La ruta de guardado no debe ser nula");
        assertTrue(adapter.existeArchivo(rutaGuardado), "El archivo debe existir después del guardado");
        
        // Verificar que el archivo realmente existe en el sistema de archivos
        Path archivoPath = Path.of(rutaGuardado);
        assertTrue(Files.exists(archivoPath), "El archivo físico debe existir");
        
        // Verificar contenido del archivo
        String contenidoGuardado = Files.readString(archivoPath);
        assertEquals("Contenido de prueba para licencia de instructor", contenidoGuardado,
                    "El contenido del archivo debe coincidir");
    }

    @Test
    @DisplayName("Debe generar nombres únicos para archivos con mismo prefijo")
    void debeGenerarNombresUnicosParaArchivos() throws IOException {
        // When - Cuando se guardan múltiples archivos con el mismo prefijo
        String ruta1 = adapter.guardarArchivo(archivoEjemplo, "licencia-instructor");
        
        // Esperar un milisegundo para garantizar timestamp diferente
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String ruta2 = adapter.guardarArchivo(archivoEjemplo, "licencia-instructor");
        
        // Then - Entonces las rutas deben ser diferentes
        assertNotEquals(ruta1, ruta2, "Las rutas de archivos con mismo prefijo deben ser únicas");
        assertTrue(adapter.existeArchivo(ruta1), "Primer archivo debe existir");
        assertTrue(adapter.existeArchivo(ruta2), "Segundo archivo debe existir");
    }

    @Test
    @DisplayName("Debe eliminar archivo existente correctamente")
    void debeEliminarArchivoExistente() throws IOException {
        // Given - Dado un archivo guardado
        String rutaArchivo = adapter.guardarArchivo(archivoEjemplo, "licencia-para-eliminar");
        assertTrue(adapter.existeArchivo(rutaArchivo), "El archivo debe existir antes de eliminar");
        
        // When - Cuando se elimina el archivo
        boolean eliminado = adapter.eliminarArchivo(rutaArchivo);
        
        // Then - Entonces el archivo no debe existir
        assertTrue(eliminado, "La operación de eliminado debe retornar true");
        assertFalse(adapter.existeArchivo(rutaArchivo), "El archivo no debe existir después de eliminar");
    }

    @Test
    @DisplayName("Debe retornar false al eliminar archivo inexistente")
    void debeRetornarFalseAlEliminarArchivoInexistente() {
        // Given - Dada una ruta de archivo que no existe
        String rutaInexistente = directorioTemporal.resolve("archivo-inexistente.pdf").toString();
        
        // When - Cuando se intenta eliminar el archivo inexistente
        boolean eliminado = adapter.eliminarArchivo(rutaInexistente);
        
        // Then - Entonces debe retornar false
        assertFalse(eliminado, "Eliminar archivo inexistente debe retornar false");
    }

    @Test
    @DisplayName("Debe verificar existencia de archivo correctamente")
    void debeVerificarExistenciaDeArchivo() throws IOException {
        // Given - Dado un archivo que existe y otro que no
        String rutaExistente = adapter.guardarArchivo(archivoEjemplo, "archivo-existente");
        String rutaInexistente = directorioTemporal.resolve("archivo-inexistente.pdf").toString();
        
        // When & Then - Cuando se verifica existencia
        assertTrue(adapter.existeArchivo(rutaExistente), "Archivo existente debe retornar true");
        assertFalse(adapter.existeArchivo(rutaInexistente), "Archivo inexistente debe retornar false");
    }

    @Test
    @DisplayName("Debe obtener tamaño de archivo correctamente")
    void debeObtenerTamanoArchivoCorrectamente() throws IOException {
        // Given - Dado un archivo guardado
        String rutaArchivo = adapter.guardarArchivo(archivoEjemplo, "archivo-tamaño");
        
        // When - Cuando se obtiene el tamaño
        long tamaño = adapter.obtenerTamanoArchivo(rutaArchivo);
        
        // Then - Entonces el tamaño debe coincidir con el contenido
        assertEquals(archivoEjemplo.getSize(), tamaño, "El tamaño debe coincidir con el archivo original");
        assertTrue(tamaño > 0, "El tamaño debe ser mayor a cero");
    }

    @Test
    @DisplayName("Debe retornar -1 para tamaño de archivo inexistente")
    void debeRetornarMenosUnoParaTamanoArchivoInexistente() {
        // Given - Dada una ruta de archivo inexistente
        String rutaInexistente = directorioTemporal.resolve("archivo-inexistente.pdf").toString();
        
        // When - Cuando se obtiene el tamaño
        long tamaño = adapter.obtenerTamanoArchivo(rutaInexistente);
        
        // Then - Entonces debe retornar -1
        assertEquals(-1, tamaño, "Tamaño de archivo inexistente debe ser -1");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando archivo es nulo")
    void debeLanzarExcepcionCuandoArchivoEsNulo() {
        // When & Then - Cuando se pasa archivo nulo, debe lanzar excepción
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> adapter.guardarArchivo(null, "prefijo"),
            "Debe lanzar IllegalArgumentException cuando el archivo es nulo"
        );
        
        assertTrue(excepcion.getMessage().contains("no puede ser nulo"), 
                  "El mensaje debe indicar que el archivo no puede ser nulo");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando archivo está vacío")
    void debeLanzarExcepcionCuandoArchivoEstaVacio() {
        // Given - Dado un archivo vacío
        MultipartFile archivoVacio = new MockMultipartFile(
            "archivo", "vacio.txt", "text/plain", new byte[0]
        );
        
        // When & Then - Cuando se guarda archivo vacío, debe lanzar excepción
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> adapter.guardarArchivo(archivoVacio, "prefijo"),
            "Debe lanzar IllegalArgumentException cuando el archivo está vacío"
        );
        
        assertTrue(excepcion.getMessage().contains("vacío"), 
                  "El mensaje debe indicar que el archivo no puede estar vacío");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando nombre de archivo es nulo")
    void debeLanzarExcepcionCuandoNombreArchivoEsNulo() {
        // Given - Dado un archivo sin nombre
        MultipartFile archivoSinNombre = new MockMultipartFile(
            "archivo", null, "text/plain", "contenido".getBytes()
        );
        
        // When & Then - Cuando se guarda archivo sin nombre, debe lanzar excepción
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> adapter.guardarArchivo(archivoSinNombre, "prefijo"),
            "Debe lanzar IllegalArgumentException cuando el nombre del archivo es nulo"
        );
        
        assertTrue(excepcion.getMessage().contains("nombre del archivo"), 
                  "El mensaje debe indicar que el nombre del archivo es requerido");
    }

    @Test
    @DisplayName("Debe manejar caracteres especiales en prefijo")
    void debeManejarCaracteresEspecialesEnPrefijo() throws IOException {
        // Given - Dado un prefijo con caracteres especiales
        String prefijoEspecial = "licencia-instructor#123@test!";
        
        // When - Cuando se guarda con prefijo especial
        String rutaGuardado = adapter.guardarArchivo(archivoEjemplo, prefijoEspecial);
        
        // Then - Entonces debe guardar correctamente (caracteres especiales limpiados)
        assertNotNull(rutaGuardado, "Debe guardar archivo con prefijo especial");
        assertTrue(adapter.existeArchivo(rutaGuardado), "Archivo debe existir después del guardado");
        
        // Verificar que el nombre no contiene caracteres especiales
        Path archivoPath = Path.of(rutaGuardado);
        String nombreArchivo = archivoPath.getFileName().toString();
        assertFalse(nombreArchivo.contains("#"), "Nombre no debe contener #");
        assertFalse(nombreArchivo.contains("@"), "Nombre no debe contener @");
        assertFalse(nombreArchivo.contains("!"), "Nombre no debe contener !");
    }
}
