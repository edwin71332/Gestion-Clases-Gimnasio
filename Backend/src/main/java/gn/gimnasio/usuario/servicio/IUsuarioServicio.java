package gn.gimnasio.usuario.servicio;

import gn.gimnasio.usuario.dto.UsuarioModalDTO;
import gn.gimnasio.usuario.dto.UsuarioPostDTO;
import gn.gimnasio.usuario.modelo.Usuario;

import java.util.List;

public interface IUsuarioServicio {

    // listado de usuarios
    List<Usuario> obtenerUsuario();

    // Metodo para registrar un usuario
    //Usuario guardarUsuario(Usuario usuario);

    // guardar
    Usuario guardarUsuario(UsuarioPostDTO usuario);
    // Para Registra el usuario en una clase
    List<UsuarioModalDTO> obtenerUsuariosResumen();

}
