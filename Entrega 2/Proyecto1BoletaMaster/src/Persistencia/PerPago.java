package Persistencia;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;
import Tiquetes.TiqueteMultiple;
import modelo.estadoEvento;
import pagos.Pago;
import pagos.metodoPago;
import modelo.Venue;
import modelo.Evento;
import modelo.Localidad;
import modelo.Organizador;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class PerPago {

	private static final String ARCHIVO = "pagos.json";
	
	public static void guardarPagos(List<Pago> pagos) throws IOException{
		
		JSONArray array = new JSONArray();
		
		for(Pago p : pagos) {
			JSONObject obj = new JSONObject();
			obj.put("idPago", p.getIdPago());
			obj.put("fecha", p.getFecha().toString());
			obj.put("metodo", p.getMetodo().name());
			obj.put("cargoServicio", p.getCargoServicio());
			obj.put("cargoImpresion", p.getCargoImpresion());
			
			JSONArray tiquetesArray = new JSONArray();
			for(Tiquete t : p.getTiquetesComprados()) {
				JSONObject tObj = new JSONObject();
				tObj.put("idTiquete", t.getIdT());
				tObj.put("propietario", t.getPropietario());
				tObj.put("estado", t.getStatus().name());
				tObj.put("transferibilidad", t.esTransferible());
				if(t instanceof TiqueteSimple) {
					TiqueteSimple simple = (TiqueteSimple) t;
					
					JSONObject eObj = new JSONObject();
					eObj.put("idE", simple.getEvento().getIdE());
					eObj.put("nombreE", simple.getEvento().getNombreE());
					eObj.put("fecha", simple.getEvento().getFecha().toString());
					eObj.put("hora", simple.getEvento().getHora().toString());
					eObj.put("tipoE", simple.getEvento().getTipoE());
					eObj.put("estado", simple.getEvento().getEstado().name());
					
					JSONObject vObj = new JSONObject();
					vObj.put("nombreV", simple.getEvento().getVenue().getNombreV());
					vObj.put("capacidad", simple.getEvento().getVenue().getCapacidad());
					vObj.put("tipoV", simple.getEvento().getVenue().getTipoV());
					vObj.put("ubicacion", simple.getEvento().getVenue().getUbicacion());
					vObj.put("restricciones", simple.getEvento().getVenue().getRestricciones());
					eObj.put("venue", vObj);
					
					JSONObject orObj = new JSONObject();
					orObj.put("login", simple.getEvento().getOrganizador().getLogin());
					orObj.put("password", simple.getEvento().getOrganizador().getPassword());
					orObj.put("idO", simple.getEvento().getOrganizador().getIdO());
					eObj.put("organizador", orObj);
					
					tObj.put("evento", eObj);
					
					JSONObject lObj = new JSONObject();
					lObj.put("idL", simple.getLocalidad().getIdL());
					lObj.put("nombreL", simple.getLocalidad().getNombreL());
					lObj.put("precioBase", simple.getLocalidad().getPrecioBase());
					lObj.put("capacidadL", simple.getLocalidad().getCapacidadL());
					
					tObj.put("localidad", lObj);
					
					
					tObj.put("tipo", "simple");
					if(simple.isEnumerado()) {
						tObj.put("numAsiento", simple.getNumAsiento());
					}
				}
				else if(t instanceof TiqueteMultiple) {
					TiqueteMultiple multiple = (TiqueteMultiple) t;
					tObj.put("tipo", "multiple");
					JSONArray entradas = new JSONArray();
					for(TiqueteSimple s : multiple.getEntradas()) {
						JSONObject sObj = new JSONObject();
						sObj.put("idTiquete", s.getIdT());
						sObj.put("propietario", s.getPropietario());
						sObj.put("estado", s.getStatus().name());
						sObj.put("transferibilidad", t.esTransferible());
						
						JSONObject esObj = new JSONObject();
						esObj.put("idE", s.getEvento().getIdE());
						esObj.put("nombreE", s.getEvento().getNombreE());
						esObj.put("fecha", s.getEvento().getFecha().toString());
						esObj.put("hora", s.getEvento().getHora().toString());
						esObj.put("tipoE", s.getEvento().getTipoE());
						esObj.put("estado", s.getEvento().getEstado().name());
			
						
						
						JSONObject vsObj = new JSONObject();
						vsObj.put("nombreV", s.getEvento().getVenue().getNombreV());
						vsObj.put("capacidad", s.getEvento().getVenue().getCapacidad());
						vsObj.put("tipoV", s.getEvento().getVenue().getTipoV());
						vsObj.put("ubicacion", s.getEvento().getVenue().getUbicacion());
						vsObj.put("restricciones", s.getEvento().getVenue().getRestricciones());
						esObj.put("venue", vsObj);
						
						JSONObject orsObj = new JSONObject();
						orsObj.put("login", s.getEvento().getOrganizador().getLogin());
						orsObj.put("password", s.getEvento().getOrganizador().getPassword());
						orsObj.put("idO", s.getEvento().getOrganizador().getIdO());
						esObj.put("organizador", orsObj);
						
						sObj.put("evento", esObj);
						
						
						JSONObject lsObj = new JSONObject();
						lsObj.put("idL", s.getLocalidad().getIdL());
						lsObj.put("nombreL", s.getLocalidad().getNombreL());
						lsObj.put("precioBase", s.getLocalidad().getPrecioBase());
						lsObj.put("capacidadL", s.getLocalidad().getCapacidadL());
						
						sObj.put("localidad", lsObj);
						
						
						sObj.put("tipo", "simple");
						if(s.isEnumerado()) {
							tObj.put("numAsiento", s.getNumAsiento());
						}
						entradas.put(sObj);
					}
					tObj.put("entradas", entradas);
				}
				tiquetesArray.put(tObj);
			}
		
			array.put(obj);
		}
		
		Persistencia.guardarJSONArray(array, ARCHIVO);
	}
	
	
	
	public static List<Pago> cargarPagos() throws IOException{
		
		List<Pago> lista = new ArrayList<Pago>();
		JSONArray array = Persistencia.cargarJSONArray(ARCHIVO);
		
		for(int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			
			int idPago = obj.getInt("idPago");
			LocalDate fecha = LocalDate.parse(obj.getString("fecha"));
			metodoPago metodo = metodoPago.valueOf(obj.getString("metodo"));
			double cargoServicio = obj.getDouble("cargoServicio");
			double cargoImpresion = obj.getDouble("cargoImpresion");
			
			
			ArrayList<Tiquete> tiquetes = new ArrayList<Tiquete>();
			JSONArray tiquetesArray = obj.getJSONArray("tiquetes");
			for(int j = 0; j < tiquetesArray.length();j++) {
				JSONObject tObj = tiquetesArray.getJSONObject(j);
				String tipo = tObj.getString("tipo");
				
				JSONObject eObj = tObj.getJSONObject("evento");
				int idE = eObj.getInt("idE");
				String nombreE = eObj.getString("nombreE");
				LocalDate fechaE = LocalDate.parse(eObj.getString("fecha"));
				LocalTime horaE = LocalTime.parse(eObj.getString("hora"));
				String tipoE = eObj.getString("tipoE");
				estadoEvento estadoE = estadoEvento.valueOf(eObj.getString("estado")); 
				
				
				JSONObject vObj = eObj.getJSONObject("venue");
				String nombreV = vObj.getString("nombreV");
				int capacidadV = vObj.getInt("capacidad");
				String tipoV = vObj.getString("tipoV");
				String ubicacionV = vObj.getString("ubicacion");
				String restricciones = vObj.getString("restricciones");
				
				
				JSONObject orObj = eObj.getJSONObject("organizador");
				String login = orObj.getString("login");
				String password = orObj.getString("password");
				int idO = orObj.getInt("idO");
				
				
				Venue venue = new Venue(nombreV, capacidadV, tipoV, ubicacionV, restricciones);
				Organizador organizador = new Organizador(login,password,idO);
				Evento evento = new Evento(idE,nombreE,fechaE,horaE,tipoE,estadoE,venue,organizador);
				
				 
				JSONObject lObj = tObj.getJSONObject("localidad");
				int idL = lObj.getInt("idL");
				String nombreL = lObj.getString("nombreL");
				double precioBase = lObj.getDouble("precioBase");
				int capacidadL = lObj.getInt("capacidadL");
				
				Localidad localidad = new Localidad(idL, nombreL, precioBase, capacidadL);
				
				if(tipo.equals("simple")) {
					int idTiquete = tObj.getInt("idTiquete");
					String propietario = tObj.getString("propietario");
					estadoTiquete tEstado = estadoTiquete.valueOf(tObj.getString("estado"));
					boolean transferibilidad = tObj.getBoolean("transferibilidad");
					
					TiqueteSimple simple = new TiqueteSimple(idTiquete, transferibilidad, propietario, tEstado, "simple", evento, localidad);
					
					if(tObj.has("numAsiento")) {
						simple.setNumAsiento(tObj.getInt("numAsiento"));
					}
					
					tiquetes.add(simple);
					
				}
				
				else if(tipo.equals("multiple")) {
					int idTM = tObj.getInt("idTiquete");
					String propietarioM = tObj.getString("propietario");
					estadoTiquete tEstadoM = estadoTiquete.valueOf(tObj.getString("estado"));
					boolean transferibilidadM = tObj.getBoolean("transferibilidad");
					
					TiqueteMultiple multiple = new TiqueteMultiple(idTM, transferibilidadM, propietarioM, tEstadoM, "mupltiple");
					
					JSONArray entradas = tObj.getJSONArray("entradas");
					
					for(int k = 0; k < entradas.length(); k++) {
						
						JSONObject sObj = entradas.getJSONObject(k);
						
						JSONObject eSObj = sObj.getJSONObject("evento");
						Evento eventoE = new Evento(
								eSObj.getInt("idE"),
								eSObj.getString("nombreE"),
								LocalDate.parse(eSObj.getString("fecha")),
								LocalTime.parse(eSObj.getString("hora")),
								eSObj.getString("tipoE"),
								estadoEvento.valueOf(eSObj.getString("estado")),
								new Venue(
										eSObj.getJSONObject("venue").getString("nombreV"),
										eSObj.getJSONObject("venue").getInt("capacidad"),
										eSObj.getJSONObject("venue").getString("tipoV"),
										eSObj.getJSONObject("venue").getString("ubicacion"),
										eSObj.getJSONObject("venue").getString("restricciones")
										),
								new Organizador(
										eSObj.getJSONObject("organizador").getString("login"),
										eSObj.getJSONObject("organizador").getString("password"),
										eSObj.getJSONObject("organizador").getInt("idO")));
						
						
						JSONObject lSObj = sObj.getJSONObject("localidad");
						Localidad locE = new Localidad(
								lSObj.getInt("idL"),
								lSObj.getString("nombreL"),
								lSObj.getDouble("precioBase"),
								lSObj.getInt("capacidadL"));
						
						
						int idEntrada = sObj.getInt("idTiquete");
						String propietarioEntrada = sObj.getString("propietario");
						estadoTiquete estadoEntrada = estadoTiquete.valueOf(sObj.getString("estado"));
						boolean transferibilidadE = sObj.getBoolean("transferibilidad");
						
						TiqueteSimple simpleE = new TiqueteSimple(idEntrada, transferibilidadE, propietarioEntrada, estadoEntrada, "simple", eventoE, locE);
						
						if(sObj.has("numAsineto")) {
							simpleE.setNumAsiento(sObj.getInt("numAsiento"));
						}
						
						multiple.getEntradas().add(simpleE);
						
								
								
					}
					tiquetes.add(multiple);
				}
				
			}
			
			Pago pago = new Pago(idPago, fecha, metodo, cargoServicio, cargoImpresion, tiquetes);
			lista.add(pago);
		}
		
		return lista;
	}
}
