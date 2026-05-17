package uniquindio.edu.co.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;
import uniquindio.edu.co.viewController.IngresoViewController;
import uniquindio.edu.co.viewController.SalidaViewController;

public class OperadorMenuController {

    private static Cuenta cuentaActualEstatica;
    private final Parqueadero parqueadero = Parqueadero.getInstance();

    public void setCuentaActual(Cuenta cuenta) {
        cuentaActualEstatica = cuenta;
    }

    public void irRegistrarIngreso(ActionEvent event) {
        if (cuentaActualEstatica == null) { mostrarMensaje("Sesión no válida"); return; }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/IngresoView.fxml"));
            Parent vista = loader.load();
            IngresoViewController controller = loader.getController();
            controller.setCuentaActual(cuentaActualEstatica);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(vista));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al abrir ingreso");
        }
    }

    public void irRegistrarSalida(ActionEvent event) {
        if (cuentaActualEstatica == null) { mostrarMensaje("Sesión no válida"); return; }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SalidaView.fxml"));
            Parent vista = loader.load();
            SalidaViewController controller = loader.getController();
            controller.setCuentaActual(cuentaActualEstatica);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(vista));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al abrir salida");
        }
    }

    public void verVehiculosDentro() { mostrarMensaje(parqueadero.getVehiculosDentroResumen()); }
    public void verEspaciosDisponibles() { mostrarMensaje(parqueadero.getEspaciosDisponiblesResumen()); }
    public void verReportes() { mostrarMensaje(parqueadero.generarReporteSimple()); }

    public void cerrarSesion(ActionEvent event) {
        cuentaActualEstatica = null;
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Navegador.irA("/view/LoginView.fxml", stage);
        } catch (Exception e) {
            mostrarMensaje("Error al cerrar sesión");
        }
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}