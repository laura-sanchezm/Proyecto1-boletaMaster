package Persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;


public class Persistencia {

	private static final String RUTA_BASE = "data/";
	
	
	static {
		File carpeta = new File(RUTA_BASE);
		if(!carpeta.exists()) {
			carpeta.mkdirs();
		}
	}
	
	public static void guardarJSONArray(JSONArray array, String nombreArchivo) throws IOException {
		
		File archivo = new File(RUTA_BASE + nombreArchivo);
		
		try(FileWriter writer = new FileWriter(archivo)){
			writer.write(array.toString(4));
		}
	}
	
	public static JSONArray cargarJSONArray(String nombreArchivo) throws IOException {
		
		File archivo = new File(RUTA_BASE + nombreArchivo);
		
		if(!archivo.exists()) {
			return new JSONArray();
		}
		
		StringBuilder contenido = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
			String linea;
			while((linea = br.readLine()) != null) {
				contenido.append(linea);
			}
		}
		
		return new JSONArray(contenido.toString());
	}
}
