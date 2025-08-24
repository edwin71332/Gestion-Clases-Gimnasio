package gn.gimnasio.sala.servicio;

import gn.gimnasio.sala.modelo.Sala;

import java.util.List;

public interface ISalaServicio {

    List<Sala> listarSalas();

    Sala agregarSala(Sala sala);
}
