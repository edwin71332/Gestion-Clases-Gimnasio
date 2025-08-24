package gn.gimnasio.clase.repositorio;

import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.clase.modelo.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClaseRepositorio extends JpaRepository<Clase,Integer> {
    List<Clase> findByEstado(Estado estado);
    List<Clase> findByFechaFinalBeforeAndEstadoNot(LocalDate fecha, Estado estado);


}
