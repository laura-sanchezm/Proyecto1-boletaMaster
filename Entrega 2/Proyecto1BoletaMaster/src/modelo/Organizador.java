package modelo;

import java.util.*;
import java.time.*;

public class Organizador extends Usuario{
	
	private int idO;
	private List<Evento> eventos;
	// finanzas
	
	
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
	
	
	public Evento crearEvento(int idE, String nombre, LocalDate fecha, LocalTime hora, String tipo,  Enum<estadoEvento> estado, Venue venue) {
		Evento evento = new Evento(idE, nombre, fecha, hora, tipo, estadoEvento.PROGRAMADO, venue);
		return evento;
	}
	
	
	public void asignarLocalidad(int idE, List<Localidad> localidades) {
		Evento e = buscarEventoPorId(idE);
		for (Localidad l: localidades) {
			e.addLocalidad(l);
		}
	}
	
	
	public void aplicarOferta(int idL, int porcentaje, LocalTime horaInicio, LocalTime horaFinal) {
		Localidad target = null;
	    for (Evento e : eventos) {
	        if (e == null || e.getLocalidades() == null) continue;
	        for (Localidad l : e.getLocalidades()) {
	            if (l != null && l.getIdL() == idL) {
	                target = l;                 
	            }
	        }
	    }
	    
	    Oferta o = new Oferta(idL, porcentaje, horaInicio, horaFinal);
	}
	
	public void solicitarCancelacion(int idE) {
		// TO DO - PUEDE SOLICITAR POR INSOLVENCIA
		for (Evento e: eventos) {
			if(e.getIdE() == idE) {
				e.setCancelacionSolicitada(true);
			}
		}
	}
	
	public int verIngresos(int idO) {
		//TO DO - los ingresos del organizador son los tiquetes isn acrgos adiconales
		return 0;
	}
	
	public int verPorcentajeVenta(int idE, int idL) {
		// TO DO, (ventas/disponibles) se puede ver total, o filtrar por evento o localidad.
		return 0;
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
