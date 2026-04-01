package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.Rol;

public class Cuenta {

    private final String username;
    private String password;
    private final Rol rol;
    private final Usuario usuarioAsociado;

    public Cuenta(String username, String password, Rol rol, Usuario usuarioAsociado) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username no puede estar vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        this.username = username.trim().toLowerCase();
        this.password = password.trim();
        this.rol = rol;
        this.usuarioAsociado = usuarioAsociado;
    }


    public boolean intentarLogin(String username, String password) {
        if (username == null || password == null) return false;
        return this.username.equals(username.trim().toLowerCase()) && this.password.equals(password);
    }

    public boolean esAdministrador() {
        return rol == Rol.ADMINISTRADOR;
    }

    public boolean esOperador() {
        return rol == Rol.OPERADOR;
    }


    public boolean cambiarClave(String claveActual, String nuevaClave) {
        if (nuevaClave == null || nuevaClave.trim().isEmpty()) return false;
        if (!this.password.equals(claveActual)) return false;

        this.password = nuevaClave.trim();
        return true;
    }

    public void setPassword(String nuevaPassword) {
        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
        }
        this.password = nuevaPassword.trim();
    }

    // Getters
    public String getUsername() { return username; }
    public Rol getRol() { return rol; }
    public Usuario getUsuarioAsociado() { return usuarioAsociado; }

    @Override
    public String toString() {
        return username + " (" + rol + ")";
    }
}