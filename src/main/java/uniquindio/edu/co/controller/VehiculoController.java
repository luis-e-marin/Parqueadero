package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Usuario;
import uniquindio.edu.co.model.Vehiculo;
import uniquindio.edu.co.utils.Navegador;

public class VehiculoController {

    @FXML private TextField txtIdUsuario;
    @FXML private TextField txtPlaca;
    @FXML private ComboBox<TipoVehiculo> comboTipo;
    @FXML private TextArea areaResultado;
    @FXML private Button btnVolver;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll(TipoVehiculo.values());
    }

    @FXML
    public void agregarVehiculo() {
        try {
            String idUsuario = txtIdUsuario.getText().trim();
            String placa = txtPlaca.getText().trim();
            TipoVehiculo tipo = comboTipo.getValue();

            if (idUsuario.isEmpty() || placa.isEmpty() || tipo == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }

            Usuario usuario = parqueadero.buscarUsuarioPorIdentificacion(idUsuario);
            if (usuario == null) {
                areaResultado.setText("Usuario no encontrado");
                return;
            }

            Vehiculo vehiculo = new Vehiculo(placa, tipo, usuario.getNombre(), idUsuario);
            usuario.agregarVehiculo(vehiculo);

            areaResultado.setText("✓ Vehículo agregado correctamente a " + usuario.getNombre());

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void buscarPorPlaca() {
        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) {
            areaResultado.setText("Ingrese una placa");
            return;
        }

        Usuario u = parqueadero.buscarUsuarioPorPlaca(placa);
        areaResultado.setText(u != null ? "Pertenece a: " + u.getNombre() : "No encontrado");
    }

    @FXML
    public void eliminarVehiculo() {
        String idUsuario = txtIdUsuario.getText().trim();
        String placa = txtPlaca.getText().trim();

        Usuario u = parqueadero.buscarUsuarioPorIdentificacion(idUsuario);
        if (u == null) {
            areaResultado.setText("Usuario no encontrado");
            return;
        }

        u.eliminarVehiculo(placa);
        areaResultado.setText("✓ Vehículo eliminado correctamente");
    }

    @FXML
    public void volverAlAdmin() {
        try {

            Stage stage = (Stage) areaResultado.getScene().getWindow();
            Navegador.volverAlAdmin(stage);
        } catch (Exception e) {
            System.err.println("Error al volver al Admin: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de navegación");
            alert.setContentText("No se pudo regresar al menú principal.");
            alert.showAndWait();
        }
    }

    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }
}
