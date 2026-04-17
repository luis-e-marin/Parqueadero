package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.enums.EstadoEspacio;
import uniquindio.edu.co.exceptions.EspacioNoDisponibleException;

public class Espacio {

    private final String codigo;
    private final TipoVehiculo tipo;
    private EstadoEspacio estado;
    private Vehiculo vehiculoAsignado;

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

    public boolean estaDisponible() {
        return estado == EstadoEspacio.DISPONIBLE;
    }

    public boolean estaOcupado() {
        return estado == EstadoEspacio.OCUPADO;
    }

    public boolean estaFueraDeServicio() {
        return estado == EstadoEspacio.FUERA_DE_SERVICIO;
    }


    public void ocupar(Vehiculo vehiculo) throws EspacioNoDisponibleException {
        if (!estaDisponible()) {
            throw new EspacioNoDisponibleException(
                    "El espacio " + codigo + " no está disponible. Estado actual: " + estado);
        }
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser null");
        }

        this.vehiculoAsignado = vehiculo;
        this.estado = EstadoEspacio.OCUPADO;
    }


    public void liberar() throws EspacioNoDisponibleException {
        if (!estaOcupado()) {
            throw new EspacioNoDisponibleException(
                    "El espacio " + codigo + " no está ocupado. Estado actual: " + estado);
        }

        this.vehiculoAsignado = null;
        this.estado = EstadoEspacio.DISPONIBLE;
    }


    public boolean deshabilitar() {
        if (estaOcupado()) {
            return false;
        }
        if (estaFueraDeServicio()) {
            return false;
        }

        this.estado = EstadoEspacio.FUERA_DE_SERVICIO;
        return true;
    }


    public void habilitar() {
        if (!estaFueraDeServicio()) {
            throw new IllegalStateException("El espacio " + codigo + " no está en mantenimiento");
        }

        this.estado = (vehiculoAsignado != null)
                ? EstadoEspacio.OCUPADO
                : EstadoEspacio.DISPONIBLE;
    }

    // GETTERS

    public String getCodigo() {
        return codigo;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public EstadoEspacio getEstado() {
        return estado;
    }

    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(codigo).append(" - ").append(tipo).append(" (").append(estado).append(")");

        if (vehiculoAsignado != null) {
            sb.append(" ---> ").append(vehiculoAsignado.getPlaca());
        }
        return sb.toString();
    }
}
