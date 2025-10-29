package modelo;

import java.util.*;
import java.time.*;
import java.time.LocalDate;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;
import pagos.*;
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
	
	// funcion para administrador
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
	
	// funcion para administrador
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
	
	// funcion para administrador - incluye ganancias totales
	public double totalGananciasPorOrganizador(int idO) {
		double ganancias = 0.0;
		
		for(Pago p: transacciones) {
			ArrayList<Tiquete> tiquetes = p.getTiquetesComprados();
			boolean pertenece = false;
			for(Tiquete t: tiquetes) {
				if (t instanceof TiqueteSimple) {
					TiqueteSimple ts = (TiqueteSimple) t;
					Evento e = ts.getEvento();
					Organizador o = e.getOrganizador();
					if(o.getIdO() == idO) {
						pertenece = true;
					}
				} else if (t instanceof TiqueteMultiple multiple) {
					for (TiqueteSimple ts: multiple.getEntradas()) {
						Evento e = ts.getEvento();
						Organizador o = e.getOrganizador();
						if(o.getIdO() == idO) {
							pertenece = true;
						}
					}
				}
			} if(pertenece) {
	    		ganancias += p.getMonto();
	    	}	
		}
		return ganancias;
	}
		

}
