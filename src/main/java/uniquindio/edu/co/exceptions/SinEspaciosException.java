package uniquindio.edu.co.exceptions;

import uniquindio.edu.co.enums.TipoVehiculo;

public class SinEspaciosException extends RuntimeException {

    public SinEspaciosException(TipoVehiculo tipo) {
        super("No hay espacios disponibles para vehículos tipo " + tipo);
    }

    public SinEspaciosException(TipoVehiculo tipo, int espaciosTotales, int espaciosOcupados) {
        super("No hay espacios disponibles para " + tipo +
                ". Total espacios: " + espaciosTotales +
                " | Ocupados: " + espaciosOcupados);
    }

    public SinEspaciosException(String mensajePersonalizado) {
        super(mensajePersonalizado);
    }
}