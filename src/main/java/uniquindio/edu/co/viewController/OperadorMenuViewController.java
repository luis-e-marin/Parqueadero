package uniquindio.edu.co.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import uniquindio.edu.co.controller.OperadorMenuController;
import uniquindio.edu.co.model.Cuenta;

public class OperadorMenuViewController {

    private final OperadorMenuController operadorMenuController = new OperadorMenuController();

    public void setCuentaActual(Cuenta cuenta) {
        operadorMenuController.setCuentaActual(cuenta);
    }

    @FXML public void irRegistrarIngreso(ActionEvent event) { operadorMenuController.irRegistrarIngreso(event); }
    @FXML public void irRegistrarSalida(ActionEvent event) { operadorMenuController.irRegistrarSalida(event); }
    @FXML public void verVehiculosDentro() { operadorMenuController.verVehiculosDentro(); }
    @FXML public void verEspaciosDisponibles() { operadorMenuController.verEspaciosDisponibles(); }
    @FXML public void verReportes() { operadorMenuController.verReportes(); }
    @FXML public void cerrarSesion(ActionEvent event) { operadorMenuController.cerrarSesion(event); }
}