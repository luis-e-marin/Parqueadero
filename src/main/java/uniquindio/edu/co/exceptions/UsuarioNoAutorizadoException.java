package uniquindio.edu.co.exceptions;

import uniquindio.edu.co.model.Cuenta;

public class UsuarioNoAutorizadoException extends RuntimeException {

    public UsuarioNoAutorizadoException(String accion) {
        super("Usuario no autorizado para realizar la acción: " + accion);
    }

    public UsuarioNoAutorizadoException(Cuenta usuario, String accion) {
        super("El usuario '" + usuario.getUsuario() +
                "' (" + usuario.getUsuario() + ") " +
                "no tiene permiso para realizar la acción: " + accion);
    }

    public UsuarioNoAutorizadoException(String usuario, String accion) {
        super("El usuario '" + usuario + "' no tiene permiso para: " + accion);
    }
}