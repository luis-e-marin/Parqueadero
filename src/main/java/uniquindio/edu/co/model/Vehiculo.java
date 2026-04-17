package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;
import java.time.LocalDateTime;
import java.time.Duration;

public class Vehiculo {

    private  String placa;
    private final TipoVehiculo tipo;
    private final String nombreConductor;
    private final String identificacionConductor;
    private String espacioAsignado;
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;
    private boolean estaDentro;

    // Constructor
    public Vehiculo(String placa, TipoVehiculo tipo, String nombreConductor, String identificacionConductor) {

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa obligatoria");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de vehículo obligatorio");
        }
        if (nombreConductor == null || nombreConductor.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre del conductor obligatorio");
        }
        if (identificacionConductor == null || identificacionConductor.trim().isEmpty()) {
            throw new IllegalArgumentException("Identificación del conductor obligatoria");
        }

        this.placa = placa.trim().toUpperCase();
        this.tipo = tipo;
        this.nombreConductor = nombreConductor.trim();
        this.identificacionConductor = identificacionConductor.trim();
        this.espacioAsignado = null;
        this.horaIngreso = null;
        this.horaSalida = null;
        this.estaDentro = false;
    }

    public void registrarIngreso(String espacio) {
        this.horaIngreso = LocalDateTime.now();
        this.horaSalida = null;
        this.espacioAsignado = espacio;
        this.estaDentro = true;
    }

    public void registrarSalida() {
        this.horaSalida = LocalDateTime.now();
        this.estaDentro = false;
    }

    public long getMinutosPermanencia() {
        if (horaIngreso == null) return 0;
        LocalDateTime fin = horaSalida != null ? horaSalida : LocalDateTime.now();
        return Math.max(Duration.between(horaIngreso, fin).toMinutes(), 1);
    }

    public String getTiempoPermanenciaFormateado() {
        if (!estaDentro) return "Fuera del parqueadero";
        long minutos = getMinutosPermanencia();
        long horas = minutos / 60;
        long mins = minutos % 60;
        return (horas > 0 ? horas + "horas " : "") + mins + "minutos";
    }

    // Getters
    public String getPlaca() { return placa; }
    public TipoVehiculo getTipo() { return tipo; }
    public String getNombreConductor() { return nombreConductor; }

    public String getIdentificacionConductor() {
        return identificacionConductor;
    }

    public String getEspacioAsignado() { return espacioAsignado; }
    public boolean isEstaDentro() { return estaDentro; }

    @Override
    public String toString() {
        return placa + " | " + tipo + " | " + nombreConductor;
    }
}
