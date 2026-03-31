package uniquindio.edu.co.exceptions;

import uniquindio.edu.co.enums.EstadoEspacio;

public class EspacioNoDisponibleException extends RuntimeException {
    public EspacioNoDisponibleException(String message) {
        super(message);
    }

    // Constructor específico para ocupar
    public EspacioNoDisponibleException(String codigoEspacio, EstadoEspacio estadoActual, String operacion) {
        super("No se puede " + operacion + " el espacio " + codigoEspacio
                + ". Estado actual: " + estadoActual);
    }
}
