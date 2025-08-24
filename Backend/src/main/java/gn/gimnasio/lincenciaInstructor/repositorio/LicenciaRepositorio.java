package gn.gimnasio.lincenciaInstructor.repositorio;

import gn.gimnasio.lincenciaInstructor.modelo.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LicenciaRepositorio extends JpaRepository<Licencia, Integer> {

    /**
     * Busca todas las licencias para un instructor dado.
     * 'id_instructor' es la columna tal cual en la tabla SQL.
     */
    @Query(
      value = "SELECT * FROM Licencia WHERE id_instructor = :instructorId",
      nativeQuery = true
    )
    List<Licencia> buscarPorInstructorId(@Param("instructorId") Integer instructorId);
}
