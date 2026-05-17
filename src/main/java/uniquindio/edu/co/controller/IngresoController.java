package uniquindio.edu.co.controller;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class IngresoController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    public void registrarIngreso(TextField txtPlaca, TextField txtNombre, TextField txtId,
                                 ComboBox<TipoVehiculo> comboTipo, ComboBox<TipoUsuario> comboUsuario,
                                 TextArea areaResultado) {
        if (cuentaActual == null) {
            areaResultado.setText("Error: No hay sesión activa (debe ser Operador o Admin)");
            return;
        }
        try {
            String placa = txtPlaca.getText().trim().toUpperCase();
            String nombre = txtNombre.getText().trim();
            String id = txtId.getText().trim();
            TipoVehiculo tipoVehiculo = comboTipo.getValue();
            TipoUsuario tipoUsuario = comboUsuario.getValue();

            if (placa.isEmpty() || nombre.isEmpty() || id.isEmpty() || tipoVehiculo == null || tipoUsuario == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }
            parqueadero.registrarIngreso(placa, tipoVehiculo, nombre, id, tipoUsuario);
            areaResultado.setText(" Ingreso registrado correctamente\nPlaca: " + placa);

            txtPlaca.clear();
            txtNombre.clear();
            txtId.clear();
            comboTipo.setValue(null);
            comboUsuario.setValue(null);
        } catch (Exception exception) {
            areaResultado.setText("Error: " + exception.getMessage());
        }
    }

    public void volverAlMenu(VBox root) {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Navegador.volverAlOperador(stage);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al menú del operador");
            alert.showAndWait();
        }
    }
}