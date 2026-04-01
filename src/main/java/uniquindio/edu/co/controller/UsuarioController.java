package uniquindio.edu.co.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.model.Parqueadero;
import uniquindio.edu.co.model.Usuario;
import uniquindio.edu.co.utils.Navegador;

public class UsuarioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private ComboBox<TipoUsuario> comboTipo;
    @FXML private TextArea areaResultado;
    @FXML private Button btnVolver;

    private final Parqueadero parqueadero = Parqueadero.getInstance();

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll(TipoUsuario.values());
    }

    @FXML
    public void crearUsuario() {
        try {
            String nombre = txtNombre.getText().trim();
            String id = txtId.getText().trim();
            TipoUsuario tipo = comboTipo.getValue();

            if (nombre.isEmpty() || id.isEmpty() || tipo == null) {
                areaResultado.setText("Error: Complete todos los campos");
                return;
            }

            Usuario usuario = new Usuario(nombre, id, tipo);
            parqueadero.registrarUsuario(usuario);

            areaResultado.setText("✓ Usuario creado exitosamente:\n" + usuario);

            // Limpiar campos
            txtNombre.clear();
            txtId.clear();
            comboTipo.setValue(null);

        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void buscarUsuario() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            areaResultado.setText("Ingrese una identificación");
            return;
        }

        Usuario u = parqueadero.buscarUsuarioPorIdentificacion(id);
        areaResultado.setText(u != null ? u.toString() : "Usuario no encontrado");
    }

    @FXML
    public void editarUsuario() {
        String id = txtId.getText().trim();
        Usuario u = parqueadero.buscarUsuarioPorIdentificacion(id);

        if (u == null) {
            areaResultado.setText("Usuario no encontrado");
            return;
        }

        u.modificar(txtNombre.getText().trim(), comboTipo.getValue());
        areaResultado.setText("✓ Usuario actualizado:\n" + u);
    }

    @FXML
    public void verVehiculos() {
        String id = txtId.getText().trim();
        Usuario u = parqueadero.buscarUsuarioPorIdentificacion(id);

        if (u == null) {
            areaResultado.setText("Usuario no encontrado");
            return;
        }

        StringBuilder sb = new StringBuilder("Vehículos del usuario:\n");
        if (u.getVehiculos().isEmpty()) {
            sb.append("Este usuario no tiene vehículos registrados.");
        } else {
            u.getVehiculos().forEach(v -> sb.append(v).append("\n"));
        }
        areaResultado.setText(sb.toString());
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
}