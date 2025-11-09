package modelo;

import java.util.*;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteMultiple;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;

import java.time.*;

public class Organizador extends Cliente{
	
	private int idO;
	private List<Evento> eventos;
	private static int contadorOfertas = 1;
	
	
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
	
	
	public void aplicarOferta(int idL, double porcentaje, LocalDate fechaInicio, LocalDate fechaFin) {
		Localidad target = null;
		
		for(Evento e : eventos) {
			for(Localidad l : e.getLocalidades()) {
				if(l.getIdL() == idL) {
					target = l;
					break;
				}
			}
			if(target != null)break;
		}
		if(target == null) {
			System.out.println("No se encontro la localidad con el ID: " + idL);
			return;
		}
		
		int idOferta = contadorOfertas++;
		Oferta nueva = new Oferta(idOferta, idL, porcentaje /100, fechaInicio, fechaFin);
		target.aplicarOferta(nueva);
		System.out.println("Oferta #" + idOferta + " creada y aplicada correctamente a la localidad " + target.getNombreL());
	}
	
	public void solicitarCancelacion(int idE, String motivo) {
		Evento evento = buscarEventoPorId(idE);
		
		if(evento == null) {
			System.out.println("No se encontro el evento on el ID proporcionado.");
			return;
		}
		
		if(evento.getEstado() == estadoEvento.CANCELADO) {
			System.out.println("El evneto ya fue cancelado");
			return;
		}
		
		evento.setCancelacionSolicitada(true);
		evento.setMotivoCancelacion(motivo);
		 System.out.println("Solicitud de cancelación enviada para el evento " + evento.getNombreE() + " con motivo: " + motivo);
	}
	
	public double verIngresos() {
		double ingresos = 0.0;
		
		for(Evento e : this.eventos) {
			if(e.getEstado() == estadoEvento.CANCELADO) {
				continue;
			}
			
			for(Localidad loc : e.getLocalidades()) {
				HashSet<Tiquete> tiquetes = loc.getTiquetes();
				
				for(Tiquete t : tiquetes) {
					if(t.getStatus() == estadoTiquete.COMPRADO) {
						if(t.getPropietario().equals(this.getLogin())) {
							continue;
						}
						
						if(t instanceof TiqueteSimple simple) {
							ingresos += simple.getLocalidad().getPrecioBase();
						}else if(t instanceof TiqueteMultiple multiple) {
							for(TiqueteSimple s : multiple.getEntradas()) {
								if(s.getStatus() == estadoTiquete.COMPRADO && !s.getPropietario().equals(this.getLogin())) {
									ingresos +=  s.getLocalidad().getPrecioBase();
								}
							}
						}
					}
				}
			}
		}
		System.out.println("El organizador " + this.getLogin() + " ha generado ingresos de $" + ingresos);
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
        	if(e.getEstado() == estadoEvento.CANCELADO) continue;
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
            if (e.getIdE() == idE && e.getEstado() != estadoEvento.CANCELADO) {
            	for (Localidad l : e.getLocalidades()) {
                    vendidos  += contarVendidos(l);
                    capacidad += l.getCapacidadL();
                }
            	break;
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
        	if(e.getEstado() == estadoEvento.CANCELADO) continue;
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
