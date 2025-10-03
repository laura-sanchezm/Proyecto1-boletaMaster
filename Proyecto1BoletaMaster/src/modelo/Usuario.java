package modelo;

public class Usuario {
	
	private String login;
	private String password;
	
	
	public Usuario (String Login, String Password) {
		
		this.login = Login;
		this.password = Password;	 
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
