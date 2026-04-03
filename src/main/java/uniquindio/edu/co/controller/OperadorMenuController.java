package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class OperadorMenuController {

    @FXML private Node root;

    private Cuenta cuentaActual;
    private final Parqueadero parqueadero = Parqueadero.getInstance();

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    @FXML
    public void irRegistrarIngreso() {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Navegador.irA("/view/IngresoView.fxml", stage);   // o ParqueaderoView si usas esa
        } catch (Exception e) {
            mostrarMensaje("Error al abrir registro de ingreso");
        }
    }

    @FXML
    public void irRegistrarSalida() {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Navegador.irA("/view/SalidaView.fxml", stage);
        } catch (Exception e) {
            mostrarMensaje("Error al abrir registro de salida");
        }
    }

    @FXML
    public void verVehiculosDentro() {
        String resumen = parqueadero.getVehiculosDentroResumen();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vehículos Dentro");
        alert.setHeaderText(null);
        alert.setContentText(resumen);
        alert.showAndWait();
    }

    @FXML
    public void verEspaciosDisponibles() {
        String resumen = parqueadero.getEspaciosDisponiblesResumen();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Espacios Disponibles");
        alert.setHeaderText(null);
        alert.setContentText(resumen);
        alert.showAndWait();
    }

    @FXML
    public void verReportes() {
        String reporte = parqueadero.generarReporteSimple();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reportes");
        alert.setHeaderText(null);
        alert.setContentText(reporte);
        alert.showAndWait();
    }

    @FXML
    public void cerrarSesion() {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Navegador.irA("/view/LoginView.fxml", stage);
        } catch (Exception e) {
            mostrarMensaje("Error al cerrar sesión");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}