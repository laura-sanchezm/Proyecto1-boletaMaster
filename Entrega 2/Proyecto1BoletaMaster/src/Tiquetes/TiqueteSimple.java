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

     // llama al constructor detiquete
     super(idT, transferible, propietario, status, tipo);

    
     this.evento = evento;
     this.localidad = localidad;
     this.enumerado = localidad.isNumerada();
     this.numAsiento = -1; // por defult no tiene asiento asignado
 }

 

 public void asignarAsiento() {

     // Si la localidad no tiene asientos numerados, no hacemos nada
     if (!localidad.isNumerada()) {
         enumerado = false;
         numAsiento = -1;
         return;
     }

     
     if (numAsiento > 0) {
         return;
     }

     // Pide un asiento disponible a la localidad
     int asiento = localidad.asignarAsientoDisponible();

     
     if (asiento == -1) {
         throw new IllegalStateException("No hay asientos disponibles en esta localidad.");
     }

    
     enumerado = true;
     numAsiento = asiento;
 }

 
 //Calcula el precio total del tiquete.
 public double precioTotal(double cargoServicioPct, double cargoImpresionFijo) {
     double base = localidad.getPrecioBase();
     double cargoServicio = base * (cargoServicioPct / 100.0);
     return base + cargoServicio + cargoImpresionFijo;
 }


 public void transferir(String loginComprador) {

   
     if (!esTransferible()) {
         throw new IllegalStateException("Este tiquete no se puede transferir.");
     }


     if (status == estadoTiquete.DISPONIBLE) {
         throw new IllegalStateException("El tiquete aún no ha sido comprado.");
     }

     if (status == estadoTiquete.EXPIRADO) {
         throw new IllegalStateException("El tiquete ya expiró.");
     }

    
     propietario = loginComprador;
     status = estadoTiquete.TRANSFERIDO;
 }


 public void aplicarOferta() {
     
 }

 // Getters y Setters
 public Evento getEvento() { return evento; }
 public Localidad getLocalidad() { return localidad; }
 public boolean isEnumerado() { return enumerado; }
 public int getNumAsiento() { return numAsiento; }

 public void setEvento(Evento evento) { this.evento = evento; }
 public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
 public void setEnumerado(boolean enumerado) { this.enumerado = enumerado; }
 public void setNumAsiento(int numAsiento) { this.numAsiento = numAsiento; }


 public String toString() {
     return "TiqueteSimple{" +
             "id=" + idT +
             ", propietario='" + propietario + '\'' +
             ", estado=" + status +
             ", tipo='" + tipo + '\'' +
             ", asiento=" + numAsiento +
             '}';
 }
}
