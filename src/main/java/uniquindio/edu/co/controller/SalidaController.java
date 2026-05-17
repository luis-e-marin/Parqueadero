package uniquindio.edu.co.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class SalidaController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    public void registrarSalida(TextField txtPlacaSalida, TextArea areaResultado) {
        if (cuentaActual == null) { areaResultado.setText("Error: No hay sesión activa (debe ser Operador o Admin)"); return; }
        try {
            String placa = txtPlacaSalida.getText().trim().toUpperCase();
            if (placa.isEmpty()) { areaResultado.setText("Ingrese una placa"); return; }
            double valor = parqueadero.registrarSalida(placa);
            areaResultado.setText("Salida registrada correctamente\nTotal a pagar: " + String.format("%.0f", valor));
            txtPlacaSalida.clear();
        } catch (Exception exception) {
            areaResultado.setText("Error: " + exception.getMessage());
        }
    }

    public void volverAlMenu(TextField txtPlacaSalida) {
        try {
            Stage stage = (Stage) txtPlacaSalida.getScene().getWindow();
            Navegador.volverAlOperador(stage);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo volver al menú del operador").showAndWait();
        }
    }
}