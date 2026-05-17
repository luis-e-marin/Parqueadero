package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uniquindio.edu.co.controller.ParqueaderoController;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;

public class ParqueaderoViewController {

    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipo;
    @FXML private TextField txtNombreConductor;
    @FXML private TextField txtIdConductor;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;
    @FXML private TextField txtPlacaSalida;
    @FXML private TextArea areaResultado;
    @FXML private Button btnVolver;

    private final ParqueaderoController parqueaderoController = new ParqueaderoController();

    public void setCuentaActual(Cuenta cuenta) {
        parqueaderoController.setCuentaActual(cuenta);
    }

    @FXML
    public void initialize() {
        if (comboTipo != null) comboTipo.getItems().addAll(TipoVehiculo.values());
        if (comboTipoUsuario != null) comboTipoUsuario.getItems().addAll(TipoUsuario.values());
    }

    @FXML
    public void registrarIngreso() {
        parqueaderoController.registrarIngreso(txtPlaca, txtNombreConductor, txtIdConductor,
                comboTipo, comboTipoUsuario, areaResultado);
    }

    @FXML
    public void registrarSalida() {
        parqueaderoController.registrarSalida(txtPlacaSalida, areaResultado);
    }

    @FXML
    public void volverAlAdmin() {
        parqueaderoController.volverAlAdmin(btnVolver);
    }
}