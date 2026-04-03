package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        String user = txtUsuario.getText().trim().toLowerCase();
        String pass = txtClave.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            lblMensaje.setText("Ingrese usuario y contraseña");
            return;
        }

        try {
            Cuenta cuenta = parqueadero.login(user, pass);

            Stage stage = (Stage) txtUsuario.getScene().getWindow();

            if (cuenta.esAdministrador()) {
                Navegador.irA("/view/AdminView.fxml", stage);
            } else if (cuenta.esOperador()) {
                // Cargamos el menú del operador y le pasamos la cuenta
                Stage operadorStage = new Stage();
                operadorStage.setTitle("PARKUQ - Menú Operador");
                Navegador.irA("/view/OperadorMenu.fxml", operadorStage);

                // Buscamos el controlador del menú y le pasamos la cuenta
                // (esto se hace en el Navegador o en el controlador del menú)
                // Por ahora cerramos la ventana de login
                stage.close();

            } else {
                lblMensaje.setText("Rol no reconocido");
            }

        } catch (Exception e) {
            lblMensaje.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}