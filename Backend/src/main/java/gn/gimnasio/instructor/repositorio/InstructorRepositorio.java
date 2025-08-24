package gn.gimnasio.instructor.repositorio;

import gn.gimnasio.instructor.modelo.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositorio para la entidad Instructor.
 * Extiende JpaRepository para proporcionar métodos CRUD de forma automática.
 */
public interface InstructorRepositorio extends JpaRepository<Instructor,Integer>{

    @Query(
            value = "SELECT DISTINCT i FROM Instructor i LEFT JOIN FETCH i.instructorEspecialidades",
            countQuery = "SELECT COUNT(DISTINCT i) FROM Instructor i"
    )
    Page<Instructor> findAllWithEspecialidades(Pageable pageable);
}


