package Tiquetes;

import modelo.Evento;
import modelo.Localidad;


public class TiqueteSimple extends Tiquete {

    private Evento evento;
    private Localidad localidad;
    private boolean enumerado;
    private int numAsiento;


    public TiqueteSimple(int idT, boolean transferible, String propietario, estadoTiquete status,
                         String tipo, Evento evento, Localidad localidad) {
        super(idT, transferible, propietario, status, tipo);
        this.evento = evento;
        this.localidad = localidad;
        this.enumerado = localidad.isNumerada();
        this.numAsiento = -1; // sin asiento al crear
    }

    // Asigna asiento si la localidad es numerada y sihay cupos
    public void asignarAsiento() {
        if (!localidad.isNumerada()) {
            enumerado = false;
            numAsiento = -1;
            return;
        }
        if (numAsiento > 0) return; // si ya tenía asiento

        int asiento = localidad.asignarAsientoDisponible();
        if (asiento == -1) {
            throw new IllegalStateException("No hay asientos disponibles.");
        }
        enumerado = true;
        numAsiento = asiento;
    }



 
    
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
    public boolean isEnumerado() { return enumerado; }
    public int getNumAsiento() { return numAsiento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
    public void setEnumerado(boolean enumerado) { this.enumerado = enumerado; }
    public void setNumAsiento(int numAsiento) { this.numAsiento = numAsiento; }


    public String toString() {
        return "TiqueteSimple{id=" + idT + ", prop='" + propietario + "', estado=" + status +
               ", tipo='" + tipo + "', asiento=" + numAsiento + "}";
    }
}