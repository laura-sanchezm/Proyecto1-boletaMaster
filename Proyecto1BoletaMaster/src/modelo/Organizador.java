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
	
	
	public Evento crearEvento(int idE, String nombre, LocalDate fecha, LocalTime hora, String tipo,  Enum<estadoEvento> estado, Venue venue) {
		Evento evento = new Evento(idE, nombre, fecha, hora, tipo, estadoEvento.PROGRAMADO, venue);
		return evento;
	}
	
	public void asignarLocalidad(int idE, List<Localidad> localidades) {
		// TO DO
	}
	
	public void aplicarOferta(int idL, int porcentaje, LocalTime horaInicio, LocalTime horaFinal) {
		// TO DO
	}
	
	public void solicitarCancelacion(int idE, String motivo) {
		// TO DO
	}
	
	public int verIngresos(int idO, LocalDate fechaMin, LocalDate fechaMax, int idE, int idL) {
		return 0;
	}
	
	public int verPorcentajeVenta(int idE, int idL) {
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
