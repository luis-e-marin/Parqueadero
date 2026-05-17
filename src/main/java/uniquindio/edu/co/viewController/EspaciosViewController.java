package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uniquindio.edu.co.controller.EspaciosController;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Espacio;
import uniquindio.edu.co.model.Vehiculo;

public class EspaciosViewController {

    @FXML private TableView<Espacio> tablaEspacios;
    @FXML private TableColumn<Espacio, String> colCodigo;
    @FXML private TableColumn<Espacio, String> colTipo;
    @FXML private TableColumn<Espacio, String> colEstado;
    @FXML private TableColumn<Espacio, String> colVehiculo;
    @FXML private TextField txtCodigo;
    @FXML private Button btnVolver;

    private final EspaciosController espaciosController = new EspaciosController();

    public void setCuentaActual(Cuenta cuenta) {
        espaciosController.setCuentaActual(cuenta);
    }

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo().toString()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado().toString()));
        colVehiculo.setCellValueFactory(data -> {
            Vehiculo vehiculo = data.getValue().getVehiculoAsignado();
            return new javafx.beans.property.SimpleStringProperty(vehiculo != null ? vehiculo.getPlaca() : "Libre");
        });
        espaciosController.cargarTabla(tablaEspacios);
    }

    @FXML
    public void deshabilitarEspacio() {
        espaciosController.deshabilitarEspacio(txtCodigo, tablaEspacios);
    }

    @FXML
    public void habilitarEspacio() {
        espaciosController.habilitarEspacio(txtCodigo, tablaEspacios);
    }

    @FXML
    public void volverAlMenu() {
        espaciosController.volverAlMenu(btnVolver);
    }
}