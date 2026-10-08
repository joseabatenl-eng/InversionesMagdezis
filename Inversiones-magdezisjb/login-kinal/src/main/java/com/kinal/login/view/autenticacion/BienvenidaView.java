package main.java.com.kinal.login.view.autenticacion;

import main.java.com.kinal.login.model.usuario.Usuario;
import main.java.com.kinal.login.util.VentanaManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Ventana pequeña que se muestra al iniciar sesión correctamente.
 * Mantiene el mismo estilo "sin bordes" que el Login (Stage transparente
 * + arrastre manual + boton de cerrar hecho a mano).
 */
public class BienvenidaView {

    private Stage stage;

    // Constructor: recibe el usuario autenticado para personalizar el saludo
    public BienvenidaView(Usuario usuario) {
        this.stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        construirVentana(usuario);
    }

    private void construirVentana(Usuario usuario) {
        // Circulo decorativo con inicial del nombre (icono/logo simple)
        Circle circuloLogo = new Circle(26);
        circuloLogo.getStyleClass().add("logo-circulo");
        Label iniciales = new Label(usuario.getNombreCompleto().substring(0, 1).toUpperCase());
        iniciales.getStyleClass().add("logo-texto");
        StackPane logo = new StackPane(circuloLogo, iniciales);

        Label labelBienvenida = new Label("¡Bienvenido, " + usuario.getNombreCompleto() + "!");
        labelBienvenida.getStyleClass().add("label-bienvenida");

        Label labelRol = new Label("Rol: " + usuario.getRolTexto());
        labelRol.getStyleClass().add("label-rol");

        // Boton de cerrar hecho a mano (X)
        Button btnCerrar = new Button("✕");
        btnCerrar.getStyleClass().add("btn-cerrar");
        btnCerrar.setOnAction(e -> stage.close());

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        HBox barraSuperior = new HBox(espacio, btnCerrar);
        barraSuperior.getStyleClass().add("barra-superior");

        VBox card = new VBox(14, barraSuperior, logo, labelBienvenida, labelRol);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10, 24, 30, 24));
        card.setMaxWidth(300);

        StackPane root = new StackPane(card);
        root.getStyleClass().add("root-transparente");
        root.setPadding(new Insets(20));

        // La ventana se puede arrastrar desde la barra superior
        VentanaManager.habilitarArrastre(stage, barraSuperior);

        Scene scene = new Scene(root, 340, 260);
        scene.setFill(Color.TRANSPARENT);
        VentanaManager.aplicarEstilos(scene);

        stage.setScene(scene);
    }

    public void mostrar() {
        stage.show();
    }
}
