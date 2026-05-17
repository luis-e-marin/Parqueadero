package uniquindio.edu.co.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import uniquindio.edu.co.controller.UsuarioController;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.model.Cuenta;

public class UsuarioViewController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtIdentificacion;
    @FXML private ComboBox<TipoUsuario> comboTipoUsuario;
    @FXML private TextArea areaResultado;
    @FXML private VBox root;

    private final UsuarioController usuarioController = new UsuarioController();

    public void setCuentaActual(Cuenta cuenta) {
        usuarioController.setCuentaActual(cuenta);
    }

    @FXML
    public void initialize() {
        if (comboTipoUsuario != null) comboTipoUsuario.getItems().addAll(TipoUsuario.values());
    }

    @FXML public void registrarUsuario() { usuarioController.registrarUsuario(txtNombre, txtIdentificacion, comboTipoUsuario, areaResultado); }
    @FXML public void buscarUsuario() { usuarioController.buscarUsuario(txtIdentificacion, txtNombre, comboTipoUsuario, areaResultado); }
    @FXML public void modificarUsuario() { usuarioController.modificarUsuario(txtNombre, comboTipoUsuario, areaResultado); }
    @FXML public void volverAlAdmin() { usuarioController.volverAlAdmin(root); }
}