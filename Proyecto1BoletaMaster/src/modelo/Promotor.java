package modelo;

import java.util.List;

public class Promotor extends Usuario{
	
	private int idP;
	private List<Evento> eventos;
	// finanzas
	
	public Promotor(String login, String password) {
		super(login, password);
	}

}
