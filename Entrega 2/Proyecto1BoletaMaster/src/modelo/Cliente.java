package modelo;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.TiqueteMultiple;
import Tiquetes.estadoTiquete;
import pagos.Pago;
import pagos.metodoPago;

public class Cliente extends Usuario {
	
	private double saldo;
	private HashSet<Tiquete> tiquetes;
	
	public Cliente(String login, String password) {
		super(login, password);
		this.saldo = 0;
		this.tiquetes = new HashSet<Tiquete>();
	}
	
	
	public double consultarSaldo() {
		return saldo;
	}
	
	public void recargarSaldo(double recarga) {
		saldo += recarga;
	}


	public HashSet<Tiquete> getTiquetes() {
		return tiquetes;
	}


	public void addTiquete(Tiquete tiquete) {
		tiquetes.add(tiquete);
	}
	
	public void solicitarDevolucion(int idT, String login, String motivo) {
		for (Tiquete t : tiquetes) {
			if (t.getIdT() == idT) {
				if (t.getPropietario() == login) {
					t.setDevolucionSolicitada(true);
					t.setMotivoDevolucion(motivo);
				}
			}
		}
		
	}
	
	public void transferirTiquete(int idT, String loginComprador, String passwordVendedor) {
		//el vendedor va a ser el cliente que transfeira el tiquete
		if(this.getPassword().equals(passwordVendedor)) {
			
			TiqueteSimple encontrado = null;
			HashSet<Tiquete> misTiquetes = this.getTiquetes();
			
			for (Tiquete t : misTiquetes) {
				if (t instanceof TiqueteSimple) {
		            TiqueteSimple ts = (TiqueteSimple) t;
		            if (ts.getIdT() == idT) {
		                encontrado = ts;
		            }
		        } else if (t instanceof TiqueteMultiple) {
		            TiqueteMultiple tm = (TiqueteMultiple) t;
		            for (TiqueteSimple ts : tm.getEntradas()) {
		                if (ts.getIdT() == idT) {
		                    encontrado = ts;
		                }
		            }
		        }
			}
			
			if (encontrado.getPropietario().equals(this.getLogin())) {
				encontrado.setPropietario(loginComprador);
			}
			
		}
	}
	
	
	public void comprarTiqueteSimple(Evento evento, Localidad localidad, int cantidad, metodoPago metodo, Administrador admin) {
		
		if(!localidad.puedeVender()) {
			throw new IllegalArgumentException("No se pueden vender mas tiquetes en esta localidad");
		}
		
		
		ArrayList<Tiquete> seleccionados = new ArrayList<>();
		for(Tiquete t : localidad.getTiquetes()) {
			if(t instanceof TiqueteSimple simple && simple.getStatus() == estadoTiquete.DISPONIBLE) {
				seleccionados.add(simple);
				if(seleccionados.size() == cantidad)break;
			}
		}
		
		if(seleccionados.size() < cantidad) {
			throw new IllegalArgumentException("No hay suficientes tiquetes disponibles");
		}
		
		
		Pago pago = new Pago(admin.generarIdPago(), LocalDate.now(), metodo, admin.getCargoServicio(), admin.getCargoImpresion(), seleccionados);
		
		
		boolean aprobado = pago.procesar();
		if(!aprobado) 
			throw new  IllegalStateException("El pago fue rechazado");
			
		if(metodo == metodoPago.SALDO) {
			if(saldo < pago.getMonto()) {
				throw new IllegalStateException("Saldo Insuficiente");
			}
			saldo -= pago.getMonto();
		}
		
		for(Tiquete t: seleccionados) {
			((TiqueteSimple) t).setPropietario(this.getLogin());
			addTiquete(t);
		}
		
		System.out.println("Compra exitosa de" + cantidad + " tiquet(s) en" + localidad.getNombreL());
		System.out.println("Total de la compra: " + pago.getMonto());
	}
	
	
	

	public void comprarTiqueteMultiple(Evento e, TiqueteMultiple paquete, metodoPago metodo, Administrador admin ) {
		
		if(paquete == null) {
			throw new IllegalStateException("El paquete no puede ser nulo");
		}
		
		List<TiqueteSimple> tiquetes = paquete.getEntradas();
		
		if(tiquetes == null || tiquetes.isEmpty()) {
			throw new IllegalStateException("El paquete no contiene entradas validas");
		}
		
		ArrayList<Tiquete> tiquetesCompra = new ArrayList<>();
		tiquetesCompra.add(paquete);
		
		Pago pago = new Pago(admin.generarIdPago(), LocalDate.now(), metodo, admin.getCargoServicio(), admin.getCargoImpresion(), tiquetesCompra);
		
		boolean aprobado = pago.procesar();
		
		if(!aprobado) {
			throw new IllegalStateException("El pago del paquete fue rechazado");
		}
		
		if(metodo == metodoPago.SALDO) {
			if(saldo < pago.getMonto()) {
				throw new IllegalStateException("Saldo Insuficiente");
			}
			saldo -= pago.getMonto();
		}
		
		paquete.setPropietario(this.getLogin());
		for(TiqueteSimple s : tiquetes) {
			s.setPropietario(this.getLogin());
		}
		
		addTiquete(paquete);
		
		System.out.println("Compra exitosa del paquete para el evento: " + e.getNombreE());
		System.out.println("Total de la compra: " + pago.getMonto());
	}
	
}
