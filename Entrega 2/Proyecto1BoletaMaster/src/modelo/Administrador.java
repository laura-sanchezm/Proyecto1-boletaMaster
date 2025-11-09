package modelo;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import Tiquetes.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Administrador extends Usuario{
	
	double cargoServicio;
	double cargoImpresion;
	private List<Evento> eventos;
	private List<Venue> venues;
	private EstadosFinancieros estadosFinancieros;
	
	
	public Administrador(String login, String password) {
		super(login, password);
		this.eventos = new ArrayList<>();
		this.venues = new ArrayList<>();
		this.estadosFinancieros = new EstadosFinancieros();
	}
	
	
	public void registrarVenue(Venue v) {
		venues.add(v);
	}
	
	public void aprobarVenue(String nombreV, LocalDate fechaEvento) {
		for(Venue v : venues) {
			if(v.getNombreV().equalsIgnoreCase(nombreV)) {
				if(v.consultarDisponibilidad(fechaEvento)) {
					v.setAprobado(true);
					System.out.println(" El venue " + nombreV +" aprobado para el " + fechaEvento );
				}
				else {
					System.out.println("El venue " + nombreV +" no esta disponible para esta fecha");
				}
				return;
			}
		}
		System.out.println("No se encontró un venue con ese nombre");
	}
	
	
	
	public void registrarEvento(Evento e) {
		eventos.add(e);
	}
	
	public void cancelarEvento(int idE) {
		for (Evento e : eventos) {
			if (e.getIdE() == idE) {
				e.setEstado(estadoEvento.CANCELADO);
				System.out.println("Evento Cancelado");
			}
		}
		
	}
	
	public void aprobarCancelacion(int idE, HashSet<Usuario> usuarios) {
		for(Evento e : eventos) {
			if(e.getIdE() == idE && e.getCancelacionSolicitada()) {
				e.setEstado(estadoEvento.CANCELADO);
				e.setCancelacionSolicitada(false);
				e.setCancelacionAprobada(true);
				System.out.println("Cancelacion del evento " + e.getNombreE() + " aprobada.");
				
				for(Localidad loc : e.getLocalidades()) {
					for(Tiquete t : loc.getTiquetes()) {
						if(t.getStatus() == estadoTiquete.COMPRADO) {
							for(Usuario u : usuarios) {
								if(u instanceof Cliente cliente && cliente.getLogin().equals(t.getPropietario())) {
									double monto = (t instanceof TiqueteSimple s) ? s.getLocalidad().getPrecioBase() : (t instanceof TiqueteMultiple m) ? m.getPrecioPaquete() : 0;
									cliente.recargarSaldo(monto);
									t.setStatus(estadoTiquete.DEVUELTO);
									System.out.println("Reembolso de $" + monto + " al cliente " + cliente.getLogin());
								}
							}
						}
					}
				}
				
				Organizador org = e.getOrganizador();
				  System.out.println("Notificacion enviada al organizador '" + org.getLogin() + "':");
		            System.out.println("La cancelacion de tu evento '" + e.getNombreE() + "' fue aprobada.\n");

		            return; 
			}
		}
		System.out.println("No se encontro el evento o no tiene cancelacion pendiente.");
	}
	
	// generador de id para pago
    private final AtomicInteger seqPago = new AtomicInteger(1);

    public int generarIdPago() {
        return seqPago.getAndIncrement();
    }
	
    
    
	public void fijarCargoServicio(double s) {
		this.cargoServicio = s /100;
	}
	
	public void fijarCargoImpresion(double i) {
		this.cargoImpresion = i;
	}
	
	public boolean aprobarDevolucion(int idT, HashSet<Usuario> usuarios) {
		for (Evento e : eventos) {
			Tiquete t = e.buscarTiquete(idT);
			if (t != null && t.getDevolucionSolicitada()) {
				// aprueba devolucion si el motivo contiene la palabra hospital o viaje
				String motivo = (t.getMotivoDevolucion().toLowerCase() !=  null) ? t.getMotivoDevolucion().toLowerCase():"";
				if (motivo.contains("hospital") || motivo.contains("viaje")) {
					
					Cliente clienteProp = null;
					for(Usuario u : usuarios) {
						if(u instanceof Cliente && u.getLogin().equals(t.getPropietario())) {
							clienteProp = (Cliente) u;
							break;
						}
					}
					double montoReembolso = 0;
					if(t instanceof TiqueteSimple simple)  {
						montoReembolso = simple.getLocalidad().getPrecioBase();
					}else if(t instanceof TiqueteMultiple multiple) {
						montoReembolso = multiple.getPrecioPaquete();
					}
					
					
					if(clienteProp != null) {
						clienteProp.recargarSaldo(montoReembolso);
						System.out.println("Se reembolsaron $" + montoReembolso + " al cliente " + clienteProp.getLogin());
					}else {
						System.out.println("No se encontro el cliente propietario para el reembolso.");
					}
					
					t.setStatus(estadoTiquete.DEVUELTO);
					t.setDevolucionSolicitada(false);
					t.setMotivoDevolucion(null);
					
					if(t instanceof TiqueteSimple simple) {
						simple.getLocalidad().reponerDevolucion(simple);
					}
					
					System.out.println("Devolucion aprobada y tiquete repuesto (ID): " +idT);
					return true;
					
				}else {
					System.out.println("Motivo no valido. Devolucion rechazada");
					t.setDevolucionSolicitada(false);
					t.setMotivoDevolucion(null);
					return false;
				}
			}
		}
		System.out.println("No se encontro el tiquete o no tiene solicitud pendiente");
		return false;
	}
	
	public List<Tiquete> listarDevolucionesPendientes() {
	    List<Tiquete> pendientes = new ArrayList<>();

	    for (Evento e : eventos) {
	        for (Localidad loc : e.getLocalidades()) {
	            for (Tiquete t : loc.getTiquetes()) {
	                if (t.getDevolucionSolicitada()) {
	                    pendientes.add(t);
	                }
	            }
	        }
	    }

	    return pendientes;
	}
	
	public void mostrarDevolucionesPendientes() {
	    List<Tiquete> pendientes = listarDevolucionesPendientes();

	    if (pendientes.isEmpty()) {
	        System.out.println("No hay solicitudes de devolución pendientes.");
	        return;
	    }

	    System.out.println("===== Solicitudes de Devolución Pendientes =====");
	    for (Tiquete t : pendientes) {
	        System.out.println("ID Tiquete: " + t.getIdT());
	        System.out.println("Propietario: " + t.getPropietario());
	        System.out.println("Motivo: " + t.getMotivoDevolucion());
	        System.out.println("Tipo: " + t.getTipo());
	        System.out.println("-----------------------------------");
	    }
	}
	
	public double verGananciasPorFecha(LocalDate fechaMin, LocalDate fechaMax, EstadosFinancieros ef) {
		return ef.totalGananciasPorFecha(fechaMin, fechaMax);
	}
	
	public double verGananciasPorEvento(int idE, EstadosFinancieros ef) {
		return ef.totalGananciasPorEvento(idE);
	}
	
	public double verGananciasPorOrganizador(int idO, EstadosFinancieros ef) {
		return ef.totalGananciasPorOrganizador(idO);
	}

	public double getCargoServicio() {
		return cargoServicio;
	}

	public double getCargoImpresion() {
		return cargoImpresion;
	}

	public List<Evento> getEventos() {
		return eventos;
	}
	
	public List<Venue> getVenues(){
		return venues;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
	public EstadosFinancieros getEstadosFinancieros() {
		return estadosFinancieros;
	}
	
	

}
