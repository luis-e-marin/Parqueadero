package uniquindio.edu.co.exceptions;


public class EspacioNoDisponibleException extends RuntimeException {
    public EspacioNoDisponibleException(String mensaje) {
        super(mensaje);
    }

    public EspacioNoDisponibleException(String codigoEspacio, String operacion) {
        super("No se puede " + operacion + " el espacio " + codigoEspacio + ". Está ocupado o fuera de servicio.");
    }
}