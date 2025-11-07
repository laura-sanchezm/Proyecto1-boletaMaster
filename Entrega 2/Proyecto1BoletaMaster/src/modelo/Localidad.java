package modelo;

import java.util.HashSet;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import java.util.Random;


public class Localidad {
	
	private int idL;
	private String nombreL;
	private boolean numerada;
	private double precioBase; 
	private int capacidadL;
	private HashSet<Tiquete> tiquetes;
	private final HashSet<Integer> asientosOcupados;
	private Oferta oferta;
	
	
	
	public Localidad(int idL, String nombreL, double precioBase, int capacidadL) {
		this.idL = idL;
		this.nombreL = nombreL;
		this.numerada = false;
		this.precioBase = precioBase;
		this.capacidadL = capacidadL;
		this.tiquetes = new HashSet<Tiquete>();
		this.asientosOcupados = new HashSet<Integer>();
		this.oferta = null;
	}


	public int getIdL() {
		return idL;
	}


	public void setIdL(int idL) {
		this.idL = idL;
	}


	public String getNombreL() {
		return nombreL;
	}


	public void setNombreL(String nombreL) {
		this.nombreL = nombreL;
	}


	public boolean isNumerada() {
		return numerada;
	}


	public void setNumerada(boolean numerada) {
		this.numerada = numerada;
	}


	public double getPrecioBase() {
		return precioBase;
	}


	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}


	public int getCapacidadL() {
		return capacidadL;
	}


	public void setCapacidadL(int capacidadL) {
		this.capacidadL = capacidadL;
	}
	
	
	
	public boolean puedeVender() {
		for(Tiquete t : tiquetes) {
			if(t.getStatus() == Tiquetes.estadoTiquete.DISPONIBLE) {
				return true;
			}
		}
		return false;
	}
	
	public HashSet<Tiquete> getTiquetes(){
		return tiquetes;
	}
	
	public void addTiquete(Tiquete tiquete) {
		tiquetes.add(tiquete);
	}
	
	public void reponerDevolucion(Tiquete tiquete) {
		 if (tiquetes.contains(tiquete)) {
		        
		    
		        if (numerada && tiquete instanceof TiqueteSimple simple) {
		            if (simple.getNumAsiento() > 0) {
		                liberarAsiento(simple.getNumAsiento());
		                simple.setNumAsiento(-1); 
		            }
		        }

		        
		        tiquete.setStatus(Tiquetes.estadoTiquete.DISPONIBLE);
		        tiquete.setPropietario(null);
		        tiquete.setDevolucionSolicitada(false);
		        tiquete.setMotivoDevolucion(null);
		    }
	}
	
	public int asignarAsientoDisponible() {
		
		if(!numerada) {
			return -1;
		}
		if(asientosOcupados.size() >= capacidadL) {
			return -1;
		}
		
		Random random = new Random();
		int numAsiento;
		do {
			numAsiento = random.nextInt(capacidadL) + 1;
		}while(asientosOcupados.contains(numAsiento));
		
		
		asientosOcupados.add(numAsiento);
		return numAsiento;
	}
	
	public void liberarAsiento(int asiento) {
		asientosOcupados.remove(asiento);
	}
	
	
	public boolean hayOferta() {
		return oferta != null && oferta.estaVigente();
	}
	
	
	public Oferta getOferta() {
		return oferta;
	}
	
	//funcion auxiliar
		public Tiquete buscarTiquete(int idT) {
			for (Tiquete t: tiquetes) {
				if (t.getIdT() == idT) {
					return t;
				}
			}
				return null;
		}
	
			
}
