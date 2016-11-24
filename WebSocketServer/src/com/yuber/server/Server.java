package com.yuber.server;


import java.security.Provider.Service;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.yuber.collections.ManejadorVertical;
import com.yuber.collections.VerticalesManager;

import tsi2.yuber.model.entities.Servicio;
import tsi2.yuber.model.entities.Vertical;
import tsi2.yuber.services.IProveedorCommonServiceLocal;
//import tsi2.yuber.services.IServiciosServiceLocal;
import tsi2.yuber.services.IServiciosServiceLocal;
import tsi2.yuber.services.IVerticalServiceLocal;

import java.util.Date;;

 


@ServerEndpoint(value ="/servicio/{vertical}", decoders = { ServiceLocationDecoder.class }, encoders = {ServiceLocationEncoder.class})
public class Server {
	private final double distanciaMinimaComenzar = 0.150; //in KM
	private final double distanciaMinimaSolicitar = 3.0; //in KM
	private final long timeoutSolicitar = 60000; //in milisegs
	private Session sessionTimer = null;
	private String verticalTimer = null;
	
	/*
	 	-se regsitran clientes y proveedores
 		-el cliente pide servicio                        
 		-se avisa a los proveedores sercanos que hay un cliente solicitando
        - comienza un timer por 60segs, timeout-> enviar expiro el servicio, sino apagar timer
		-un proveedor acepta                        
		-el proveedor envia al cliente sus datos                        
		(mientras no llegue el proveedor hay que mostrar donde esta el cliente y donde esta el prov)                      
		-luego, el prov envia el comando comenzar servicio                        
		- finalimente proveedor envia el comando finalizar servicio y su review
		- por ultimo el cliente envia su review
		
	 */
	@OnOpen
    public void onOpen(Session session,@PathParam("vertical") String vertical) {
		ManejadorVertical man = VerticalesManager.getInstance().obtenerManejador(vertical);
        System.out.println("New websocket session opened in "+vertical +": " + session.getId());
        session.setMaxIdleTimeout(0);
		man.CrearSession(session);
    }
	 
	@OnClose
	public void onClose(Session session,@PathParam("vertical") String vertical) {
		ManejadorVertical man = VerticalesManager.getInstance().obtenerManejador(vertical);
		System.out.println("Websoket session closed: " + session.getId());
		man.BorrarSession(session);
		String sessionId = session.getId();
		
		Proveedor prov = man.getProveedor(sessionId);
		Cliente cli = man.getCliente(sessionId);
		if (prov != null){
			System.out.println("saco el prov: "+prov.toString());
			man.BorrarProveedor(sessionId);
		}
		if (cli != null){
			System.out.println("saco el cli: "+cli.toString());
			man.BorrarCliente(sessionId);
		}
    }
	
