package main.java.com.kinal.login.view.autenticacion;

import main.java.com.kinal.login.controller.autenticacion.LoginController;
import main.java.com.kinal.login.exception.ServiceException;
import main.java.com.kinal.login.model.usuario.Usuario;
import main.java.com.kinal.login.util.VentanaManager;
import main.java.com.kinal.login.view.producto.ProductosView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
 * Ventana de inicio de sesion. No usa FXML: todo el arbol de nodos
 * y los estilos se arman en Java puro. El Stage es TRANSPARENT para
 * romper con la ventana cuadrada estandar de Windows.
 * El login valida las credenciales contra la base de datos MySQL
 * (misma base de datos que usa Abarroteria Kinal).
 */
public class LoginView {

    private Stage stage;
    private LoginController loginController;

    private TextField txtEmail = new TextField();
    private PasswordField txtContrasena = new PasswordField();
    private Label labelError = new Label();
    private Button btnIngresar = new Button("Ingresar");
    private Button btnCrearCuenta = new Button("Crear cuenta");

    // Constructor
    public LoginView(Stage stage) {
        this.stage = stage;
        this.loginController = new LoginController();
        this.stage.initStyle(StageStyle.TRANSPARENT);
        construirVentana();
    }

    private void construirVentana() {
        // ----- Barra superior (titulo + boton cerrar), tambien sirve para arrastrar -----
        Label labelTituloBarra = new Label("JB TECH CORPORATION");
        labelTituloBarra.getStyleClass().add("titulo-barra");

        Button btnCerrar = new Button("✕");
        btnCerrar.getStyleClass().add("btn-cerrar");
        btnCerrar.setOnAction(e -> System.exit(0));

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        HBox barraSuperior = new HBox(labelTituloBarra, espacio, btnCerrar);
        barraSuperior.getStyleClass().add("barra-superior");
        barraSuperior.setAlignment(Pos.CENTER_LEFT);

        VentanaManager.habilitarArrastre(stage, barraSuperior);

        // ----- Logo dibujado con Shape (circulo + inicial), sin depender de un archivo de imagen -----
        Circle circuloLogo = new Circle(30);
        circuloLogo.getStyleClass().add("logo-circulo");
        Label iniciales = new Label("JBC");
        iniciales.getStyleClass().add("logo-texto");
        StackPane logo = new StackPane(circuloLogo, iniciales);

        Label labelTitulo = new Label("Bienvenido de nuevo");
        labelTitulo.getStyleClass().add("label-titulo");
        Label labelSubtitulo = new Label("Ingresa tus credenciales para continuar");
        labelSubtitulo.getStyleClass().add("label-subtitulo");

        VBox encabezado = new VBox(8, logo, labelTitulo, labelSubtitulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setPadding(new Insets(4, 0, 18, 0));

        // ----- Campo correo -----
        Label labelEmail = new Label("Correo electrónico");
        labelEmail.getStyleClass().add("label-campo");
        txtEmail.setPromptText("correo@ejemplo.com");
        txtEmail.getStyleClass().add("campo-texto");

        // ----- Campo contraseña -----
        Label labelContrasena = new Label("Contraseña");
        labelContrasena.getStyleClass().add("label-campo");
        txtContrasena.setPromptText("••••••••");
        txtContrasena.getStyleClass().add("campo-texto");

        // ----- Mensaje de error (oculto por defecto) -----
        labelError.getStyleClass().add("label-error");
        labelError.setWrapText(true);
        labelError.setVisible(false);
        labelError.setManaged(false);

        VBox formulario = new VBox(12,
                labelEmail, txtEmail,
                labelContrasena, txtContrasena,
                labelError);

        // ----- Botones (el efecto hover vive en el CSS) -----
        btnIngresar.getStyleClass().add("boton-primario");
        btnIngresar.setMaxWidth(Double.MAX_VALUE);
        btnIngresar.setOnAction(e -> intentarIniciarSesion());
        txtContrasena.setOnAction(e -> intentarIniciarSesion());

        btnCrearCuenta.getStyleClass().add("boton-secundario");
        btnCrearCuenta.setMaxWidth(Double.MAX_VALUE);
        btnCrearCuenta.setOnAction(e -> abrirRegistro());

        VBox contenedorBotones = new VBox(10, btnIngresar, btnCrearCuenta);

        VBox card = new VBox(18, barraSuperior, encabezado, formulario, contenedorBotones);
        card.getStyleClass().add("card");
        card.setMaxWidth(340);
        card.setPadding(new Insets(14, 30, 30, 30));

        StackPane root = new StackPane(card);
        root.getStyleClass().add("root-transparente");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 420, 560);
        scene.setFill(Color.TRANSPARENT);
        VentanaManager.aplicarEstilos(scene);

        stage.setScene(scene);
    }

    // Valida campos vacios, consulta la base de datos y da feedback visual
    private void intentarIniciarSesion() {
        ocultarError();

        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String contrasena = txtContrasena.getText() == null ? "" : txtContrasena.getText();

        try {
            Usuario usuarioValido = loginController.autenticar(email, contrasena);

            txtContrasena.clear();
            BienvenidaView bienvenida = new BienvenidaView(usuarioValido);
            bienvenida.mostrar();

            ProductosView productosView = new ProductosView();
            productosView.mostrar();

            stage.close();

        } catch (ServiceException excepcionServicio) {
            // Correo/contraseña vacios o credenciales invalidas
            mostrarError(excepcionServicio.getMessage());
        } catch (Exception excepcionInesperada) {
            mostrarError("No se pudo conectar con la base de datos.");
        }
    }

    // Abre la ventana de registro (boton "Crear cuenta")
    private void abrirRegistro() {
        RegistroView registroView = new RegistroView(new Stage(), loginController);
        registroView.mostrar();
    }

    private void mostrarError(String mensaje) {
        labelError.setText(mensaje);
        labelError.setVisible(true);
        labelError.setManaged(true);

        if (!txtEmail.getStyleClass().contains("campo-texto-error")) {
            txtEmail.getStyleClass().add("campo-texto-error");
        }
        if (!txtContrasena.getStyleClass().contains("campo-texto-error")) {
            txtContrasena.getStyleClass().add("campo-texto-error");
        }
    }

    private void ocultarError() {
        labelError.setVisible(false);
        labelError.setManaged(false);
        txtEmail.getStyleClass().remove("campo-texto-error");
        txtContrasena.getStyleClass().remove("campo-texto-error");
    }

    public void mostrar() {
        stage.show();
    }
}
