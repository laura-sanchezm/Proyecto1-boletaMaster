package Persistencia;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.TiqueteMultiple;
import Tiquetes.estadoTiquete;
import modelo.Evento;
import modelo.Localidad;
import modelo.Venue;
import modelo.estadoEvento;
import modelo.Organizador;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PerTiquete {
	
	private static final String ARCHIVO = "tiquetes.json";
	
	public static void guardarTiquetes(List<Tiquete> tiquetes) throws IOException{
		
		JSONArray array = new JSONArray();
		
		for(Tiquete t : tiquetes ) {
			JSONObject tObj = new JSONObject();
			tObj.put("idTiquete", t.getIdT());
			tObj.put("propietario", t.getPropietario());
			tObj.put("estado", t.getStatus().name());
			tObj.put("transferibilidad", t.esTransferible());
			tObj.put("tipo", t.getTipo());
			
			if(t instanceof TiqueteSimple) {
				TiqueteSimple simple = (TiqueteSimple) t;
				
				Evento e = simple.getEvento();
				JSONObject eObj = new JSONObject();
				eObj.put("idE", e.getIdE());
				eObj.put("nombreE", e.getNombreE());
				eObj.put("fecha", e.getFecha().toString());
				eObj.put("hora", e.getHora().toString());
				eObj.put("tipoE", e.getTipoE());
				eObj.put("estado", e.getEstado().name());
				
				JSONObject vObj = new JSONObject();
				vObj.put("nombreV", e.getVenue().getNombreV());
				vObj.put("capacidad", e.getVenue().getCapacidad());
				vObj.put("tipoV", e.getVenue().getTipoV());
				vObj.put("ubicacion", e.getVenue().getUbicacion());
				vObj.put("restricciones", e.getVenue().getRestricciones());
				eObj.put("venue", vObj);
				
				JSONObject orObj = new JSONObject();
				orObj.put("login", e.getOrganizador().getLogin());
				orObj.put("password", e.getOrganizador().getPassword());
				orObj.put("idO", e.getOrganizador().getPassword());
				eObj.put("organizador", orObj);
				
				tObj.put("evento", eObj);
				
				Localidad l = simple.getLocalidad();
				JSONObject lObj = new JSONObject();
				lObj.put("idL", l.getIdL());
				lObj.put("nombreL", l.getNombreL());
	            lObj.put("precioBase", l.getPrecioBase());
	            lObj.put("capacidadL", l.getCapacidadL());
	            lObj.put("numerada", l.isNumerada());
	            
	            tObj.put("localidad", lObj);
	            
	            if(simple.isEnumerado()) {
	            	tObj.put("numAsiento", simple.getNumAsiento());
	            }
			}
			
			else if(t instanceof TiqueteMultiple) {
				TiqueteMultiple multiple = (TiqueteMultiple) t; 
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
					orsObj.put("idO", s.getEvento().getOrganizador().getPassword());
					esObj.put("organizador", orsObj);
					
					sObj.put("evento", esObj);
					
					if(s.isEnumerado()) {
						sObj.put("numAsineto", s.getNumAsiento());
					}
					
					entradas.put(sObj);
			}
				tObj.put("entradas", entradas);
		}
		
			array.put(tObj);
	}
		Persistencia.guardarJSONArray(array, ARCHIVO);
  }
	
	public static List<Tiquete> cargarTiquetes() throws IOException{
		
		List<Tiquete> lista = new ArrayList<Tiquete>();
		JSONArray array = Persistencia.cargarJSONArray(ARCHIVO);
		
		for(int i = 0; i < array.length(); i++ ) {
			JSONObject tObj = new JSONObject();
			String tipo = tObj.getString("tipo");
			
			if(tipo.equals("simple")) {
                int id = tObj.getInt("idTiquete");
                String propietario = tObj.getString("propietario");
                boolean transferible = tObj.getBoolean("transferible");
                estadoTiquete estado = estadoTiquete.valueOf(tObj.getString("estado"));

                JSONObject eObj = tObj.getJSONObject("evento");
                JSONObject vObj = eObj.getJSONObject("venue");
                JSONObject orObj = eObj.getJSONObject("organizador");
                
                Venue v = new Venue(
                        vObj.getString("nombreV"),
                        vObj.getInt("capacidad"),
                        vObj.getString("tipoV"),
                        vObj.getString("ubicacion"),
                        vObj.getString("restricciones")
                );
                
                Organizador o = new Organizador(
                		orObj.getString("login"),
                		orObj.getString("password"),
                		orObj.getInt("idO"));

                Evento e = new Evento(
                        eObj.getInt("idE"),
                        eObj.getString("nombreE"),
                        LocalDate.parse(eObj.getString("fecha")),
                        LocalTime.parse(eObj.getString("hora")),
                        eObj.getString("tipoE"),
                        estadoEvento.valueOf(eObj.getString("estado")),
                        v,
                        o
                );

                JSONObject lObj = tObj.getJSONObject("localidad");
                Localidad l = new Localidad(
                        lObj.getInt("idL"),
                        lObj.getString("nombreL"),
                        lObj.getDouble("precioBase"),
                        lObj.getInt("capacidadL")
                );
                l.setNumerada(lObj.getBoolean("numerada"));

                TiqueteSimple simple = new TiqueteSimple(id, transferible, propietario, estado, "simple", e, l);
                if (tObj.has("numAsiento")) {
                    simple.setNumAsiento(tObj.getInt("numAsiento"));
                }
                lista.add(simple);
			}
            else if (tipo.equals("multiple")) {
                int idM = tObj.getInt("idTiquete");
                String propietarioM = tObj.getString("propietario");
                boolean transferibleM = tObj.getBoolean("transferible");
                estadoTiquete estadoM = estadoTiquete.valueOf(tObj.getString("estado"));

                TiqueteMultiple multiple = new TiqueteMultiple(idM, transferibleM, propietarioM, estadoM, "multiple");
                JSONArray entradas = tObj.getJSONArray("entradas");

                for (int k = 0; k < entradas.length(); k++) {
                    JSONObject sObj = entradas.getJSONObject(k);

                    int id = sObj.getInt("idTiquete");
                    String propietario = sObj.getString("propietario");
                    boolean transferible = sObj.getBoolean("transferible");
                    estadoTiquete estado = estadoTiquete.valueOf(sObj.getString("estado"));

                    JSONObject esObj = sObj.getJSONObject("evento");
                    JSONObject vsObj = esObj.getJSONObject("venue");
                    JSONObject orsObj = esObj.getJSONObject("organizador");
                    
                    Venue v = new Venue(
                            vsObj.getString("nombreV"),
                            vsObj.getInt("capacidad"),
                            vsObj.getString("tipoV"),
                            vsObj.getString("ubicacion"),
                            vsObj.getString("restricciones")
                    );
                    
                    Organizador o = new Organizador(
                    		orsObj.getString("login"),
                    		orsObj.getString("password"),
                    		orsObj.getInt("idO"));

                    Evento e = new Evento(
                            esObj.getInt("idE"),
                            esObj.getString("nombreE"),
                            LocalDate.parse(esObj.getString("fecha")),
                            LocalTime.parse(esObj.getString("hora")),
                            esObj.getString("tipoE"),
                            estadoEvento.valueOf(esObj.getString("estado")),
                            v,
                            o
                    );

                    JSONObject lObj = sObj.getJSONObject("localidad");
                    Localidad l = new Localidad(
                            lObj.getInt("idL"),
                            lObj.getString("nombreL"),
                            lObj.getDouble("precioBase"),
                            lObj.getInt("capacidadL")
                    );
                    l.setNumerada(lObj.getBoolean("numerada"));

                    TiqueteSimple simple = new TiqueteSimple(id, transferible, propietario, estado, "simple", e, l);
                    if (sObj.has("numAsiento")) {
                        simple.setNumAsiento(sObj.getInt("numAsiento"));
                    }
                    multiple.getEntradas().add(simple);
                }

                lista.add(multiple);
            }
		}
		return lista;
	}
}
