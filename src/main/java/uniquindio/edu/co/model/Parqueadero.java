package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;
import uniquindio.edu.co.exceptions.EspacioNoDisponibleException;
import uniquindio.edu.co.exceptions.PlacaDuplicadaException;
import uniquindio.edu.co.exceptions.SinEspaciosException;
import uniquindio.edu.co.exceptions.VehiculoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;

public class Parqueadero {

    private final List<Espacio> espacios;
    private final List<Vehiculo> vehiculosDentro;
    private final GestorTarifas gestorTarifas;

    public Parqueadero() {
        this.espacios = new ArrayList<>();
        this.vehiculosDentro = new ArrayList<>();
        this.gestorTarifas = new GestorTarifas();
        inicializarEspacios();
    }

    private void inicializarEspacios() {
        for (int i = 1; i <= 5; i++) {
            espacios.add(new Espacio("C" + i, TipoVehiculo.CARRO));
        }
        for (int i = 1; i <= 5; i++) {
            espacios.add(new Espacio("M" + i, TipoVehiculo.MOTO));
        }
        for (int i = 1; i <= 3; i++) {
            espacios.add(new Espacio("B" + i, TipoVehiculo.BICICLETA));
        }
    }


    public void registrarIngreso(Vehiculo vehiculo)
            throws EspacioNoDisponibleException, PlacaDuplicadaException, SinEspaciosException {

        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser null");
        }

        validarPlacaDuplicada(vehiculo.getPlaca());

        Espacio espacioDisponible = null;
        for (Espacio e : espacios) {
            if (e.disponible() && e.getTipo() == vehiculo.getTipo()) {
                espacioDisponible = e;
                break;
            }
        }

        if (espacioDisponible == null) {
            throw new SinEspaciosException("No hay espacios disponibles para vehículos tipo " + vehiculo.getTipo());
        }

        espacioDisponible.ocupar(vehiculo);
        vehiculo.setEspacioAsignado(espacioDisponible.getCodigo());
        vehiculosDentro.add(vehiculo);

        System.out.println("✓ Ingreso exitoso → Placa: " + vehiculo.getPlaca()
                + " | Espacio: " + espacioDisponible.getCodigo());
    }


    public double registrarSalida(String placa)
            throws VehiculoNoEncontradoException, EspacioNoDisponibleException {

        String placaBuscada = placa.trim().toUpperCase();

        Vehiculo vehiculoSalida = null;
        for (Vehiculo v : vehiculosDentro) {
            if (v.getPlaca().equals(placaBuscada)) {
                vehiculoSalida = v;
                break;
            }
        }

        if (vehiculoSalida == null) {
            throw new VehiculoNoEncontradoException(placaBuscada);
        }

        double valorAPagar = gestorTarifas.calcularValorAPagar(vehiculoSalida);

        for (Espacio e : espacios) {
            if (e.getCodigo().equals(vehiculoSalida.getEspacioAsignado())) {
                e.liberar();
                break;
            }
        }

        vehiculoSalida.setEstaDentro(false);

        vehiculosDentro.remove(vehiculoSalida);

        System.out.println("✓ Salida registrada");
        System.out.println("Placa: " + placaBuscada);
        System.out.println("Tiempo: " + vehiculoSalida.getTiempoPermanenciaFormateado());
        System.out.println("Valor: $" + String.format("%.0f", valorAPagar));

        return valorAPagar;
    }

    private void validarPlacaDuplicada(String placa) throws PlacaDuplicadaException {
        String placaBuscada = placa.trim().toUpperCase();
        for (Vehiculo v : vehiculosDentro) {
            if (v.getPlaca().equals(placaBuscada)) {
                throw new PlacaDuplicadaException(placaBuscada);
            }
        }
    }

    public void deshabilitarEspacio(String codigo) {
        for (Espacio e : espacios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                boolean exito = e.deshabilitar();
                if (!exito) {
                    System.out.println("   No se realizó ninguna acción.");
                }
                return;
            }
        }
        System.out.println("✗ Espacio con código " + codigo + " no encontrado.");
    }

    public void habilitarEspacio(String codigo) {
        for (Espacio e : espacios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                e.habilitar();
                System.out.println("✓ Espacio " + codigo + " habilitado");
                return;
            }
        }
        System.out.println("✗ Espacio " + codigo + " no encontrado");
    }


    public List<Espacio> getEspacios() {
        return new ArrayList<>(espacios);
    }

    public List<Vehiculo> getVehiculosDentro() {
        return new ArrayList<>(vehiculosDentro);
    }

    public GestorTarifas getGestorTarifas() {
        return gestorTarifas;
    }

    public int getTotalEspacios() {
        return espacios.size();
    }

    public int getEspaciosOcupados() {
        return vehiculosDentro.size();
    }

    public int getEspaciosDisponiblesCount() {
        return (int) espacios.stream().filter(Espacio::disponible).count();
    }
}
