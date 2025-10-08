package modelo;

import java.time.LocalDate;

public class Oferta {
	
	private int idOferta;
	private double porcentajeDescuento;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	
	
	public Oferta(int idOferta, double porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) {
		this.idOferta = idOferta;
		this.porcentajeDescuento = porcentajeDescuento;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	
	public boolean estaVigente() {
		
		LocalDate hoy = LocalDate.now();
		
		return ((hoy.isEqual(fechaInicio) || hoy.isAfter(fechaInicio)) && ((hoy.isEqual(fechaFin)) || hoy.isBefore(fechaFin)));
				
	}
	
	
	public double aplicarDescuento(double precioBase) {
		
		if(estaVigente()) {
			return precioBase * (1 - porcentajeDescuento);
		}
		return precioBase;
	}


	public int getIdOferta() {
		return idOferta;
	}


	public void setIdOferta(int idOferta) {
		this.idOferta = idOferta;
	}


	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}


	public void setPorcentajeDescuento(double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}


	public LocalDate getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public LocalDate getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	
	
	
	

}
