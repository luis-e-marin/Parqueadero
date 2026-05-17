package uniquindio.edu.co.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Vehiculo;
import uniquindio.edu.co.utils.Navegador;

import java.util.List;

public class VehiculoAdminController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    public void buscarVehiculo(TextField txtPlacaBuscar, TextArea areaResultado) {
        String placa = txtPlacaBuscar.getText().trim();
        if (placa.isEmpty()) { areaResultado.setText("Ingrese una placa para buscar"); return; }
        Vehiculo encontrado = parqueadero.getVehiculosDentro().stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa)).findFirst().orElse(null);
        if (encontrado != null) {
            areaResultado.setText("Vehículo encontrado dentro del parqueadero:\n\n" +
                    "Placa: " + encontrado.getPlaca() + "\nTipo: " + encontrado.getTipo() +
                    "\nConductor: " + encontrado.getNombreConductor() +
                    "\nID: " + encontrado.getIdentificacionConductor() +
                    "\nEspacio: " + encontrado.getEspacioAsignado() +
                    "\nTiempo: " + encontrado.getTiempoPermanenciaFormateado());
        } else {
            areaResultado.setText("No se encontró vehículo con placa: " + placa.toUpperCase() +
                    "\n(Puede que ya haya salido o no esté registrado)");
        }
    }

    public void verTodosVehiculos(TextArea areaResultado) {
        List<Vehiculo> lista = parqueadero.getVehiculosDentro();
        if (lista.isEmpty()) { areaResultado.setText("No hay vehículos dentro del parqueadero en este momento."); return; }
        StringBuilder sb = new StringBuilder("TODOS LOS VEHÍCULOS DENTRO\n\n");
        for (Vehiculo v : lista)
            sb.append("Placa: ").append(v.getPlaca()).append(" | Tipo: ").append(v.getTipo())
                    .append(" | Conductor: ").append(v.getNombreConductor())
                    .append(" | Tiempo: ").append(v.getTiempoPermanenciaFormateado()).append("\n");
        areaResultado.setText(sb.toString());
    }

    public void eliminarVehiculo(TextField txtPlacaBuscar, TextArea areaResultado) {
        String placa = txtPlacaBuscar.getText().trim();
        if (placa.isEmpty()) { areaResultado.setText("Ingrese una placa para eliminar"); return; }
        List<Vehiculo> lista = parqueadero.getVehiculosDentro();
        Vehiculo encontrado = lista.stream().filter(v -> v.getPlaca().equalsIgnoreCase(placa)).findFirst().orElse(null);
        if (encontrado != null) {
            lista.remove(encontrado);
            areaResultado.setText("✓ Vehículo eliminado: " + placa.toUpperCase());
        } else {
            areaResultado.setText("No se encontró vehículo con placa: " + placa.toUpperCase());
        }
    }

    public void volverAlAdmin(TextField txtPlacaBuscar) {
        try {
            Stage stage = (Stage) txtPlacaBuscar.getScene().getWindow();
            Navegador.volverAlAdmin(stage);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo volver al panel de administrador").showAndWait();
        }
    }
}