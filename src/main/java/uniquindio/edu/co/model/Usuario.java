package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoUsuario;

public class Usuario {
    private final String nombre;
    private final String identificacion;
    private final TipoUsuario tipoUsuario;
    private double descuento;

    public Usuario(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede estar vacía");
        }
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio");
        }

        this.nombre = nombre.trim();
        this.identificacion = identificacion.trim();
        this.tipoUsuario = tipoUsuario;
        this.descuento = calcularDescuentoPorDefecto(tipoUsuario);
    }

    private double calcularDescuentoPorDefecto(TipoUsuario tipo) {
        return switch (tipo) {
            case ESTUDIANTE -> 20.0;      // 20% descuento
            case DOCENTE -> 15.0;         // 15% descuento
            case ADMINISTRATIVO -> 10.0;  // 10% descuento
            case VISITANTE -> 0.0;        // sin descuento
        };
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return nombre + " | " + identificacion + " | " + tipoUsuario
                + " (Descuento: " + descuento + "%)";
    }
}

