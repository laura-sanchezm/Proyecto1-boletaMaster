package modelo;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.TiqueteMultiple;
import Tiquetes.estadoTiquete;
import modelo.Evento;
import modelo.Localidad;
import modelo.Oferta;
import pagos.Pago;
import pagos.estadoPago;
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
	
	
	public void comprarTiquetes(Evento e, Map<Integer, Integer> localidadesYcantidades , metodoPago metodo, Administrador admin) {

		ArrayList<Tiquete> tiquetesSeleccionados = new ArrayList<>();
		
		for(Map.Entry<Integer,Integer> entry : localidadesYcantidades.entrySet()) {
			int idL = entry.getKey();
			int cantidad = entry.getValue();
			
			Localidad loc = null;
			for(Localidad l: e.getLocalidades()) {
				if(l != null && l.getIdL() == idL) {
					loc = l;
					break;
				}
			}
			
			if(loc == null) {
				System.out.println("La localidad con el ID " + idL + "no existe en el evento " + e.getNombreE());
				continue;
			}
			
			ArrayList<Tiquete> disponibles = new ArrayList<>();
			for(Tiquete t: loc.getTiquetes()) {
				
			}
		}
		
	}
	
	
}
