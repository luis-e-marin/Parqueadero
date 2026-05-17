package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoUsuario;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nombre;
    private final String identificacion;
    private TipoUsuario tipoUsuario;

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
        this.vehiculos = new ArrayList<>();
    }

    public void modificar(String nuevoNombre, TipoUsuario nuevoTipo) {
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            this.nombre = nuevoNombre.trim();
        }
        if (nuevoTipo != null) {
            this.tipoUsuario = nuevoTipo;
        }
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser null");
        }
        if (!vehiculo.getIdentificacionConductor().equals(this.identificacion)) {
            throw new IllegalArgumentException("Este vehículo no pertenece a este usuario");
        }
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

    public boolean tieneVehiculoConPlaca(String placa) {
        return buscarVehiculo(placa) != null;
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

    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos);
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        if (tipoUsuario != null) {
            this.tipoUsuario = tipoUsuario;
        }
    }

    @Override
    public String toString() {
        return nombre + " (" + identificacion + ") - " + tipoUsuario +
                " | Vehículos: " + vehiculos.size();
    }


}
