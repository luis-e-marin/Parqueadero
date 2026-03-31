package uniquindio.edu.co.enums;

public enum EstadoEspacio {
    DISPONIBLE(true, "Disponible"),
    OCUPADO(false, "Ocupado"),
    FUERA_DE_SERVICIO(false, "Fuera de servicio");

    private final boolean disponibleParaAsignar;
    private final String nombreMostrar;

    EstadoEspacio(boolean disponibleParaAsignar, String nombreMostrar) {
        this.disponibleParaAsignar = disponibleParaAsignar;
        this.nombreMostrar = nombreMostrar;
    }


    public boolean isDisponibleParaAsignar() {
        return disponibleParaAsignar;
    }


    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public boolean puedeRecibirVehiculo() {
        return this == DISPONIBLE;
    }
}
