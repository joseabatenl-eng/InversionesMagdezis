package main.java.com.kinal.login.util;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class VentanaManager {

    private static final String RUTA_ESTILOS = "/com/kinal/login/styles/login.css";

    // Constructor privado: esta clase solo expone métodos estáticos
    private VentanaManager() {
    }

    public static void aplicarEstilos(Scene scene) {
        scene.getStylesheets().add(VentanaManager.class.getResource(RUTA_ESTILOS).toExternalForm());
    }

    // Mueve el Stage siguiendo al mouse mientras se arrastra el nodo "barra"
    public static void habilitarArrastre(Stage stage, Region barra) {
        double[] offset = new double[2];

        barra.setOnMousePressed(e -> {
            offset[0] = e.getSceneX();
            offset[1] = e.getSceneY();
        });
        barra.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - offset[0]);
            stage.setY(e.getScreenY() - offset[1]);
        });
    }
}
