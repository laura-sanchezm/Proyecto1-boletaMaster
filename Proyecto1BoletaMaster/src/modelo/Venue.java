package modelo;

import java.util.HashSet;
import java.time.LocalDate;

public class Venue {
	
	private String nombreV;
	private int capacidad;
	private String tipoV;
	private String ubicacion;
	private String restricciones;
	private boolean aprobado;
	private HashSet<LocalDate> fechasOcupadas;
	
	
	public Venue(String nombreV, int capacidad, String tipoV, String ubicacion, String restricciones) {
		this.nombreV = nombreV;
		this.capacidad = capacidad;
		this.tipoV = tipoV;
		this.ubicacion = ubicacion;
		this.restricciones = restricciones;
		this.aprobado = false;
		this.fechasOcupadas = new HashSet<LocalDate>();
	}


	public String getNombreV() {
		return nombreV;
	}


	public void setNombreV(String nombreV) {
		this.nombreV = nombreV;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


	public String getTipoV() {
		return tipoV;
	}


	public void setTipoV(String tipoV) {
		this.tipoV = tipoV;
	}


	public String getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


	public String getRestricciones() {
		return restricciones;
	}


	public void setRestricciones(String restricciones) {
		this.restricciones = restricciones;
	}


	public boolean isAprobado() {
		return aprobado;
	}
	
	public void setAprobado(boolean aprueba) {
		this.aprobado = aprueba;
	}


	public HashSet<LocalDate> getFechasOcupadas() {
		return fechasOcupadas;
	}


	public void addFechaOcupada(LocalDate fecha) {
		fechasOcupadas.add(fecha);
	}
	
	
	public boolean consultarDisponibilidad(LocalDate fecha) {
		return !fechasOcupadas.contains(fecha);
	}

}
