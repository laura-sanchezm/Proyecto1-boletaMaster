package Pago;

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
	private ArrayList<Oferta> ofertasActivas;
	
	
	
	public Pago(int idPago, LocalDate fecha, double monto, estadoPago estado, metodoPago metodo, double cargoServicio,
			double cargoImpresion, ArrayList<Tiquete> tiquetesComprados, ArrayList<Oferta> ofertasActivas) {

		this.idPago = idPago;
		this.fecha = LocalDate.now();
		this.monto = monto;
		this.estado = estadoPago.PENDIENTE;
		this.metodo = metodo;
		this.cargoServicio = cargoServicio;
		this.cargoImpresion = cargoImpresion;
		this.tiquetesComprados = tiquetesComprados;
		this.ofertasActivas = ofertasActivas;
	}
	
	
	public double calcularMonto() {
		
		double subtotal = 0;
		
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
		
		double cargos = (subtotal * cargoServicio / 100) + cargoImpresion;
		return subtotal + cargos;
	}
	
	
	
	private double calcularPrecioOferta(Localidad localidad) {
		
		double precioBase = localidad.getPrecioBase();
		Oferta oferta = buscarOfertaParaLocalidad(localidad.getIdL());
		
		if(oferta != null && oferta.estaVigente()) {
			double descuento = precioBase * (oferta.getPorcentajeDescuento()/100);
			return precioBase - descuento;
		}
		
		return precioBase;
	}
	
	
	private Oferta buscarOfertaParaLocalidad(int idL) {
		
		for(Oferta o : ofertasActivas) {
			if(o.getIdL() == idL) {
				return o;
			}
		}
		return null;
	}
	
	public boolean procesar() {
		
		boolean aprobado = !tiquetesComprados.isEmpty();
		
		if(aprobado) {
			estado = estadoPago.APROBADO;
			for(Tiquete t : tiquetesComprados) {
				if(t instanceof TiqueteSimple) {
					TiqueteSimple simple = (TiqueteSimple) t;
					if(simple.getLocalidad().isNumerada()) {
						simple.asignarAsiento();
					}
					simple.setStatus(estadoTiquete.COMPRADO);
				}
				else if(t instanceof TiqueteMultiple) {
					TiqueteMultiple multiple = (TiqueteMultiple) t;
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
		else {
			estado = estadoPago.RECHAZADO;
			return false;	
		}
	}
	
	
	public void anular() {
		if(estado == estadoPago.APROBADO) {
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


	public ArrayList<Oferta> getOfertasActivas() {
		return ofertasActivas;
	}


	public void setOfertasActivas(ArrayList<Oferta> ofertasActivas) {
		this.ofertasActivas = ofertasActivas;
	}
	
	
	

}
