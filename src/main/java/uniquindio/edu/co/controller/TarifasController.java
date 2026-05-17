package uniquindio.edu.co.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class TarifasController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    public void guardarTarifas(TextField txtCarro, TextField txtMoto, TextField txtBicicleta, TextArea areaResultado) {
        if (cuentaActual == null) { areaResultado.setText("Error: No hay sesión activa"); return; }
        try {
            double valorCarro = Double.parseDouble(txtCarro.getText().trim());
            double valorMoto = Double.parseDouble(txtMoto.getText().trim());
            double valorBicicleta = Double.parseDouble(txtBicicleta.getText().trim());
            parqueadero.actualizarTarifa(TipoVehiculo.CARRO, valorCarro);
            parqueadero.actualizarTarifa(TipoVehiculo.MOTO, valorMoto);
            parqueadero.actualizarTarifa(TipoVehiculo.BICICLETA, valorBicicleta);
            areaResultado.setText(" Tarifas actualizadas correctamente:\nCarro: " + valorCarro +
                    "/hora\nMoto: " + valorMoto + "/hora\nBicicleta: " + valorBicicleta + "/hora");
        } catch (NumberFormatException e) {
            areaResultado.setText("Error: Ingrese solo números válidos");
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    public void volverAlAdmin(TextField txtCarro) {
        Stage stage = (Stage) txtCarro.getScene().getWindow();
        Navegador.volverAlAdmin(stage);
    }
}