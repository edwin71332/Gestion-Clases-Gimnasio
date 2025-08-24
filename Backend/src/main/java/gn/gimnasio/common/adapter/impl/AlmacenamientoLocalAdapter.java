package gn.gimnasio.common.adapter.impl;

import gn.gimnasio.common.adapter.IAlmacenamientoArchivoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.Objects;

/**
 * Implementación del adaptador para almacenamiento local de archivos.
 * Maneja el guardado de archivos en el sistema de archivos local del servidor.
 * 
 * @author Sistema Gimnasio
 */
@Component
public class AlmacenamientoLocalAdapter implements IAlmacenamientoArchivoAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(AlmacenamientoLocalAdapter.class);
    
    private final Path directorioSubida;
    
    /**
     * Constructor que inicializa el directorio de subida.
     * 
     * @param rutaDirectorio Ruta del directorio donde se guardarán los archivos
     */
    public AlmacenamientoLocalAdapter(@Value("${app.upload.dir:uploads}") String rutaDirectorio) {
        this.directorioSubida = Paths.get(rutaDirectorio);
        inicializarDirectorio();
    }
    
    /**
     * Inicializa el directorio de subida si no existe.
     */
    private void inicializarDirectorio() {
        try {
            Files.createDirectories(directorioSubida);
            logger.info("Directorio de subida inicializado en: {}", directorioSubida.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Error al crear el directorio de subida: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo inicializar el directorio de almacenamiento", e);
        }
    }
    
    @Override
    public String guardarArchivo(MultipartFile archivo, String prefijo) throws IOException {
        validarArchivo(archivo);
        
        String nombreArchivo = generarNombreArchivo(archivo, prefijo);
        Path rutaDestino = directorioSubida.resolve(nombreArchivo);
        
        try {
            Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Archivo guardado exitosamente: {}", rutaDestino.toAbsolutePath());
            return rutaDestino.toString();
        } catch (IOException e) {
            logger.error("Error al guardar archivo {}: {}", nombreArchivo, e.getMessage(), e);
            throw new IOException("Error al guardar el archivo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminarArchivo(String rutaArchivo) {
        try {
            Path archivoPath = Paths.get(rutaArchivo);
            boolean eliminado = Files.deleteIfExists(archivoPath);
            
            if (eliminado) {
                logger.info("Archivo eliminado exitosamente: {}", rutaArchivo);
            } else {
                logger.warn("El archivo no existe: {}", rutaArchivo);
            }
            
            return eliminado;
        } catch (IOException e) {
            logger.error("Error al eliminar archivo {}: {}", rutaArchivo, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean existeArchivo(String rutaArchivo) {
        try {
            Path archivoPath = Paths.get(rutaArchivo);
            return Files.exists(archivoPath);
        } catch (Exception e) {
            logger.error("Error al verificar existencia del archivo {}: {}", rutaArchivo, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public long obtenerTamanoArchivo(String rutaArchivo) {
        try {
            Path archivoPath = Paths.get(rutaArchivo);
            return Files.size(archivoPath);
        } catch (IOException e) {
            logger.error("Error al obtener tamaño del archivo {}: {}", rutaArchivo, e.getMessage(), e);
            return -1;
        }
    }
    
    /**
     * Valida que el archivo sea válido para el guardado.
     * 
     * @param archivo Archivo a validar
     * @throws IllegalArgumentException Si el archivo no es válido
     */
    private void validarArchivo(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede ser nulo o vacío");
        }
        
        if (archivo.getOriginalFilename() == null || archivo.getOriginalFilename().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
    }
    
    /**
     * Genera un nombre único para el archivo basado en el prefijo y timestamp.
     * 
     * @param archivo Archivo original
     * @param prefijo Prefijo para el nombre
     * @return Nombre único del archivo
     */
    private String generarNombreArchivo(MultipartFile archivo, String prefijo) {
        String nombreOriginal = Objects.requireNonNull(archivo.getOriginalFilename());
        String extension = obtenerExtension(nombreOriginal);
        
        return String.format("%s-%d%s", 
            prefijo.replaceAll("[^a-zA-Z0-9-_]", ""), 
            Instant.now().toEpochMilli(), 
            extension.isEmpty() ? "" : "." + extension
        );
    }
    
    /**
     * Obtiene la extensión del archivo.
     * 
     * @param nombreArchivo Nombre del archivo
     * @return Extensión del archivo sin el punto
     */
    private String obtenerExtension(String nombreArchivo) {
        int ultimoPunto = nombreArchivo.lastIndexOf('.');
        return ultimoPunto > 0 ? nombreArchivo.substring(ultimoPunto + 1) : "";
    }
}
