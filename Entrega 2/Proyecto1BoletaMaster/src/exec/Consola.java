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
	
	private static Administrador buscarAdministrador(HashSet<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            if (u instanceof Administrador) return (Administrador) u;
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
				
				System.out.println("Confirme su login");
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
		case 3:
			System.out.println("===== Aplicar Oferta =====");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				ArrayList<Evento> eventosO = new ArrayList<>();
				
				for(Evento e : eventos) {
					if(e.getOrganizador().equals(organizador)) {
						eventosO.add(e);
					}
				}
				
				if(eventos.isEmpty()) {
					System.out.println("No hay eventos creados");
					break;
				}
				
				
				for(int i = 0 ; i < eventosO.size() ; i++) {
					Evento e = eventosO.get(i);
					System.out.println((i + 1) + ". " + e.getNombreE() + "- Fecha: " + e.getFecha() + "- Venue: " + e.getVenue().toString());
				}
				
				System.out.println("Seleccione el evento al cual le quiere agreager una oferta a una localidad: ");
				int optE = Integer.parseInt(br.readLine().trim());
				
				if(optE < 1 || optE > eventosO.size()) {
					System.out.println("Opcion Invalida\n");
				}
				
				Evento eventoSelect = eventosO.get(optE - 1);
				List<Localidad> localidades = eventoSelect.getLocalidades();
				
				for(int j = 0 ; j < localidades.size() ; j++) {
					Localidad l = localidades.get(j);
					System.out.println((j + 1) + ". " + l.getNombreL() + "- Precio: " + l.getPrecioBase() + "- Capacidad: " + l.getCapacidadL() );
				}
				
				System.out.println("Seleccione el numero de la localidad a la que desea aplicar la oferta");
				int optL = Integer.parseInt(br.readLine().trim());
				Localidad localidadSelect = localidades.get(optL - 1);
				
				System.out.println("Ingrese el porcentaje de descuento (ej: 20 para 20%): ");
				double porcentaje = Double.parseDouble(br.readLine().trim());
				
				
				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				System.out.println("Ingrese la fecha de inicio (dd/MM/yyyy): ");
				LocalDate inicio = LocalDate.parse(br.readLine().trim(), formatoFecha);
				System.out.println("Ingrese la fecha de fin (dd/MM/yyyy): ");
				LocalDate fin = LocalDate.parse(br.readLine().trim(), formatoFecha);
				
				organizador.aplicarOferta(localidadSelect.getIdL(), porcentaje, inicio, fin);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.println("===== Solicitud de Cancelacion de Evento =====");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				ArrayList<Evento> eventosO = new ArrayList<>();
				
				for(Evento e : eventos) {
					if(e.getOrganizador().equals(organizador)) {
						eventosO.add(e);
					}
				}
				
				if(eventos.isEmpty()) {
					System.out.println("No hay eventos creados");
					break;
				}
				
				
				for(int i = 0 ; i < eventosO.size() ; i++) {
					Evento e = eventosO.get(i);
					System.out.println((i + 1) + ". " + e.getNombreE() + "- Fecha: " + e.getFecha() + "- Venue: " + e.getVenue().toString());
				}
				
				System.out.println("Seleccione el evento al cual le quiere agreager una oferta a una localidad: ");
				int optE = Integer.parseInt(br.readLine().trim());
				
				if(optE < 1 || optE > eventosO.size()) {
					System.out.println("Opcion Invalida\n");
				}
				
				Evento eventoSelect = eventosO.get(optE - 1);
				
				System.out.println("Ingrese el motivo de la cancelacion del evento");
				String motivo = br.readLine().trim();
				
				organizador.solicitarCancelacion(eventoSelect.getIdE(), motivo);
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 5:
			System.out.println("===== Consultar Estado de Cancelaciones =====\n");
			
			ArrayList<Evento> eventosOrganizador = new ArrayList<>();
			for(Evento e : eventos) {
				if(e.getOrganizador().equals(organizador)) {
					eventosOrganizador.add(e);
				}
			}
			
			if(eventosOrganizador.isEmpty()) {
				System.out.println("No tienes eventos registrados.\n");
				break;
			}
			
			for(Evento e : eventosOrganizador) {
				String estado;
				if(e.getCancelacionAprobada()) {
					estado = "Cancelacion aprobada";
				}else if(e.getCancelacionSolicitada())	{
					estado = "En espera de aprobacion";
				}else {
					estado = "Activo";
				}
				
				System.out.println("- " + e.getNombreE() + " | Estado: " + estado);
			}
			System.out.println();
			break;
		case 6:
			System.out.println("===== Consulta de Ingresos =====\n");
			
			if(organizador.getEventos().isEmpty()) {
				System.out.println("No hay eventos creados aun");
				break;
			}
			
			System.out.println("Eventos del organizador " + organizador.getLogin() + ":");
			for(Evento e : organizador.getEventos()){
				System.out.println("- " + e.getNombreE() + " | Estado: " + e.getEstado());
			}
			
			double ingresos = organizador.verIngresos();
			
			System.out.println("\nIngresos totales generados: $" + ingresos);
		    System.out.println("Recuerda: no se incluyen eventos cancelados ni cortesias.\n");
		    break;
		case 7:
			System.out.println("===== Consulta Porcentaje de Venta Total =====\n");
			if (organizador.getEventos().isEmpty()) {
				System.out.println("No hay eventos creados aun.\n");
				break;
			}

			double total = organizador.porcentajeVentaTotal();
			System.out.println("El porcentaje total de venta de todos tus eventos activos es: "
					+ String.format("%.2f", total) + "%\n");
			break;

		case 8:
			System.out.println("===== Consulta Porcentaje de Venta por Localidad =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				List<Localidad> todasLocalidades = new ArrayList<>();
				for (Evento e : organizador.getEventos()) {
					if (e.getEstado() != estadoEvento.CANCELADO) {
						todasLocalidades.addAll(e.getLocalidades());
					}
				}

				if (todasLocalidades.isEmpty()) {
					System.out.println("No hay localidades activas.\n");
					break;
				}

				for (int i = 0; i < todasLocalidades.size(); i++) {
					Localidad l = todasLocalidades.get(i);
					System.out.println((i + 1) + ". " + l.getNombreL() + " (Capacidad: " + l.getCapacidadL() + ")");
				}

				System.out.println("Seleccione la localidad para consultar: ");
				int optL = Integer.parseInt(br.readLine().trim());
				if (optL < 1 || optL > todasLocalidades.size()) {
					System.out.println("Opcion invalida.\n");
					break;
				}

				Localidad localidadSelect = todasLocalidades.get(optL - 1);
				double porLocalidad = organizador.porcentajeVentaPorLocalidad(localidadSelect.getIdL());
				System.out.println("La localidad '" + localidadSelect.getNombreL() + "' tiene un porcentaje de venta de "
						+ String.format("%.2f", porLocalidad) + "%\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 9:
			System.out.println("===== Consulta Porcentaje de Venta por Evento =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				List<Evento> eventosO = organizador.getEventos();
				if (eventosO.isEmpty()) {
					System.out.println("No hay eventos registrados.\n");
					break;
				}

				for (int i = 0; i < eventosO.size(); i++) {
					Evento e = eventosO.get(i);
					System.out.println((i + 1) + ". " + e.getNombreE() + " - Estado: " + e.getEstado());
				}

				System.out.println("Seleccione el evento para consultar: ");
				int optE = Integer.parseInt(br.readLine().trim());
				if (optE < 1 || optE > eventosO.size()) {
					System.out.println("Opcion invalida.\n");
					break;
				}

				Evento eventoSelect = eventosO.get(optE - 1);
				double porEvento = organizador.porcentajeVentaPorEvento(eventoSelect.getIdE());
				System.out.println("El evento '" + eventoSelect.getNombreE() + "' tiene un porcentaje de venta de "
						+ String.format("%.2f", porEvento) + "%\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 10:
			
			
			break;
		case 11:
			System.out.println("===== Sugerir Venue =====\n");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.println("Ingrese el nombre del venue: ");
				String nombreV = br.readLine().trim();
				
				System.out.println("Ingrese el tipo de venue: ");
				String tipoV = br.readLine().trim();
				
				System.out.println("Ingrese la ubicacion del venue: ");
				String ubiV = br.readLine().trim();
				
				System.out.println("Ingrese las restricciones del venue: ");
				String restricciones = br.readLine().trim();
				
				System.out.println("Ingrese la capacidad del venue: ");
				int capacidad = Integer.parseInt(br.readLine().trim());
				
				Venue sugerido = new Venue(nombreV, capacidad, tipoV, ubiV, restricciones);
				sugerido.setAprobado(false);
				
				admin.registrarVenue(sugerido);
				
				System.out.println("\nSugerencia enviada correctamente.");
		        System.out.println("El venue '" + nombreV + "' quedara pendiente de aprobacion por el administrador.\n");
		        
			}catch(Exception e) {
				System.out.println("Error al sugerir el venue: " + e.getMessage());
			}
			break;
		case 12: 
			 System.out.println("===== MODO CLIENTE (ORGANIZADOR) =====");
			    try {
			        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			        int optCliente;
			        do {
			            System.out.println("===== MENU CLIENTE =====");
			            System.out.println("1. Consultar Saldo");
			            System.out.println("2. Recargar Saldo");
			            System.out.println("3. Comprar Tiquete");
			            System.out.println("4. Consultar Tiquetes Comprados");
			            System.out.println("5. Transferir Tiquete");
			            System.out.println("6. Solicitar Devolucion");
			            System.out.println("7. Consultar Estado Devoluciones");
			            System.out.println("8. Salir del modo Cliente");
			            System.out.print("Seleccione una opcion: ");
			            optCliente = Integer.parseInt(br.readLine().trim());

			            if (optCliente >= 1 && optCliente <= 7) {           
			                menuCliente(optCliente, (Cliente) organizador, eventos, admin);
			            }
			        } while (optCliente != 8);

			        System.out.println("Saliendo del modo Cliente...");
			    } catch (Exception e) {
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
				System.out.println("===== Fijar Cargos Adicionales =====\n");
				System.out.println("1. Fijar cargo de servicio (%)");
				System.out.println("2. Fijar cargo de impresion ($)");
				int tipoCargo = Integer.parseInt(br.readLine().trim());
				
				if(tipoCargo == 1) {
					System.out.println("Ingrese el nuevo porcentaje de cargo de servicio (%): ");
					double nuevoServicio = Double.parseDouble(br.readLine().trim());
					admin.fijarCargoServicio(nuevoServicio);
					System.out.println("Cargo de servicio actualizado a " + nuevoServicio + "%");
				} else if(tipoCargo == 2) {
					System.out.println("Ingreso el nuevo cargo fijo de impresion: ");
					double nuevoImpresion = Double.parseDouble(br.readLine().trim());
					admin.fijarCargoImpresion(nuevoImpresion);
					System.out.println("Cargo fijo de impresion actualizado a $" + nuevoImpresion);
				}else {
					System.out.println("Opcion no valida");
				}
				break;
			case 4:
				admin.mostrarDevolucionesPendientes();
			    break;
			case 5: 
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
			case 6:
				System.out.println("===== Aprobar/Rechazar Solicitud de Cancelacion de Evento =====");
				
				ArrayList<Evento> pendientes = new ArrayList<>();
				for(Evento e : eventos) {
					if(e.getCancelacionSolicitada()) {
						pendientes.add(e);
					}
				}
				
				if(pendientes.isEmpty()) {
					System.out.println("No hay solicitudes de cancelacion pendientes.\n");
					break;
				}
				
				for(int i = 0; i < pendientes.size() ; i++) {
					Evento e = pendientes.get(i);
					System.out.println((i +1) + ". " + e.getNombreE() +"- Motivo: " + e.getMotivoCancelacion() + " - Organizador: " + e.getOrganizador().getLogin());
				}
				
				System.out.println("\nIngrese el evento cuya cancelacion quiere aprobar:");
				int opt = Integer.parseInt(br.readLine().trim());
				
				if(opt < 1 || opt > pendientes.size()) {
					System.out.println("Opcion invalida.\n");
					break;
				}
				
				Evento eventoAprobado = pendientes.get(opt -1);
				
				admin.aprobarCancelacion(eventoAprobado.getIdE(), usuarios);
	
				break;
			case 7:
				System.out.println("===== Consultas Financieras =====\n");
				System.out.println("Seleccione el tipo de consulta:");
			    System.out.println("1. Ganancias por rango de fechas");
			    System.out.println("2. Ganancias por evento");
			    System.out.println("3. Ganancias por organizador");

			    int tipoConsulta = Integer.parseInt(br.readLine().trim());
			    EstadosFinancieros ef = admin.getEstadosFinancieros();
			    

			    switch (tipoConsulta) {
		        case 1:
		            System.out.println("Ingrese la fecha inicial (YYYY-MM-DD): ");
		            LocalDate fechaMin = LocalDate.parse(br.readLine().trim());
		            System.out.println("Ingrese la fecha final (YYYY-MM-DD): ");
		            LocalDate fechaMax = LocalDate.parse(br.readLine().trim());
		            
		            double totalFechas = ef.totalGananciasPorFecha(fechaMin, fechaMax);
		            System.out.println("Ganancias de la tiquetera entre " + fechaMin + " y " + fechaMax + ": $" + totalFechas);
		            break;

		        case 2:
		            System.out.println("\nEventos disponibles en la plataforma:");
		            if (eventos.isEmpty()) {
		                System.out.println("No hay eventos registrados.\n");
		                break;
		            }
		            for (Evento e : eventos) {
		                System.out.println("ID: " + e.getIdE() + " | Nombre: " + e.getNombreE() + " | Organizador: " + e.getOrganizador().getLogin() + " | Estado: " + e.getEstado());
		            }

		            System.out.println("\nIngrese el ID del evento para consultar ganancias: ");
		            int idE = Integer.parseInt(br.readLine().trim());
		            double totalEvento = ef.totalGananciasPorEvento(idE);
		            System.out.println("\nGanancias totales del evento con ID " + idE + ": $" + totalEvento);
		            break;

		        case 3:
		            System.out.println("\nOrganizadores registrados:");
		            boolean hayOrg = false;
		            for (Usuario u : usuarios) {
		                if (u instanceof Organizador o) {
		                    System.out.println("ID: " + o.getIdO() + " | Login: " + o.getLogin());
		                    hayOrg = true;
		                }
		            }
		            if (!hayOrg) {
		                System.out.println("No hay organizadores registrados.\n");
		                break;
		            }

		            System.out.println("\nIngrese el ID del organizador para consultar ganancias: ");
		            int idO = Integer.parseInt(br.readLine().trim());
		            double totalOrg = ef.totalGananciasPorOrganizador(idO);
		            System.out.println("\nGanancias totales asociadas al organizador con ID " + idO + ": $" + totalOrg);
		            break;

		        default:
		            System.out.println("Opcion no valida.\n");
		            break;
		    }
		    break;
			case 8:
				System.out.println("Guardando toda la informacion del sistema...");

			    try {
			        PerUsuario.guardarUsuarios(new ArrayList<>(usuarios));

			  
			        List<Tiquete> todosTiquetes = new ArrayList<>();
			        for (Usuario u : usuarios) {
			            if (u instanceof Cliente c) {
			                todosTiquetes.addAll(c.getTiquetes());
			            }
			        }
			        PerTiquete.guardarTiquetes(todosTiquetes);

			  
			        PerPago.guardarPagos(admin.getEstadosFinancieros().getTransacciones());

			        System.out.println("Todos los datos fueron guardados correctamente.\n");

			    } catch (Exception e) {
			        System.out.println(" Error al guardar la informacion: " + e.getMessage());
			        e.printStackTrace();
			    }
			    break;
				
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	
	}	
	
	
	public static void main(String []args) {
		 try {
	            HashSet<Usuario> usuarios = new HashSet<>();
	            ArrayList<Evento> eventos = new ArrayList<>();

	            try {
	                List<Usuario> usuariosCargados = PerUsuario.cargarUsuario();
	                usuarios.addAll(usuariosCargados);
	                System.out.println("Usuarios cargados desde persistencia (" + usuarios.size() + ")");
	            } catch (Exception e) {
	                System.out.println("No se pudieron cargar usuarios, se inicia lista vacia.");
	            }

	            try {
	                List<Tiquete> tiquetes = PerTiquete.cargarTiquetes();
	                System.out.println("Tiquetes cargados: " + tiquetes.size());
	            } catch (Exception e) {
	                System.out.println("No se pudieron cargar tiquetes previos.");
	            }

	            Administrador admin = buscarAdministrador(usuarios);
	            if (admin == null) {
	                admin = new Administrador("ADMIN", "DPOO");
	                usuarios.add(admin);
	            }

	            admin.setEventos(eventos);
	            Random random = new Random();

	         
	            while (true) {
	                System.out.println("====== INICIO DE SESION ======");
	                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	                System.out.print("Ingrese su login: ");
	                String login = br.readLine().trim();
	                System.out.print("Ingrese su contraseña: ");
	                String password = br.readLine().trim();

	                Usuario usuarioExistente = buscarUsuario(usuarios, login, password);

	                if (usuarioExistente != null) {
	                    System.out.println("Inicio de sesion exitoso.\n");

	                     if (usuarioExistente instanceof Organizador) {
	                        Organizador organizador = (Organizador) usuarioExistente;
	                        while (true) {
	                            System.out.println("===== MENU ORGANIZADOR =====");
	                            System.out.println("1. Crear Evento");
	                            System.out.println("2. Asignar Localidad");
	                            System.out.println("3. Aplicar Oferta");
	                            System.out.println("4. Solicitar Cancelacion");
	                            System.out.println("5. Consultar Estado Cancelaciones");
	                            System.out.println("6. Ver Ingresos");
	                            System.out.println("7. Consultar Porcentaje Venta Total");
	                            System.out.println("8. Consultar Porcentaje Venta por Localidad");
	                            System.out.println("9. Consultar Porcentaje Venta por Evento");
	                            System.out.println("10. Guardar mis datos");
	                            System.out.println("11. Sugerir Venue");
	                            System.out.println("12. Acceder al menu de Cliente");
	                            System.out.println("13. Salir");

	                            int optO = Integer.parseInt(br.readLine().trim());
	                            menuOrganizador(optO, organizador, eventos, admin);

	                            if (optO == 10) {
	                                PerUsuario.guardarUsuarios(new ArrayList<>(usuarios));
	                                System.out.println("Datos del organizador guardados correctamente.");
	                            }

	                            if (optO == 13) {
	                                System.out.println("Saliendo del menu Organizador...");
	                                break;
	                            }
	                        }
	                    }
	                    
	                     else if (usuarioExistente instanceof Cliente) {
	                        Cliente cliente = (Cliente) usuarioExistente;
	                        while (true) {
	                            System.out.println("===== MENU CLIENTE =====");
	                            System.out.println("1. Consultar Saldo");
	                            System.out.println("2. Recargar Saldo");
	                            System.out.println("3. Comprar Tiquete");
	                            System.out.println("4. Consultar Tiquetes Comprados");
	                            System.out.println("5. Transferir Tiquete");
	                            System.out.println("6. Solicitar Devolucion");
	                            System.out.println("7. Consultar Estado Devoluciones");
	                            System.out.println("8. Guardar mis datos");
	                            System.out.println("9. Salir");

	                            int optC = Integer.parseInt(br.readLine().trim());
	                            menuCliente(optC, cliente, eventos, admin);

	                            if (optC == 8) {
	                                PerUsuario.guardarUsuarios(new ArrayList<>(usuarios));
	                                System.out.println("Datos del cliente guardados correctamente.");
	                            }

	                            if (optC == 9) {
	                                System.out.println("Saliendo del menu Cliente...");
	                                break;
	                            }
	                        }
	                    }


	             
	                    else if (usuarioExistente instanceof Administrador) {
	                        while (true) {
	                            System.out.println("===== MENU ADMINISTRADOR =====");
	                            System.out.println("1. Registrar Venue");
	                            System.out.println("2. Aprobar Venue");
	                            System.out.println("3. Fijar Cargos Adicionales");
	                            System.out.println("4. Ver Solicitudes de Devolucion Pendientes");
	                            System.out.println("5. Aprobar/Rechazar Solicitudes de Devolucion");
	                            System.out.println("6. Aprobar/Rechazar Cancelaciones de Eventos");
	                            System.out.println("7. Consultar Estados Financieros");
	                            System.out.println("8. Guardar todos los datos");
	                            System.out.println("9. Salir");

	                            int optA = Integer.parseInt(br.readLine().trim());
	                            menuAdministrador(optA, admin, eventos, usuarios);


	                            if (optA == 9) {
	                                System.out.println("Saliendo del menu Administrador...");
	                                break;
	                            }
	                        }
	                    }

	                } else {
	                    System.out.println("\nNo existe un usuario con esas credenciales.");
	                    System.out.println("¿Desea crear una nueva cuenta?");
	                    System.out.println("1. Si");
	                    System.out.println("2. No");
	                    int crear = Integer.parseInt(br.readLine().trim());
	                    if (crear == 2) continue;

	                    System.out.print("Ingrese un nuevo login: ");
	                    String newLogin = br.readLine().trim();
	                    while (loginYaExiste(usuarios, newLogin)) {
	                        System.out.print("Ese login ya esta en uso. Ingrese otro: ");
	                        newLogin = br.readLine().trim();
	                    }

	                    System.out.print("Ingrese la contraseña: ");
	                    String newPassword = br.readLine().trim();
	                    System.out.println("Seleccione tipo de usuario:");
	                    System.out.println("1. Cliente");
	                    System.out.println("2. Organizador");

	                    int tipo = Integer.parseInt(br.readLine().trim());
	                    if (tipo == 1) {
	                        Cliente nuevoCliente = new Cliente(newLogin, newPassword);
	                        usuarios.add(nuevoCliente);
	                        System.out.println("Cliente creado exitosamente.");
	                    } else if (tipo == 2) {
	                        int idO = random.nextInt(100000);
	                        while (buscarOrganizadorPorId(usuarios, idO) != null) {
	                            idO = random.nextInt(100000);
	                        }
	                        Organizador nuevoOrg = new Organizador(newLogin, newPassword, idO);
	                        usuarios.add(nuevoOrg);
	                        System.out.println("Organizador creado exitosamente.");
	                    }

	                   
	                    PerUsuario.guardarUsuarios(new ArrayList<>(usuarios));
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
