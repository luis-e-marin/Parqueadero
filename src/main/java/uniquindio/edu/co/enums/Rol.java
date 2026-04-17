package uniquindio.edu.co.enums;

public enum Rol {
    ADMINISTRADOR("Administrador"),
    OPERADOR("Operador");

    private final String nombre;

    Rol(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public boolean esAdministrador() {
        return this == ADMINISTRADOR;
    }

    public boolean esOperador() {
        return this == OPERADOR;
    }
}