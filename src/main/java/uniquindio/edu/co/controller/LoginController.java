package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
                Parent root = loader.load();
                AdminController adminCtrl = loader.getController();
                adminCtrl.setCuentaActual(cuenta);   // ← importante
                stage.setScene(new Scene(root));
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OperadorMenu.fxml"));
                Parent root = loader.load();

                OperadorMenuController operadorCtrl = loader.getController();
                operadorCtrl.setCuentaActual(cuenta);

                stage.setScene(new Scene(root));
            }

            stage.show();

        } catch (Exception e) {
            lblMensaje.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
