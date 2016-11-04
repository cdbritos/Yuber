package com.yuber.collections;

import java.util.HashMap;
import java.util.Map;

import com.yuber.server.Proveedor;

public class VerticalesManager {

	
	private Map<String,ManejadorVertical> manejador;
	private static VerticalesManager instancia;
	
	private VerticalesManager(){
		this.manejador = new HashMap<String,ManejadorVertical>();
	}
	
	public static synchronized VerticalesManager getInstance(){
		if (instancia == null){
			instancia = new VerticalesManager();
			return instancia;
		}else{
			return instancia;
		}
	}
	
	public synchronized void crearManejadorVertical(String vertical){
		if (!this.manejador.containsKey(vertical)){
			ManejadorVertical elem = new ManejadorVertical();
			manejador.put(vertical, elem);
		}
	}
	
	public synchronized void borrarManejadorVertical(String vertical) throws Exception {
		if (this.manejador.containsKey(vertical)){
			manejador.remove(vertical);
		}
	}
	

	public synchronized ManejadorVertical obtenerManejador(String vertical) {
		if (this.manejador.containsKey(vertical)){
			return manejador.get(vertical);
		}
		else{
			crearManejadorVertical(vertical);
			return manejador.get(vertical);
		}
	}
}
