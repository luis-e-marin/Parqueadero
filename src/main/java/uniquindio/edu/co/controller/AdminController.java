package uniquindio.edu.co.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.utils.Navegador;
import uniquindio.edu.co.viewController.EspaciosViewController;
import uniquindio.edu.co.viewController.TarifasViewController;
import uniquindio.edu.co.viewController.UsuarioViewController;
import uniquindio.edu.co.viewController.vehiculosViewController;

public class AdminController {

    private static Cuenta cuentaActualEstatica;

    public void setCuentaActual(Cuenta cuenta) {
        cuentaActualEstatica = cuenta;
    }

    public void irGestionUsuarios(Button btn) { cambiarEscena("/view/UsuarioView.fxml", btn); }
    public void irGestionTarifas(Button btn) { cambiarEscena("/view/TarifasView.fxml", btn); }
    public void irGestionEspacios(Button btn) { cambiarEscena("/view/EspaciosView.fxml", btn); }
    public void irGestionVehiculos(Button btn) { cambiarEscena("/view/VehiculosView.fxml", btn); }
    public void irReportes(Button btn) { cambiarEscena("/view/ReportesView.fxml", btn); }

    public void cerrarSesion(Button btn) {
        cuentaActualEstatica = null;
        try {
            Stage stage = (Stage) btn.getScene().getWindow();
            Navegador.irA("/view/LoginView.fxml", stage);
        } catch (Exception exception) {
            mostrarMensaje("Error al cerrar sesión");
        }
    }

    private void cambiarEscena(String fxmlPath, Button btn) {
        if (cuentaActualEstatica == null) {
            mostrarMensaje("Error: No hay sesión activa");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Object controller = loader.getController();

            if (controller instanceof TarifasViewController tc) tc.setCuentaActual(cuentaActualEstatica);
            else if (controller instanceof UsuarioViewController uc) uc.setCuentaActual(cuentaActualEstatica);
            else if (controller instanceof EspaciosViewController ec) ec.setCuentaActual(cuentaActualEstatica);
            else if (controller instanceof vehiculosViewController vc) vc.setCuentaActual(cuentaActualEstatica);

            Stage stage = (Stage) btn.getScene().getWindow();
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