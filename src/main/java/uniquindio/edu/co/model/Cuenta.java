package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.Rol;

public class Cuenta {

    private final String user;
    private String clave;
    private final Rol rol;
    private final Usuario usuarioAsociado;

    public Cuenta(String user, String clave, Rol rol, Usuario usuarioAsociado) {
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("Username no puede estar vacío");
        }
        if (clave == null || clave.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        this.user = user.trim().toLowerCase();
        this.clave = clave.trim();
        this.rol = rol;
        this.usuarioAsociado = usuarioAsociado;
    }


    public boolean intentarLogin(String user, String clave) {
        if (user == null || clave == null) return false;
        return this.user.equals(user.trim().toLowerCase()) && this.clave.equals(clave);
    }

    public boolean esAdministrador() {
        return rol == Rol.ADMINISTRADOR;
    }

    public boolean esOperador() {
        return rol == Rol.OPERADOR;
    }


    public boolean cambiarClave(String claveActual, String nuevaClave) {
        if (nuevaClave == null || nuevaClave.trim().isEmpty()) return false;
        if (!this.clave.equals(claveActual)) return false;

        this.clave = nuevaClave.trim();
        return true;
    }

    public void setClave(String nuevaClave) {
        if (nuevaClave == null || nuevaClave.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
        }
        this.clave = nuevaClave.trim();
    }

    // Getters
    public String getUser() { return user; }
    public Rol getRol() { return rol; }
    public Usuario getUsuarioAsociado() { return usuarioAsociado; }

    @Override
    public String toString() {
        return user + " (" + rol + ")";
    }
}