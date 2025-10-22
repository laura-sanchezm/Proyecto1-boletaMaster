package modelo;

import java.util.*;

import Pago.*;

import java.time.*;
import java.time.LocalDate;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.TiqueteMultiple;

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
	
	public double totalGananciasPorFecha(LocalDate fechaMin, LocalDate fechaMax) {
		int ganancias = 0;
		for (Pago p: transacciones) {
			if(p.getEstado() == estadoPago.APROBADO) {
				LocalDate f = p.getFecha();
				if(!f.isBefore(fechaMin) && !f.isAfter(fechaMax)) {
					ganancias += p.getMonto();
				}
			}
		}
		return ganancias;
	}
	
	public double totalGananciasPorEvento(int idE) {
		double ganancias = 0.0;

	    for (Pago p : transacciones) {
	    	boolean pertenece = false;
	    	ArrayList<Tiquete> tiquetes = p.getTiquetesComprados();
	    	for(Tiquete t: tiquetes) {
	    		if(t instanceof TiqueteSimple) {
	    			TiqueteSimple ts = (TiqueteSimple) t;
					Evento e = ts.getEvento();
					if (e.getIdE() == idE) {
						pertenece = true;
					}
	    		} else if (t instanceof TiqueteMultiple multiple) {
	    			for (TiqueteSimple ts: multiple.getEntradas()) {
						Evento e = ts.getEvento();
						if (e.getIdE() == idE) {
							pertenece = true;
						}
					}
	    		}
	    	}
	    	if(pertenece) {
	    		ganancias += p.getMonto();
	    	}
	    }
	    return ganancias;
	}
	
	public double totalGananciasPorOrganizador(int idO) {
		// TO DO
		for(Pago p: transacciones) {
			ArrayList<Tiquete> tiquetes = p.getTiquetesComprados();
			boolean pertenece = false;
			
		}
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
