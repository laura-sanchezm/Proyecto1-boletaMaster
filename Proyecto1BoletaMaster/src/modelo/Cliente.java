package modelo;

import java.util.HashSet;
import Tiquetes.Tiquete;

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
	
	public void solicitarDevolucion(int idT, int id, String motivo) {
		for (Tiquete t : tiquetes) {
			if (t.getIdT() == idT) {
				
			}
		}
		
	}
	
	public void transferirTiquete(int idT, String loginComprador, String passwordVendedor) {
		//TO DO
	}
	
	
	
}
