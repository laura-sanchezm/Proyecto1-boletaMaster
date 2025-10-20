package Tiquetes;


public abstract class Tiquete {

   
    protected int idT;              // identificador del tiquete
    protected boolean transferible; 
    protected String propietario;   // login o nombre del dueño actual
    protected estadoTiquete status; 
    protected String tipo;          

    
    public Tiquete(int idT, boolean transferible, String propietario, estadoTiquete status, String tipo) {
        this.idT = idT;
        this.transferible = transferible;
        this.propietario = propietario;
        this.status = status;
        this.tipo = tipo;
    }

    // Getters
    public int getIdT() { return idT; }
    public boolean esTransferible() { return transferible; }
    public String getPropietario() { return propietario; }
    public estadoTiquete getStatus() { return status; }
    public String getTipo() { return tipo; }

    // Setters
    public void setTransferible(boolean transferible) { this.transferible = transferible; }
    public void setPropietario(String propietario) { this.propietario = propietario; }
    public void setStatus(estadoTiquete status) { this.status = status; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setIdT(int idT) { this.idT = idT; }

    // las hijas lo hacen y aca se traen
    public abstract double precioTotal(double cargoServicioPct, double cargoImpresionFijo);
    public abstract void transferir(String loginComprador);

    // estado del tiquete a COMPRADO cuando se hace el pago
    public void marcarComprado() {
        this.status = estadoTiquete.COMPRADO;
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
