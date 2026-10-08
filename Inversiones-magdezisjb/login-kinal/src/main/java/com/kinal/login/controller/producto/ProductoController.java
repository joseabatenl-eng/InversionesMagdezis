package main.java.com.kinal.login.controller.producto;

import main.java.com.kinal.login.model.producto.Producto;
import main.java.com.kinal.login.repository.producto.ProductoRepository;
import main.java.com.kinal.login.service.producto.ProductoService;
import java.util.List;

/**
 * Controlador: conecta la Vista con el Service (mismo criterio que
 * LoginController).
 */
public class ProductoController {

    private final ProductoService productoService;

    // Constructor
    public ProductoController() {
        ProductoRepository productoRepository = new ProductoRepository();
        this.productoService = new ProductoService(productoRepository);
    }

    public List<Producto> obtenerProductos() {
        return productoService.listarProductos();
    }
}
