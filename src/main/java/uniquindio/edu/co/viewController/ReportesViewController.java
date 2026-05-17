package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import uniquindio.edu.co.controller.ReportesController;

public class ReportesViewController {

    @FXML private TextArea areaReportes;

    private final ReportesController reportesController = new ReportesController();

    @FXML public void reporteVehiculosHoy() { reportesController.reporteVehiculosHoy(areaReportes); }
    @FXML public void reporteIngresosDia() { reportesController.reporteIngresosDia(areaReportes); }
    @FXML public void reporteTiempoPromedio() { reportesController.reporteTiempoPromedio(areaReportes); }
    @FXML public void reporteTiempoExcedido() { reportesController.reporteTiempoExcedido(areaReportes); }
    @FXML public void volverAlAdmin() { reportesController.volverAlAdmin(areaReportes); }
}