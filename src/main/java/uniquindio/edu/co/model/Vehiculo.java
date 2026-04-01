package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;
import java.time.LocalDateTime;
import java.time.Duration;

public class Vehiculo {

    private final String placa;
    private final TipoVehiculo tipo;
    private String nombreConductor;
    private String identificacionConductor;
    private String idUsuarioDueno;

    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;
    private String espacioAsignado;
    private boolean estaDentro;

    public Vehiculo(String placa, TipoVehiculo tipo, String nombreConductor,
                    String identificacionConductor, String idUsuarioDueno) {

        if (placa == null || placa.trim().isEmpty()) throw new IllegalArgumentException("Placa obligatoria");
        if (tipo == null) throw new IllegalArgumentException("Tipo de vehículo obligatorio");
        if (idUsuarioDueno == null || idUsuarioDueno.trim().isEmpty()) throw new IllegalArgumentException("Debe tener un dueño");

        this.placa = placa.toUpperCase().trim();
        this.tipo = tipo;
        this.nombreConductor = nombreConductor != null ? nombreConductor.trim() : "";
        this.identificacionConductor = identificacionConductor != null ? identificacionConductor.trim() : "";
        this.idUsuarioDueno = idUsuarioDueno.trim();
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
        return Duration.between(horaIngreso, fin).toMinutes();
    }

    public String getTiempoPermanenciaFormateado() {
        if (!estaDentro) return "Fuera del parqueadero";
        long min = getMinutosPermanencia();
        long h = min / 60;
        long m = min % 60;
        return (h > 0 ? h + "h " : "") + m + "m";
    }

    // Getters
    public String getPlaca() { return placa; }
    public TipoVehiculo getTipo() { return tipo; }
    public String getNombreConductor() { return nombreConductor; }
    public String getIdentificacionConductor() { return identificacionConductor; }
    public String getIdUsuarioDueno() { return idUsuarioDueno; }
    public String getEspacioAsignado() { return espacioAsignado; }
    public boolean isEstaDentro() { return estaDentro; }

    @Override
    public String toString() {
        return placa + " (" + tipo + ") - Dueño: " + idUsuarioDueno;
    }
}