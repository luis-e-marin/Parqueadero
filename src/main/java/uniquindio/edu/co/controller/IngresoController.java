package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class IngresoController {

    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipoVehiculo;
    @FXML private TextField txtNombreConductor;
    @FXML private TextField txtIdConductor;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;
    @FXML private TextArea areaResultado;
    @FXML private VBox root;

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    @FXML
    public void initialize() {
        if (comboTipoVehiculo != null) comboTipoVehiculo.getItems().addAll(TipoVehiculo.values());
        if (comboTipoUsuario != null) comboTipoUsuario.getItems().addAll(TipoUsuario.values());
    }

    @FXML
    public void registrarIngreso() {
        if (cuentaActual == null) {
            areaResultado.setText("Error: No hay sesión activa (debe ser Operador o Admin)");
            return;
        }

        try {
            String placa = txtPlaca.getText().trim().toUpperCase();
            String nombre = txtNombreConductor.getText().trim();
            String id = txtIdConductor.getText().trim();
            TipoVehiculo tipoVehiculo = comboTipoVehiculo.getValue();
            TipoUsuario tipoUsuario = comboTipoUsuario.getValue();

            if (placa.isEmpty() || nombre.isEmpty() || id.isEmpty() ||
                    tipoVehiculo == null || tipoUsuario == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }

            parqueadero.registrarIngreso(placa, tipoVehiculo, nombre, id, tipoUsuario);

            areaResultado.setText(" Ingreso registrado correctamente\nPlaca: " + placa);

            // Limpiar campos
            txtPlaca.clear();
            txtNombreConductor.clear();
            txtIdConductor.clear();
            comboTipoVehiculo.setValue(null);
            comboTipoUsuario.setValue(null);

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void volverAlMenu() {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Navegador.volverAlOperador(stage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al menú del operador");
            alert.showAndWait();
        }
    }
}