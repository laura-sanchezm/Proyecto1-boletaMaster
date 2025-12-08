package Tiquetes;

import modelo.Evento;
import modelo.Localidad;


public class TiqueteSimple extends Tiquete {

    private Evento evento;
    private Localidad localidad;
    private int numAsiento;


    public TiqueteSimple(int idT, boolean transferible, String propietario, estadoTiquete status,
                         String tipo, Evento evento, Localidad localidad) {
        super(idT, transferible, propietario, status, tipo);
        this.evento = evento;
        this.localidad = localidad;
        
        if(localidad != null && localidad.isNumerada()) {
        	this.numAsiento = localidad.asignarAsientoDisponible();
        }else {
        	this.numAsiento = -1;
        }
    }

    // Asigna asiento si la localidad es numerada y sihay cupos
    public void asignarAsiento() {
    	if (localidad != null && localidad.isNumerada() && numAsiento == -1) {
            this.numAsiento = localidad.asignarAsientoDisponible();
            
            System.out.println("Asignando asiento en localidad " + localidad.getNombreL() + " → " + numAsiento);

        } 
    }



 
    @Override
    public void transferir(String loginComprador) {
        if (!esTransferible()) {
            throw new IllegalStateException("No se puede transferir.");
        }
        if (status == estadoTiquete.DISPONIBLE) {
            throw new IllegalStateException("Aún no ha sido comprado.");
        }
        if (status == estadoTiquete.EXPIRADO) {
            throw new IllegalStateException("El tiquete expiró.");
        }
        propietario = loginComprador;
        status = estadoTiquete.TRANSFERIDO;
    }


    // Getters y setters 
    public Evento getEvento() { return evento; }
    public Localidad getLocalidad() { return localidad; }
    public String getPropietario() { return propietario; }
    public boolean isEnumerado() { return localidad != null && localidad.isNumerada(); }
    public int getNumAsiento() { return numAsiento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
    public void setNumAsiento(int numAsiento) { this.numAsiento = numAsiento; }


    public String toString() {
        return "TiqueteSimple{id=" + idT + ", prop='" + propietario + "', estado=" + status +
               ", tipo='" + tipo + "', asiento=" + numAsiento + "}";
    }
}