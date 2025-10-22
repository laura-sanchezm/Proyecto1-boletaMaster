package modelo;

import java.util.*;

import Pago.*;

import java.time.*;

public class EstadosFinancieros {
	
	private List<Pago> transacciones;
	
	public void registrarTransaccion(Pago p) {
		transacciones.add(p);
	}
	
	public void anularTransaccion(int idPago) {
		for (Pago p : transacciones) {
            if (p.getIdPago() == idPago) {
                p.anular();
            }
		}
	}
	
	public int totalGanancias(LocalDate fechaMin, LocalDate fechaMax, int idE, int idO) {
		// TO DO
		return 0;
	}
	
	public int verIngresos(int idO, LocalDate fechaMin, LocalDate fechaMax, int idE, int idL) {
		// PODRA ver sus ingresos - el tiquete sin rercargos
		return 0;
	}
	
	public int porcentajeVenta(int idE, int idL) {
		// TO DO
		return 0;
	}

}
