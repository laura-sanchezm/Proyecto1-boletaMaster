package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {

	private int idE;
	private String nombreE;
	private LocalDate fecha;
	private LocalTime hora; 
	private String tipoE;
	private estadoEvento estado;
	private Venue venue;

	
	
	public Evento(int idE, String nombreE, LocalDate fecha, LocalTime hora, String tipoE, estadoEvento estado, Venue Venue) {
		this.idE = idE;
		this.nombreE = nombreE;
		this.fecha = fecha;
		this.hora = hora;
		this.tipoE = tipoE;
		this.estado = estado;
		this.venue = Venue;
	}



	public int getIdE() {
		return idE;
	}



	public void setIdE(int idE) {
		this.idE = idE;
	}



	public String getNombreE() {
		return nombreE;
	}



	public void setNombreE(String nombreE) {
		this.nombreE = nombreE;
	}



	public LocalDate getFecha() {
		return fecha;
	}



	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}



	public LocalTime getHora() {
		return hora;
	}



	public void setHora(LocalTime hora) {
		this.hora = hora;
	}



	public String getTipoE() {
		return tipoE;
	}



	public void setTipoE(String tipoE) {
		this.tipoE = tipoE;
	}



	public Enum<estadoEvento> getEstado() {
		return estado;
	}



	public void setEstado(estadoEvento status) {
		this.estado = status;
	}



	public Venue getVenue() {
		return venue;
	}



	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	
	
	public int aforoDisponible() {
		return venue.getCapacidad();
	}
	
	
}
