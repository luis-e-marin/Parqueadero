package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Vehiculo;
import uniquindio.edu.co.utils.Navegador;

import java.util.List;

public class VehiculoAdminController {

    @FXML private TextField txtPlacaBuscar;
    @FXML private TextArea areaResultado;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    @FXML
    public void buscarVehiculo() {
        String placa = txtPlacaBuscar.getText().trim();

        if (placa.isEmpty()) {
            areaResultado.setText("Ingrese una placa para buscar");
            return;
        }

        List<Vehiculo> dentro = parqueadero.getVehiculosDentro();

        Vehiculo encontrado = dentro.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElse(null);

        if (encontrado != null) {
            areaResultado.setText(
                    "Vehículo encontrado dentro del parqueadero:\n\n" +
                            "Placa: " + encontrado.getPlaca() + "\n" +
                            "Tipo: " + encontrado.getTipo() + "\n" +
                            "Conductor: " + encontrado.getNombreConductor() + "\n" +
                            "ID: " + encontrado.getIdentificacionConductor() + "\n" +
                            "Espacio: " + encontrado.getEspacioAsignado() + "\n" +
                            "Tiempo: " + encontrado.getTiempoPermanenciaFormateado()
            );
        } else {
            areaResultado.setText(
                    "No se encontró vehículo con placa: " + placa.toUpperCase() +
                            "\n(Puede que ya haya salido o no esté registrado)"
            );
        }
    }

    @FXML
    public void verTodosVehiculos() {
        List<Vehiculo> lista = parqueadero.getVehiculosDentro();

        if (lista.isEmpty()) {
            areaResultado.setText("No hay vehículos dentro del parqueadero en este momento.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("TODOS LOS VEHÍCULOS DENTRO\n\n");

        for (Vehiculo v : lista) {
            sb.append("Placa: ").append(v.getPlaca())
                    .append(" | Tipo: ").append(v.getTipo())
                    .append(" | Conductor: ").append(v.getNombreConductor())
                    .append(" | Tiempo: ").append(v.getTiempoPermanenciaFormateado())
                    .append("\n");
        }

        areaResultado.setText(sb.toString());
    }

    @FXML
    public void eliminarVehiculo() {
        String placa = txtPlacaBuscar.getText().trim();

        if (placa.isEmpty()) {
            areaResultado.setText("Ingrese una placa para eliminar");
            return;
        }

        List<Vehiculo> lista = parqueadero.getVehiculosDentro();

        Vehiculo encontrado = lista.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElse(null);

        if (encontrado != null) {
            lista.remove(encontrado);
            areaResultado.setText("✓ Vehículo eliminado: " + placa.toUpperCase());
        } else {
            areaResultado.setText("No se encontró vehículo con placa: " + placa.toUpperCase());
        }
    }

    @FXML
    public void volverAlAdmin() {
        try {
            Stage stage = (Stage) txtPlacaBuscar.getScene().getWindow();
            Navegador.volverAlAdmin(stage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al panel de administrador");
            alert.showAndWait();
        }
    }
}
