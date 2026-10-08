package main.java.com.kinal.login.repository.autenticacion;

import main.java.com.kinal.login.config.MySQLConnection;
import main.java.com.kinal.login.model.usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Acceso a datos: aquí y solo aquí se habla SQL. El Service nunca
 * abre una conexión directamente.
 */
public class AutenticacionRepository {

    // Busca un usuario completo por su correo (se usa para el login)
    public Usuario findUsuarioByEmail(String email) {
        String consultaSeleccionar = "select id_usuario, nombre, apellido, email, contrasena_hash, id_rol "
                + "from usuarios where email = ?";

        try (Connection conexion = MySQLConnection.getConnection();
             PreparedStatement declaracion = conexion.prepareStatement(consultaSeleccionar)) {

            declaracion.setString(1, email);

            try (ResultSet resultado = declaracion.executeQuery()) {
                if (resultado.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getString("id_usuario"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellido(resultado.getString("apellido"));
                    usuario.setEmail(resultado.getString("email"));
                    usuario.setContrasenaHash(resultado.getString("contrasena_hash"));
                    usuario.setIdRol(resultado.getInt("id_rol"));
                    return usuario;
                }
            }
        } catch (SQLException excepcionSql) {
            System.out.println("Error al consultar la base de datos: " + excepcionSql.getMessage());
        }

        return null;
    }

    // Inserta un nuevo usuario (usado por el boton "Crear cuenta")
    public boolean save(Usuario usuario) {
        String consultaInsertar = "insert into usuarios values(?, ?, ?, ?, ?, ?);";

        try (Connection conexion = MySQLConnection.getConnection();
             PreparedStatement declaracion = conexion.prepareStatement(consultaInsertar)) {

            declaracion.setString(1, usuario.getIdUsuario());
            declaracion.setString(2, usuario.getNombre());
            declaracion.setString(3, usuario.getApellido());
            declaracion.setString(4, usuario.getEmail());
            declaracion.setString(5, usuario.getContrasenaHash());
            declaracion.setInt(6, usuario.getIdRol());

            return declaracion.executeUpdate() > 0;

        } catch (SQLException excepcionSql) {
            System.out.println("Error al registrar usuario: " + excepcionSql.getMessage());
            return false;
        }
    }
}
