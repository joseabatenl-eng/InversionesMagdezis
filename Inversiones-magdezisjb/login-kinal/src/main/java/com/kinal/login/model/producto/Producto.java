package main.java.com.kinal.login.model.producto;

/**
 * Modelo de datos que representa un registro de la tabla "productos".
 * Encapsulamiento estricto: atributos privados, acceso solo por getters/setters.
 * Usa propiedades de tipo simple (no JavaFX Property) porque el TableView
 * de la vista se llena a partir de PropertyValueFactory por nombre de atributo.
 */
public class Producto {

    private String idProducto;
    private String nombreProducto;
    private int stock;
    private double precio;

    public Producto() {
    }

    // Constructor
    public Producto(String idProducto, String nombreProducto, int stock, double precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.stock = stock;
        this.precio = precio;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
