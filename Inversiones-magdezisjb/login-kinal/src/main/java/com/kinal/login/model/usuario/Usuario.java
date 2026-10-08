package main.java.com.kinal.login.model.usuario;

/**
 * Modelo de datos que representa un registro de la tabla "usuarios"
 * (misma tabla que usa el proyecto Abarrotería Kinal).
 * Encapsulamiento estricto: atributos privados, acceso solo por getters/setters.
 */
public class Usuario {

    private String idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenaHash;
    private int idRol; // 1 = Admin, 2 = User (mismo criterio de la base de datos)

    public Usuario() {
    }

    // Constructor
    public Usuario(String idUsuario, String nombre, String apellido, String email,
            String contrasenaHash, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenaHash = contrasenaHash;
        this.idRol = idRol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    // Nombre completo, util para el mensaje de bienvenida
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // Traduce el id numerico de rol a un texto legible
    public String getRolTexto() {
        return idRol == 1 ? "Admin" : "User";
    }
}
