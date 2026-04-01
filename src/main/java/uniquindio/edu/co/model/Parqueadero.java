package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.Rol;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Parqueadero {

    private static Parqueadero instancia;

    private final List<Espacio> espacios;
    private final List<Vehiculo> vehiculosDentro;
    private final List<Cuenta> cuentas;
    private final List<Usuario> usuariosRegistrados;

    private  Tarifa tarifaCarro;
    private  Tarifa tarifaMoto;
    private  Tarifa tarifaBicicleta;


    public static Parqueadero getInstance() {
        if (instancia == null) {
            instancia = new Parqueadero();
        }
        return instancia;
    }

    private Parqueadero() {
        this.espacios = new ArrayList<>();
        this.vehiculosDentro = new ArrayList<>();
        this.cuentas = new ArrayList<>();
        this.usuariosRegistrados = new ArrayList<>();

        inicializarEspacios();
        inicializarTarifas();
        inicializarCuentasPorDefecto();
    }

    private void inicializarEspacios() {
        for (int i = 1; i <= 10; i++) espacios.add(new Espacio("C" + i, TipoVehiculo.CARRO));
        for (int i = 1; i <= 8; i++)  espacios.add(new Espacio("M" + i, TipoVehiculo.MOTO));
        for (int i = 1; i <= 5; i++)  espacios.add(new Espacio("B" + i, TipoVehiculo.BICICLETA));
    }

    private void inicializarTarifas() {
        this.tarifaCarro = new Tarifa(TipoVehiculo.CARRO, 5000);
        this.tarifaMoto = new Tarifa(TipoVehiculo.MOTO, 2500);
        this.tarifaBicicleta = new Tarifa(TipoVehiculo.BICICLETA, 1000);
    }

    private void inicializarCuentasPorDefecto() {
        cuentas.add(new Cuenta("admin", "admin", Rol.ADMINISTRADOR, null));
        cuentas.add(new Cuenta("operador", "1234", Rol.OPERADOR, null));
    }


    public Cuenta login(String username, String password) throws UsuarioNoAutorizadoException {
        for (Cuenta c : cuentas) {
            if (c.intentarLogin(username, password)) {
                return c;
            }
        }
        throw new UsuarioNoAutorizadoException("Credenciales incorrectas");
    }


    public void registrarUsuario(Usuario usuario) {
        if (buscarUsuarioPorIdentificacion(usuario.getIdentificacion()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con esa identificación");
        }
        usuariosRegistrados.add(usuario);
    }

    public Usuario buscarUsuarioPorIdentificacion(String identificacion) {
        return usuariosRegistrados.stream()
                .filter(u -> u.getIdentificacion().equalsIgnoreCase(identificacion))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarUsuarioPorPlaca(String placa) {
        for (Usuario u : usuariosRegistrados) {
            if (u.tieneVehiculoConPlaca(placa)) {
                return u;
            }
        }
        return null;
    }


    public boolean deshabilitarEspacio(String codigo) {
        for (Espacio e : espacios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e.deshabilitar();
            }
        }
        return false;
    }

    public void habilitarEspacio(String codigo) {
        for (Espacio e : espacios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                e.habilitar();
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró el espacio: " + codigo);
    }

    // INGRESO Y SALIDA
    public void registrarIngreso(Vehiculo vehiculo, Cuenta cuentaActual) throws Exception {
        if (!cuentaActual.esAdministrador() && !cuentaActual.esOperador()) {
            throw new UsuarioNoAutorizadoException("registrar ingreso");
        }

        // Validar placa duplicada
        if (vehiculosDentro.stream().anyMatch(v -> v.getPlaca().equalsIgnoreCase(vehiculo.getPlaca()))) {
            throw new PlacaDuplicadaException(vehiculo.getPlaca());
        }

        // Buscar espacio disponible del mismo tipo
        Espacio espacio = espacios.stream()
                .filter(e -> e.estaDisponible() && e.getTipo() == vehiculo.getTipo())
                .findFirst()
                .orElseThrow(() -> new SinEspaciosException(vehiculo.getTipo()));

        // Registrar ingreso
        vehiculo.registrarIngreso(espacio.getCodigo());
        espacio.ocupar(vehiculo);
        vehiculosDentro.add(vehiculo);

        // Agregar vehículo al usuario si existe
        Usuario dueno = buscarUsuarioPorIdentificacion(vehiculo.getIdUsuarioDueno());
        if (dueno != null) {
            dueno.agregarVehiculo(vehiculo);
        }
    }

    public double registrarSalida(String placa, Cuenta cuentaActual) throws Exception {
        if (!cuentaActual.esAdministrador() && !cuentaActual.esOperador()) {
            throw new UsuarioNoAutorizadoException("registrar salida");
        }

        Vehiculo vehiculo = vehiculosDentro.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElseThrow(() -> new VehiculoNoEncontradoException(placa));

        Tarifa tarifa = switch (vehiculo.getTipo()) {
            case CARRO -> tarifaCarro;
            case MOTO -> tarifaMoto;
            case BICICLETA -> tarifaBicicleta;
        };

        long minutos = vehiculo.getMinutosPermanencia();
        Usuario dueno = buscarUsuarioPorPlaca(placa);
        double valor = (dueno != null)
                ? tarifa.calcularValorConDescuentoUsuario(minutos, dueno.getDescuento())
                : tarifa.calcularValor(minutos);

        // Liberar espacio
        espacios.stream()
                .filter(e -> e.getCodigo().equals(vehiculo.getEspacioAsignado()))
                .findFirst()
                .ifPresent(e -> {
                    try { e.liberar(); } catch (Exception ignored) {}
                });

        vehiculosDentro.remove(vehiculo);
        vehiculo.registrarSalida();

        return valor;
    }

    //  GETTERS
    public List<Espacio> getEspacios() {
        return new ArrayList<>(espacios);
    }

    public List<Vehiculo> getVehiculosDentro() {
        return new ArrayList<>(vehiculosDentro);
    }

    public List<Usuario> getUsuariosRegistrados() {
        return new ArrayList<>(usuariosRegistrados);
    }
}