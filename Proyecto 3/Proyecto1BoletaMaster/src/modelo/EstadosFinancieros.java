package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteMultiple;
import Tiquetes.TiqueteSimple;
import pagos.Pago;
import pagos.estadoPago;

public class EstadosFinancieros {
	
	private List<Pago> transacciones;
	
	public EstadosFinancieros() {
		this.transacciones = new ArrayList<>();
	}
	
	public void registrarTransaccion(Pago p) {
		if(p != null) {
			transacciones.add(p);
		}
	}
	
	public void anularTransaccion(int idPago) {
		for(Pago p : transacciones) {
			if(p.getIdPago() == idPago) {
				p.anular();
				System.out.println("Transaccion con ID " + idPago + " anulada correctamete.");
				return;
			}
		}
		System.out.println("No se encontro una transacción con el ID " + idPago);
	}
	
	// funcion para administrador
	public double totalGananciasPorFecha(LocalDate fechaMin, LocalDate fechaMax) {
		double ganancias = 0.0;
		for (Pago p: transacciones) {
			if(p.getEstado() == estadoPago.APROBADO) {
				LocalDate f = p.getFecha();
				if(!f.isBefore(fechaMin) && !f.isAfter(fechaMax)) {
					ganancias += p.getMonto();
				}
			}
		}
		return ganancias;
	}
	
	// funcion para administrador
	public double totalGananciasPorEvento(int idE) {
		double ganancias = 0.0;
		
		for(Pago p : transacciones) {
			if(p.getEstado() != estadoPago.APROBADO) continue;
			
			for(Tiquete t : p.getTiquetesComprados()) {
				if(t instanceof TiqueteSimple s) {
					if(s.getEvento().getIdE() == idE) {
						ganancias += p.getMonto();
						break;
					}
				}else if(t instanceof TiqueteMultiple m) {
					for(TiqueteSimple Ms : m.getEntradas()){
						if(Ms.getEvento().getIdE() == idE) {
							ganancias += p.getMonto();
							break;
						}
					}
				}
			}
		}
		return ganancias;
	}
	
	// funcion para administrador - incluye ganancias totales
	public double totalGananciasPorOrganizador(int idO) {
		double ganancias = 0.0;
		
		for(Pago p : transacciones) {
			if(p.getEstado() != estadoPago.APROBADO) continue;
			
			for(Tiquete t : p.getTiquetesComprados()) {
				if(t instanceof TiqueteSimple s) {
					if(s.getEvento().getOrganizador().getIdO() == idO) {
						ganancias += p.getMonto();
						break;
					}
				}else if(t instanceof TiqueteMultiple m) {
					for(TiqueteSimple Ms : m.getEntradas()){
						if(Ms.getEvento().getOrganizador().getIdO() == idO) {
							ganancias += p.getMonto();
							break;
						}
					}
				}
			}
		}
		return ganancias;
	}
	
	
	public List<Pago> getTransacciones(){
		return transacciones;
	}
}
