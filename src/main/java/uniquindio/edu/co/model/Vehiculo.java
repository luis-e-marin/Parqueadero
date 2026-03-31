package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;
import java.time.LocalDateTime;
import java.time.Duration;

public class Vehiculo {
    private final String placa;                    // final: la placa no cambia
    private final TipoVehiculo tipo;               // final: el tipo no cambia
    private String nombreConductor;
    private String identificacionConductor;
    private final LocalDateTime horaIngreso;       // final: la hora de ingreso no debe cambiar
    private String espacioAsignado;
    private boolean estaDentro;

    public Vehiculo(String placa, TipoVehiculo tipo, String nombreConductor,
                    String identificacionConductor, String espacioAsignado) {

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }
        if (nombreConductor == null || nombreConductor.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del conductor es obligatorio");
        }

        this.placa = placa.toUpperCase().trim();
        this.tipo = tipo;
        this.nombreConductor = nombreConductor.trim();
        this.identificacionConductor = identificacionConductor != null ? identificacionConductor.trim() : "";
        this.espacioAsignado = espacioAsignado != null ? espacioAsignado.trim() : "";
        this.horaIngreso = LocalDateTime.now();
        this.estaDentro = true;
    }

    // ====================== GETTERS ======================
    public String getPlaca() {
        return placa;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public String getIdentificacionConductor() {
        return identificacionConductor;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public String getEspacioAsignado() {
        return espacioAsignado;
    }

    public boolean isEstaDentro() {
        return estaDentro;
    }

    // ====================== SETTERS CONTROLADOS ======================
    public void setNombreConductor(String nombreConductor) {
        if (nombreConductor == null || nombreConductor.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del conductor no puede estar vacío");
        }
        this.nombreConductor = nombreConductor.trim();
    }

    public void setIdentificacionConductor(String identificacionConductor) {
        this.identificacionConductor = identificacionConductor != null ? identificacionConductor.trim() : "";
    }

    public void setEspacioAsignado(String espacioAsignado) {
        this.espacioAsignado = espacioAsignado != null ? espacioAsignado.trim() : "";
    }

    public void setEstaDentro(boolean estaDentro) {
        this.estaDentro = estaDentro;
    }

    // ====================== MÉTODOS DE NEGOCIO ======================
    public long getMinutosPermanencia() {
        if (!estaDentro) {
            return 0;
        }
        return Duration.between(horaIngreso, LocalDateTime.now()).toMinutes();
    }

    public String getTiempoPermanenciaFormateado() {
        if (!estaDentro) {
            return "Ya salió";
        }

        long minutosTotal = getMinutosPermanencia();
        long horas = minutosTotal / 60;
        long minutos = minutosTotal % 60;

        if (horas > 0 && minutos > 0) {
            return horas + " horas y " + minutos + " minutos";
        } else if (horas > 0) {
            return horas + " horas";
        } else {
            return minutos + " minutos";
        }
    }

    @Override
    public String toString() {
        return placa + " | " + tipo + " | " + nombreConductor +
                " | Espacio: " + espacioAsignado +
                " | Tiempo: " + getTiempoPermanenciaFormateado();
    }
}
