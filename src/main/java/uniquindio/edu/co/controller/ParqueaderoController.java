package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Vehiculo;
import uniquindio.edu.co.utils.Navegador;

public class ParqueaderoController {

    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipo;
    @FXML private TextField txtNombreConductor;
    @FXML private TextField txtIdConductor;
    @FXML private TextField txtPlacaSalida;
    @FXML private TextArea areaResultado;
    @FXML private Button btnVolver;

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll(TipoVehiculo.values());
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
            TipoVehiculo tipo = comboTipo.getValue();

            if (placa.isEmpty() || nombre.isEmpty() || tipo == null) {
                areaResultado.setText("Error: Complete placa, conductor y tipo de vehículo");
                return;
            }

            Vehiculo vehiculo = new Vehiculo(placa, tipo, nombre, id, id);
            parqueadero.registrarIngreso(vehiculo, cuentaActual);

            areaResultado.setText("✓ Ingreso registrado correctamente - Placa: " + placa.toUpperCase());


            txtPlaca.clear();
            txtNombreConductor.clear();
            txtIdConductor.clear();
            comboTipo.setValue(null);

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

            double valor = parqueadero.registrarSalida(placa, cuentaActual);
            areaResultado.setText("✓ Salida registrada correctamente\nTotal a pagar: $" + String.format("%.0f", valor));

            txtPlacaSalida.clear();

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void volverAlAdmin() {
        if (btnVolver == null) {
            areaResultado.setText("Error: Botón volver no configurado");
            return;
        }
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        Navegador.volverAlAdmin(stage);
    }
}