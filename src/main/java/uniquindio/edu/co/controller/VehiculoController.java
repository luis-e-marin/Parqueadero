package uniquindio.edu.co.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Usuario;
import uniquindio.edu.co.model.Vehiculo;
import uniquindio.edu.co.utils.Navegador;

public class VehiculoController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    public void agregarVehiculo(TextField txtIdUsuario, TextField txtPlaca,
                                ComboBox<TipoVehiculo> comboTipo, TextArea areaResultado) {
        try {
            String idUsuario = txtIdUsuario.getText().trim();
            String placa = txtPlaca.getText().trim();
            TipoVehiculo tipo = comboTipo.getValue();
            if (idUsuario.isEmpty() || placa.isEmpty() || tipo == null) { areaResultado.setText("Error: Complete todos los campos"); return; }
            Usuario usuario = parqueadero.buscarUsuarioPorIdentificacion(idUsuario);
            if (usuario == null) { areaResultado.setText("Usuario no encontrado"); return; }
            Vehiculo vehiculo = new Vehiculo(placa, tipo, usuario.getNombre(), idUsuario);
            usuario.agregarVehiculo(vehiculo);
            areaResultado.setText("Vehículo agregado correctamente a " + usuario.getNombre());
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    public void buscarPorPlaca(TextField txtPlaca, TextArea areaResultado) {
        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) { areaResultado.setText("Ingrese una placa"); return; }
        Usuario usuario = parqueadero.buscarUsuarioPorPlaca(placa);
        areaResultado.setText(usuario != null ? "Pertenece a: " + usuario.getNombre() : "No encontrado");
    }

    public void eliminarVehiculo(TextField txtIdUsuario, TextField txtPlaca, TextArea areaResultado) {
        String idUsuario = txtIdUsuario.getText().trim();
        String placa = txtPlaca.getText().trim();
        Usuario usuario = parqueadero.buscarUsuarioPorIdentificacion(idUsuario);
        if (usuario == null) { areaResultado.setText("Usuario no encontrado"); return; }
        usuario.eliminarVehiculo(placa);
        areaResultado.setText(" Vehículo eliminado correctamente");
    }

    public void volverAlAdmin(TextArea areaResultado) {
        try {
            Stage stage = (Stage) areaResultado.getScene().getWindow();
            Navegador.volverAlAdmin(stage);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo regresar al menú principal.").showAndWait();
        }
    }
}