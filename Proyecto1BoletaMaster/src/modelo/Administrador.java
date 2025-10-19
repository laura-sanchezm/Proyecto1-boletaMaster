package modelo;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario{
	
	int cargoServicio;
	int cargoImpresion;
	private List<Evento> eventos;
	
	//finanzas
	
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
	
	public void fijarCargoServicio(int s) {
		this.cargoServicio = s;
	}
	
	public void fijarCargoImpresion(int i) {
		this.cargoImpresion = i;
	}
	
	public boolean aprobarDevolucion(int idT) {
		
	}
	
	public void verGanancias(LocalDate fechaMin, LocalDate fechaMax, int idE) {
		
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
