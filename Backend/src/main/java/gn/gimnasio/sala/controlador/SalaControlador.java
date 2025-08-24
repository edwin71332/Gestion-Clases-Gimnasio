package gn.gimnasio.sala.controlador;

import gn.gimnasio.sala.modelo.Sala;
import gn.gimnasio.sala.servicio.ISalaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "https://alesky-gym-rosa.web.app")
@RestController
@RequestMapping("/gimnasio-app/salas")
public class SalaControlador {


    @Autowired
    private ISalaServicio salaServicio;

    private static final Logger logger = LoggerFactory.getLogger(SalaControlador.class);

    @GetMapping
    public List<Sala> listar() {
        return salaServicio.listarSalas();
    }

    // agregar una categoria
    @PostMapping
    public Sala registrarSala(@RequestBody Sala sala){
        logger.info("Sala a Registra:{} ",sala);
        return salaServicio.agregarSala(sala);
    }
}