	@OnMessage
	public void onMessage(Session session, ServiceLocation message,@PathParam("vertical") String vertical){
		ManejadorVertical man = VerticalesManager.getInstance().obtenerManejador(vertical);
		System.out.println("Estoy en: "+vertical);
		System.out.println(man.ImprimirClientes());
		System.out.println(man.ImprimirProveedores());
	try{
		Thread timerSolicitar = new Thread() {
		    public void run() {
		    		ManejadorVertical man = VerticalesManager.getInstance().obtenerManejador(verticalTimer);
		    		Session var = sessionTimer;
					try {
						Thread.sleep(timeoutSolicitar);
						Match mat = man.getMatch(var.getId());
						if (mat.getStatus().equals("Pendiente")){
							mat.setStatus("Timeout");
							ServiceLocation respTimeOut = new ServiceLocation("ErrorTimeOut", "",null,null,"");
							var.getAsyncRemote().sendObject(respTimeOut);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }  
		};
		if (message.getCommand().equals("ProveedorDisponible")){
			man.CrearProveedor(session.getId(),message.getUserName(),message.getLat(),message.getLng(),message.getTelefono());
			Proveedor prov = man.getProveedor(session.getId());
			System.out.println("se agrego el prov: "+message.getUserName());
			ServiceLocation respOk = new ServiceLocation();
			respOk.setCommand("Ok");
			session.getAsyncRemote().sendObject(respOk);
			//buscar matches pendientes en este momento
			Match mat = man.getMatchPendiente();
			if (mat != null){ // si hay matchs pendientes
				Cliente cli = man.getCliente(mat.getClienteId());
				double distProvCli = distanciaCoord(prov.getLat().doubleValue(),prov.getLng().doubleValue(),cli.getLat().doubleValue(),cli.getLng().doubleValue());
				if (prov.getClientePendienteMatchId().isEmpty() &&  distProvCli < distanciaMinimaSolicitar){
					prov.setClientePendienteMatchId(cli.getSessionId());
					System.out.println("Distancia al cliente: "+distProvCli);
					ServiceLocation respMatchProveedor = new ServiceLocation("SolicitudMatch", cli.getUserName(),cli.getLat(),cli.getLng(),cli.getAddress());
					session.getAsyncRemote().sendObject(respMatchProveedor);
				}
			}
		}
		if (message.getCommand().equals("SolicitarServicio")){
			man.CrearCliente(session.getId(),message.getUserName(),message.getLat(),message.getLng(),message.getAddress());
			System.out.println("Se agrego cliente: "+message.getUserName()+" en "+vertical);
			ServiceLocation respOk = new ServiceLocation();
			respOk.setCommand("Ok");
			session.getAsyncRemote().sendObject(respOk);
			Cliente cli = man.getCliente(session.getId());
			System.out.println("Cliente: "+cli.getUserName()+"solicita servico");
			man.CrearMatch("",session.getId());
			System.out.println("Buscando proveedores...");
			man.NotificarProveedoresCercanos(cli,distanciaMinimaSolicitar);
			sessionTimer = session;
			verticalTimer = vertical;
			timerSolicitar.start(); // timer si en el tiempo determinado no acepta ningun proveedor, pone el match en status TimeOut
		}
		if (message.getCommand().equals("ClientePosicion")){
			Cliente cliViejo = man.getCliente(session.getId());
			cliViejo.setLat(message.getLat());
			cliViejo.setLng(message.getLng());
		}
		if (message.getCommand().equals("ProveedorPosicion")){
			System.out.println("proveedor update pos: "+message.getLat().toString());
			Proveedor prov = man.getProveedor(session.getId());
			if (!prov.getClientePendienteMatchId().isEmpty()){
				Match mat = man.getMatch(session.getId());
				if (mat.getStatus().equals("Active")){
					mat.addPosition(message.getLat(),message.getLng());
					Session sessionCli = man.getSession(mat.getClienteId());
					ServiceLocation respUpdate = new ServiceLocation("UpdatePos", "",message.getLat(),message.getLng(),"");
					sessionCli.getAsyncRemote().sendObject(respUpdate);
				}
			}
				Proveedor provViejo = man.getProveedor(session.getId());
				provViejo.setLat(message.getLat());
				provViejo.setLng(message.getLng());
		}	
		if (message.getCommand().equals("ProveedorAcceptMatch")){
			Proveedor prov = man.getProveedor(session.getId());
			String clienteId = prov.getClientePendienteMatchId();
			if (!clienteId.isEmpty()){
				Match mat = man.getMatch(clienteId);
				if (!mat.getStatus().equals("Timeout") && !mat.getStatus().equals("Active")){
					mat.setStatus("Active");
					mat.setProveedorAccept(true);
					mat.setProveedorId(session.getId());
					InitialContext ctx;
					ctx = new InitialContext();
					IProveedorCommonServiceLocal proveedorCommonService = (IProveedorCommonServiceLocal) ctx.lookup("java:global/" + getAppName() +  "/WebSocketServer-0.0.1-SNAPSHOT/ProveedorCommonService!tsi2.yuber.services.IProveedorCommonServiceLocal");
					tsi2.yuber.model.entities.Proveedor proveedorDB = proveedorCommonService.findProveedorByUsername(vertical,prov.getUserName());
					ServiceLocation respMatch= new ServiceLocation("Match",prov.getUserName(),prov.getLat(),prov.getLng());
					respMatch.setTelefono(proveedorDB.getTelefono());
					Session sessionCli = man.getSession(mat.getClienteId());
					sessionCli.getAsyncRemote().sendObject(respMatch);
				}else{
					// el match ya fue aceptado por otro
					ServiceLocation respMatch= new ServiceLocation("Error",null,null,null);
					session.getAsyncRemote().sendObject(respMatch);
				}
			}else{
				// el prov no tiene match pendiente
				ServiceLocation respMatch= new ServiceLocation("Error",null,null,null);
				session.getAsyncRemote().sendObject(respMatch);
			}
		}
		if (message.getCommand().equals("ProveedorDeclineMatch")){
			Proveedor prov = man.getProveedor(session.getId());
			Match mat = man.getMatch(prov.getClientePendienteMatchId());
			prov.setClientePendienteMatchId("");
			//mat.setStatus("Pendiente");
		}
		if (message.getCommand().equals("ComenzarServicio")){
			Match mat = man.getMatch(session.getId());
			Cliente cli = man.getCliente(mat.getClienteId());
			Double distancia = distanciaCoord(message.getLat().bigDecimalValue().doubleValue(), message.getLng().bigDecimalValue().doubleValue(),cli.getLat().bigDecimalValue().doubleValue(),cli.getLng().bigDecimalValue().doubleValue());
			//guardar ubicacion, tiempo, cambiar estado
			System.out.println("comenzar servicio: "+"distancia:"+distancia.toString());
			if (distancia < distanciaMinimaComenzar){
				mat.setStartLat(message.getLat());
				mat.setStartLng(message.getLng());
				mat.setStatus("InService");
				mat.addPosition(message.getLat(),message.getLng());
				Date date = new Date();
				mat.setStartTime(date);
				Session sessionCli = man.getSession(mat.getClienteId());
				ServiceLocation respMatch= new ServiceLocation("InService", null,null,null);
				sessionCli.getAsyncRemote().sendObject(respMatch);
				ServiceLocation respProv= new ServiceLocation("OkComenzar", null,null,null);
				session.getAsyncRemote().sendObject(respProv);
			}else{
				ServiceLocation respProv= new ServiceLocation("ErrorComenzar", null,null,null);
				session.getAsyncRemote().sendObject(respProv);
			}
		}
		if (message.getCommand().equals("FinalizarServicio")){
			//guardar ubicacion, tiempo, cambiar estado
			Match mat = man.getMatch(session.getId());
			mat.setFinishLat(message.getLat());
			mat.setFinishLng(message.getLng());
			mat.setStatus("Finished");
			System.out.println("Finalizar servicio: "+mat.toString());
			Date date = new Date();
			mat.setFinishTime(date);
			mat.setReviewProveedor(message.getRating());
			mat.addPosition(message.getLat(),message.getLng());
			String clienteId = mat.getClienteId();
			Cliente cliente = man.getCliente(clienteId);
			Session sessionCli = man.getSession(clienteId);
			Double distTotal = calcularDistanciaTotal(mat.getPositions());
			long tiempoServicio = (mat.getFinishTime().getTime() - mat.getStartTime().getTime())/1000; //cantidad segundos de servicio
			tiempoServicio = tiempoServicio / 60; //en minutos
			System.out.println("Finalizar servicio: calcular costo");
			Double cost = CalcularCostoServicio(vertical, tiempoServicio, distTotal);
			mat.setDuration(tiempoServicio);
			mat.setCost(cost);
			mat.setDisTotal(distTotal);
			ServiceLocation respTarifa = new ServiceLocation("ServicioFinalizado","",null,null,"","",0,cost);
			session.getAsyncRemote().sendObject(respTarifa);
			sessionCli.getAsyncRemote().sendObject(respTarifa);
			Proveedor prov = man.getProveedor(session.getId());
			prov.setClientePendienteMatchId("");
		}
		if (message.getCommand().equals("ReviewCliente")){
			Match mat = man.getMatch(session.getId());
			System.out.println("Review cliente:"+mat.toString());
			if (mat.getStatus().equals("Finished")){
				mat.setReviewCliente(message.getRating());
				Proveedor prov = man.getProveedor(mat.getProveedorId());
				Cliente cli = man.getCliente(session.getId());
				//se elimina de memoria
				InitialContext ctx = new InitialContext();
				System.out.println("Culmino el servicio: "+mat.toString());
				IServiciosServiceLocal services = (IServiciosServiceLocal) ctx.lookup("java:global/" + getAppName() +  "/WebSocketServer-0.0.1-SNAPSHOT/ServiciosService!tsi2.yuber.services.IServiciosServiceLocal");
				Servicio serv = new Servicio(prov.getUserName(),cli.getUserName(),mat.getStatus(),mat.getStartTime(),mat.getFinishTime(),mat.getReviewProveedor(),mat.getReviewCliente(),mat.getCost(),mat.getDuration(),"");
				CustomData cdata = new CustomData(mat.getStartLat(),mat.getStartLng(),mat.getFinishLat(),mat.getFinishLng(),mat.getDisTotal(),mat.getPositions());
				serv.setCustomData(new Gson().toJson(cdata));
				services.saveServicio(serv, vertical);
				session.close(); //cierro websocket cliente
				man.BorrarMatch(mat);
			}
		}
	}catch (Exception e) {
		System.out.println("error: " +e.getCause()+"/"+e.getMessage());
		ServiceLocation errorDesc = new ServiceLocation(e.getMessage(),"",null,null,"");
		session.getAsyncRemote().sendObject(errorDesc);
	}
}
	
	
	
	private double calcularDistanciaTotal(List<Position> list ){
		Position pos = null;
		double total = 0;
		for (Iterator<Position> it = list.iterator(); it.hasNext(); ) {
			pos = it.next();
			if (it.hasNext()){
				Position pos2 = it.next();
				total += distanciaCoord(pos.getLat().doubleValue(),pos.getLng().doubleValue(),pos2.getLat().doubleValue(),pos2.getLng().doubleValue());
			}
	    }
		return total; 
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
	
	private String getAppName(){
		String appName = System.getenv("appName");
		if ( appName == null)
			return "myapp";
		return appName;
	}
	
	private Double CalcularCostoServicio(String vertical,long timeService,double dist){
		
		String tipoVertical = System.getenv("tipoVertical");
		System.out.println("Calcular costo vertical: "+tipoVertical);
		InitialContext ctx;
		Double total = 0.0;
		try {
			ctx = new InitialContext();
			IVerticalServiceLocal verticalService = (IVerticalServiceLocal) ctx.lookup("java:global/" + getAppName() + "/WebSocketServer-0.0.1-SNAPSHOT/VerticalService!tsi2.yuber.services.IVerticalServiceLocal");
			Vertical vert = verticalService.findVertical(vertical);
			System.out.println("Agarre la vertical: "+vert.toString());
			if (tipoVertical.equals("1")){
				// tipo transporte
				Double tarBase = vert.getTarifaBase();
				Double preKm = vert.getPrecio();
				total = tarBase + preKm * dist;
			}else{
				//tipo on-site
				Double tarBase = vert.getTarifaBase();
				Double preTime = vert.getPrecio();
				total = tarBase + preTime * timeService;
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

}