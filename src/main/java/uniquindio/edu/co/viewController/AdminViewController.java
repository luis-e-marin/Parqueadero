package uniquindio.edu.co.viewController ;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import uniquindio.edu.co.controller.AdminController;
import uniquindio.edu.co.model.Cuenta;

public class AdminViewController {

    @FXML private Button btnCerrarSesion;

    private final AdminController adminController = new AdminController();

    public void setCuentaActual(Cuenta cuenta) {
        adminController.setCuentaActual(cuenta);
    }

    @FXML public void irGestionUsuarios() { adminController.irGestionUsuarios(btnCerrarSesion); }
    @FXML public void irGestionTarifas() { adminController.irGestionTarifas(btnCerrarSesion); }
    @FXML public void irGestionEspacios() { adminController.irGestionEspacios(btnCerrarSesion); }
    @FXML public void irGestionVehiculos() { adminController.irGestionVehiculos(btnCerrarSesion); }
    @FXML public void irReportes() { adminController.irReportes(btnCerrarSesion); }
    @FXML public void cerrarSesion() { adminController.cerrarSesion(btnCerrarSesion); }
}