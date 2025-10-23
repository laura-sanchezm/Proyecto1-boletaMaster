package Persistencia;

import modelo.Usuario;
import modelo.Cliente;
import modelo.Organizador;
import modelo.Administrador;
import modelo.Evento;
import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.TiqueteMultiple;
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
            

            if (tipo.equals("organizador")) {
                int idO = obj.getInt("idO");
                Organizador o = new Organizador(login, password, idO);
                
                if(obj.has("eventos")) {
                	JSONArray eventosArray = obj.getJSONArray("eventos");
                    if (eventosArray != null) {
                        for (int j = 0; j < eventosArray.length(); j++) {
                            int idEvento = eventosArray.getInt(j);
                            Evento e = buscarEventoPorIdEnTiquetes(tiquetesGlobales, idEvento);
                            if (e != null) o.getEventos().add(e);
                        }
                    }
                    usuarios.add(o);
                }
                
                else if(tipo.equals("administrador")) {
                    Administrador admin = new Administrador(login, password);
                    admin.fijarCargoServicio(obj.getInt("cargoServicio"));
                    admin.fijarCargoImpresion(obj.getInt("cargoImpresion"));

                    if (obj.has("eventos")) {
                        JSONArray eventosArray = obj.getJSONArray("eventos");
                        for (int j = 0; j < eventosArray.length(); j++) {
                            int idEvento = eventosArray.getInt(j);
                            Evento e = buscarEventoPorIdEnTiquetes(tiquetesGlobales, idEvento);
                            if (e != null) admin.getEventos().add(e);
                        }
                    }

                    usuarios.add(admin);
                }
                else if(tipo.equals("cliente")) {
                    Cliente c = new Cliente(login, password);

                    if (obj.has("saldo")) {
                        double saldo = obj.getDouble("saldo");
                        c.recargarSaldo(saldo);
                    }

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
                else {
                	usuarios.add(new Usuario(login, password));
                }
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

    private static Evento buscarEventoPorIdEnTiquetes(List<Tiquete> tiquetes, int idEvento) {
        for (Tiquete t : tiquetes) {
            if (t instanceof TiqueteSimple) {
                TiqueteSimple s = (TiqueteSimple) t;
                if (s.getEvento().getIdE() == idEvento) {
                    return s.getEvento();
                }
            } else if (t instanceof TiqueteMultiple) {
                TiqueteMultiple m = (TiqueteMultiple) t;
                for (TiqueteSimple s : m.getEntradas()) {
                    if (s.getEvento().getIdE() == idEvento) {
                        return s.getEvento();
                    }
                }
            }
        }
        return null;
    }
}


