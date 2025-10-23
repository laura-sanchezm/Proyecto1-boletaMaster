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
	
	
	public Administrador(String login, String password) {
		super(login, password);
		this.eventos = new ArrayList<>();
	}
	
	public void aprobarVenue(int idE) {
		for (Evento e : eventos) {
			if (e.getIdE() == idE) {
				e.setEstado(estadoEvento.CANCELADO);
			}
		}
	}
	
	
	
	public void cancelarEvento(int idE) {
		for (Evento e : eventos) {
			if (e.getIdE() == idE) {
				Venue v = e.getVenue();
				//aprueba segun disponibilidad basado en fecha
				LocalDate fecha = e.getFecha();
				if (!v.consultarDisponibilidad(fecha)) {
					v.setAprobado(false);
				} else {
					v.setAprobado(true);
					v.addFechaOcupada(fecha);
					e.setEstado(estadoEvento.PROGRAMADO);
				}
			}
		}
		
	}
	
	public void aprobarCancelacion(int idE) {
		for (Evento e : eventos) {
			if(e.getDevolucionSolicitada()== true) {
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

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
	

}
