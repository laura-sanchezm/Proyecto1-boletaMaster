package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import Tiquetes.Tiquete;

public class Evento {

	private int idE;
	private String nombreE;
	private LocalDate fecha;
	private LocalTime hora; 
	private String tipoE;
	private estadoEvento estado;
	private Venue venue;
	private List<Localidad> localidades;
	private boolean cancelacionSolicitada;

	
	
	public Evento(int idE, String nombreE, LocalDate fecha, LocalTime hora, String tipoE, estadoEvento estado, Venue Venue) {
		this.idE = idE;
		this.nombreE = nombreE;
		this.fecha = fecha;
		this.hora = hora;
		this.tipoE = tipoE;
		this.estado = estado;
		this.venue = Venue;
		this.localidades = new ArrayList<Localidad>();
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



	public estadoEvento getEstado() {
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
	
	public List<Localidad> getLocalidades(){
		return localidades;
	}
	
	public void addLocalidad(Localidad localidad) {
		localidades.add(localidad);
	}
	
	
	public int aforoDisponible() {
		return venue.getCapacidad();
	}
	
	//funcion auxiliar
	public Tiquete buscarTiquete(int idT) {
		for (Localidad l: localidades) {
			Tiquete t = l.buscarTiquete(idT);
			return t;
		}
		return null;
	}
	
	public void setCancelacionSolicitada(boolean cancelacionSolicitada) {
		this.cancelacionSolicitada = cancelacionSolicitada;
	}
	
	public boolean getDevolucionSolicitada() {
		return cancelacionSolicitada;
	}
	
	
}
