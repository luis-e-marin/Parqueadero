package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Vehiculo;
import uniquindio.edu.co.utils.Navegador;

import java.util.List;

public class ReportesController {

    @FXML private TextArea areaReportes;
    @FXML private Button btnVolver;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    @FXML
    public void reporteVehiculosHoy() {
        int total = parqueadero.getVehiculosDentro().size();
        StringBuilder sb = new StringBuilder();
        sb.append("=== VEHÍCULOS INGRESADOS HOY ===\n\n");
        sb.append("Total de vehículos actualmente en el parqueadero: ").append(total).append("\n\n");

        List<Vehiculo> dentro = parqueadero.getVehiculosDentro();
        for (Vehiculo v : dentro) {
            sb.append(v.getPlaca()).append(" - ").append(v.getTipo())
                    .append(" - ").append(v.getTiempoPermanenciaFormateado()).append("\n");
        }

        if (dentro.isEmpty()) {
            sb.append("No hay vehículos en el parqueadero en este momento.");
        }
        areaReportes.setText(sb.toString());
    }

    @FXML
    public void reporteIngresosDia() {
        List<Vehiculo> dentro = parqueadero.getVehiculosDentro();
        double ingresosEstimados = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("=== INGRESOS GENERADOS DEL DÍA ===\n\n");
        sb.append("Vehículos actualmente parqueados: ").append(dentro.size()).append("\n\n");

        for (Vehiculo v : dentro) {
            ingresosEstimados += 5000;
            sb.append(v.getPlaca()).append(" → $").append(String.format("%.0f", 5000.0)).append("\n");
        }

        sb.append("\nIngresos estimados del día: $").append(String.format("%.0f", ingresosEstimados));
        areaReportes.setText(sb.toString());
    }

    @FXML
    public void reporteTiempoPromedio() {
        List<Vehiculo> dentro = parqueadero.getVehiculosDentro();
        if (dentro.isEmpty()) {
            areaReportes.setText("No hay vehículos para calcular tiempo promedio.");
            return;
        }

        long totalMinutos = 0;
        for (Vehiculo v : dentro) {
            totalMinutos += v.getMinutosPermanencia();
        }

        double promedio = (double) totalMinutos / dentro.size();

        StringBuilder sb = new StringBuilder();
        sb.append("=== TIEMPO PROMEDIO DE PERMANENCIA ===\n\n");
        sb.append("Total vehículos: ").append(dentro.size()).append("\n");
        sb.append("Tiempo promedio: ").append(String.format("%.1f", promedio)).append(" minutos\n");
        sb.append("Equivalente a: ").append(String.format("%.1f", promedio/60)).append(" horas");

        areaReportes.setText(sb.toString());
    }

    @FXML
    public void reporteTiempoExcedido() {
        List<Vehiculo> dentro = parqueadero.getVehiculosDentro();
        StringBuilder sb = new StringBuilder();
        sb.append("=== VEHÍCULOS CON TIEMPO EXCEDIDO (> 4 horas) ===\n\n");

        boolean hayExcedidos = false;
        for (Vehiculo v : dentro) {
            if (v.getMinutosPermanencia() > 240) {
                sb.append(v.getPlaca()).append(" - ").append(v.getTipo())
                        .append(" → ").append(v.getTiempoPermanenciaFormateado()).append("\n");
                hayExcedidos = true;
            }
        }

        if (!hayExcedidos) {
            sb.append("No hay vehículos que hayan superado las 4 horas.");
        }
        areaReportes.setText(sb.toString());
    }

    @FXML
    public void volverAlAdmin() {
        try {

            Stage stage = (Stage) areaReportes.getScene().getWindow();
            Navegador.volverAlAdmin(stage);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se pudo volver al menú principal");
            alert.showAndWait();
        }
    }
}