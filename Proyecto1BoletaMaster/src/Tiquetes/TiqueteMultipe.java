package Tiquetes;

import java.util.*;

public class TiqueteMultipe extends Tiquete{
	
	private List<TiqueteSimple> entradas;
	private double precioPaquete;
	private boolean transferenciaParcial = false;
	
	public TiqueteMultipe(int idT, boolean transferible, String propietario, estadoTiquete status, String tipo){
		super(idT, transferible, propietario, status, tipo);
		Tiquete.setTransferible(transferenciaParcial);
		
	}
	
	private void transferirPaquete(String loginComprador, String passwordVendedor) {
		
	}
	
	private boolean puedeTransferirseComoPaquete() {
		return true;
	}

}
