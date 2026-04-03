package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.Rol;
import uniquindio.edu.co.enums.TipoUsuario;
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
        for (int j = 1; j <= 8; j++)  espacios.add(new Espacio("M" + j, TipoVehiculo.MOTO));
        for (int k = 1; k <= 5; k++)  espacios.add(new Espacio("B" + k, TipoVehiculo.BICICLETA));
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

    // ====================== REGISTRAR INGRESO CON TIPO DE USUARIO ======================
    public void registrarIngreso(String placa, TipoVehiculo tipoVehiculo, String nombreConductor,
                                 String identificacionConductor, TipoUsuario tipoUsuario) throws Exception {

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía");
        }
        if (tipoVehiculo == null) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de vehículo");
        }
        if (nombreConductor == null || nombreConductor.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del conductor es obligatorio");
        }
        if (identificacionConductor == null || identificacionConductor.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("Debe seleccionar tipo de usuario");
        }

        placa = placa.trim().toUpperCase();

        String finalPlaca = placa;
        if (vehiculosDentro.stream().anyMatch(v -> v.getPlaca().equals(finalPlaca))) {
            throw new PlacaDuplicadaException(placa);
        }

        Espacio espacio = espacios.stream()
                .filter(e -> e.estaDisponible() && e.getTipo() == tipoVehiculo)
                .findFirst()
                .orElseThrow(() -> new SinEspaciosException(tipoVehiculo));

        Vehiculo vehiculo = new Vehiculo(placa, tipoVehiculo, nombreConductor.trim(), identificacionConductor.trim());
        vehiculo.registrarIngreso(espacio.getCodigo());
        espacio.ocupar(vehiculo);
        vehiculosDentro.add(vehiculo);

        // ← ESTO ES CLAVE: Guardamos el tipo de usuario en el propietario
        Usuario propietario = buscarUsuarioPorIdentificacion(identificacionConductor);
        if (propietario != null) {
            propietario.setTipoUsuario(tipoUsuario);   // actualizamos el tipo
            propietario.agregarVehiculo(vehiculo);
        }

        System.out.println("✓ Ingreso registrado: " + placa + " | Tipo usuario: " + tipoUsuario);
    }

    // ====================== REGISTRAR SALIDA CON DESCUENTO ======================
    public double registrarSalida(String placa) throws Exception {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía");
        }

        final String placaFinal = placa.trim().toUpperCase();

        Vehiculo vehiculo = vehiculosDentro.stream()
                .filter(v -> v.getPlaca().equals(placaFinal))
                .findFirst()
                .orElseThrow(() -> new VehiculoNoEncontradoException(placaFinal));

        Tarifa tarifa = switch (vehiculo.getTipo()) {
            case CARRO -> tarifaCarro;
            case MOTO -> tarifaMoto;
            case BICICLETA -> tarifaBicicleta;
        };

        long minutos = vehiculo.getMinutosPermanencia();

        // Aplicar descuento según tipo de usuario del propietario
        Usuario propietario = buscarUsuarioPorIdentificacion(vehiculo.getIdentificacionConductor());
        double valor = (propietario != null && propietario.getTipoUsuario() != null)
                ? tarifa.calcularValorConDescuento(minutos, propietario.getTipoUsuario())
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

        System.out.println("✓ Salida: " + placaFinal + " | Valor final: $" + valor);
        return valor;
    }

    // ====================== BUSCAR USUARIO ======================
    public Usuario buscarUsuarioPorPlaca(String placa) {
        if (placa == null) return null;
        placa = placa.trim().toUpperCase();
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.tieneVehiculoConPlaca(placa)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorIdentificacion(String identificacion) {
        if (identificacion == null) return null;
        return usuariosRegistrados.stream()
                .filter(u -> u.getIdentificacion().equalsIgnoreCase(identificacion))
                .findFirst()
                .orElse(null);
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuario == null) return;
        if (buscarUsuarioPorIdentificacion(usuario.getIdentificacion()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con esa identificación");
        }
        usuariosRegistrados.add(usuario);
    }

    // ====================== CONSULTAS PARA OPERADOR ======================
    public String getVehiculosDentroResumen() {
        if (vehiculosDentro.isEmpty()) {
            return "No hay vehículos dentro del parqueadero en este momento.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("VEHÍCULOS DENTRO DEL PARQUEADERO\n");
        sb.append("Total: ").append(vehiculosDentro.size()).append("\n\n");

        for (Vehiculo v : vehiculosDentro) {
            sb.append("Placa: ").append(v.getPlaca())
                    .append(" | Tipo: ").append(v.getTipo())
                    .append(" | Conductor: ").append(v.getNombreConductor())
                    .append(" | ID: ").append(v.getIdentificacionConductor())
                    .append(" | Espacio: ").append(v.getEspacioAsignado() != null ? v.getEspacioAsignado() : "N/A")
                    .append(" | Tiempo: ").append(v.getTiempoPermanenciaFormateado())
                    .append("\n");
        }
        return sb.toString();
    }

    public String getEspaciosDisponiblesResumen() {
        long total = espacios.size();
        long ocupados = espacios.stream().filter(e -> !e.estaDisponible()).count();
        long disponibles = total - ocupados;

        long carrosDisp = espacios.stream().filter(e -> e.getTipo() == TipoVehiculo.CARRO && e.estaDisponible()).count();
        long motosDisp = espacios.stream().filter(e -> e.getTipo() == TipoVehiculo.MOTO && e.estaDisponible()).count();
        long bicisDisp = espacios.stream().filter(e -> e.getTipo() == TipoVehiculo.BICICLETA && e.estaDisponible()).count();

        return "RESUMEN DE ESPACIOS\n\n" +
                "Total espacios: " + total + "\n" +
                "Ocupados: " + ocupados + "\n" +
                "Disponibles: " + disponibles + "\n\n" +
                "Carros disponibles: " + carrosDisp + "\n" +
                "Motos disponibles: " + motosDisp + "\n" +
                "Bicicletas disponibles: " + bicisDisp;
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
        long totalMinutos = vehiculosDentro.stream()
                .mapToLong(Vehiculo::getMinutosPermanencia)
                .sum();
        long promedio = totalMinutos / vehiculosDentro.size();
        long horas = promedio / 60;
        long mins = promedio % 60;
        return horas + "h " + mins + "m";
    }

    public boolean deshabilitarEspacio(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        for (Espacio espacio : espacios) {
            if (espacio.getCodigo().equalsIgnoreCase(codigo)) {
                // No se puede deshabilitar si está ocupado
                if (!espacio.estaDisponible()) {
                    return false;
                }
                return espacio.deshabilitar();
            }
        }
        return false; // no se encontró el espacio
    }

    public void habilitarEspacio(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del espacio no puede estar vacío");
        }

        for (Espacio espacio : espacios) {
            if (espacio.getCodigo().equalsIgnoreCase(codigo)) {
                espacio.habilitar();
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró el espacio con código: " + codigo);
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
}