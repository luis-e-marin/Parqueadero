package uniquindio.edu.co.exceptions;

public class UsuarioNoAutorizadoException extends RuntimeException {
    public UsuarioNoAutorizadoException(String accion) {
        super("No tienes permiso para realizar esta acción: " + accion);
    }

    public UsuarioNoAutorizadoException(String username, String accion) {
        super("El usuario '" + username + "' no tiene permiso para: " + accion);
    }
}
