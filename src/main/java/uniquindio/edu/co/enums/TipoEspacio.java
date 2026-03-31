package uniquindio.edu.co.enums;

public enum TipoEspacio {
    CARRO("Carro"),
    MOTO("Moto"),
    BICICLETA("Bicicleta");

    private final String nombre;

    TipoEspacio(String nombre) {
        this.nombre = nombre;
        }

        @Override
        public String toString() {
        return nombre;
    }
}
