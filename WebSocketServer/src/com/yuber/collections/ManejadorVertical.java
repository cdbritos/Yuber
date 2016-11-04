package com.yuber.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.json.JsonNumber;

import javax.websocket.Session;

import com.yuber.server.Cliente;
import com.yuber.server.Match;
import com.yuber.server.Position;
import com.yuber.server.Proveedor;
import com.yuber.server.ServiceLocation;

public class ManejadorVertical {

	
	private Set<Session> sessions;
	private Map<String,Proveedor> proveedoresActivos;
	private Map<String,Cliente> clientesActivos;
	private Set<Match> Matchs;

	
	public  ManejadorVertical(){
		this.sessions = Collections.synchronizedSet(new HashSet<Session>());
		this.proveedoresActivos = Collections.synchronizedMap(new HashMap<String,Proveedor>());
		this. clientesActivos = Collections.synchronizedMap(new HashMap<String,Cliente>());
		this.Matchs = Collections.synchronizedSet(new HashSet<Match>());
	}
	
	public synchronized void CrearProveedor(String sessionId, String userName,JsonNumber lat, JsonNumber lng,String tel){
	Proveedor prov = new Proveedor(sessionId,userName,lat,lng,"",tel);
	proveedoresActivos.put(sessionId,prov);
	}
	
	public synchronized void CrearCliente(String sessionId, String userName,JsonNumber lat, JsonNumber lng,String address){
		Cliente cli = new Cliente(sessionId,userName,lat,lng,address);
		clientesActivos.put(sessionId,cli);
	}
	
	public synchronized void CrearMatch(String sessionId1, String sessionId2){
		Match match = new Match(sessionId1,sessionId2,"Pendiente",false,false);
		Matchs.add(match);
	}
	public synchronized void CrearSession(Session session){
		sessions.add(session);
	}
	
	public synchronized void BorrarSession(Session session){
		sessions.remove(session);
	}
	
	public synchronized void BorrarProveedor(String sessionId){
		proveedoresActivos.remove(sessionId);
	}
	
	public synchronized void BorrarMatch(Match mat){
		Matchs.remove(mat);
	}
	
	public synchronized void BorrarCliente(String sessionId){
		clientesActivos.remove(sessionId);
	}
	
	public synchronized void NotificarProveedoresCercanos(Cliente cli, double distanciaMinimaSolicitar ){
			Proveedor prov;
			ServiceLocation respMatchProveedor = new ServiceLocation("SolicitudMatch", cli.getUserName(),cli.getLat(),cli.getLng(),cli.getAddress());
			for (Map.Entry<String, Proveedor> entry : proveedoresActivos.entrySet())
			{
				prov = entry.getValue();
				// si no esta matcheando
				double distProvCli = distanciaCoord(prov.getLat().doubleValue(),prov.getLng().doubleValue(),cli.getLat().doubleValue(),cli.getLng().doubleValue());
				System.out.println("distancia al cliente: "+distProvCli);
				if (prov.getClientePendienteMatchId().isEmpty() &&  distProvCli < distanciaMinimaSolicitar){
					prov.setClientePendienteMatchId(cli.getSessionId());
					Session sessionProv = getSession(prov.getSessionId());
					sessionProv.getAsyncRemote().sendObject(respMatchProveedor);
				}
			}
	}
	
	
	public synchronized Session getSession(String id){
		Session session = null;
		for (Iterator<Session> it = sessions.iterator(); it.hasNext(); ) {
			Session elem = it.next();
			if (elem.getId().equals(id)){
				session = elem;
			}
	    }
		return session;
	}
	
	public synchronized Proveedor getProveedor(String sessionId){
		if (this.proveedoresActivos.containsKey(sessionId)){
			return proveedoresActivos.get(sessionId);
		}
		else{
			return null;
		}
	}
	
	public synchronized Cliente getCliente(String sessionId){
		if (this.clientesActivos.containsKey(sessionId)){
			return clientesActivos.get(sessionId);
		}
		else{
			return null;
		}
	}
	

	
	public synchronized Match getMatch (String id){
		Match mat = null;
		for (Iterator<Match> it = Matchs.iterator(); it.hasNext(); ) {
			Match elem = it.next();
			if (elem.getProveedorId().equals(id) || elem.getClienteId().equals(id)){
				mat = elem;
			}
	    }
		return mat;
	}
	public String ImprimirProveedores(){
		Proveedor prov=null;
		String salida="";
		for (Map.Entry<String, Proveedor> entry : proveedoresActivos.entrySet())
		{
			prov = entry.getValue();
			salida += prov.getUserName()+"--";
		}
		return "Proveedores: "+salida;
	}
	
	public String ImprimirClientes(){
		Cliente cli=null;
		String salida="";
		for (Map.Entry<String, Cliente> entry : clientesActivos.entrySet())
		{
			cli = entry.getValue();
			salida += cli.getUserName()+"--";
		}
		return "Clientes: "+salida;
	}
	
	public synchronized Match getMatchPendiente (){
		Match mat = null;
		for (Iterator<Match> it = Matchs.iterator(); it.hasNext(); ) {
			Match elem = it.next();
			if (elem.getStatus().equals("Pendiente")){
				mat = elem;
				break;
			}
	    }
		return mat;
	}
	

	public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {  
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
        return distancia;  
    }
	

}
