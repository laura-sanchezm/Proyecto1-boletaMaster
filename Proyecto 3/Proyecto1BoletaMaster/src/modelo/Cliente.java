package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteMultiple;
import Tiquetes.TiqueteSimple;
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
		
		boolean encontrado = false;	
		for (Tiquete t : tiquetes) {
			if (t.getIdT() == idT) {
				encontrado =  true;
				if (t.getPropietario().equals(login)) {
					if(t.getDevolucionSolicitada()) {
						System.out.println("Ya existe una solicitud de devolucion para este tiquete");
					}else {
						t.setDevolucionSolicitada(true);
						t.setMotivoDevolucion(motivo);
						System.out.println("Solicitud de devolucion registrada para el tiquete " + idT);
					}
				}else {
					System.out.println("No eres el propietario de este tiquete");
				}
				break;
			}
		}
		if(!encontrado) {
			System.out.println("No se encontro el tiquete con el ID proporcionado");
		}
		
	}
	
	
	public void consultarDevoluciones() {
	    boolean hayPendientes = false;
	    for (Tiquete t : tiquetes) {
	        if (t.getDevolucionSolicitada()) {
	            System.out.println("Devolucion pendiente - Tiquete ID: " + t.getIdT() +
	                               " | Motivo: " + t.getMotivoDevolucion());
	            hayPendientes = true;
	        }
	    }
	    if (!hayPendientes) {
	        System.out.println("No tienes devoluciones pendientes o en proceso.");
	    }
	}
	
	public boolean transferirTiquete(int idT, String loginReceptor, String passwordConfirmacion) {
		if(!this.getPassword().equals(passwordConfirmacion)) {
			System.out.println("Contraseña incorrecta, No se puede transferir el tiquete");
			return false;
		}
		
		Tiquete tiquete = buscarTiquetePorId(idT);
		if(tiquete == null) {
			System.out.println("No se encontro ningun tiquete con ese Id");
			return false;
		}
		
		if(!tiquete.esTransferible()) {
			System.out.println("Este tiquete no es transferible");
			return false;
		}
		
		if(tiquete.getStatus() == estadoTiquete.EXPIRADO) {
			System.out.println("El tiquete ha expirado y no es transferible");
			return false;
		}
		
		try {
			tiquete.transferir(loginReceptor);
			System.out.println("Tiquete transferido exitosamente al usuario: " + loginReceptor);
			return true;	
		}catch(Exception e) {
			System.out.println("Error al transferir el tiquete: " + e.getMessage());
			return false;
		}
	}
	
	
	public void comprarTiqueteSimple(Evento evento, Localidad localidad, int cantidad, metodoPago metodo, Administrador admin) {
		
		if(cantidad <= 0) {
			throw new IllegalArgumentException("Cantidad invalida");
		}
		
		
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
		
		admin.getEstadosFinancieros().registrarTransaccion(pago);
		
		for(Tiquete t: seleccionados) {
			if(t instanceof TiqueteSimple simple) {
				simple.setPropietario(this.getLogin());
				
				 if (simple.getLocalidad().isNumerada() && simple.getNumAsiento() == -1) {
					 int nuevoAsiento = simple.getLocalidad().asignarAsientoDisponible();
			            simple.setNumAsiento(nuevoAsiento);
			        }
				 

				simple.setStatus(estadoTiquete.COMPRADO);
				addTiquete(simple);
			}
		}
		
		System.out.println("Compra exitosa de" + cantidad + " tiquete(s) en" + localidad.getNombreL());
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
		
		if(metodo == metodoPago.SALDO) {
			if(saldo < pago.getMonto()) {
				throw new IllegalStateException("Saldo Insuficiente");
			}
		}
		
		
		boolean aprobado = pago.procesar();
		
		if(!aprobado) {
			throw new IllegalStateException("El pago del paquete fue rechazado");
		}
		
		if(metodo == metodoPago.SALDO) {
			saldo -= pago.getMonto();
		}
		
		admin.getEstadosFinancieros().registrarTransaccion(pago);
		
		
		paquete.setPropietario(this.getLogin());
		for(TiqueteSimple s : tiquetes) {
			if(s instanceof TiqueteSimple simple) {
				simple.setPropietario(this.getLogin());
				simple.setStatus(estadoTiquete.COMPRADO);
				
				
				
				addTiquete(simple);
			}
		}
		
		addTiquete(paquete);
		
		System.out.println("Compra exitosa del paquete para el evento: " + e.getNombreE());
		System.out.println("Total de la compra: " + pago.getMonto());
	}
	
	
	public Tiquete buscarTiquetePorId(int idT) {
	    for (Tiquete t : tiquetes) {
	        if (t.getIdT() == idT) {
	            return t;
	        }
	        if (t instanceof TiqueteMultiple multiple) {
	            for (TiqueteSimple entrada : multiple.getEntradas()) {
	                if (entrada.getIdT() == idT) {
	                    return entrada;
	                }
	            }
	        }
	    }
	    return null;
	}

	
}



