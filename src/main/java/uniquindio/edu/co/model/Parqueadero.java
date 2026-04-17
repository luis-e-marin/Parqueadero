package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.Rol;
import uniquindio.edu.co.enums.TipoUsuario;
import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Parqueadero {

    private static Parqueadero instancia;

    private final List<Espacio> espacios = new ArrayList<>();
    private final List<Vehiculo> vehiculosDentro = new ArrayList<>();
    private final List<Cuenta> cuentas = new ArrayList<>();
    private final List<Usuario> usuariosRegistrados = new ArrayList<>();

    private Tarifa tarifaCarro;
    private Tarifa tarifaMoto;
    private Tarifa tarifaBicicleta;

    public static Parqueadero getInstance() {
        if (instancia == null) {
            instancia = new Parqueadero();
        }
        return instancia;
    }

    private Parqueadero() {
        inicializarEspacios();
        inicializarTarifas();
        inicializarCuentasPorDefecto();
    }

    private void inicializarEspacios() {
        for (int i = 1; i <= 10; i++) espacios.add(new Espacio("C" + i, TipoVehiculo.CARRO));
        for (int j = 1; j <= 8; j++) espacios.add(new Espacio("M" + j, TipoVehiculo.MOTO));
        for (int k = 1; k <= 5; k++) espacios.add(new Espacio("B" + k, TipoVehiculo.BICICLETA));
    }

    private void inicializarTarifas() {
        tarifaCarro = new Tarifa(TipoVehiculo.CARRO, 5000);
        tarifaMoto = new Tarifa(TipoVehiculo.MOTO, 2500);
        tarifaBicicleta = new Tarifa(TipoVehiculo.BICICLETA, 1000);
    }

    private void inicializarCuentasPorDefecto() {
        cuentas.add(new Cuenta("admin", "admin", Rol.ADMINISTRADOR, null));
        cuentas.add(new Cuenta("operador", "1234", Rol.OPERADOR, null));
    }

    public Cuenta login(String username, String password) throws UsuarioNoAutorizadoException {
        return cuentas.stream()
                .filter(c -> c.intentarLogin(username, password))
                .findFirst()
                .orElseThrow(() -> new UsuarioNoAutorizadoException("Credenciales incorrectas"));
    }

    // ====================== OPERADOR ======================
    public void registrarIngreso(String placa, TipoVehiculo tipo, String nombre,
                                 String id, TipoUsuario tipoUsuario) throws Exception {

        placa = placa.trim().toUpperCase();

        // Verificar placa duplicada
        String finalPlaca = placa;
        if (vehiculosDentro.stream().anyMatch(v -> v.getPlaca().equals(finalPlaca))) {
            throw new PlacaDuplicadaException(placa);
        }

        // Buscar o crear usuario
        Usuario usuario = buscarUsuarioPorIdentificacion(id);
        if (usuario == null) {
            usuario = new Usuario(nombre, id, tipoUsuario);
            usuariosRegistrados.add(usuario);
        } else {
            if (!usuario.getNombre().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Identificación ya registrada con otro nombre");
            }
            usuario.setTipoUsuario(tipoUsuario);  // actualiza el tipo si cambió
        }

        // Buscar espacio disponible del tipo correcto
        Espacio espacio = espacios.stream()
                .filter(e -> e.estaDisponible() && e.getTipo() == tipo)
                .findFirst()
                .orElseThrow(() -> new SinEspaciosException(tipo));

        // Crear y registrar vehículo
        Vehiculo v = new Vehiculo(placa, tipo, nombre, id);
        v.registrarIngreso(espacio.getCodigo());

        espacio.ocupar(v);
        vehiculosDentro.add(v);
        usuario.agregarVehiculo(v);
    }

    public double registrarSalida(String placa) throws Exception {
        placa = placa.trim().toUpperCase();

        // Buscar el vehículo
        String finalPlaca = placa;
        Vehiculo vehiculo = vehiculosDentro.stream()
                .filter(vehiculos -> vehiculos.getPlaca().equals(finalPlaca))
                .findFirst()
                .orElseThrow(() -> new VehiculoNoEncontradoException(finalPlaca));

        // Obtener la tarifa según tipo
        Tarifa tarifa = switch (vehiculo.getTipo()) {
            case CARRO -> tarifaCarro;
            case MOTO -> tarifaMoto;
            case BICICLETA -> tarifaBicicleta;
        };

        long minutos = vehiculo.getMinutosPermanencia();

        // Buscar si tiene usuario registrado para aplicar descuento
        Usuario usuario = buscarUsuarioPorIdentificacion(vehiculo.getIdentificacionConductor());

        // Calcular valor (con o sin descuento)
        double valor = (usuario != null && usuario.getTipoUsuario() != null)
                ? tarifa.calcularValorConDescuento(minutos, usuario.getTipoUsuario())
                : tarifa.calcularValor(minutos);

        // Liberar el espacio
        espacios.stream()
                .filter(e -> e.getCodigo().equals(vehiculo.getEspacioAsignado()))
                .findFirst()
                .ifPresent(e -> {
                    try {
                        e.liberar();
                    } catch (Exception ignored) {}
                });

        // Quitar del parqueadero y registrar salida
        vehiculosDentro.remove(vehiculo);
        vehiculo.registrarSalida();

        return valor;
    }

    // ====================== BÚSQUEDAS ======================
    public Usuario buscarUsuarioPorPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) return null;
        placa = placa.trim().toUpperCase();
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.tieneVehiculoConPlaca(placa)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorIdentificacion(String id) {
        if (id == null || id.trim().isEmpty()) return null;
        return usuariosRegistrados.stream()
                .filter(u -> u.getIdentificacion().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // ====================== ADMIN ======================
    public void actualizarTarifa(TipoVehiculo tipo, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor por hora debe ser mayor a 0");
        }

        switch (tipo) {
            case CARRO:
                tarifaCarro = new Tarifa(TipoVehiculo.CARRO, valor);
                break;
            case MOTO:
                tarifaMoto = new Tarifa(TipoVehiculo.MOTO, valor);
                break;
            case BICICLETA:
                tarifaBicicleta = new Tarifa(TipoVehiculo.BICICLETA, valor);
                break;
            default:
                throw new IllegalArgumentException("Tipo de vehículo no válido");
        }
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
        throw new IllegalArgumentException("Espacio no encontrado");
    }

    public void registrarUsuarioAutorizado(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        if (buscarUsuarioPorIdentificacion(identificacion) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con esa identificación");
        }
        usuariosRegistrados.add(new Usuario(nombre, identificacion, tipoUsuario));
    }

    // ====================== CONSULTAS ======================
    public String getVehiculosDentroResumen() {
        if (vehiculosDentro.isEmpty()) {
            return "No hay vehículos dentro del parqueadero.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("VEHÍCULOS DENTRO DEL PARQUEADERO\n");
        sb.append("Total: ").append(vehiculosDentro.size()).append("\n\n");

        for (Vehiculo v : vehiculosDentro) {
            sb.append("Placa: ").append(v.getPlaca())
                    .append(" | Tipo: ").append(v.getTipo())
                    .append(" | Conductor: ").append(v.getNombreConductor())
                    .append(" | ID: ").append(v.getIdentificacionConductor())
                    .append(" | Espacio: ").append(v.getEspacioAsignado())
                    .append(" | Tiempo: ").append(v.getTiempoPermanenciaFormateado())
                    .append("\n");
        }
        return sb.toString();
    }

    public String getEspaciosDisponiblesResumen() {
        long total = espacios.size();
        long ocupados = espacios.stream().filter(e -> !e.estaDisponible()).count();
        long disponibles = total - ocupados;

        long carros = espacios.stream().filter(e -> e.getTipo() == TipoVehiculo.CARRO && e.estaDisponible()).count();
        long motos = espacios.stream().filter(e -> e.getTipo() == TipoVehiculo.MOTO && e.estaDisponible()).count();
        long bicis = espacios.stream().filter(e -> e.getTipo() == TipoVehiculo.BICICLETA && e.estaDisponible()).count();

        return "RESUMEN DE ESPACIOS\n\n" +
                "Total: " + total + "\n" +
                "Ocupados: " + ocupados + "\n" +
                "Disponibles: " + disponibles + "\n\n" +
                "Carros disponibles: " + carros + "\n" +
                "Motos disponibles: " + motos + "\n" +
                "Bicicletas disponibles: " + bicis;
    }


    public String generarReporteSimple() {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE SIMPLE\n\n");
        sb.append("Vehículos dentro ahora: ").append(vehiculosDentro.size()).append("\n");
        sb.append("Espacios totales: ").append(espacios.size()).append("\n");
        sb.append("Espacios ocupados: ").append(espacios.stream().filter(e -> !e.estaDisponible()).count()).append("\n");
        sb.append("Tiempo promedio permanencia: ").append(calcularTiempoPromedio()).append("\n");
        return sb.toString();
    }

    private String calcularTiempoPromedio() {
        if (vehiculosDentro.isEmpty()) return "0h 0m";
        long total = vehiculosDentro.stream().mapToLong(Vehiculo::getMinutosPermanencia).sum();
        long prom = total / vehiculosDentro.size();
        return (prom / 60) + "h " + (prom % 60) + "m";
    }

    public List<Espacio> getEspacios() {
        return new ArrayList<>(espacios);
    }

    public List<Vehiculo> getVehiculosDentro() {
        return new ArrayList<>(vehiculosDentro);
    }

    public List<Usuario> getUsuariosRegistrados() {
        return new ArrayList<>(usuariosRegistrados);
    }

    // ====================== REPORTE ADMIN (CORREGIDO) ======================
    public String generarReporteAdmin() {
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════\n");
        sb.append("     REPORTE GENERAL - ADMINISTRADOR\n");
        sb.append("══════════════════════════════════════\n\n");

        sb.append("Usuarios registrados: ").append(usuariosRegistrados.size()).append("\n");
        sb.append("Vehículos dentro: ").append(vehiculosDentro.size()).append("\n");
        sb.append("Espacios totales: ").append(espacios.size()).append("\n");
        sb.append("Ocupados: ").append(espacios.stream().filter(e -> !e.estaDisponible()).count()).append("\n");
        sb.append("Disponibles: ").append(espacios.size() - espacios.stream().filter(e -> !e.estaDisponible()).count()).append("\n\n");

        sb.append("Tiempo promedio: ").append(calcularTiempoPromedio()).append("\n");

        long masDe3Horas = vehiculosDentro.stream().filter(v -> v.getMinutosPermanencia() > 180).count();
        sb.append("Vehículos > 3 horas: ").append(masDe3Horas).append("\n\n");

        // Cálculo correcto de ingresos usando las tarifas ACTUALES
        double ingresos = 0;
        for (Vehiculo v : vehiculosDentro) {
            Usuario p = buscarUsuarioPorIdentificacion(v.getIdentificacionConductor());
            Tarifa t = switch (v.getTipo()) {
                case CARRO -> tarifaCarro;
                case MOTO -> tarifaMoto;
                case BICICLETA -> tarifaBicicleta;
            };
            double valor = (p != null && p.getTipoUsuario() != null)
                    ? t.calcularValorConDescuento(v.getMinutosPermanencia(), p.getTipoUsuario())
                    : t.calcularValor(v.getMinutosPermanencia());
            ingresos += valor;
        }
        sb.append("Ingresos estimados (vehículos actuales): $").append(String.format("%.0f", ingresos)).append("\n");
        sb.append("══════════════════════════════════════");
        return sb.toString();
    }

}