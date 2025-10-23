package modelo;

import java.util.HashSet;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;


public class Localidad {
	
	private int idL;
	private String nombreL;
	private boolean numerada;
	private double precioBase; 
	private int capacidadL;
	private HashSet<Tiquete> tiquetes;
	private final HashSet<Integer> asientosOcupados;
	private boolean oferta;
	
	
	
	public Localidad(int idL, String nombreL, double precioBase, int capacidadL) {
		this.idL = idL;
		this.nombreL = nombreL;
		this.numerada = false;
		this.precioBase = precioBase;
		this.capacidadL = capacidadL;
		this.tiquetes = new HashSet<Tiquete>();
		this.asientosOcupados = new HashSet<Integer>();
		this.oferta = false;
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
		return tiquetes.size() < capacidadL;
	}
	
	public HashSet<Tiquete> getTiquetes(){
		return tiquetes;
	}
	
	public void addTiquete(Tiquete tiquete) {
		tiquetes.add(tiquete);
	}
	
	public void reponerDevolucion(Tiquete tiquete) {
		if(tiquetes.contains(tiquete)) {
			if(numerada && tiquete instanceof TiqueteSimple) {
				TiqueteSimple simple = (TiqueteSimple) tiquete;
				if(simple.getNumAsiento() > 0) {
					liberarAsiento(simple.getNumAsiento());
				}
				
			}
			tiquetes.remove(tiquete);
		}
	}
	
	public int asignarAsientoDisponible() {
		
		if(!numerada) {
			return -1;
		}
		if(asientosOcupados.size() >= capacidadL) {
			return -1;
		}
		
		for(int i = 1 ; i <= capacidadL ; i++) {
			if(!asientosOcupados.contains(i)) {
				asientosOcupados.add(i);
				return i;
			}
		}
		
		return -1;
	}
	
	public void liberarAsiento(int asiento) {
		asientosOcupados.remove(asiento);
	}
	
	
	public boolean hayOferta() {
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
