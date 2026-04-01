package uniquindio.edu.co.exceptions;

import uniquindio.edu.co.enums.TipoVehiculo;

public class SinEspaciosException extends RuntimeException {
    public SinEspaciosException(String mensaje) {
        super(mensaje);
    }

    public SinEspaciosException(TipoVehiculo tipo) {
        super("No hay espacios disponibles para vehículos tipo " + tipo);
    }
}