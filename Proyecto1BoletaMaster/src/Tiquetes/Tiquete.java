package Tiquetes;

public abstract class Tiquete {
	

	protected int idT;
	protected boolean transferible;
	protected String propietario; // login del dueno actual
	protected estadoTiquete status;
	protected String tipo; // SIMPLE, MULTIPLE, DELUXE, etc.
	protected boolean devolucionSolicitada;
	protected String motivoDevolucion;
		
	public Tiquete(int idT, boolean transferible, String propietario, estadoTiquete status, String tipo) {
		
		this.idT = idT;
		this.transferible = transferible;
		this.propietario = propietario;
		this.status = status;
		this.tipo = tipo;
		
	}

    // Getters simples para leer o ver los valores de los atribbutos ptrotegidos o privados
    public int getIdT() { return idT; }
    public boolean esTransferible() { return transferible; }
    public String getPropietario() { return propietario; }
    public estadoTiquete getStatus() { return status; }
    public String getTipo() { return tipo; }

    // Setters simples para modificar los valores de os atrbutos si se llega a necesitar
    public void setTransferible(boolean t) { this.transferible = t; }
    public void setPropietario(String login) { this.propietario = login; }
    public void setStatus(estadoTiquete s) { this.status = s; }
    public void setTipo(String t) { this.tipo = t; }

    // Cada subclase define cómo calcula el  precio y cómo se transfiere
    public abstract double precioTotal(double cargoServicioPct, double cargoImpresionFijo);
    public abstract void transferir(String loginComprador);

    // Cambia el estado del tiquete a COMPRADO cuando se realiza el pago.
    public void marcarComprado() {
        this.status = estadoTiquete.COMPRADO;
    }

	public void setIdT(int idT) {
		this.idT = idT;
	}
	
	
	public void setDevolucionSolicitada(boolean devolucionSolicitada) {
		this.devolucionSolicitada = devolucionSolicitada;
	}
	
	public boolean getDevolucionSolicitada() {
		return devolucionSolicitada;
	}
	
	
	public void setMotivoDevolucion(String motivoDevolucion) {
		this.motivoDevolucion = motivoDevolucion;
	}
	
	
	public String getMotivoDevolucion() {
		return motivoDevolucion;
	}
	

}
