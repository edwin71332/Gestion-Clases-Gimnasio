package gn.gimnasio.usuario.servicio;

import gn.gimnasio.usuario.dto.UsuarioModalDTO;
import gn.gimnasio.usuario.dto.UsuarioPostDTO;
import gn.gimnasio.usuario.modelo.Usuario;
import gn.gimnasio.usuario.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio  implements IUsuarioServicio{

    private final UsuarioRepositorio  usuarioRepositorio;

    @Autowired
    public UsuarioServicio( UsuarioRepositorio usuarioRepositorio){
        this.usuarioRepositorio=usuarioRepositorio;
    }

    //Obtener listado de usuarios
    @Override
    public List<Usuario> obtenerUsuario() {
        return usuarioRepositorio.findAll();
    }

    //guardar un usuario
    @Override
    public Usuario guardarUsuario(UsuarioPostDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setCedula(usuarioDTO.getCedula());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setCorreo(usuarioDTO.getCorreo());

        return usuarioRepositorio.save(usuario);
    }


    // Obtener datos básicos del usuario para el select en el modal
    @Override
    public List<UsuarioModalDTO> obtenerUsuariosResumen() {
        return usuarioRepositorio.findAll().stream().map(i-> {
            // Convertir cada UsuarioObtenido en UsuarioModalDTO
            UsuarioModalDTO dto = new UsuarioModalDTO(i);
            return dto;

        }) .toList();
    }
}
