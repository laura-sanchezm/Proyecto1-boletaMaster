package Persistencia;

import Pago.Pago;
import Pago.estadoPago;
import Pago.metodoPago;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;
import Tiquetes.TiqueteMultipe;
import modelo.Oferta;
import modelo.estadoEvento;
import modelo.Venue;
import modelo.Evento;
import modelo.Localidad;
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
			obj.put("monto", p.getMonto());
			obj.put("estado", p.getEstado().name());
			obj.put("metodo", p.getMetodo().name());
			obj.put("cargoServicio", p.getCargoServicio());
			obj.put("cargoImpresion", p.getCargoImpresion());
			
			JSONArray tiquetesArray = new JSONArray();
			for(Tiquete t : p.getTiquetesComprados()) {
				JSONObject tObj = new JSONObject();
				tObj.put("idTiquete", t.getIdT());
				tObj.put("propietario", t.getPropietario());
				tObj.put("estado", t.getStatus().name());
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
				else if(t instanceof TiqueteMultipe) {
					TiqueteMultipe multiple = (TiqueteMultipe) t;
					tObj.put("tipo", "multiple");
					JSONArray entradas = new JSONArray();
					for(TiqueteSimple s : multiple.getEntradas()) {
						JSONObject sObj = new JSONObject();
						sObj.put("idTiquete", s.getIdT());
						sObj.put("propietario", s.getPropietario());
						sObj.put("estado", s.getStatus().name());
						
						JSONObject esObj = new JSONObject();
						esObj.put("idE", s.getEvento().getIdE());
						esObj.put("nombreE", s.getEvento().getNombreE());
						esObj.put("fecha", s.getEvento().getFecha().toString());
						esObj.put("hora", s.getEvento().getHora().toString());
						esObj.put("tipoE", s.getEvento().getTipoE());
						esObj.put("estado", s.getEvento().getEstado().name());
						esObj.put("venue", s.getEvento().getVenue().getNombreV());
						
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
			obj.put("tiquetes", tiquetesArray);
			
			JSONArray ofertasArray = new JSONArray();
			for(Oferta o : p.getOfertasActivas()) {
				JSONObject oObj = new JSONObject();
				oObj.put("idOferta", o.getIdOferta());
				oObj.put("idL", o.getIdL());
				oObj.put("porcentaDescuento", o.getPorcentajeDescuento());
				oObj.put("fechaInicio", o.getFechaInicio().toString());
				oObj.put("fechaFin", o.getFechaFin().toString());
				ofertasArray.put(oObj);
			}
			obj.put("ofertas", ofertasArray);
			
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
			double monto = obj.getDouble("monto");
			estadoPago estado = estadoPago.valueOf(obj.getString("estado"));
			metodoPago metodo = metodoPago.valueOf(obj.getString("metodo"));
			double cargoServicio = obj.getDouble("cargoServicio");
			double cargoImpresion = obj.getDouble("cargoImpresion");
			
			
			ArrayList<Tiquete> tiquetes = new ArrayList<Tiquete>();
			JSONArray tiquetesArray = obj.getJSONArray("tiquetes");
			for(int j = 0; j > tiquetesArray.length();i++) {
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
				
				
				Venue venue = new Venue(nombreV, capacidadV, tipoV, ubicacionV, restricciones);
				Evento evento = new Evento(idE,nombreE,fechaE,horaE,tipoE,estadoE,venue);
				
				 
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
					
					TiqueteSimple simple = new TiqueteSimple(idTiquete, false, propietario, tEstado, "simple", evento, localidad);
					
					if(tObj.has("numAsiento")) {
						simple.setNumAsiento(tObj.getInt("numAsiento"));
					}
					
					tiquetes.add(simple);
					
				}
				
				else if(tipo.equals("multiple")); //Falta completar implementacion de TiqueteMultiple
			}
			
		}
	}
}
