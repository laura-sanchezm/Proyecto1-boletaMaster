package modelo;

import java.util.*;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteMultiple;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;

import java.time.*;

public class Organizador extends Usuario{
	
	private int idO;
	private List<Evento> eventos;
	
	
	public Organizador(String login, String password, int id) {
		super(login, password);
		this.idO = id;
		this.eventos = new ArrayList<>();
		
	}
	
	// funcion auxiliar
	private Evento buscarEventoPorId(int idE) {
		for(Evento e : eventos) {
			if(e.getIdE() == idE) {
				return e;
			}
		}
		return null;
	}
	
	
	public Evento crearEvento(int idE, String nombre, LocalDate fecha, LocalTime hora, String tipo, Venue venue) {
		Evento evento = new Evento(idE, nombre, fecha, hora, tipo, estadoEvento.PROGRAMADO, venue, this);
		this.eventos.add(evento);
		return evento;
	}
	
	
	public void asignarLocalidad(int idE, List<Localidad> localidades) {
		Evento e = buscarEventoPorId(idE);
		for (Localidad l: localidades) {
			e.addLocalidad(l);
		}
	}
	
	
	public void aplicarOferta(int idO, int idL, int porcentaje, LocalDate fechaInicio, LocalDate fechaFin) {
		Localidad target = null;
	    for (Evento e : eventos) {
	        if (e == null || e.getLocalidades() == null) continue;
	        for (Localidad l : e.getLocalidades()) {
	            if (l != null && l.getIdL() == idL) {
	                target = l;
	                
	            }
	        }
	    }
	    
	    Oferta nueva = new Oferta(idO, idL, porcentaje, fechaInicio, fechaFin);
	}
	
	public void solicitarCancelacion(int idE) {
		// TO DO - PUEDE SOLICITAR POR INSOLVENCIA
		for (Evento e: eventos) {
			if(e.getIdE() == idE) {
				e.setCancelacionSolicitada(true);
			}
		}
	}
	
	public double verIngresos() {
		double ingresos = 0.0;
		List<Evento> eventos = this.getEventos();
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
	
	
	//funcion auxiliar para contar tiquetes vendidos por localidad
	private int contarVendidos(Localidad l) {
        int vendidos = 0;
        HashSet<Tiquete> tiquetes = l.getTiquetes();
       
        for (Tiquete t : tiquetes) {
            if (t instanceof TiqueteSimple) {
                TiqueteSimple ts = (TiqueteSimple) t;
                if (ts.getStatus() == estadoTiquete.COMPRADO) {
                    vendidos++;
                }
            } else if (t instanceof TiqueteMultiple) {
                TiqueteMultiple tm = (TiqueteMultiple) t;
                for (TiqueteSimple ts : tm.getEntradas()) {
                    if (ts.getStatus() == estadoTiquete.COMPRADO) {
                        vendidos++;
                    }
                }
            }
        }
        return vendidos;
    }
	
	
	public double porcentajeVentaTotal() {
		int vendidos = 0;
        int capacidad = 0;

        for (Evento e : this.eventos) {
            for (Localidad l : e.getLocalidades()) {
                vendidos  += contarVendidos(l);
                capacidad += l.getCapacidadL();
            }
        }

        if (capacidad <= 0) {
        	return 0.0;
        } else {
        	return (vendidos * 100.0) / capacidad;
        }
	}
	
	public double porcentajeVentaPorEvento(int idE) {

        int vendidos = 0;
        int capacidad = 0;

        for (Evento e : this.eventos) {
            if (e.getIdE() == idE) {
            	for (Localidad l : e.getLocalidades()) {
                    if (l == null) continue;
                    vendidos  += contarVendidos(l);
                    capacidad += l.getCapacidadL();
                }
            }

        }

        if (capacidad <= 0) {
        	return 0.0;
        } else {
        	return (vendidos * 100.0) / capacidad;
        }
	}
	
	public double porcentajeVentaPorLocalidad(int idL){
		int vendidos = 0;
        int capacidad = 0;

        for (Evento e : this.eventos) {
            for (Localidad l : e.getLocalidades()) {
                if (l.getIdL() == idL) {
                    vendidos  += contarVendidos(l);
                    capacidad += l.getCapacidadL();
                }
            }
        }

        if (capacidad <= 0) {
        	return 0.0;
        } else {
        	return (vendidos * 100.0) / capacidad;
        }
	}
	


	public int getIdO() {
		return idO;
	}


	public void setIdO(int idO) {
		this.idO = idO;
	}


	public List<Evento> getEventos() {
		return eventos;
	}


	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
	
	

}
