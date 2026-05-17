package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import uniquindio.edu.co.controller.IngresoController;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;

public class IngresoViewController {

    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipoVehiculo;
    @FXML private TextField txtNombreConductor;
    @FXML private TextField txtIdConductor;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;
    @FXML private TextArea areaResultado;
    @FXML private VBox root;

    private final IngresoController ingresoController = new IngresoController();

    public void setCuentaActual(Cuenta cuenta) {
        ingresoController.setCuentaActual(cuenta);
    }

    @FXML
    public void initialize() {
        if (comboTipoVehiculo != null) comboTipoVehiculo.getItems().addAll(TipoVehiculo.values());
        if (comboTipoUsuario != null) comboTipoUsuario.getItems().addAll(TipoUsuario.values());
    }

    @FXML
    public void registrarIngreso() {
        ingresoController.registrarIngreso(txtPlaca, txtNombreConductor, txtIdConductor,
                comboTipoVehiculo, comboTipoUsuario, areaResultado);
    }

    @FXML
    public void volverAlMenu() {
        ingresoController.volverAlMenu(root);
    }
}