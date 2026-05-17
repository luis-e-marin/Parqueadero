package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uniquindio.edu.co.controller.VehiculoController;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;

public class vehiculosViewController {

    @FXML private TextField txtIdUsuario;
    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipo;
    @FXML private TextArea areaResultado;

    private final VehiculoController vehiculoController = new VehiculoController();

    public void setCuentaActual(Cuenta cuenta) {
        vehiculoController.setCuentaActual(cuenta);
    }

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll(TipoVehiculo.values());
    }

    @FXML public void agregarVehiculo() { vehiculoController.agregarVehiculo(txtIdUsuario, txtPlaca, comboTipo, areaResultado); }
    @FXML public void buscarPorPlaca() { vehiculoController.buscarPorPlaca(txtPlaca, areaResultado); }
    @FXML public void eliminarVehiculo() { vehiculoController.eliminarVehiculo(txtIdUsuario, txtPlaca, areaResultado); }
    @FXML public void volverAlAdmin() { vehiculoController.volverAlAdmin(areaResultado); }
}