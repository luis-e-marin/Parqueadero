package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;
    @FXML private Label lblMensaje;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    @FXML
    public void login() {
        String user = txtUsuario.getText().trim();
        String pass = txtClave.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            lblMensaje.setText("Ingrese usuario y contraseña");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Cuenta cuenta = parqueadero.login(user, pass);

            System.out.println("Login exitoso: " + cuenta.getUsername() + " (" + cuenta.getRol() + ")");

            Stage stage = (Stage) txtUsuario.getScene().getWindow();

            if (cuenta.esAdministrador()) {
                Navegador.irA("/view/AdminView.fxml", stage);
            } else if (cuenta.esOperador()) {
                Navegador.irA("/view/OperadorView.fxml", stage);
            }

        } catch (Exception e) {
            lblMensaje.setText("Credenciales incorrectas");
            lblMensaje.setStyle("-fx-text-fill: red;");
            System.err.println("Intento de login fallido: " + user);
        }
    }
}