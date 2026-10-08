package main.java.com.kinal.login;

import main.java.com.kinal.login.view.autenticacion.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicacion.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView(primaryStage);
        loginView.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
