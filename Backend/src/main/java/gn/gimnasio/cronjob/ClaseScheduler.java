package gn.gimnasio.cronjob;

import gn.gimnasio.clase.modelo.Clase;
import gn.gimnasio.clase.modelo.Estado;
import gn.gimnasio.clase.repositorio.ClaseRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ClaseScheduler {
    @Autowired
    private ClaseRepositorio claseRepositorio;

    @Transactional
    // Cada hora en punto
    @Scheduled(cron = "0 0 21 * * *")
    public void verificarClasesFinalizadas() {
        List<Clase> clasesFinalizadas = claseRepositorio.findByFechaFinalBeforeAndEstadoNot(LocalDate.now(), Estado.Cancelada);
        System.out.println("¡Cron job ejecutado! Hora: " + java.time.LocalDateTime.now());
        for (Clase clase : clasesFinalizadas) {
            clase.setEstado(Estado.Cancelada);
        }

        claseRepositorio.saveAll(clasesFinalizadas);

        if (!clasesFinalizadas.isEmpty()) {
            System.out.println("Clases finalizadas actualizadas: " + clasesFinalizadas.size());
        }
    }
}
