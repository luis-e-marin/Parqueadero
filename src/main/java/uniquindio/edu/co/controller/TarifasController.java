package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class TarifasController {

    @FXML private TextField txtCarro;
    @FXML private TextField txtMoto;
    @FXML private TextField txtBicicleta;
    @FXML private TextArea areaResultado;

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    @FXML
    public void initialize() {
        txtCarro.setText(String.valueOf(5000));
        txtMoto.setText(String.valueOf(2500));
        txtBicicleta.setText(String.valueOf(1000));
    }

    @FXML
    public void guardarTarifas() {
        if (cuentaActual == null) {
            areaResultado.setText("Error: No hay sesión activa");
            return;
        }

        try {
            double valorCarro = Double.parseDouble(txtCarro.getText().trim());
            double valorMoto = Double.parseDouble(txtMoto.getText().trim());
            double valorBicicleta = Double.parseDouble(txtBicicleta.getText().trim());

            parqueadero.actualizarTarifa(TipoVehiculo.CARRO, valorCarro);
            parqueadero.actualizarTarifa(TipoVehiculo.MOTO, valorMoto);
            parqueadero.actualizarTarifa(TipoVehiculo.BICICLETA, valorBicicleta);
            areaResultado.setText("✓ Tarifas actualizadas correctamente:\n" +
                    "Carro: $" + valorCarro + "/hora\n" +
                    "Moto: $" + valorMoto + "/hora\n" +
                    "Bicicleta: $" + valorBicicleta + "/hora");

        } catch (NumberFormatException e) {
            areaResultado.setText("Error: Ingrese solo números válidos");
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void volverAlAdmin() {
        Stage stage = (Stage) txtCarro.getScene().getWindow();
        Navegador.volverAlAdmin(stage);
    }
}
