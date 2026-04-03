package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class SalidaController {

    @FXML private TextField txtPlacaSalida;
    @FXML private TextArea areaResultado;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    @FXML
    public void registrarSalida() {
        try {
            String placa = txtPlacaSalida.getText().trim().toUpperCase();
            if (placa.isEmpty()) {
                areaResultado.setText("Ingrese una placa");
                return;
            }

            double valor = parqueadero.registrarSalida(placa);
            areaResultado.setText("Salida registrada correctamente.\nValor a pagar: $" + String.format("%.2f", valor));

            txtPlacaSalida.clear();

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void volverAlMenu() {
        try {
            Stage stage = (Stage) txtPlacaSalida.getScene().getWindow();
            Navegador.irA("/view/OperadorMenu.fxml", stage);
        } catch (Exception e) {
            mostrarMensaje("Error al volver al menú");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}