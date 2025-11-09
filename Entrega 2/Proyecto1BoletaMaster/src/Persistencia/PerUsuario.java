package Persistencia;

import modelo.Usuario;
import modelo.Cliente;
import modelo.Organizador;
import modelo.Administrador;
import modelo.Evento;
import Tiquetes.Tiquete;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerUsuario {
	
	private static final String ARCHIVO = "usuarios.json";
	
	public static void guardarUsuarios(List<Usuario> usuarios) throws IOException{
		
		JSONArray array = new JSONArray();
		
		for(Usuario u : usuarios) {
			JSONObject obj = new JSONObject();
			obj.put("login", u.getLogin());
			obj.put("password", u.getPassword());
			
			if(u instanceof Organizador) {
				Organizador o = (Organizador) u;
				obj.put("tipo", "organizador");
				obj.put("idO", o.getIdO());
				
				JSONArray eventosArray = new JSONArray();
				for(Evento e: o.getEventos()) {
					eventosArray.put(e.getIdE());
				}
				obj.put("eventos", eventosArray);
			}
			
			else if( u instanceof Administrador) {
				
				Administrador a = (Administrador) u;
				obj.put("tipo", "administrador");
				obj.put("cargoServicio", a.getCargoServicio());
				obj.put("cargoImpresion", a.getCargoImpresion());
				
				JSONArray eventosArray = new JSONArray();
				for(Evento e: a.getEventos()) {
					eventosArray.put(e.getIdE());
				}
				obj.put("eventos", eventosArray);
				
			}
			
			else if(u instanceof Cliente) {
                Cliente c = (Cliente) u;
                obj.put("tipo", "cliente");
                obj.put("saldo", c.consultarSaldo());

                JSONArray tiquetesArray = new JSONArray();
                for (Tiquete t : c.getTiquetes()) {
                    tiquetesArray.put(t.getIdT()); 
                }
                obj.put("tiquetes", tiquetesArray);
			}
			
			else {
				obj.put("tipo", "usuario");
			}
			
			array.put(obj);
		}
		Persistencia.guardarJSONArray(array, ARCHIVO);
	}
	
	
	public static List<Usuario> cargarUsuario() throws IOException {
		
		List<Tiquete> tiquetesGlobales = PerTiquete.cargarTiquetes();
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		JSONArray array = Persistencia.cargarJSONArray(ARCHIVO);
		
		for(int i = 0; i < array.length();i++) {
			  JSONObject obj = array.getJSONObject(i);
	            String login = obj.getString("login");
	            String password = obj.getString("password");
	            String tipo = obj.getString("tipo");

	            switch (tipo) {
	                case "administrador" -> {
	                    Administrador a = new Administrador(login, password);
	                    a.fijarCargoServicio(obj.optInt("cargoServicio", 0));
	                    a.fijarCargoImpresion(obj.optInt("cargoImpresion", 0));
	                    usuarios.add(a);
	                }
	                case "organizador" -> {
	                    int idO = obj.getInt("idO");
	                    Organizador o = new Organizador(login, password, idO);
	                    usuarios.add(o);
	                }
	                case "cliente" -> {
	                    Cliente c = new Cliente(login, password);
	                    c.recargarSaldo(obj.optDouble("saldo", 0));
	                    if (obj.has("tiquetes")) {
	                        JSONArray tiquetesArray = obj.getJSONArray("tiquetes");
	                        for (int j = 0; j < tiquetesArray.length(); j++) {
	                            int idT = tiquetesArray.getInt(j);
	                            Tiquete t = buscarTiquetePorId(tiquetesGlobales, idT);
	                            if (t != null) c.addTiquete(t);
	                        }
	                    }
	                    usuarios.add(c);
	                }
	                default -> usuarios.add(new Usuario(login, password));
            }
		}
		
		return usuarios;
	}
	
	
	
	private static Tiquete buscarTiquetePorId(List<Tiquete> tiquetes, int id) {
        for (Tiquete t : tiquetes) {
            if (t.getIdT() == id) return t;
        }
        return null;
    }

}


