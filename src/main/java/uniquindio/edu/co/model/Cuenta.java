package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.Rol;

public class Cuenta {

    private final String usuario;
    private String clave;
    private final Rol rol;
    private final Usuario usuarioAsociado;

    public Cuenta(String usuario, String clave, Rol rol, Usuario usuarioAsociado) {

        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }
        if (clave == null || clave.trim().isEmpty()) {
            throw new IllegalArgumentException("La clave no puede estar vacía");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        this.usuario = usuario.trim();
        this.clave = clave.trim();
        this.rol = rol;
        this.usuarioAsociado = usuarioAsociado;
    }

    // ================= LOGIN =================
    public boolean login(String usuarioIngresado, String claveIngresada) {
        if (usuarioIngresado == null || claveIngresada == null) return false;

        return this.usuario.equalsIgnoreCase(usuarioIngresado.trim()) &&
                this.clave.equals(claveIngresada.trim());
    }

    // ================= ROLES =================
    public boolean esAdministrador() {
        return rol == Rol.ADMIN;
    }

    public boolean esOperador() {
        return rol == Rol.OPERADOR;
    }

    // ================= GETTERS =================
    public String getUsuario() {
        return usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    // ================= SEGURIDAD =================
    public boolean cambiarClave(String actual, String nueva) {
        if (nueva == null || nueva.trim().isEmpty()) return false;

        if (this.clave.equals(actual)) {
            this.clave = nueva.trim();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return usuario + " (" + rol + ")";
    }
}