package Tiquetes;

public abstract class Tiquete {
	
	private int idT;
	private boolean transferible;
	private String propietario;
	private estadoTiquete status;
	private String tipo;
	private boolean devolucionSolicitada;
	private String motivoDevolucion;
	
	
	public Tiquete(int idT, boolean transferible, String propietario, estadoTiquete status, String tipo) {
		
		this.idT = idT;
		this.transferible = transferible;
		this.propietario = propietario;
		this.status = status;
		this.tipo = tipo;
		
	}
	
	
	public boolean esDisponible() {
		return this.status.equals(estadoTiquete.DISPONIBLE);	
	}
	

	public abstract boolean esTransferible();
		
	
	public abstract void transferir(String loginARecivir, String passwordDuenio);


	public int getIdT() {
		return idT;
	}


	public void setIdT(int idT) {
		this.idT = idT;
	}


	public boolean isTransferible() {
		return transferible;
	}


	public void setTransferible(boolean transferible) {
		this.transferible = transferible;
	}


	public String getPropietario() {
		return propietario;
	}


	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}


	public estadoTiquete getStatus() {
		return status;
	}


	public void setStatus(estadoTiquete status) {
		this.status = status;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
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
