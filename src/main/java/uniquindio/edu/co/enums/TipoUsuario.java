package uniquindio.edu.co.enums;

public enum TipoUsuario {
    ESTUDIANTE("Estudiante"),
    DOCENTE("Docente"),
    ADMINISTRATIVO("Administrativo"),
    VISITANTE("Visitante");

    private final String nombre;

    TipoUsuario(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
