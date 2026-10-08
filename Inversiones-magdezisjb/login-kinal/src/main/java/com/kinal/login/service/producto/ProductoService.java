package main.java.com.kinal.login.service.producto;

import main.java.com.kinal.login.model.producto.Producto;
import main.java.com.kinal.login.repository.producto.ProductoRepository;
import java.util.List;

/**
 * Contiene la logica relacionada a productos. La Vista nunca habla
 * directo con el Repository, siempre pasa por aqui (mismo criterio
 * que AutenticacionService).
 */
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Constructor
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Devuelve la lista completa de productos para mostrarla en la tabla
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
}
