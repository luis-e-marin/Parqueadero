package uniquindio.edu.co.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class OperadorController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    public void registrarIngreso(TextField txtPlaca, TextField txtNombre, TextField txtId,
                                 ComboBox<TipoVehiculo> comboTipo, ComboBox<TipoUsuario> comboTipoUsuario,
                                 TextArea areaResultado) {
        if (cuentaActual == null) { areaResultado.setText("Error: No hay sesión activa"); return; }
        try {
            String placa = txtPlaca.getText().trim();
            String nombre = txtNombre.getText().trim();
            String id = txtId.getText().trim();
            TipoVehiculo tipoVehiculo = comboTipo.getValue();
            TipoUsuario tipoUsuario = comboTipoUsuario.getValue();

            if (placa.isEmpty() || nombre.isEmpty() || id.isEmpty() || tipoVehiculo == null || tipoUsuario == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }
            parqueadero.registrarIngreso(placa, tipoVehiculo, nombre, id, tipoUsuario);
            areaResultado.setText(" Ingreso registrado correctamente\nPlaca: " + placa.toUpperCase() + "\nTipo de usuario: " + tipoUsuario);

            txtPlaca.clear(); txtNombre.clear(); txtId.clear();
            comboTipo.setValue(null); comboTipoUsuario.setValue(null);
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    public void registrarSalida(TextField txtPlacaSalida, TextArea areaResultado) {
        if (cuentaActual == null) { areaResultado.setText("Error: No hay sesión activa"); return; }
        try {
            String placa = txtPlacaSalida.getText().trim();
            if (placa.isEmpty()) { areaResultado.setText("Ingrese la placa del vehículo"); return; }
            double valor = parqueadero.registrarSalida(placa);
            areaResultado.setText(" Salida registrada correctamente\nTotal a pagar: $" + String.format("%.0f", valor));
            txtPlacaSalida.clear();
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    public void volverAlMenuOperador(Button btnVolver) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Navegador.irA("/view/OperadorMenu.fxml", stage);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo volver al menú del operador").showAndWait();
        }
    }

    public void volverAlLogin(Button btnVolver) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Navegador.irA("/view/LoginView.fxml", stage);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo volver al login").showAndWait();
        }
    }
}