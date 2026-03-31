package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;

public class Tarifa {
    private final TipoVehiculo tipo;
    private double valorPorHora;
    private double descuentoPorcentaje;   // 0 = sin descuento

    public Tarifa(TipoVehiculo tipo, double valorPorHora) {
        this(tipo, valorPorHora, 0.0);
    }

    public Tarifa(TipoVehiculo tipo, double valorPorHora, double descuentoPorcentaje) {
        if (valorPorHora <= 0) {
            throw new IllegalArgumentException("El valor por hora debe ser mayor a 0");
        }
        if (descuentoPorcentaje < 0 || descuentoPorcentaje > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }

        this.tipo = tipo;
        this.valorPorHora = valorPorHora;
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    public double calcularValor(long minutosPermanencia) {
        if (minutosPermanencia <= 0) return 0.0;
        double horas = Math.ceil(minutosPermanencia / 60.0);
        double valorBruto = horas * valorPorHora;
        double descuento = valorBruto * (descuentoPorcentaje / 100.0);
        return valorBruto - descuento;
    }

    // Getters
    public TipoVehiculo getTipo() {
        return tipo;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public double getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    @Override
    public String toString() {
        return tipo + " → $" + valorPorHora + "/hora"
                + (descuentoPorcentaje > 0 ? " (Descuento " + descuentoPorcentaje + "%)" : "");
    }
}
