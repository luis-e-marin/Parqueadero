package uniquindio.edu.co.exceptions;

public class VehiculoNoEncontradoException extends RuntimeException {

    public VehiculoNoEncontradoException(String placa) {
        super("No se encontró ningún vehículo con la placa: " + formatearPlaca(placa));
    }

    public VehiculoNoEncontradoException(String placa, String mensajeAdicional) {
        super("No se encontró el vehículo con placa " +
                formatearPlaca(placa) +
                ". " + mensajeAdicional);
    }

    private static String formatearPlaca(String placa) {
        return placa != null ? placa.toUpperCase() : "SIN PLACA";
    }
}