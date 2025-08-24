package gn.gimnasio.usuario.controlador;

import gn.gimnasio.usuario.dto.UsuarioModalDTO;
import gn.gimnasio.usuario.dto.UsuarioPostDTO;
import gn.gimnasio.usuario.modelo.Usuario;
import gn.gimnasio.usuario.servicio.IUsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://alesky-gym-rosa.web.app")
@RestController
@RequestMapping("/gimnasio-app/usuarios")
public class UsuarioControlador {
    @Autowired
    private IUsuarioServicio usuarioServicio;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

    //Listar de todos los usuarios
    @GetMapping
    public List<Usuario> listar() {return usuarioServicio.obtenerUsuario();}

    //Lista usuario para poblar el select del modal
    @GetMapping("Modal")
    public List<UsuarioModalDTO> obterneUsuariosResumen(){
        return usuarioServicio.obtenerUsuariosResumen();
    }

    // Registrar un usuario
    @PostMapping("/Registrar")
    public Usuario guardarUsuario(@RequestBody UsuarioPostDTO usuario){
        logger.info("Usuario a Registra:{} ",usuario);
        return  usuarioServicio.guardarUsuario(usuario);
    }
}
