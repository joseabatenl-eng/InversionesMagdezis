package main.java.com.kinal.login.repository.producto;

import main.java.com.kinal.login.config.MySQLConnection;
import main.java.com.kinal.login.model.producto.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos de la tabla "productos". Aquí y solo aquí se habla SQL
 * (mismo criterio que AutenticacionRepository).
 */
public class ProductoRepository {

    // Trae todos los productos registrados en la base de datos
    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<>();
        String consultaSeleccionar = "select id_producto, nombre_producto, stock, precio from productos";

        try (Connection conexion = MySQLConnection.getConnection();
             PreparedStatement declaracion = conexion.prepareStatement(consultaSeleccionar);
             ResultSet resultado = declaracion.executeQuery()) {

            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(resultado.getString("id_producto"));
                producto.setNombreProducto(resultado.getString("nombre_producto"));
                producto.setStock(resultado.getInt("stock"));
                producto.setPrecio(resultado.getDouble("precio"));
                productos.add(producto);
            }

        } catch (SQLException excepcionSql) {
            System.out.println("Error al consultar los productos: " + excepcionSql.getMessage());
        }

        return productos;
    }
}
