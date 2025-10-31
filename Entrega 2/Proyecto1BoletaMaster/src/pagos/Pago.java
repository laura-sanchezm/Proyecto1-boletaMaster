package pagos;

import java.time.LocalDate;
import Tiquetes.Tiquete;
import java.util.ArrayList; 	
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;
import Tiquetes.TiqueteMultiple;
import modelo.Oferta;
import modelo.Localidad;

public class Pago {
	
	private int idPago;
	private LocalDate fecha;
	private double monto;
	private estadoPago estado;
	private metodoPago metodo;
	private double cargoServicio;
	private double cargoImpresion;
	private ArrayList<Tiquete> tiquetesComprados;
	
	
	
	public Pago(int idPago, LocalDate fecha, metodoPago metodo, double cargoServicio,
			double cargoImpresion, ArrayList<Tiquete> tiquetesComprados) {

		this.idPago = idPago;
		this.fecha = fecha != null ? fecha : LocalDate.now();
		this.monto = calcularMonto();
		this.estado = estadoPago.PENDIENTE;
		this.metodo = metodo;
		this.cargoServicio = cargoServicio;
		this.cargoImpresion = cargoImpresion;
		this.tiquetesComprados = tiquetesComprados;
	}
	
	
	public double calcularMonto() {
		
		double subtotal = 0.0;
		
		for(Tiquete t : tiquetesComprados) {
			if(t instanceof TiqueteSimple) {
				TiqueteSimple simple = (TiqueteSimple) t;
				subtotal += calcularPrecioOferta(simple.getLocalidad());
			}
			
			else if(t instanceof TiqueteMultiple) {
				TiqueteMultiple multiple = (TiqueteMultiple) t;
				for(TiqueteSimple simple : multiple.getEntradas()) {
					subtotal += calcularPrecioOferta(simple.getLocalidad());
				}
			}
		}
		
		double cargos = (subtotal * cargoServicio / 100.0) + cargoImpresion;
		return subtotal + cargos;
	}
	
	
	
	private double calcularPrecioOferta(Localidad localidad) {
		
		double precioBase = localidad.getPrecioBase();
		
		if(localidad.hayOferta()) {
			Oferta oferta = localidad.getOferta();
			if(oferta != null && oferta.estaVigente()) {
				double descuento = precioBase * (oferta.getPorcentajeDescuento()/100);
				return precioBase - descuento;
			}
		}
		
		return precioBase;
	}
	
	
	
	public boolean procesar() {
		
		if(tiquetesComprados == null || tiquetesComprados.isEmpty()) {
			estado = estadoPago.RECHAZADO;
		}
		
	
		estado = estadoPago.APROBADO;
		
		for(Tiquete t : tiquetesComprados) {
			if(t instanceof TiqueteSimple) {
				TiqueteSimple simple = (TiqueteSimple) t;
				if(simple.getLocalidad().isNumerada()) {
					simple.asignarAsiento();
				}
				simple.setStatus(estadoTiquete.COMPRADO);
			}
			else if(t instanceof TiqueteMultiple multiple) {
				for(TiqueteSimple s : multiple.getEntradas()) {
					if(s.getLocalidad().isNumerada()) {
						s.asignarAsiento();
					}
					s.setStatus(estadoTiquete.COMPRADO);
				}
			}
		}
		return true;
			
	}
	
	
	public void anular() {
	
		if(estado != estadoPago.APROBADO)return;
		
		estado = estadoPago.ANULADO;
		
		
		for(Tiquete t : tiquetesComprados) {
			if(t instanceof TiqueteSimple) {
				TiqueteSimple simple = (TiqueteSimple) t;
				Localidad loc = simple.getLocalidad();
				loc.reponerDevolucion(simple);
				simple.setStatus(estadoTiquete.DISPONIBLE);
			}
			else if(t instanceof TiqueteMultiple) {
				TiqueteMultiple multiple = (TiqueteMultiple) t;
				for(TiqueteSimple s : multiple.getEntradas()) {
					Localidad loc = s.getLocalidad();
					loc.reponerDevolucion(s);
					s.setStatus(estadoTiquete.DISPONIBLE);
				}
			}
		}
		
	}


	public int getIdPago() {
		return idPago;
	}


	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public double getMonto() {
		return monto;
	}


	public void setMonto(double monto) {
		this.monto = monto;
	}


	public estadoPago getEstado() {
		return estado;
	}


	public void setEstado(estadoPago estado) {
		this.estado = estado;
	}


	public metodoPago getMetodo() {
		return metodo;
	}


	public void setMetodo(metodoPago metodo) {
		this.metodo = metodo;
	}


	public double getCargoServicio() {
		return cargoServicio;
	}


	public void setCargoServicio(double cargoServicio) {
		this.cargoServicio = cargoServicio;
	}


	public double getCargoImpresion() {
		return cargoImpresion;
	}


	public void setCargoImpresion(double cargoImpresion) {
		this.cargoImpresion = cargoImpresion;
	}


	public ArrayList<Tiquete> getTiquetesComprados() {
		return tiquetesComprados;
	}


	public void setTiquetesComprados(ArrayList<Tiquete> tiquetesComprados) {
		this.tiquetesComprados = tiquetesComprados;
	}
		

}
