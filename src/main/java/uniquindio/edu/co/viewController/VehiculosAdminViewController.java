package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uniquindio.edu.co.controller.VehiculoAdminController;
import uniquindio.edu.co.model.Cuenta;

public class VehiculosAdminViewController {

    @FXML private TextField txtPlacaBuscar;
    @FXML private TextArea areaResultado;

    private final VehiculoAdminController vehiculoAdminController = new VehiculoAdminController();

    public void setCuentaActual(Cuenta cuenta) {
        vehiculoAdminController.setCuentaActual(cuenta);
    }

    @FXML public void buscarVehiculo() { vehiculoAdminController.buscarVehiculo(txtPlacaBuscar, areaResultado); }
    @FXML public void verTodosVehiculos() { vehiculoAdminController.verTodosVehiculos(areaResultado); }
    @FXML public void eliminarVehiculo() { vehiculoAdminController.eliminarVehiculo(txtPlacaBuscar, areaResultado); }
    @FXML public void volverAlAdmin() { vehiculoAdminController.volverAlAdmin(txtPlacaBuscar); }
}