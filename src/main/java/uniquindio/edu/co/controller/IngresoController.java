package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class IngresoController {

    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipoVehiculo;
    @FXML private TextField txtNombreConductor;
    @FXML private TextField txtIdConductor;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;
    @FXML private TextArea areaResultado;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    @FXML
    public void initialize() {
        comboTipoVehiculo.getItems().addAll(TipoVehiculo.values());
        comboTipoUsuario.getItems().addAll(TipoUsuario.values());
    }

    @FXML
    public void registrarIngreso() {
        try {
            String placa = txtPlaca.getText().trim();
            String nombre = txtNombreConductor.getText().trim();
            String id = txtIdConductor.getText().trim();
            TipoVehiculo tipoVehiculo = comboTipoVehiculo.getValue();
            TipoUsuario tipoUsuario = comboTipoUsuario.getValue();

            if (placa.isEmpty() || nombre.isEmpty() || id.isEmpty() || tipoVehiculo == null || tipoUsuario == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }

            // Pasamos también el tipo de usuario para aplicar descuento
            parqueadero.registrarIngreso(placa, tipoVehiculo, nombre, id, tipoUsuario);

            areaResultado.setText("✓ Ingreso registrado correctamente\nPlaca: " + placa.toUpperCase()
                    + "\nTipo usuario: " + tipoUsuario);

            // Limpiar
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
            Stage stage = (Stage) txtPlaca.getScene().getWindow();
            Navegador.irA("/view/OperadorMenu.fxml", stage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al menú");
            alert.showAndWait();
        }
    }
}