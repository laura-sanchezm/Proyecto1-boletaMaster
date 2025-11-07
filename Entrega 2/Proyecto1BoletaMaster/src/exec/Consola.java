package exec;

import modelo.*;
import pagos.*;
import Tiquetes.*;
import Persistencia.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Random;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Consola {
	
	
	public static Usuario buscarUsuario(HashSet<Usuario> usuarios, String login, String password){
		for(Usuario u : usuarios) {
			if(u.getLogin().equals(login) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
	}
	
	public static boolean loginYaExiste(HashSet<Usuario> usuarios, String login) {
		for(Usuario u : usuarios) {
			if(u.getLogin().equals(login)) {
				return true;
			}
		}
		return false;
	}
	
	public static Organizador buscarOrganizadorPorId(HashSet<Usuario> usuarios, int idO){
		for(Usuario u : usuarios) {
			if(u instanceof Organizador) {
				Organizador o = (Organizador) u;
				if(o.getIdO() == idO) {
					return o;
				}
			}
		}
		return null;
	}
	
	
	public static void menuCliente(int option, Cliente cliente, ArrayList<Evento> eventos, Administrador admin) {
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
				
				metodoPago metodo = (optP == 1) ? pagos.metodoPago.SALDO : pagos.metodoPago.PASARELA_EXTERNA;
				
				System.out.println("Seleccione el evento al cual quiere asistir.");
				
				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
				
				for(int i = 0 ; i < eventos.size(); i++) {
					Evento evento =  eventos.get(i);
					
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
				
				Evento eventoSelection = eventos.get(selectionE - 1);
				System.out.println("\nHa seleccionado el evento: " + eventoSelection.getNombreE() + "\n");
				
				
				List<Localidad> localidades = eventoSelection.getLocalidades();
				

					System.out.println("Seleccione la localidad del tiquete: ");
					
					for(int j = 0; j < localidades.size(); j++) {
						Localidad localidad = localidades.get(j);
						if(localidad.puedeVender()) {
							System.out.println((j+1) +"."+ " " + localidad.getNombreL() + " - Precio: " + Double.toString(localidad.getPrecioBase()) );
						}
					}
				
					System.out.println("Ingrese el numero de la localidad: ");
					int selectionL = Integer.parseInt(br.readLine().trim());
				
					if(selectionL < 1 || selectionL > localidades.size()) {
						System.out.println("Opcion invalida. Intente de nuevo.\n");
						break;
					}
				
					Localidad localidadSelection = localidades.get(selectionL - 1);
					
					
					ArrayList<Tiquetes.TiqueteMultiple> paquetes = new ArrayList<>();
					for(Tiquetes.Tiquete t : localidadSelection.getTiquetes()) {
						if(t instanceof Tiquetes.TiqueteMultiple && t.getStatus() == Tiquetes.estadoTiquete.DISPONIBLE ) {
							paquetes.add((Tiquetes.TiqueteMultiple) t);
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
					
					
					if(optCompra == 1 && !paquetes.isEmpty()){
						System.out.println("Paquetes disponibles");
						for(Tiquetes.TiqueteMultiple tm : paquetes) {
							System.out.println("ID paquete : " + tm.getIdT() + " | Entradas" + tm.getEntradas().size() + " | Precio" + " " + tm.getPrecioPaquete());
						}
						
						System.out.println("Ingrese el ID del paquete que quiera comprar: ");
						int idTM = Integer.parseInt(br.readLine().trim());
						
						Tiquetes.TiqueteMultiple paqueteSelection = null;
						for(Tiquetes.TiqueteMultiple tm : paquetes) {
							if(tm.getIdT() == idTM) {
								paqueteSelection = tm;
								break;
							}
						}
						
						if( paqueteSelection != null) {
							cliente.comprarTiqueteMultiple(eventoSelection, paqueteSelection, metodo, admin);
							System.out.println("Compra de paquete realizada exitosamente.\n");
						}
						else {
							System.out.println("paquete no encontrado.\n");
						}
					}
					else if(optCompra == 2) {
						System.out.println("\nCuantos tiquetes desea comprar en " + localidadSelection.getNombreL() + "?");
						int cantidad = Integer.parseInt(br.readLine().trim());
						
						
						cliente.comprarTiqueteSimple(eventoSelection, localidadSelection, cantidad, metodo, admin);
					}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.println("===== Tiquetes Comprados =====\n");
			HashSet<Tiquete> tiquetesCliente = cliente.getTiquetes();
			
			if(tiquetesCliente.isEmpty()) {
				System.out.println("No se han comprado tiquetes");
				break;
			}
			
			for(Tiquete t : tiquetesCliente) {
				if(t instanceof TiqueteSimple simple) {
					  System.out.println("ID: " + simple.getIdT());
			          System.out.println("Tipo: Simple");
			          System.out.println("Evento: " + simple.getEvento().getNombreE());
			          System.out.println("Localidad: " + simple.getLocalidad().getNombreL());
			          System.out.println("Estado: " + simple.getStatus());
			          System.out.println("Propietario: " + simple.getPropietario());
			          if(simple.isEnumerado()) {
			        	  System.out.println("Asiento: " + simple.getNumAsiento());
			          }
			          System.out.println("----------------------------------------");
				}
				else if(t instanceof TiqueteMultiple multiple) {
					System.out.println("ID: " + multiple.getIdT());
		            System.out.println("Tipo: Multiple");
		            System.out.println("Entradas: " + multiple.getEntradas().size());
		            System.out.println("Estado: " + multiple.getStatus());
		            System.out.println("Propietario: " + multiple.getPropietario());
		            System.out.println("Precio total del paquete: " + multiple.getPrecioPaquete());
		            System.out.println("Entradas incluidas:");
		            for(TiqueteSimple entrada : multiple.getEntradas()) {
		            	System.out.println("   - ID entrada: " + entrada.getIdT() +
                                " | Evento: " + entrada.getEvento().getNombreE() +
                                " | Localidad: " + entrada.getLocalidad().getNombreL() +
                                " | Estado: " + entrada.getStatus());
		            }
		            System.out.println("----------------------------------------");
				}
			}
			
			break;
		case 5:
			System.out.println("===== Transferencia de Tiquete =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Ingrese el ID del tiquete que desea transferir");
				int idT = Integer.parseInt(br.readLine().trim());
				
				System.out.println("Ingrese el login del nuevo propietario");
				String loginRsp = br.readLine().trim();
				
				System.out.println("Confirme su contraseña: ");
				String passwordConf = br.readLine().trim();
				
				boolean exito = cliente.transferirTiquete(idT, loginRsp, passwordConf);
				if(exito) {
					System.out.println("Transferencia Exitosa\n");	
				}
				else {
					System.out.println("No se pudo realiar la transferencia\n");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 6: 
			System.out.println("===== Solicitud de Devolucion =====");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Ingrese el ID del tiquete que desea devolver: ");
				int idT = Integer.parseInt(br.readLine().trim());
				
				System.out.println("Confirme su  login");
				String loginConf = br.readLine().trim();
				
				System.out.println("Ingrese el motivo de su devolucion: ");
				String motivo = br.readLine().trim();
				
				cliente.solicitarDevolucion(idT, loginConf, motivo);
				
				System.out.println("Solicitud de devolucion registrada correctamente.\n");
			}catch(Exception e) {
				System.out.println("Error al solicitar la devolucion: " + e.getMessage());
				e.printStackTrace();
			}
			break;
		case 7:
			System.out.println("===== Consultar Estado de Devoluciones =====\n");
		    cliente.consultarDevoluciones();
		    break;
			
			
			
		}
	}
	
	
	public static void menuOrganizador(int option, Organizador organizador, ArrayList<Evento> eventos, Administrador admin) {
		switch(option) {
		case 1: 
			System.out.println("===== Creacion de Evento =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Ingrese el nombre del evento: ");
				String nombreE = br.readLine().trim();
				
				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
				
				System.out.println("Ingrese la fecha del evento (dd/MM/yyyy): ");
				String fechaStr = br.readLine().trim();
				LocalDate fecha = LocalDate.parse(fechaStr, formatoFecha);
				
				System.out.println("Ingresse la hora del evento (HH:mm formato 24h): ");
				String horaStr = br.readLine().trim();
				LocalTime hora = LocalTime.parse(horaStr, formatoHora);
				
				System.out.println("Ingrese el tipo de evento: ");
				String tipo = br.readLine().trim();
				
				System.out.println("===== Venues Disponibles =====");
				List<Venue> venues = admin.getVenues();
				List<Venue> venuesAprobados = new ArrayList<>();
				for(Venue v : venues) {
					if(v.getAprobado() && v.consultarDisponibilidad(fecha)) {
						venuesAprobados.add(v);
						System.out.println((venuesAprobados.size()) + ". " + v.getNombreV() + "- Capacidad: " + v.getCapacidad());
					}
				}
				
				if(venuesAprobados.isEmpty()) {
					System.out.println("No hay venues aprobados o disponibles para esta fecha");
					return;
				}
				
				System.out.println("Ingrese el numero del venue: ");
				int optV = Integer.parseInt(br.readLine().trim());
				Venue venueSelect = venuesAprobados.get(optV - 1);
				
				int idE = new Random().nextInt(100000);
				Evento evento = organizador.crearEvento(idE, nombreE, fecha, hora, tipo, venueSelect);
				
				if(evento != null) {
					admin.registrarEvento(evento);
					venueSelect.addFechaOcupada(fecha);
					
					System.out.println("Evento creado exitosamente");
				}else {
					System.out.println("Error al crear el evento. No se ocupara el venue.");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("===== Asignacion de Localidades =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				List<Evento> eventosO = new ArrayList<>();
				
				for(Evento e : eventos) {
					if(e.getOrganizador().equals(organizador)) {
						eventosO.add(e);
					}
				}
				
				
				
				if(eventosO.isEmpty()) {
					System.out.println("No hay eventos creados\n");
					break;
				}
				
				for(int i = 0 ; i < eventosO.size() ; i++) {
					Evento e = eventosO.get(i);
					System.out.println((i + 1) + ". " + e.getNombreE() + "- Fecha: " + e.getFecha() + "- Venue: " + e.getVenue().toString());
				}
				
				System.out.println("Seleccione el evento al cual le quiere agregar la localida: ");
				int optE = Integer.parseInt(br.readLine().trim());
				
				if(optE < 1 || optE > eventosO.size()) {
					System.out.println("Opcion Invalida\n");
				}
				
				Evento eventoSelect = eventosO.get(optE - 1); 
				Venue venue = eventoSelect.getVenue();
				int capacidadV = venue.getCapacidad();
				int capacidadOcupada = eventoSelect.capacidadOcupada();
				

		        System.out.println("Capacidad total del venue: " + capacidadV);
		        System.out.println("Capacidad actualmente asignada: " + capacidadOcupada);
		        System.out.println("Capacidad restante disponible: " + (capacidadV - capacidadOcupada));
		        
		        
		        System.out.println("\nIngrese el nombre de la nueva localidad: ");
		        String nombreL = br.readLine().trim();
		        
		        System.out.println("Ingrese el precio base de los tiquetes: ");
		        double precioBL = Double.parseDouble(br.readLine().trim());
		        
		        System.out.println("Ingrese la capacidad de la localidad: ");
		        int capacidadL = Integer.parseInt(br.readLine().trim());
		        
		        if(capacidadL <= 0 || capacidadL + capacidadOcupada > capacidadV) {
		        	System.out.println("Error: la capacidad excede la capacidad del venue");
		        	break;
		        }
		        
		        
		        System.out.println("La localidad es numerada? (1 = Si, 2 = No)");
		        boolean numerada = Integer.parseInt(br.readLine().trim()) == 1;
		        
		        int idL = new Random().nextInt(10000);
		        Localidad nueva = new Localidad(idL, nombreL, precioBL, capacidadL);
		        nueva.setNumerada(numerada);
		        
		       for(int i = 0 ; i < capacidadL ; i++) {
		    	   int idT = new Random().nextInt(10000);
		    	   TiqueteSimple t = new TiqueteSimple(idT, true, "ADMIN", Tiquetes.estadoTiquete.DISPONIBLE, "SIMPLE", eventoSelect, nueva);
		    	   nueva.addTiquete(t);
		    	   if(numerada) {
		    		   
		    		   System.out.println("Creado tiquete ID " + t.getIdT() + " con asiento " + t.getNumAsiento());

		    	   }
		       }
		       
		       
		       System.out.println("\nDesea crear paquetes de tiquetes para esta localidad? (1 = Si, 2 = No)");
		       int crearPaquetes = Integer.parseInt(br.readLine().trim());
		       
		       if(crearPaquetes == 1) {
		    	   System.out.println("Que tipo de paquete desea crear?");
		    	   System.out.println("1. Paquete Normal");
		    	   System.out.println("2. Paquete Deluxe");
		    	   int tipoP = Integer.parseInt(br.readLine().trim());
		    	   
		    	   System.out.println("Cuantos tiquetes incluira cada paquete?");
		    	   int cantidadP = Integer.parseInt(br.readLine().trim());
		    	   
		    	   if(cantidadP <= 0 || cantidadP > capacidadL) {
		    		   System.out.println("Cantidad invalida");
		    	   }else {
		    		   System.out.println("¿Cuantos paquetes desea crear?");
		    		   int numP = Integer.parseInt(br.readLine().trim());
		    		   
		    		   for(int j = 0; j < numP ; j++) {
		    			   int idPack = new Random().nextInt(10000);
		    			   
		    			   TiqueteMultiple paquete;
		    			   if(tipoP == 2) {
		    				   paquete = new PaqueteDeluxe(idPack, "ADMIN");
		    			   }else {
		    				   paquete = new TiqueteMultiple(idPack, true, "ADMIN", Tiquetes.estadoTiquete.DISPONIBLE, "MULTIPLE");
		    			   }
		    			   
		    			   double precioTotal = 0;
		    			   int agregados = 0;
		    			   
		    			   for(Tiquete t : nueva.getTiquetes()) {
		    				   if(t instanceof TiqueteSimple && t.getStatus() == Tiquetes.estadoTiquete.DISPONIBLE && agregados < cantidadP) {
		    					   paquete.addEntrada((TiqueteSimple) t);
		    					   precioTotal += nueva.getPrecioBase();
		    					   agregados ++; 
		    				   }
		    			   }
		    			   
		    			   paquete.setPrecioPaquete(precioTotal);
		    			   nueva.addTiquete(paquete);
		    		   }
		    		   
		    		   System.out.println("Se crearon " + numP + " paquetes (" +(tipoP == 2 ? "Deluxe" : "Normales") + ").");
		    	   }
		       }
		        
		       
		       eventoSelect.addLocalidad(nueva);
		       
		       
		        System.out.println("\nLocalidad '" + nombreL + "' asignada correctamente al evento '" + eventoSelect.getNombreE() + "'.");
		        System.out.println("Capacidad total usada: " + eventoSelect.capacidadOcupada() + "/" + venue.getCapacidad());
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public static void menuAdministrador(int option, Administrador admin, ArrayList<Evento> eventos, HashSet<Usuario> usuarios) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			switch(option) {
			case 1:
				System.out.println("===== Registro de Venue =====\n");
				System.out.println("Ingrese el nombre del venue: ");
				String nombre = br.readLine().trim();
				
				System.out.println("Ingrese el tipo del venue: ");
				String tipo = br.readLine().trim();
				
				System.out.println("Ingrese la ubicacion del venue: ");
				String ubi = br.readLine().trim();
				
				System.out.println("Ingrese las restricciones del venue: ");
				String restricciones = br.readLine().trim();
				
				System.out.println("Ingrese la capacidad del venue: ");
				int capacidad = Integer.parseInt(br.readLine().trim());
				
				Venue v = new Venue(nombre, capacidad, tipo, ubi, restricciones);
				admin.registrarVenue(v); 
				break;
			case 2:
				System.out.println("===== Aprobacion de Venue =====\n");
				List<Venue> venues = admin.getVenues();
				
				if(venues.isEmpty()) {
					System.out.println("No hay venues registrados");
					break;
				}
				for(int i = 0; i < venues.size(); i++) {
					System.out.println((i + 1) + ". " + venues.get(i).getNombreV() + "- Aprobado: " + venues.get(i).getAprobado());
				}
				System.out.println("Seleccione el numero del venue a aprobar: ");
				int optAP = Integer.parseInt(br.readLine().trim());
				Venue venueSelect = venues.get((optAP -1));
				
				System.out.println("Ingrese la fecha que desea aprobar para este venue (dd/MM/yyy)");
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fechaAprob = LocalDate.parse(br.readLine().trim(), formato);
				
				admin.aprobarVenue(venueSelect.getNombreV(), fechaAprob);
				break;
			case 3:
				admin.mostrarDevolucionesPendientes();
			    break;
			case 4: 
				System.out.println("===== Aprobar/Rechazar Solicitud de Devolucion =====\n");
				System.out.println("Ingrese el ID del tiquete que desea revisar: ");
				int idT = Integer.parseInt(br.readLine().trim());
				boolean resultado = admin.aprobarDevolucion(idT, usuarios);
				
				if(resultado) {
					System.out.println("Devolucion aprobada exitosamente");
				}else {
					System.out.println("La devolucion fue rechazada o no existe solicitud");
				}
				break;
				
			
	
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}	
	
	
	public static void main(String []args) {
		
		Random random = new Random();
		HashSet<Usuario> usuarios = new HashSet<Usuario>();
		Administrador admin = new Administrador("ADMIN", "DPOO");
		usuarios.add(admin);
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		admin.setEventos(eventos);
		
		while(true) {
		System.out.println("====== Inicio De Sesion ======");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Ingrese su login:");
			String login = br.readLine().trim();
			System.out.println("Ingrese su contraseña:");
			String password = br.readLine().trim();
				
			// se pide el login y la contraseña 
				
			Usuario usuarioExistente = buscarUsuario(usuarios, login, password);
			
			// revisa si el usuario ya esta registrado, si lo esta inicia sesion de lo contrario se tiene que crear un nuevo usuario
			
			if(usuarioExistente != null) {   
				System.out.println("Inicio de sesion exitoso\n");
				
				if(usuarioExistente instanceof Cliente) {
					BufferedReader brC = new BufferedReader(new InputStreamReader(System.in));
					while(true) {
						System.out.println("===== Menu Cliente =====");
						System.out.println("1. Consultar Saldo");
						System.out.println("2. Recargar Saldo");
						System.out.println("3. Comprar Tiquete");
						System.out.println("4. Consultar Los Tiquetes Comprados");
						System.out.println("5. Transferir Tiquetes");
						System.out.println("6. Solicitar Devolucion");
						System.out.println("7. Consultar Estado Devoluciones");
						System.out.println("8. Salir");
						
						int optC = Integer.parseInt(brC.readLine().trim());
						menuCliente(optC, (Cliente) usuarioExistente  ,eventos, admin);
						
						if(optC == 8) {
							System.out.println("Saliendo de la aplicacion....");
							break;
						}
					}
				}
				else if(usuarioExistente instanceof Organizador) {
					//TODO menu organizador
					BufferedReader brO = new BufferedReader(new InputStreamReader(System.in));
					while(true) {
						System.out.println("===== Menu Organizador =====");
						System.out.println("1. Crear Evento");
						System.out.println("2. Asignar localidad a un evento");
						System.out.println("3. Aplicar Oferta A Una Localidad");
						System.out.println("4. Solicitar Cancelacion");
						System.out.println("5. Ver Ingresos");
						System.out.println("6. Consultar Porcentaje Venta Total");
						System.out.println("7. Consultar Porcentaje Venta Por Localidad");
						System.out.println("8. Consultar Porcentaje Venta Por Evento");
						System.out.println("9. Guardar localidades de un evento");
						System.out.println("10. Sugerir Venue");
						System.out.println("11. Salir");
						
						int optO = Integer.parseInt(brO.readLine().trim());
						menuOrganizador(optO, (Organizador) usuarioExistente, eventos, admin);
						
						if(optO == 11) {
							System.out.println("Saliendo de la aplicacion....");
							break;
						}
					}
				}
				else if(usuarioExistente instanceof Administrador) {
					//TODO menu Admin
					BufferedReader brA = new BufferedReader(new InputStreamReader(System.in));
					while(true) {
						System.out.println("===== Menu Administrador =====");
						System.out.println("1. Registrar Venue");
						System.out.println("2. Aprobar Venue");
						System.out.println("3. Ver Solicitudes de Devolucion Pendientes");
						System.out.println("4. Aprobar/Rechazar Solicitud de Devolucion");
						System.out.println("5. Salir");
						
						int optA = Integer.parseInt(brA.readLine().trim());
						menuAdministrador(optA, admin, eventos, usuarios);
						
						if(optA == 5) {
							System.out.println("Saliendo de la aplicacion....");
							break;
						}
					}
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
						Cliente cliente = new Cliente(newLogin, newPassword);
						usuarios.add(cliente);
						System.out.println("Cliente creado exitosamente\n");
						
						// se inicialisa el menu de cliente
						BufferedReader brC = new BufferedReader(new InputStreamReader(System.in));
						while(true) {
							System.out.println("===== Menu Cliente =====");
							System.out.println("1. Consultar Saldo");
							System.out.println("2. Recargar Saldo");
							System.out.println("3. Comprar Tiquetes");
							System.out.println("4. Consultar Los Tiquetes Comprados");
							System.out.println("5. Transferir Tiquete");
							System.out.println("6. Solicitar Devolucion");
							System.out.println("7. Consultar Estado Devoluciones");
							System.out.println("8. Salir");
							
							int optC = Integer.parseInt(brC.readLine().trim());
							menuCliente(optC, cliente  ,eventos, admin);
							
							if(optC == 8) {
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
						
						Organizador organizador = new Organizador(newLogin, newPassword, idO);
						usuarios.add(organizador);;
						System.out.println("Organizador creado exitosamente\n");
						
						BufferedReader brO = new BufferedReader(new InputStreamReader(System.in));
						while(true) {
							System.out.println("===== Menu Organizador =====");
							System.out.println("1. Crear Evento");
							System.out.println("2. Asignar localidad a un evento");
							System.out.println("3. Aplicar Oferta A Una Localidad");
							System.out.println("4. Solicitar Cancelacion");
							System.out.println("5. Ver Ingresos");
							System.out.println("6. Consultar Porcentaje Venta Total");
							System.out.println("7. Consultar Porcentaje Venta Por Localidad");
							System.out.println("8. Consultar Porcentaje Venta Por Evento");
							System.out.println("9. Guardar localidades de un evento");
							System.out.println("10. Sugerir Venue");
							System.out.println("11. Salir");
							
							int optO = Integer.parseInt(brO.readLine().trim());
							menuOrganizador(optO, organizador, eventos, admin);
							
							
							if(optO == 11) {
								System.out.println("Saliendo del menu....");
								break;
							}
						}
					}
					
				}	
			}
			catch(Exception e ) {
				e.printStackTrace();
			}
		
		}
	}
}
