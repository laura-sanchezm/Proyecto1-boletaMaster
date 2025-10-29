package exec;

import modelo.*;
import pagos.*;
import Tiquetes.*;
import Persistencia.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Random;
import java.time.format.DateTimeFormatter;


public class Consola {
	
	
	public static modelo.Usuario buscarUsuario(HashSet<modelo.Usuario> usuarios, String login, String password){
		for(modelo.Usuario u : usuarios) {
			if(u.getLogin().equals(login) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
	}
	
	public static boolean loginYaExiste(HashSet<modelo.Usuario> usuarios, String login) {
		for(modelo.Usuario u : usuarios) {
			if(u.getLogin().equals(login)) {
				return true;
			}
		}
		return false;
	}
	
	public static modelo.Organizador buscarOrganizadorPorId(HashSet<modelo.Usuario> usuarios, int idO){
		for(modelo.Usuario u : usuarios) {
			if(u instanceof modelo.Organizador) {
				modelo.Organizador o = (modelo.Organizador) u;
				if(o.getIdO() == idO) {
					return o;
				}
			}
		}
		return null;
	}
	
	
	public static void menuCliente(int option, modelo.Cliente cliente, ArrayList<modelo.Evento> eventos) {
		switch(option) {
		case 1: 
			System.out.println("===== Consulta de Saldo =====\n");
			double saldo = cliente.consultarSaldo();
			System.out.println("Saldo: " + Double.toString(saldo));
			System.out.println("");
			break;
		case 2:
			System.out.println("===== Recargar Saldo =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Ingrese el monto que quiere recargar: ");
				double recarga = Double.parseDouble(br.readLine().trim());
				cliente.recargarSaldo(recarga);
				System.out.println("Su saldo es de " + Double.toString(cliente.consultarSaldo()) + " " + "pesos");
				System.out.println("");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("===== Comprar Tiquetes =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("¿Que metodo de pago desea utilizar? ");
				System.out.println("1. Saldo");
				System.out.println("2. Pasarela externa");
				int optP = Integer.parseInt(br.readLine().trim());
				
				if(optP == 1) {
					pagos.metodoPago metodo = pagos.metodoPago.SALDO;
				}
				else if(optP == 2) {
					pagos.metodoPago metodo = pagos.metodoPago.PASARELA_EXTERNA;
				}
				
				System.out.println("Seleccione el evento al cual quiere asistir.");
				
				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
				
				for(int i = 0 ; i < eventos.size(); i++) {
					modelo.Evento evento =  eventos.get(i);
					
					String fechaFormateada = evento.getFecha().format(formatoFecha);
					String horaFormateada = evento.getHora().format(formatoHora);
					System.out.println((i+1) + ". " + evento.getNombreE() + "-" + fechaFormateada + "-" +" a las " + horaFormateada + "-" + evento.getVenue());
						
				}
				
				System.out.println("Ingrese el numero del evento: ");
				int selectionE = Integer.parseInt(br.readLine().trim());
				
				if(selectionE < 1 || selectionE > eventos.size()) {
					System.out.println("Opcion invalida. Intente de nuevo.\n");
					break;
				}
				
				modelo.Evento eventoSelection = eventos.get(selectionE - 1);
				System.out.println("\nHa seleccionado el evento: " + eventoSelection.getNombreE() + "\n");
				
				
				List<modelo.Localidad> localidades = eventoSelection.getLocalidades();
				

					System.out.println("Seleccione la localidad del tiquete: ");
					
					for(int j = 0; j < localidades.size(); j++) {
						modelo.Localidad localidad = localidades.get(j);
						if(localidad.puedeVender()) {
							System.out.println((j+1) +"."+ " " + localidad.getNombreL() + " -Precio: " + Double.toString(localidad.getPrecioBase()) );
						}
					}
				
					System.out.println("Ingrese el numero de la localidad: ");
					int selectionL = Integer.parseInt(br.readLine().trim());
				
					if(selectionL < 1 || selectionL > localidades.size()) {
						System.out.println("Opcion invalida. Intente de nuevo.\n");
						break;
					}
				
					modelo.Localidad localidadSelection = localidades.get(selectionL - 1);
					modelo.Administrador admin = new modelo.Administrador("ADMIN", "DPOO");
					
					
					ArrayList<Tiquetes.TiqueteMultiple> paquetes = new ArrayList<>();
					for(Tiquetes.Tiquete t : localidadSelection.getTiquetes()) {
						if(t instanceof Tiquetes.TiqueteMultiple && t.getStatus() == Tiquetes.estadoTiquete.DISPONIBLE ) {
							paquetes.add((Tiquetes.TiqueteMultiple)t);
						}
					}
					
					System.out.println("¿Que desea comprar?");
					if(!paquetes.isEmpty()) {
						System.out.println("1. Comprar paquete de tiquetes");
						System.out.println("2. Comprar tiquetes individuales");
					}
					else {
						System.out.println("No hay paquetes disponibles");
						System.out.println("2. Comprar tiquetes individuales");
					}
					
					int optCompra = Integer.parseInt(br.readLine().trim());
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public static void main(String []args) {
		
		Random random = new Random();
		HashSet<modelo.Usuario> usuarios = new HashSet<modelo.Usuario>();
		modelo.Administrador admin = new modelo.Administrador("ADMIN", "DPOO");
		usuarios.add(admin);
		ArrayList<modelo.Evento> eventos = new ArrayList<modelo.Evento>();
		
		while(true) {
		System.out.println("====== Inicio De Sesion ======");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Ingrese su login:");
			String login = br.readLine().trim();
			System.out.println("Ingrese su contraseña:");
			String password = br.readLine().trim();
				
			// se pide el login y la contraseña 
				
			modelo.Usuario usuarioExistente = buscarUsuario(usuarios, login, password);
			
			// revisa si el usuario ya esta registrado, si lo esta inicia sesion de lo contrario se tiene que crear un nuevo usuario
			
			if(usuarioExistente != null) {   
				System.out.println("Inicio de sesion exitoso\n");
				
				if(usuarioExistente instanceof modelo.Cliente) {
					BufferedReader brC = new BufferedReader(new InputStreamReader(System.in));
					while(true) {
						System.out.println("===== Menu Cliente =====");
						System.out.println("1. Consultar Saldo");
						System.out.println("2. Recargar Saldo");
						System.out.println("3. Comprar Tiquetes");
						System.out.println("4. Transferir Tiquetes");
						System.out.println("5. Solicitar Devolucion");
						System.out.println("6. Salir");
						
						int optC = Integer.parseInt(brC.readLine().trim());
						menuCliente(optC, (modelo.Cliente) usuarioExistente  ,eventos);
						
						if(optC == 6) {
							System.out.println("Saliendo de la aplicacion....");
							break;
						}
					}
				}
				else if(usuarioExistente instanceof modelo.Organizador) {
					//TODO menu organizador
				}
				else if(usuarioExistente instanceof modelo.Administrador) {
					//TODO menu Admin
				}
			}
			else {
				System.out.println("Crea un usuario\n");
				System.out.println("Ingresa el login que utilizaras:");
				String newLogin = br.readLine().trim();
				
				while(loginYaExiste(usuarios,newLogin)) {
					System.out.print("Ese login ya esta en uso. Intente con otro: ");
					newLogin = br.readLine().trim();					
				}
				
				
				System.out.println("Ingrese la contraseña que utilizaras:");
				String newPassword = br.readLine().trim();
				System.out.println("");
					
				System.out.println("Eres cliente o organizador:");
				System.out.println("1. Cliente");
				System.out.println("2. Organizador\n");
				int opt = Integer.parseInt(br.readLine().trim());
					
					if(opt == 1) {
						modelo.Cliente cliente = new modelo.Cliente(newLogin, newPassword);
						usuarios.add(cliente);
						System.out.println("Cliente creado exitosamente\n");
						
						// se inicialisa el menu de cliente
						BufferedReader brC = new BufferedReader(new InputStreamReader(System.in));
						while(true) {
							System.out.println("===== Menu Cliente =====");
							System.out.println("1. Consultar Saldo");
							System.out.println("2. Recargar Saldo");
							System.out.println("3. Comprar Tiquetes");
							System.out.println("4. Transferir Tiquetes");
							System.out.println("5. Solicitar Devolucion");
							System.out.println("6. Salir");
							
							int optC = Integer.parseInt(brC.readLine().trim());
							menuCliente(optC, cliente  ,eventos);
							
							if(optC == 6) {
								System.out.println("Saliendo del menu....");
								break;
							}
						}
					}
					else if(opt == 2) {
						int idO = random.nextInt();
						while(buscarOrganizadorPorId(usuarios, idO) != null) {
							idO = random.nextInt();
						}
						
						modelo.Organizador organizador = new modelo.Organizador(newLogin, newPassword, idO);
						usuarios.add(organizador);;
						System.out.println("Organizador creado exitosamente\n");
					}
					
				}	
			}
			catch(Exception e ) {
				e.printStackTrace();
			}
		
		}
	}
}
