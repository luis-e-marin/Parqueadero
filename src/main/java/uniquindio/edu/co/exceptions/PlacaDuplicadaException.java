package uniquindio.edu.co.exceptions;

public class PlacaDuplicadaException extends RuntimeException {

    public PlacaDuplicadaException(String placa) {
        super("Ya existe un vehículo con la placa " + placa + " dentro del parqueadero");
    }

    public PlacaDuplicadaException(String placa, String mensaje) {
        super("Placa duplicada: " + placa + ". " + mensaje);
    }

    public String getPlaca() {
        // Extrae la placa del mensaje si es necesario (opcional)
        String msg = getMessage();
        if (msg.contains("placa ")) {
            return msg.split("placa ")[1].split(" ")[0];
        }
        return "Desconocida";
    }
}