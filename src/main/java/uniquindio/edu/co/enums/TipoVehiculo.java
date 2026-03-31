package uniquindio.edu.co.enums;

public enum TipoVehiculo {
    CARRO("Carro"),
    MOTO("Moto"),
    BICICLETA("Bicicleta");

    private final String nombre;

    private TipoVehiculo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
