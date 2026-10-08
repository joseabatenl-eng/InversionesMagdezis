package main.java.com.kinal.login.service.autenticacion;

import main.java.com.kinal.login.exception.ServiceException;
import main.java.com.kinal.login.model.usuario.Usuario;
import main.java.com.kinal.login.repository.autenticacion.AutenticacionRepository;
import main.java.resources.BCrypt.jbcrypt;

public class AutenticacionService {

    private final AutenticacionRepository autenticacionRepository;

    // Constructor
    public AutenticacionService(AutenticacionRepository autenticacionRepository) {
        this.autenticacionRepository = autenticacionRepository;
    }

    public Usuario login(String email, String contrasena) {
        if (email == null || email.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new ServiceException("Debes llenar usuario y contraseña.");
        }

        Usuario usuario = autenticacionRepository.findUsuarioByEmail(email.trim());

        // Mismo mensaje tanto si el correo no existe como si la contraseña falla,
        // por seguridad (no revelar cual de los dos fue el error).
        if (usuario == null || !jbcrypt.checkpw(contrasena, usuario.getContrasenaHash())) {
            throw new ServiceException("Usuario o contraseña incorrectos.");
        }

        return usuario;
    }

    /**
     * Encripta la contraseña y crea el usuario en la base de datos.
     * @return true si el registro se guardó correctamente.
     */
    public boolean crearUsuario(Usuario usuario) {
        String hash = jbcrypt.hashpw(usuario.getContrasenaHash(), jbcrypt.gensalt(12));
        usuario.setContrasenaHash(hash);
        return autenticacionRepository.save(usuario);
    }
}
