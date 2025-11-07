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
		// aprobacion se inicia en false ya que administrador debe aprobar el venue
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


	public boolean getAprobado() {
		return aprobado;
	}
	

	public void setAprobado(boolean aprobado) {   // setter simple
        this.aprobado = aprobado;
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
	
	
	@Override
	public String toString() {
	    String estado = aprobado ? "Aprobado" : "No aprobado";
	    return "Venue: " + nombreV +
	           " | Tipo: " + tipoV +
	           " | Capacidad: " + capacidad +
	           " | Ubicación: " + ubicacion +
	           " | Restricciones: " + restricciones +
	           " | Estado: " + estado;
	}

}
