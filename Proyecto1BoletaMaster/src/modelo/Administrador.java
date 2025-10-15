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
		
	}
	
	public void cancelarEvento(int idE) {
		//ARREGLAR!
		Evento.setEstado(estadoEvento.CANCELADO);
	}
	
	public void fijarCargoServicio(int s) {
		this.cargoServicio = s;
	}
	
	public void fijarCargoImpresion(int i) {
		this.cargoImpresion = i;
	}
	
	public boolean aprobarDevolucion(int idT) {
		return false;
	}
	
	public void verGanancias(LocalDate fechaMin, LocalDate fechaMax, int idE) {
		
	}

}
