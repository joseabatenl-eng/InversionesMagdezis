package main.java.com.kinal.login.controller.autenticacion;

import main.java.com.kinal.login.model.usuario.Usuario;
import main.java.com.kinal.login.repository.autenticacion.AutenticacionRepository;
import main.java.com.kinal.login.service.autenticacion.AutenticacionService;

/**
 * Controlador: conecta la Vista con el Service. Ni el login ni el
 * registro le hablan directo a la base de datos.
 */
public class LoginController {

    private AutenticacionService autenticacionService;

    // Constructor
    public LoginController() {
        AutenticacionRepository autenticacionRepository = new AutenticacionRepository();
        this.autenticacionService = new AutenticacionService(autenticacionRepository);
    }

    // Puede lanzar ServiceException si las credenciales son invalidas
    public Usuario autenticar(String email, String contrasena) {
        return autenticacionService.login(email, contrasena);
    }

    // Usado por el boton "Crear cuenta"
    public boolean registrar(Usuario usuario) {
        return autenticacionService.crearUsuario(usuario);
    }
}
