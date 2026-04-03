package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;

public class Tarifa {

    private final TipoVehiculo tipoVehiculo;
    private final double valorPorHora;

    public Tarifa(TipoVehiculo tipoVehiculo, double valorPorHora) {
        if (tipoVehiculo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }
        if (valorPorHora <= 0) {
            throw new IllegalArgumentException("El valor por hora debe ser mayor a 0");
        }

        this.tipoVehiculo = tipoVehiculo;
        this.valorPorHora = valorPorHora;
    }

    // Valor base sin ningún descuento
    public double calcularValor(long minutos) {
        if (minutos <= 0) return 0.0;
        double horas = Math.ceil(minutos / 60.0);
        return horas * valorPorHora;
    }

    // Valor con descuento según Tipo de Usuario (ESTO ES LO QUE NECESITAS)
    public double calcularValorConDescuento(long minutos, TipoUsuario tipoUsuario) {
        double valorBase = calcularValor(minutos);

        if (tipoUsuario == null) {
            return valorBase;
        }

        double descuento = switch (tipoUsuario) {
            case ESTUDIANTE -> 0.20;      // 20%
            case DOCENTE -> 0.15;         // 15%
            case ADMINISTRATIVO -> 0.10;  // 10%
            case VISITANTE -> 0.0;        // 0%
        };

        return valorBase * (1 - descuento);
    }

    // Getters
    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    @Override
    public String toString() {
        return tipoVehiculo + " → $" + valorPorHora + "/hora";
    }
}