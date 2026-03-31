package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;
import java.util.HashMap;
import java.util.Map;

public class GestorTarifas {

    private final Map<TipoVehiculo, Tarifa> tarifas;

    public GestorTarifas() {
        this.tarifas = new HashMap<>();
        inicializarTarifasPorDefecto();
    }

    private void inicializarTarifasPorDefecto() {
        tarifas.put(TipoVehiculo.CARRO, new Tarifa(TipoVehiculo.CARRO, 3000));
        tarifas.put(TipoVehiculo.MOTO, new Tarifa(TipoVehiculo.MOTO, 1500));
        tarifas.put(TipoVehiculo.BICICLETA, new Tarifa(TipoVehiculo.BICICLETA, 500));
    }

    public Tarifa getTarifa(TipoVehiculo tipo) {
        return tarifas.get(tipo);
    }

    public double calcularValorAPagar(Vehiculo vehiculo) {
        if (vehiculo == null || !vehiculo.isEstaDentro()) return 0.0;

        Tarifa tarifa = getTarifa(vehiculo.getTipo());
        if (tarifa == null) return 0.0;

        long minutos = vehiculo.getMinutosPermanencia();
        return tarifa.calcularValor(minutos);
    }

    // Sobrecarga con descuento de usuario
    public double calcularValorAPagar(Vehiculo vehiculo, Usuario usuario) {
        double valor = calcularValorAPagar(vehiculo);
        if (usuario != null && usuario.getDescuento() > 0) {
            double descuento = valor * (usuario.getDescuento() / 100.0);
            return valor - descuento;
        }
        return valor;
    }

    public void mostrarTarifas() {
        System.out.println("\n=== TARIFAS ACTUALES ===");
        for (Tarifa tarifa : tarifas.values()) {
            System.out.println(tarifa);
        }
    }
}
