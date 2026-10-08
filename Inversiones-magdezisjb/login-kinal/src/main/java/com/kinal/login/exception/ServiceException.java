package main.java.com.kinal.login.exception;

/**
 * Excepción usada para comunicar errores de validación o de negocio
 * (por ejemplo: correo no registrado, contraseña incorrecta).
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
