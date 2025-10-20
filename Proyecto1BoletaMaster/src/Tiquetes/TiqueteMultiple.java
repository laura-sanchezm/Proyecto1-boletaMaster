package Tiquetes;

import java.util.ArrayList;
import java.util.List;


public class TiqueteMultiple extends Tiquete {

    private List<TiqueteSimple> entradas;   
    private double precioPaquete;           
    private boolean transferenciaParcial; 

    
    public TiqueteMultiple(int idT, boolean transferible, String propietario,
                           estadoTiquete status, String tipo) {
        super(idT, transferible, propietario, status, tipo);
        this.entradas = new ArrayList<>();
        this.precioPaquete = 0;   //se suma 
        this.transferenciaParcial = false;
    }

   
    public void addEntrada(TiqueteSimple t) {
        if (t == null) {
            throw new IllegalArgumentException("La entrada no puede ser null.");
        }
        entradas.add(t);
    }


    public double precioTotal(double cargoServicioPct, double cargoImpresionFijo) {
        if (precioPaquete > 0) {
            return precioPaquete;
        }
        double total = 0.0;
        for (TiqueteSimple t : entradas) {
            total += t.precioTotal(cargoServicioPct, cargoImpresionFijo); //se suma el precio de cada entrada simple.
        }
        return total;
    }


    private boolean puedeTransferirseComoPaquete() {
        if (!esTransferible()) return false;
        if (transferenciaParcial) return false; // No debe haber transferencia parcial antes
        if (status == estadoTiquete.DISPONIBLE) return false;
        if (status == estadoTiquete.EXPIRADO) return false;
        return true;
    }

    // Transfiere todo el paquete al nuevo dueño
    public void transferirPaquete(String loginComprador) {
        if (!puedeTransferirseComoPaquete()) {
            throw new IllegalStateException("No se puede transferir el paquete completo.");
        }
        // transfiere cada entrada 
        for (TiqueteSimple t : entradas) {
            t.transferir(loginComprador);
        }
        // actualizamos los datos del paquete
        propietario = loginComprador;
        status = estadoTiquete.TRANSFERIDO;
    }

    // Transfiere una entrada del paquete por id de tiquete simple
    public void transferirIndividual(int idTEntrada, String loginComprador) {
        TiqueteSimple objetivo = null;
        for (TiqueteSimple t : entradas) {
            if (t.getIdT() == idTEntrada) {
                objetivo = t;
                break;
            }
        }
        if (objetivo == null) {
            throw new IllegalArgumentException("No existe una entrada con id " + idTEntrada);
        }
        objetivo.transferir(loginComprador);
        transferenciaParcial = true; // ya no se podria transferir todo el paquete
    }

  
  
    public void transferir(String loginComprador) {
        transferirPaquete(loginComprador);
    }

    // Getters y setters 
    
    public void setPrecioPaquete(double precioPaquete) {
        if (precioPaquete < 0) {
            this.precioPaquete = 0;
        } else {
            this.precioPaquete = precioPaquete;
        }
    }
    public List<TiqueteSimple> getEntradas() { return entradas; }

    public boolean isTransferenciaParcial() { return transferenciaParcial; }

    public double getPrecioPaquete() { return precioPaquete; }

}
