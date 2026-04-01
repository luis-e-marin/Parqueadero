package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uniquindio.edu.co.utils.Navegador;

public class AdminController {

    @FXML private Button btnUsuarios;
    @FXML private Button btnVehiculos;
    @FXML private Button btnEspacios;
    @FXML private Button btnReportes;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void irGestionUsuarios() {
        Stage stage = (Stage) btnUsuarios.getScene().getWindow();
        Navegador.irA("/view/UsuarioView.fxml", stage);
    }

    @FXML
    public void irGestionVehiculos() {
        Stage stage = (Stage) btnVehiculos.getScene().getWindow();
        Navegador.irA("/view/VehiculoView.fxml", stage);
    }

    @FXML
    public void irGestionEspacios() {
        Stage stage = (Stage) btnEspacios.getScene().getWindow();
        Navegador.irA("/view/EspaciosView.fxml", stage);
    }

    @FXML
    public void irReportes() {
        Stage stage = (Stage) btnReportes.getScene().getWindow();
        Navegador.irA("/view/ReportesView.fxml", stage);
    }

    @FXML
    public void cerrarSesion() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        Navegador.irA("/view/LoginView.fxml", stage);
    }
}