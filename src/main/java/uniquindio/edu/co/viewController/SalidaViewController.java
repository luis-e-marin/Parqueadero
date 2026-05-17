package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uniquindio.edu.co.controller.SalidaController;
import uniquindio.edu.co.model.Cuenta;

public class SalidaViewController {

    @FXML private TextField txtPlacaSalida;
    @FXML private TextArea areaResultado;

    private final SalidaController salidaController = new SalidaController();

    public void setCuentaActual(Cuenta cuenta) {
        salidaController.setCuentaActual(cuenta);
    }

    @FXML public void registrarSalida() { salidaController.registrarSalida(txtPlacaSalida, areaResultado); }
    @FXML public void volverAlMenu() { salidaController.volverAlMenu(txtPlacaSalida); }
}