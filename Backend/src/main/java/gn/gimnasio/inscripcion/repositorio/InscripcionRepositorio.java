package gn.gimnasio.inscripcion.repositorio;

import gn.gimnasio.inscripcion.modelo.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InscripcionRepositorio  extends JpaRepository<Inscripcion,Integer> {

    @Query(value = "SELECT * FROM inscripcion WHERE id_usuario = :idUsuario AND id_clase = :idClase", nativeQuery = true)
    Optional<Inscripcion> findByUsuarioIdAndClaseId(@Param("idUsuario") Integer idUsuario, @Param("idClase") Integer idClase);

}
