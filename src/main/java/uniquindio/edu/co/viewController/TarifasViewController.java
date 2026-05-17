package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uniquindio.edu.co.controller.TarifasController;
import uniquindio.edu.co.model.Cuenta;

public class TarifasViewController {

    @FXML private TextField txtCarro;
    @FXML private TextField txtMoto;
    @FXML private TextField txtBicicleta;
    @FXML private TextArea areaResultado;

    private final TarifasController tarifasController = new TarifasController();

    public void setCuentaActual(Cuenta cuenta) {
        tarifasController.setCuentaActual(cuenta);
    }

    @FXML
    public void initialize() {
        txtCarro.setText(String.valueOf(5000));
        txtMoto.setText(String.valueOf(2500));
        txtBicicleta.setText(String.valueOf(1000));
    }

    @FXML public void guardarTarifas() { tarifasController.guardarTarifas(txtCarro, txtMoto, txtBicicleta, areaResultado); }
    @FXML public void volverAlAdmin() { tarifasController.volverAlAdmin(txtCarro); }
}