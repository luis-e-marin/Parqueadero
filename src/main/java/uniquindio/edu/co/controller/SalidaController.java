package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class SalidaController {

    @FXML private TextField txtPlacaSalida;
    @FXML private TextArea areaResultado;

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    @FXML
    public void registrarSalida() {
        if (cuentaActual == null) {
            areaResultado.setText("Error: No hay sesión activa (debe ser Operador o Admin)");
            return;
        }

        try {
            String placa = txtPlacaSalida.getText().trim().toUpperCase();
            if (placa.isEmpty()) {
                areaResultado.setText("Ingrese una placa");
                return;
            }

            double valor = parqueadero.registrarSalida(placa);

            areaResultado.setText("✓ Salida registrada correctamente\nTotal a pagar: $" + String.format("%.0f", valor));

            txtPlacaSalida.clear();

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void volverAlMenu() {
        try {
            Stage stage = (Stage) txtPlacaSalida.getScene().getWindow();
            Navegador.volverAlOperador(stage);   // o volverAlMenuOperador si usas otro nombre
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al menú del operador");
            alert.showAndWait();
        }
    }
}