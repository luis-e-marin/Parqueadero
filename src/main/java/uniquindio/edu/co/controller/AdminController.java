package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.utils.Navegador;

public class AdminController {

    private static Cuenta cuentaActualEstatica;

    @FXML private Button btnCerrarSesion;

    public void setCuentaActual(Cuenta cuenta) {
        cuentaActualEstatica = cuenta;
    }

    @FXML
    public void irGestionUsuarios() {
        cambiarEscena("/view/UsuarioView.fxml");
    }

    @FXML
    public void irGestionTarifas() {
        cambiarEscena("/view/TarifasView.fxml");
    }

    @FXML
    public void irGestionEspacios() {
        cambiarEscena("/view/EspaciosView.fxml");
    }

    @FXML
    public void irGestionVehiculos() {
        cambiarEscena("/view/VehiculosView.fxml");
    }

    @FXML
    public void irReportes() {
        cambiarEscena("/view/ReportesView.fxml");
    }

    @FXML
    public void cerrarSesion() {
        cuentaActualEstatica = null;
        try {
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            Navegador.irA("/view/LoginView.fxml", stage);
        } catch (Exception exception) {
            mostrarMensaje("Error al cerrar sesión");
        }
    }

    private void cambiarEscena(String fxmlPath) {
        if (cuentaActualEstatica == null) {
            mostrarMensaje("Error: No hay sesión activa");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof TarifasController tc) tc.setCuentaActual(cuentaActualEstatica);
            else if (controller instanceof UsuarioController uc) uc.setCuentaActual(cuentaActualEstatica);
            else if (controller instanceof EspaciosController ec) ec.setCuentaActual(cuentaActualEstatica);
            else if (controller instanceof VehiculoController vc) vc.setCuentaActual(cuentaActualEstatica);

            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception exception) {
            exception.printStackTrace();
            mostrarMensaje("Error al abrir: " + fxmlPath);
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}