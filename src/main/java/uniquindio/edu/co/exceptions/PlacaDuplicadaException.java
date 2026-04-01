package uniquindio.edu.co.exceptions;

public class PlacaDuplicadaException extends RuntimeException {
    public PlacaDuplicadaException(String placa) {
        super("Ya existe un vehículo con la placa " + placa.toUpperCase() + " dentro del parqueadero.");
    }
}