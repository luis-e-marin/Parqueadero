package uniquindio.edu.co.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Espacio;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.utils.Navegador;

public class EspaciosController {

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }

    public void cargarTabla(TableView<Espacio> tabla) {
        tabla.getItems().setAll(parqueadero.getEspacios());
    }

    public void deshabilitarEspacio(TextField txtCodigo, TableView<Espacio> tabla) {
        if (cuentaActual == null) { mostrarMensaje("Error: No hay sesión activa (debe ser Admin)"); return; }
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) { mostrarMensaje("Ingrese el código del espacio"); return; }
        if (parqueadero.deshabilitarEspacio(codigo)) {
            tabla.getItems().setAll(parqueadero.getEspacios());
            mostrarMensaje("Espacio deshabilitado correctamente");
        } else {
            mostrarMensaje("No se pudo deshabilitar el espacio (puede estar ocupado o no existe)");
        }
    }

    public void habilitarEspacio(TextField txtCodigo, TableView<Espacio> tabla) {
        if (cuentaActual == null) { mostrarMensaje("Error: No hay sesión activa (debe ser Admin)"); return; }
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) { mostrarMensaje("Ingrese el código del espacio"); return; }
        try {
            parqueadero.habilitarEspacio(codigo);
            tabla.getItems().setAll(parqueadero.getEspacios());
            mostrarMensaje("Espacio habilitado correctamente");
        } catch (Exception exception) {
            mostrarMensaje("Error: " + exception.getMessage());
        }
    }

    public void volverAlMenu(Button btnVolver) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Navegador.irA("/view/AdminView.fxml", stage);
        } catch (Exception exception) {
            mostrarMensaje("No se pudo volver al panel de administrador");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}