package Persistencia;

import modelo.Localidad;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class PerLocalidad {
	
	private static final String ARCHIVO = "localidades.json";
	
	public static void guardarLocalidades(List<Localidad> localidades) throws IOException{
	
		JSONArray array = new JSONArray();
		
		for(Localidad l : localidades) {
			JSONObject obj = new JSONObject();
			obj.put("idL", l.getIdL());
			obj.put("nombreL", l.getNombreL());
			obj.put("precioBase", l.getPrecioBase());
			obj.put("capacidadL", l.getCapacidadL());
			array.put(obj);
		}
		Persistencia.guardarJSONArray(array, ARCHIVO);
	}
	
	public static List<Localidad> cargarLocalidades() throws IOException {
		
		List<Localidad> lista = new ArrayList<Localidad>();
		JSONArray array = Persistencia.cargarJSONArray(ARCHIVO);
		
		for(int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			Localidad l = new Localidad(obj.getInt("idL"), obj.getString("nombreL"), obj.getDouble("precioBase"), obj.getInt("capacidadL"));
			lista.add(l);
		}
		
		return lista;
	}
	
	
	public static Localidad buscarLocalidadporId(int idL) {
		try {
			List<Localidad> localidades = cargarLocalidades();
			for(Localidad l : localidades) {
				if(l.getIdL() == idL) {
					return l;	
				}
			}
			
			}
			catch(Exception e) {
				e.printStackTrace();
		}
		return null;
	}
}
