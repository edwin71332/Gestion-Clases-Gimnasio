package gn.gimnasio.lincenciaInstructor.servicio;

import gn.gimnasio.common.adapter.IAlmacenamientoArchivoAdapter;
import gn.gimnasio.lincenciaInstructor.modelo.Licencia;
import gn.gimnasio.lincenciaInstructor.repositorio.LicenciaRepositorio;
import gn.gimnasio.instructor.modelo.Instructor;
import gn.gimnasio.instructor.repositorio.InstructorRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Servicio para gestión de licencias de instructores.
 * Refactorizado para usar el patrón Adapter para almacenamiento de archivos.
 * 
 * @author Sistema Gimnasio
 */
@Service
public class LicenciaServicio {
    
    private static final Logger logger = LoggerFactory.getLogger(LicenciaServicio.class);
    
    private final LicenciaRepositorio licenciaRepo;
    private final InstructorRepositorio instructorRepo;
    private final IAlmacenamientoArchivoAdapter almacenamientoAdapter;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param licenciaRepo Repositorio de licencias
     * @param instructorRepo Repositorio de instructores
     * @param almacenamientoAdapter Adaptador para almacenamiento de archivos
     */
    public LicenciaServicio(LicenciaRepositorio licenciaRepo,
                           InstructorRepositorio instructorRepo,
                           IAlmacenamientoArchivoAdapter almacenamientoAdapter) {
        this.licenciaRepo = licenciaRepo;
        this.instructorRepo = instructorRepo;
        this.almacenamientoAdapter = almacenamientoAdapter;
        
        logger.info("LicenciaServicio inicializado con adaptador de almacenamiento");
    }

    /**
     * Guarda una licencia de instructor utilizando el adaptador de almacenamiento.
     * 
     * @param instructorId ID del instructor
     * @param archivo Archivo de licencia a guardar
     * @return Licencia guardada
     * @throws IOException Si ocurre error al guardar el archivo
     * @throws RuntimeException Si el instructor no existe
     */
    public Licencia guardar(Integer instructorId, MultipartFile archivo) throws IOException {
        logger.info("Iniciando guardado de licencia para instructor ID: {}", instructorId);
        
        try {
            // Validar que el instructor existe
            Instructor instructor = instructorRepo.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("Instructor no encontrado con ID: {}", instructorId);
                    return new RuntimeException("Instructor no encontrado con ID: " + instructorId);
                });
            
            // Validar archivo
            validarArchivo(archivo);
            
            // Generar prefijo para el archivo
            String prefijoArchivo = generarPrefijoLicencia(instructorId);
            
            // Guardar archivo usando el adaptador
            String rutaArchivo = almacenamientoAdapter.guardarArchivo(archivo, prefijoArchivo);
            
            // Extraer nombre del archivo de la ruta
            String nombreArchivo = extraerNombreArchivo(rutaArchivo);
            
            // Crear y guardar entidad Licencia
            Licencia licencia = Licencia.fromFile(instructor, nombreArchivo, rutaArchivo);
            Licencia licenciaGuardada = licenciaRepo.save(licencia);
            
            logger.info("Licencia guardada exitosamente. ID: {}, Archivo: {}", 
                       licenciaGuardada.getId(), nombreArchivo);
            
            return licenciaGuardada;
            
        } catch (IOException e) {
            logger.error("Error de E/O al guardar licencia para instructor {}: {}", 
                        instructorId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al guardar licencia para instructor {}: {}", 
                        instructorId, e.getMessage(), e);
            throw new RuntimeException("Error al procesar la licencia", e);
        }
    }

    /**
     * Lista todas las licencias de un instructor específico.
     * 
     * @param instructorId ID del instructor
     * @return Lista de licencias del instructor
     */
    public List<Licencia> listarPorInstructor(Integer instructorId) {
        logger.debug("Listando licencias para instructor ID: {}", instructorId);
        
        try {
            List<Licencia> licencias = licenciaRepo.buscarPorInstructorId(instructorId);
            logger.debug("Encontradas {} licencias para instructor ID: {}", 
                        licencias.size(), instructorId);
            return licencias;
        } catch (Exception e) {
            logger.error("Error al listar licencias para instructor {}: {}", 
                        instructorId, e.getMessage(), e);
            throw new RuntimeException("Error al obtener licencias del instructor", e);
        }
    }
    
    /**
     * Elimina una licencia y su archivo asociado.
     * 
     * @param licenciaId ID de la licencia a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarLicencia(Integer licenciaId) {
        logger.info("Iniciando eliminación de licencia ID: {}", licenciaId);
        
        try {
            // Buscar la licencia
            Licencia licencia = licenciaRepo.findById(licenciaId)
                .orElseThrow(() -> {
                    logger.error("Licencia no encontrada con ID: {}", licenciaId);
                    return new RuntimeException("Licencia no encontrada con ID: " + licenciaId);
                });
            
            // Eliminar archivo físico
            boolean archivoEliminado = almacenamientoAdapter.eliminarArchivo(licencia.getFilepath());
            if (!archivoEliminado) {
                logger.warn("No se pudo eliminar el archivo de licencia: {}", licencia.getFilepath());
            }
            
            // Eliminar registro de base de datos
            licenciaRepo.delete(licencia);
            
            logger.info("Licencia eliminada exitosamente. ID: {}", licenciaId);
            return true;
            
        } catch (Exception e) {
            logger.error("Error al eliminar licencia {}: {}", licenciaId, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Valida que el archivo de licencia sea válido.
     * 
     * @param archivo Archivo a validar
     * @throws IllegalArgumentException Si el archivo no es válido
     */
    private void validarArchivo(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo de licencia no puede estar vacío");
        }
        
        if (archivo.getOriginalFilename() == null) {
            throw new IllegalArgumentException("El archivo debe tener un nombre válido");
        }
        
        // Validar tamaño máximo (ejemplo: 10MB)
        long tamañoMaximo = 10 * 1024 * 1024; // 10MB
        if (archivo.getSize() > tamañoMaximo) {
            throw new IllegalArgumentException("El archivo no puede ser mayor a 10MB");
        }
        
        logger.debug("Archivo validado: {} ({} bytes)", 
                    archivo.getOriginalFilename(), archivo.getSize());
    }
    
    /**
     * Genera un prefijo único para el archivo de licencia.
     * 
     * @param instructorId ID del instructor
     * @return Prefijo para el archivo
     */
    private String generarPrefijoLicencia(Integer instructorId) {
        return "licencia-instructor-" + instructorId;
    }
    
    /**
     * Extrae el nombre del archivo de la ruta completa.
     * 
     * @param rutaCompleta Ruta completa del archivo
     * @return Nombre del archivo
     */
    private String extraerNombreArchivo(String rutaCompleta) {
        if (rutaCompleta == null || rutaCompleta.isEmpty()) {
            return "archivo-sin-nombre";
        }
        
        // Extraer nombre del archivo de la ruta
        int ultimoSeparador = Math.max(
            rutaCompleta.lastIndexOf('/'), 
            rutaCompleta.lastIndexOf('\\')
        );
        
        return ultimoSeparador >= 0 ? 
            rutaCompleta.substring(ultimoSeparador + 1) : 
            rutaCompleta;
    }
}
