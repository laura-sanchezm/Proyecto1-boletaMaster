package modelo;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import Tiquetes.Tiquete;
import Tiquetes.estadoTiquete;
import java.util.concurrent.atomic.AtomicInteger;

public class Administrador extends Usuario{
	
	int cargoServicio;
	int cargoImpresion;
	private List<Evento> eventos;
	private List<Venue> venues;
	
	
	public Administrador(String login, String password) {
		super(login, password);
		this.eventos = new ArrayList<>();
		this.venues = new ArrayList<>();
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
	
	public void aprobarCancelacion(int idE) {
		for (Evento e : eventos) {
			if(e.getDevolucionSolicitada()) {
				cancelarEvento(idE);
			}
		}
	}
	
	// generador de id para pago
    private final AtomicInteger seqPago = new AtomicInteger(1);

    public int generarIdPago() {
        return seqPago.getAndIncrement();
    }
	
    
    
	public void fijarCargoServicio(int s) {
		this.cargoServicio = s;
	}
	
	public void fijarCargoImpresion(int i) {
		this.cargoImpresion = i;
	}
	
	public boolean aprobarDevolucion(int idT) {
		for (Evento e : eventos) {
			Tiquete t = e.buscarTiquete(idT);
			if (t.getDevolucionSolicitada() == true) {
				String motivo = t.getMotivoDevolucion();
				// aprueba devolucion si el motivo contiene la palabra hospital o viaje
				if (motivo.contains("hospital") || motivo.contains("viaje")) {
					t.setStatus(estadoTiquete.DISPONIBLE);
					t.setDevolucionSolicitada(false);
					t.setMotivoDevolucion(null);
					t.setPropietario(null);
					return true;
				}
			}
		}
		return false;
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

	public int getCargoServicio() {
		return cargoServicio;
	}

	public void setCargoServicio(int cargoServicio) {
		this.cargoServicio = cargoServicio;
	}

	public int getCargoImpresion() {
		return cargoImpresion;
	}

	public void setCargoImpresion(int cargoImpresion) {
		this.cargoImpresion = cargoImpresion;
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
	
	

}
