package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Espacio;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Vehiculo;
import uniquindio.edu.co.utils.Navegador;

public class EspaciosController {

    @FXML private TableView<Espacio> tablaEspacios;
    @FXML private TableColumn<Espacio, String> colCodigo;
    @FXML private TableColumn<Espacio, String> colTipo;
    @FXML private TableColumn<Espacio, String> colEstado;
    @FXML private TableColumn<Espacio, String> colVehiculo;
    @FXML private TextField txtCodigo;
    @FXML private Button btnVolver;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo().toString()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado().toString()));

        colVehiculo.setCellValueFactory(data -> {
            Vehiculo v = data.getValue().getVehiculoAsignado();
            return new javafx.beans.property.SimpleStringProperty(v != null ? v.getPlaca() : "Libre");
        });

        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaEspacios.getItems().setAll(parqueadero.getEspacios());
    }

    @FXML
    private void deshabilitarEspacio() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarMensaje("Ingrese el código del espacio");
            return;
        }

        if (parqueadero.deshabilitarEspacio(codigo)) {
            actualizarTabla();
            mostrarMensaje("Espacio deshabilitado correctamente");
        } else {
            mostrarMensaje("No se pudo deshabilitar el espacio (puede estar ocupado o no existe)");
        }
    }

    @FXML
    private void habilitarEspacio() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarMensaje("Ingrese el código del espacio");
            return;
        }

        try {
            parqueadero.habilitarEspacio(codigo);
            actualizarTabla();
            mostrarMensaje("Espacio habilitado correctamente");
        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    @FXML
    public void volverAlMenu() {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();   // usa btnVolver que tienes en FXML
            Navegador.irA("/view/AdminView.fxml", stage);             // o /view/OperadorMenu.fxml si es para operador
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al menú");
            alert.showAndWait();
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}