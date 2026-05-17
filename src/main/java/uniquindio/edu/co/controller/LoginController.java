package uniquindio.edu.co.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.viewController.AdminViewController;
import uniquindio.edu.co.viewController.OperadorMenuViewController;

public class LoginController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    public void login(TextField txtUsuario, PasswordField txtClave, Label lblMensaje) {
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
                AdminViewController adminCtrl = loader.getController();
                adminCtrl.setCuentaActual(cuenta);
                stage.setScene(new Scene(root));
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OperadorMenu.fxml"));
                Parent root = loader.load();
                OperadorMenuViewController operadorCtrl = loader.getController();
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
