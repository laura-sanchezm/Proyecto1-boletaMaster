package Tiquetes;

import modelo.Evento;
import modelo.Localidad;

public class TiqueteSimple extends Tiquete {
	
	private Evento evento;
	private Localidad localidad;
	private boolean enumerado;
	private int numAsiento;
	
	
	// Cliente no terminado
	
	public TiqueteSimple(int idT, boolean transferible, String propietario, estadoTiquete status, String tipo,
			Evento evento, Localidad localidad) {
		super(idT, transferible, propietario, status, tipo);
		this.evento = evento;
		this.localidad = localidad;
		this.enumerado = localidad.isNumerada();
		this.numAsiento = -1;
	}
	
	
	
	public void asignarAsiento() {
		
		if(!localidad.isNumerada()) {
			enumerado = false;
			this.numAsiento = -1;
		}
		
		if(numAsiento > 0) {
			return;
		}
		
		int asiento = localidad.asignarAsientoDisponible();
		if(asiento == -1) {
			throw new IllegalStateException("No hay asientos disponibles en la localdiad");
		}
		
		this.enumerado = true;
		this.numAsiento = asiento;
	}
	
	
	public void aplicarOferta() {
		
	}



	public Evento getEvento() {
		return evento;
	}



	public void setEvento(Evento evento) {
		this.evento = evento;
	}



	public Localidad getLocalidad() {
		return localidad;
	}



	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}



	public boolean isEnumerado() {
		return enumerado;
	}



	public void setEnumerado(boolean enumerado) {
		this.enumerado = enumerado;
	}



	public int getNumAsiento() {
		return numAsiento;
	}



	public void setNumAsiento(int numAsiento) {
		this.numAsiento = numAsiento;
	}
	
	
	
	
}
