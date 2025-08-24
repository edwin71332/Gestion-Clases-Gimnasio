package gn.gimnasio.common.adapter;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Adaptador para el almacenamiento de archivos en el sistema de gimnasio.
 * Permite abstraer la implementación específica del almacenamiento de archivos.
 * 
 * @author Sistema Gimnasio
 */
public interface IAlmacenamientoArchivoAdapter {
    
    /**
     * Guarda un archivo en el sistema de almacenamiento.
     * 
     * @param archivo Archivo multipart a guardar
     * @param prefijo Prefijo para el nombre del archivo
     * @return Ruta donde se guardó el archivo
     * @throws IOException Si ocurre un error durante el guardado
     */
    String guardarArchivo(MultipartFile archivo, String prefijo) throws IOException;
    
    /**
     * Elimina un archivo del sistema de almacenamiento.
     * 
     * @param rutaArchivo Ruta del archivo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminarArchivo(String rutaArchivo);
    
    /**
     * Verifica si existe un archivo en el sistema de almacenamiento.
     * 
     * @param rutaArchivo Ruta del archivo a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existeArchivo(String rutaArchivo);
    
    /**
     * Obtiene el tamaño de un archivo.
     * 
     * @param rutaArchivo Ruta del archivo
     * @return Tamaño del archivo en bytes
     */
    long obtenerTamanoArchivo(String rutaArchivo);
}
