package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;

public class Tarifa {

    private final TipoVehiculo tipoVehiculo;
    private double valorPorHora;
    private double descuentoPorcentaje;

    public Tarifa(TipoVehiculo tipoVehiculo, double valorPorHora) {
        this(tipoVehiculo, valorPorHora, 0.0);
    }

    public Tarifa(TipoVehiculo tipoVehiculo, double valorPorHora, double descuentoPorcentaje) {
        if (tipoVehiculo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }
        if (valorPorHora <= 0) {
            throw new IllegalArgumentException("El valor por hora debe ser mayor a 0");
        }
        if (descuentoPorcentaje < 0 || descuentoPorcentaje > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }

        this.tipoVehiculo = tipoVehiculo;
        this.valorPorHora = valorPorHora;
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    /**
     * Calcula el valor a pagar según minutos de permanencia
     * Aplica el descuento de la tarifa
     */
    public double calcularValor(long minutosPermanencia) {
        if (minutosPermanencia <= 0) {
            return 0.0;
        }

        // Redondea hacia arriba las horas
        double horas = Math.ceil(minutosPermanencia / 60.0);
        double valorBruto = horas * valorPorHora;

        // Aplicar descuento de esta tarifa
        double descuento = valorBruto * (descuentoPorcentaje / 100.0);

        return valorBruto - descuento;
    }

    /**
     * Calcula el valor aplicando también el descuento del usuario
     */
    public double calcularValorConDescuentoUsuario(long minutosPermanencia, double descuentoUsuario) {
        double valor = calcularValor(minutosPermanencia);

        if (descuentoUsuario > 0) {
            double descuentoAdicional = valor * (descuentoUsuario / 100.0);
            valor -= descuentoAdicional;
        }

        return Math.max(valor, 0.0);
    }

    //  GETTERS

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public double getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setValorPorHora(double valorPorHora) {
        if (valorPorHora <= 0) {
            throw new IllegalArgumentException("El valor por hora debe ser mayor a 0");
        }
        this.valorPorHora = valorPorHora;
    }

    public void setDescuentoPorcentaje(double descuentoPorcentaje) {
        if (descuentoPorcentaje < 0 || descuentoPorcentaje > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    @Override
    public String toString() {
        return tipoVehiculo + " → $" + valorPorHora + "/hora" +
                (descuentoPorcentaje > 0 ? " (Descuento " + descuentoPorcentaje + "%)" : "");
    }
}