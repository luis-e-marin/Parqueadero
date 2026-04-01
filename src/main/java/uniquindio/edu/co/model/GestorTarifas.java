package uniquindio.edu.co.model;

import uniquindio.edu.co.enums.TipoVehiculo;
import java.util.HashMap;
import java.util.Map;

public class GestorTarifas {

    private Map<TipoVehiculo, Tarifa> tarifas;

    public GestorTarifas() {
        tarifas = new HashMap<>();
        tarifas.put(TipoVehiculo.CARRO, new Tarifa(TipoVehiculo.CARRO, 3000));
        tarifas.put(TipoVehiculo.MOTO, new Tarifa(TipoVehiculo.MOTO, 1500));
        tarifas.put(TipoVehiculo.BICICLETA, new Tarifa(TipoVehiculo.BICICLETA, 500));
    }

    public double calcularValorAPagar(Vehiculo v, Usuario u) {
        if (v == null) return 0;
        Tarifa t = tarifas.get(v.getTipo());
        double valor = t.calcularValor(v.getMinutosPermanencia());
        if (u != null) {
            valor -= valor * (u.getDescuento() / 100);
        }

        return valor;
    }
}
