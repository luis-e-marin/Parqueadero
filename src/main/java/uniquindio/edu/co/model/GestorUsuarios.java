package uniquindio.edu.co.model;

import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {

    private List<Usuario> usuarios;

    public GestorUsuarios() {
        usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario buscarPorId(String id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getIdentificacion().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorPlaca(String placa) {
        for (Usuario usuario : usuarios) {
            if (usuario.buscarVehiculo(placa) != null) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
