package modelo;

import java.util.*;

import Pago.*;

import java.time.*;
import java.time.LocalDate;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;
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

	// funcion para organizador -  ganancias sin recargos adicionales (servicios e impresion)
	
	public double verIngresos(Organizador o) {
		double ingresos = 0.0;
		List<Evento> eventos = o.getEventos();
		for (Evento e: eventos) {
			List<Localidad> localidades = e.getLocalidades();
			for (Localidad loc : localidades) {
				HashSet<Tiquete> tiquetes = loc.getTiquetes();
				for (Tiquete t : tiquetes) {
					if (t instanceof TiqueteSimple) {
						TiqueteSimple ts = (TiqueteSimple) t;
	                    if (ts.getStatus() == estadoTiquete.COMPRADO) {
	                        ingresos += loc.getPrecioBase(); // sin cargos ni descuentos
	                    }
					} else if (t instanceof TiqueteMultiple multiple) {
						for (TiqueteSimple ts: multiple.getEntradas()) {
							if (ts.getStatus() == estadoTiquete.COMPRADO) {
	                            Localidad locSimple = ts.getLocalidad();
	                            ingresos += locSimple.getPrecioBase();
	                        }
						}
					}
				}
			}
		}
	 
		return ingresos;
	}
	
	public int porcentajeVenta(int idE, int idL) {
		// TO DO
		return 0;
	}

}
