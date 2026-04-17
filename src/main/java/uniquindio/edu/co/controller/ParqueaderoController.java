package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class ParqueaderoController {

    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipo;
    @FXML private TextField txtNombreConductor;
    @FXML private TextField txtIdConductor;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;
    @FXML private TextField txtPlacaSalida;
    @FXML private TextArea areaResultado;
    @FXML private Button btnVolver;

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }
    @FXML
    public void initialize() {
        if (comboTipo != null) comboTipo.getItems().addAll(TipoVehiculo.values());
        if (comboTipoUsuario != null) comboTipoUsuario.getItems().addAll(TipoUsuario.values());
    }

    @FXML
    public void registrarIngreso() {
        if (cuentaActual == null) {
            areaResultado.setText("Error: No hay sesión activa");
            return;
        }

        try {
            String placa = txtPlaca.getText().trim();
            String nombre = txtNombreConductor.getText().trim();
            String id = txtIdConductor.getText().trim();
            TipoVehiculo tipoVehiculo = comboTipo.getValue();
            TipoUsuario tipoUsuario = comboTipoUsuario.getValue();

            if (placa.isEmpty() || nombre.isEmpty() || id.isEmpty() ||
                    tipoVehiculo == null || tipoUsuario == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }

            // Llamada simple (sin cuentaActual)
            parqueadero.registrarIngreso(placa, tipoVehiculo, nombre, id, tipoUsuario);

            areaResultado.setText("✓ Ingreso registrado correctamente\nPlaca: " + placa.toUpperCase()
                    + "\nTipo de usuario: " + tipoUsuario);

            // Limpiar
            txtPlaca.clear();
            txtNombreConductor.clear();
            txtIdConductor.clear();
            comboTipo.setValue(null);
            comboTipoUsuario.setValue(null);

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void registrarSalida() {
        if (cuentaActual == null) {
            areaResultado.setText("Error: No hay sesión activa");
            return;
        }

        try {
            String placa = txtPlacaSalida.getText().trim();
            if (placa.isEmpty()) {
                areaResultado.setText("Ingrese la placa del vehículo");
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
    public void volverAlAdmin() {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Navegador.irA("/view/AdminView.fxml", stage);   // Cambiado a irA
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al menú principal");
            alert.showAndWait();
        }
    }
}
