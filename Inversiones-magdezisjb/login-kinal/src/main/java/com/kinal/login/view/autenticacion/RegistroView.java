package main.java.com.kinal.login.view.autenticacion;

import main.java.com.kinal.login.controller.autenticacion.LoginController;
import main.java.com.kinal.login.model.usuario.Usuario;
import main.java.com.kinal.login.util.VentanaManager;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Ventana de registro de un nuevo usuario. Mismo estilo "sin bordes"
 * que el Login. Al registrar correctamente, guarda el usuario en
 * MySQL (contraseña ya encriptada por el Service) y regresa al Login.
 */
public class RegistroView {

    private Stage stage;
    private LoginController loginController;

    private TextField txtNombre = new TextField();
    private TextField txtApellido = new TextField();
    private TextField txtEmail = new TextField();
    private PasswordField txtContrasena = new PasswordField();
    private ComboBox<String> comboRol = new ComboBox<>();
    private Label labelError = new Label();

    // Constructor: reutiliza el mismo controlador que usa el Login
    public RegistroView(Stage stage, LoginController loginController) {
        this.stage = stage;
        this.loginController = loginController;
        this.stage.initStyle(StageStyle.TRANSPARENT);
        construirVentana();
    }

    private void construirVentana() {
        Label labelTituloBarra = new Label("CREAR CUENTA");
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

        Label labelTitulo = new Label("Registro de usuario");
        labelTitulo.getStyleClass().add("label-titulo");

        Label labelNombre = new Label("Nombre");
        labelNombre.getStyleClass().add("label-campo");
        txtNombre.getStyleClass().add("campo-texto");

        Label labelApellido = new Label("Apellido");
        labelApellido.getStyleClass().add("label-campo");
        txtApellido.getStyleClass().add("campo-texto");

        Label labelEmail = new Label("Correo electrónico");
        labelEmail.getStyleClass().add("label-campo");
        txtEmail.setPromptText("correo@ejemplo.com");
        txtEmail.getStyleClass().add("campo-texto");

        Label labelContrasena = new Label("Contraseña");
        labelContrasena.getStyleClass().add("label-campo");
        txtContrasena.setPromptText("Mínimo 6 caracteres");
        txtContrasena.getStyleClass().add("campo-texto");

        Label labelRol = new Label("Rol");
        labelRol.getStyleClass().add("label-campo");
        comboRol.getItems().addAll("Admin", "User");
        comboRol.setValue("User");
        comboRol.setMaxWidth(Double.MAX_VALUE);

        labelError.getStyleClass().add("label-error");
        labelError.setWrapText(true);
        labelError.setVisible(false);
        labelError.setManaged(false);

        VBox formulario = new VBox(10,
                labelNombre, txtNombre,
                labelApellido, txtApellido,
                labelEmail, txtEmail,
                labelContrasena, txtContrasena,
                labelRol, comboRol,
                labelError);

        Button btnRegistrarse = new Button("Registrarme");
        btnRegistrarse.getStyleClass().add("boton-primario");
        btnRegistrarse.setMaxWidth(Double.MAX_VALUE);
        btnRegistrarse.setOnAction(e -> intentarRegistrar());

        VBox card = new VBox(16, barraSuperior, labelTitulo, formulario, btnRegistrarse);
        card.getStyleClass().add("card");
        card.setMaxWidth(340);
        card.setPadding(new Insets(14, 30, 30, 30));

        StackPane root = new StackPane(card);
        root.getStyleClass().add("root-transparente");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 420, 640);
        scene.setFill(Color.TRANSPARENT);
        VentanaManager.aplicarEstilos(scene);

        stage.setScene(scene);
    }

    // Valida los campos, arma el Usuario y lo manda a guardar via el Controller
    private void intentarRegistrar() {
        ocultarError();

        String nombre = txtNombre.getText() == null ? "" : txtNombre.getText().trim();
        String apellido = txtApellido.getText() == null ? "" : txtApellido.getText().trim();
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String contrasena = txtContrasena.getText() == null ? "" : txtContrasena.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return;
        }
        if (contrasena.length() < 6) {
            mostrarError("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        int idRol = "Admin".equals(comboRol.getValue()) ? 1 : 2;

        Usuario nuevoUsuario = new Usuario(
                UUID.randomUUID().toString(),
                nombre, apellido, email, contrasena, idRol);

        try {
            boolean registrado = loginController.registrar(nuevoUsuario);
            if (registrado) {
                stage.close();
            } else {
                mostrarError("No se pudo completar el registro. Intenta nuevamente.");
            }
        } catch (Exception ex) {
            mostrarError("No se pudo conectar con la base de datos.");
        }
    }

    private void mostrarError(String mensaje) {
        labelError.setText(mensaje);
        labelError.setVisible(true);
        labelError.setManaged(true);
    }

    private void ocultarError() {
        labelError.setVisible(false);
        labelError.setManaged(false);
    }

    public void mostrar() {
        stage.show();
    }
}
