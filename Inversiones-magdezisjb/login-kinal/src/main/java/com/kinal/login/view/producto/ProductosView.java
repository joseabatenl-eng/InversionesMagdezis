package main.java.com.kinal.login.view.producto;

import main.java.com.kinal.login.controller.producto.ProductoController;
import main.java.com.kinal.login.model.producto.Producto;
import main.java.com.kinal.login.util.VentanaManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Ventana que muestra en un TableView los productos registrados en la
 * base de datos MySQL (tabla "productos"). Mantiene el mismo estilo
 * "sin bordes" que el resto de la aplicacion (Stage transparente +
 * arrastre manual + boton de cerrar hecho a mano).
 */
public class ProductosView {

    private Stage stage;
    private ProductoController productoController;
    private TableView<Producto> tablaProductos = new TableView<>();

    // Constructor
    public ProductosView() {
        this.stage = new Stage();
        this.productoController = new ProductoController();
        this.stage.initStyle(StageStyle.TRANSPARENT);
        construirVentana();
    }

    private void construirVentana() {
        // ----- Barra superior (titulo + boton cerrar), tambien sirve para arrastrar -----
        Label labelTituloBarra = new Label("PRODUCTOS");
        labelTituloBarra.getStyleClass().add("titulo-barra");

        Button btnCerrar = new Button("✕");
        btnCerrar.getStyleClass().add("btn-cerrar");
        btnCerrar.setOnAction(e -> stage.close());

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        HBox barraSuperior = new HBox(labelTituloBarra, espacio, btnCerrar);
        barraSuperior.getStyleClass().add("barra-superior");
        barraSuperior.setAlignment(Pos.CENTER_LEFT);

        VentanaManager.habilitarArrastre(stage, barraSuperior);

        Label labelTitulo = new Label("Productos en inventario");
        labelTitulo.getStyleClass().add("label-titulo");

        // ----- Columnas de la tabla, mapeadas a los atributos de Producto -----
        TableColumn<Producto, String> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));

        TableColumn<Producto, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));

        TableColumn<Producto, Integer> columnaStock = new TableColumn<>("Stock");
        columnaStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Producto, Double> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        columnaId.setPrefWidth(140);
        columnaNombre.setPrefWidth(220);
        columnaStock.setPrefWidth(90);
        columnaPrecio.setPrefWidth(100);

        tablaProductos.getColumns().addAll(columnaId, columnaNombre, columnaStock, columnaPrecio);
        tablaProductos.getStyleClass().add("tabla-productos");
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarProductos();

        VBox card = new VBox(14, barraSuperior, labelTitulo, tablaProductos);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(14, 24, 24, 24));
        VBox.setVgrow(tablaProductos, Priority.ALWAYS);

        Scene scene = new Scene(card, 620, 460);
        scene.setFill(Color.TRANSPARENT);
        VentanaManager.aplicarEstilos(scene);

        stage.setScene(scene);
    }

    // Consulta el controller y llena la tabla; si no hay datos, la tabla queda vacia
    private void cargarProductos() {
        List<Producto> productos = productoController.obtenerProductos();
        ObservableList<Producto> datos = FXCollections.observableArrayList(productos);
        tablaProductos.setItems(datos);
    }

    public void mostrar() {
        stage.show();
    }
}
