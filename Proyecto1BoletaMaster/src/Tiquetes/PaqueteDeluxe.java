package Tiquetes;

//Paquete Deluxe: es un paquete de entradas que NO se puede transferir.

public class PaqueteDeluxe extends TiqueteMultiple {

   
    public PaqueteDeluxe(int idT, String propietario) {
        super(idT, false, propietario, estadoTiquete.DISPONIBLE, "DELUXE");
        setTransferible(false);
    }

   
    public void transferir(String loginComprador) {
        throw new IllegalStateException("Los paquetes Deluxe no se pueden transferir.");
    }

  
    public void transferirPaquete(String loginComprador) {
        throw new IllegalStateException("Los paquetes Deluxe no se pueden transferir.");
    }

 
    public void transferirIndividual(int idTEntrada, String loginComprador) {
        throw new IllegalStateException("Los paquetes Deluxe no se pueden transferir.");
    }
}