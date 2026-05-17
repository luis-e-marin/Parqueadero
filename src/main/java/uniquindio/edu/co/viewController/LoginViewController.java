package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uniquindio.edu.co.controller.LoginController;

public class LoginViewController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;
    @FXML private Label lblMensaje;

    private final LoginController loginController = new LoginController();

    @FXML
    public void login() {
        loginController.login(txtUsuario, txtClave, lblMensaje);
    }
}
