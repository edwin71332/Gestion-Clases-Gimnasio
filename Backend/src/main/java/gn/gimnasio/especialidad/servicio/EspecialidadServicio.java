package gn.gimnasio.especialidad.servicio;

import gn.gimnasio.especialidad.modelo.Especialidad;
import gn.gimnasio.especialidad.repositorio.EspecialidadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadServicio implements IEspecialidadServicio {

    @Autowired
    EspecialidadRepositorio especialidadRepositorio;

    @Override
    public List<Especialidad> obtenerEspecialidades() {
        return especialidadRepositorio.findAll();
    }

    @Override
    public Especialidad crearEspecialidad(Especialidad especialidad) {
        return especialidadRepositorio.save(especialidad);
    }

    // Obtiene la lista de especialidades
    @Override
    public Page<Especialidad> obtenerEspecialidadesPaginados(Pageable pageable) {
        return especialidadRepositorio.findAll((pageable));
    }

}
