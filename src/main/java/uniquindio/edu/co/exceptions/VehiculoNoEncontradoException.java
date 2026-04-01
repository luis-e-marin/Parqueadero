package uniquindio.edu.co.exceptions;

public class VehiculoNoEncontradoException extends RuntimeException {
    public VehiculoNoEncontradoException(String placa) {
        super("No se encontró ningún vehículo con la placa: " + (placa != null ? placa.toUpperCase() : "SIN PLACA"));
    }
}