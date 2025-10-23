package modelo;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;

import Pago.Pago;
import Pago.metodoPago;
import Pago.estadoPago;

import modelo.Evento;
import modelo.Localidad;
import modelo.Oferta;

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
		//TO DO
	}
	
	public void comprarTiquetes(Evento e, int idL, int cantidad, metodoPago metodo, Administrador admin) {

		//econtrar localidad en evento
		Localidad loc = null;
		for (Localidad l : e.getLocalidades()) {
            if (l != null && l.getIdL() == idL) {
                loc = l;
            }
        }
		ArrayList<Tiquete> seleccionados = new ArrayList<>();
		for (Tiquete t : loc.getTiquetes()) {
            if (t instanceof TiqueteSimple && t.getStatus() == estadoTiquete.DISPONIBLE) {
                seleccionados.add(t);
            }
        }
		
		if (seleccionados.size() < cantidad) {
            throw new IllegalStateException("No hay suficientes tiquetes disponibles");
        }
		
		//Integrar ofertas - FALTA!!
		ArrayList<Oferta> ofertasActivas = new ArrayList<>();
		
		Pago pago = new Pago(
                admin.generarIdPago(),
                LocalDate.now(),
                0.0,                     
                estadoPago.PENDIENTE,
                metodo,
                admin.getCargoServicio(),
                admin.getCargoImpresion(),
                seleccionados,
                ofertasActivas
        );
		
		boolean aprobado = pago.procesar();
        if (!aprobado) {
            throw new IllegalStateException("El pago fue rechazado.");
        }
        
        //asignar propietario de tiquetes
        for (Tiquete t : seleccionados) {
            ((TiqueteSimple) t).setPropietario(this.getLogin());
        }
		
	}
	
	
}
