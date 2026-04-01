package uniquindio.edu.co.model;

import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {

    private List<Usuario> usuarios;

    public GestorUsuarios() {
        usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public Usuario buscarPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getIdentificacion().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorPlaca(String placa) {
        for (Usuario u : usuarios) {
            if (u.buscarVehiculo(placa) != null) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
