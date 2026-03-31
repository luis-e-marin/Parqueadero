package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.EstadoEspacio;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.exceptions.EspacioNoDisponibleException;

public class Espacio {
    private final String codigo;
    private final TipoVehiculo tipo;           // según documento: tipoEspacio
    private EstadoEspacio estado;
    private Vehiculo vehiculoAsignado;         // ← ESTO ES OBLIGATORIO

    public Espacio(String codigo, TipoVehiculo tipo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del espacio no puede estar vacío");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de espacio es obligatorio");
        } 

        this.codigo = codigo.trim().toUpperCase();
        this.tipo = tipo;
        this.estado = EstadoEspacio.DISPONIBLE;
        this.vehiculoAsignado = null;
    }

    public boolean disponible() {
        return estado == EstadoEspacio.DISPONIBLE;
    }

    public void ocupar(Vehiculo vehiculo) throws EspacioNoDisponibleException {
        if (estado != EstadoEspacio.DISPONIBLE) {
            throw new EspacioNoDisponibleException(codigo, estado, "ocupar");
        }
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser null");
        }

        this.vehiculoAsignado = vehiculo;
        this.estado = EstadoEspacio.OCUPADO;
    }

    public void liberar() throws EspacioNoDisponibleException {
        if (estado != EstadoEspacio.OCUPADO) {
            throw new EspacioNoDisponibleException(codigo, estado, "liberar");
        }
        this.vehiculoAsignado = null;
        this.estado = EstadoEspacio.DISPONIBLE;
    }

    public boolean deshabilitar() {
        if (estaOcupado()) {
            System.out.println("\n⚠️  ADVERTENCIA:");
            System.out.println("   No se puede deshabilitar el espacio " + codigo);
            System.out.println("   Está ocupado por el vehículo con placa: " + vehiculoAsignado.getPlaca());
            System.out.println("   Primero registre la salida de ese vehículo.");
            return false;
        }

        if (estado == EstadoEspacio.FUERA_DE_SERVICIO) {
            System.out.println("El espacio " + codigo + " ya está en mantenimiento.");
            return false;
        }

        this.estado = EstadoEspacio.FUERA_DE_SERVICIO;
        System.out.println("✓ Espacio " + codigo + " deshabilitado por mantenimiento.");
        return true;
    }

    public void habilitar() {
        if (estado != EstadoEspacio.FUERA_DE_SERVICIO) {
            throw new IllegalStateException("El espacio " + codigo + " no está en mantenimiento.");
        }

        this.estado = (vehiculoAsignado != null) ? EstadoEspacio.OCUPADO : EstadoEspacio.DISPONIBLE;
        System.out.println("✓ Espacio " + codigo + " habilitado nuevamente.");
    }

    // ====================== GETTERS ======================
    public String getCodigo() {
        return codigo;
    }

    public TipoVehiculo getTipo() {        // puedes cambiar a getTipoEspacio() si quieres
        return tipo;
    }

    public EstadoEspacio getEstado() {
        return estado;
    }

    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    public boolean estaOcupado() {
        return estado == EstadoEspacio.OCUPADO;
    }

    @Override
    public String toString() {
        String info = codigo + " - " + tipo + " (" + estado + ")";
        if (vehiculoAsignado != null) {
            info += " → " + vehiculoAsignado.getPlaca();
        }
        return info;
    }
}
