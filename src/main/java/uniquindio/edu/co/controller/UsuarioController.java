package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.model.Cuenta;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Usuario;
import uniquindio.edu.co.utils.Navegador;

public class UsuarioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtIdentificacion;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;
    @FXML private TextArea areaResultado;
    @FXML private VBox root;

    private final Parqueadero parqueadero = Parqueadero.getInstance();
    private Usuario usuarioActual;
    private Cuenta cuentaActual;

    public void setCuentaActual(Cuenta cuenta) {
        this.cuentaActual = cuenta;
    }
    @FXML
    public void initialize() {
        if (comboTipoUsuario != null) {
            comboTipoUsuario.getItems().addAll(TipoUsuario.values());
        }
    }

    @FXML
    public void registrarUsuario() {
        if (cuentaActual == null) {
            areaResultado.setText("Error: No hay sesión activa (debe ser Admin)");
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            String id = txtIdentificacion.getText().trim();
            TipoUsuario tipo = comboTipoUsuario.getValue();

            if (nombre.isEmpty() || id.isEmpty() || tipo == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }

            parqueadero.registrarUsuarioAutorizado(nombre, id, tipo);

            areaResultado.setText("✓ Usuario registrado correctamente:\n" +
                    "Nombre: " + nombre + "\nID: " + id + "\nTipo: " + tipo);

            limpiarCampos();

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void buscarUsuario() {
        String id = txtIdentificacion.getText().trim();
        if (id.isEmpty()) {
            areaResultado.setText("Ingrese una identificación para buscar");
            return;
        }

        usuarioActual = parqueadero.buscarUsuarioPorIdentificacion(id);

        if (usuarioActual != null) {
            areaResultado.setText("Usuario encontrado:\n" + usuarioActual.toString());
            txtNombre.setText(usuarioActual.getNombre());
            comboTipoUsuario.setValue(usuarioActual.getTipoUsuario());
        } else {
            areaResultado.setText("No se encontró usuario con ID: " + id);
            usuarioActual = null;
        }
    }

    @FXML
    public void modificarUsuario() {
        if (usuarioActual == null) {
            areaResultado.setText("Primero busque un usuario");
            return;
        }

        try {
            String nuevoNombre = txtNombre.getText().trim();
            TipoUsuario nuevoTipo = comboTipoUsuario.getValue();

            if (nuevoNombre.isEmpty() || nuevoTipo == null) {
                areaResultado.setText("Complete nombre y tipo de usuario");
                return;
            }

            usuarioActual.modificar(nuevoNombre, nuevoTipo);

            areaResultado.setText("✓ Usuario modificado correctamente:\n" + usuarioActual.toString());

            limpiarCampos();
            usuarioActual = null;

        } catch (Exception e) {
            areaResultado.setText("Error al modificar: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtIdentificacion.clear();
        comboTipoUsuario.setValue(null);
    }

    @FXML
    public void volverAlAdmin() {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Navegador.volverAlAdmin(stage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al panel de administrador");
            alert.showAndWait();
        }
    }
}
