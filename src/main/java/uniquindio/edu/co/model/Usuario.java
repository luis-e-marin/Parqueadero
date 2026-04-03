package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoUsuario;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nombre;
    private final String identificacion;
    private TipoUsuario tipoUsuario;
    private double descuento;

    private final List<Vehiculo> vehiculos;

    public Usuario(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio");
        }
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio");
        }

        this.nombre = nombre.trim();
        this.identificacion = identificacion.trim().toUpperCase();
        this.tipoUsuario = tipoUsuario;
        this.descuento = calcularDescuento(tipoUsuario);
        this.vehiculos = new ArrayList<>();
    }



    public void modificar(String nuevoNombre, TipoUsuario nuevoTipo) {
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            this.nombre = nuevoNombre.trim();
        }
        if (nuevoTipo != null) {
            this.tipoUsuario = nuevoTipo;
            this.descuento = calcularDescuento(nuevoTipo);
        }
    }

    private double calcularDescuento(TipoUsuario tipo) {
        return switch (tipo) {
            case ESTUDIANTE     -> 20.0;
            case DOCENTE        -> 15.0;
            case ADMINISTRATIVO -> 10.0;
            case VISITANTE      -> 0.0;
        };
    }



    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser null");
        }
        // Verificar que el vehículo pertenezca a este usuario
        if (!vehiculo.getIdentificacionConductor().equals(this.identificacion)) {
            throw new IllegalArgumentException("Este vehículo no pertenece a este usuario");
        }
        // Evitar duplicados de placa
        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("El usuario ya tiene un vehículo con placa " + vehiculo.getPlaca());
        }

        vehiculos.add(vehiculo);
    }

    public void eliminarVehiculo(String placa) {
        vehiculos.removeIf(vehiculo -> vehiculo.getPlaca().equalsIgnoreCase(placa));
    }

    public Vehiculo buscarVehiculo(String placa) {
        return vehiculos.stream()
                .filter(vehiculo -> vehiculo.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElse(null);
    }

    //  BÚSQUEDA

    public boolean tieneVehiculoConPlaca(String placa) {
        return buscarVehiculo(placa) != null;
    }

    //  GETTERS

    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public double getDescuento() { return descuento; }
    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos); // copia para proteger la lista original
    }

    @Override
    public String toString() {
        return nombre + " (" + identificacion + ") - " + tipoUsuario +
                " | Vehículos: " + vehiculos.size() +
                " | Descuento: " + descuento + "%";
    }
}