package gn.gimnasio.usuario.repositorio;

import gn.gimnasio.usuario.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

}
