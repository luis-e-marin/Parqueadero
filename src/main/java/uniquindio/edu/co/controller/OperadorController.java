package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class OperadorController {

    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipo;
    @FXML private TextField txtNombreConductor;
    @FXML private TextField txtIdConductor;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;   // ← Nuevo
    @FXML private TextField txtPlacaSalida;
    @FXML private TextArea areaResultado;
    @FXML private Button btnVolver;

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    @FXML
    public void initialize() {
        if (comboTipo != null) {
            comboTipo.getItems().addAll(TipoVehiculo.values());
        }
        if (comboTipoUsuario != null) {
            comboTipoUsuario.getItems().addAll(TipoUsuario.values());
        }
    }

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
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
                areaResultado.setText("Error: Complete todos los campos (incluyendo Tipo de Usuario)");
                return;
            }

            // Ahora pasamos también el TipoUsuario para aplicar descuento
            parqueadero.registrarIngreso(placa, tipoVehiculo, nombre, id, tipoUsuario);

            areaResultado.setText("✓ Ingreso registrado correctamente\nPlaca: " + placa.toUpperCase()
                    + "\nTipo de usuario: " + tipoUsuario);

            // Limpiar campos
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
    public void volverAlMenuOperador() {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Navegador.irA("/view/OperadorMenu.fxml", stage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al menú del operador");
            alert.showAndWait();
        }
    }

    @FXML
    public void volverAlLogin() {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Navegador.irA("/view/LoginView.fxml", stage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al login");
            alert.showAndWait();
        }
    }
}