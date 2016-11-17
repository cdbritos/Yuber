package tsi2.yuber.services.rest;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.json.Json;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import tsi2.yuber.model.entities.Servicio;
import tsi2.yuber.model.entities.User;
import tsi2.yuber.services.IUserCommonServiceLocal;
import tsi2.yuber.services.util.HistorialCabezal;

@Path("/user")
public class UserServiceRest extends AbstractServiceRest{

	IUserCommonServiceLocal userCommonService;
	
	public UserServiceRest() {
	
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			userCommonService = (IUserCommonServiceLocal) ctx.lookup("java:global/" + getAppName() + "/YuberServices/UserCommonService!tsi2.yuber.services.IUserCommonServiceLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	public Response register(InputStream data) {
		
		String result = null;
		
		try {
			Gson gson = new Gson();
			Reader reader = new InputStreamReader(data, "UTF-8");
			User user = gson.fromJson(reader, User.class);
				
			userCommonService.saveUser(user.getVerticalName(), user);
			result = "User registrado correctamente";
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR al registrar usuario.";
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
		}
	}
	
	@POST
	@Path(value="/login")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response login(InputStream data) {
		String result = null;
		
		try {
			Gson gson = new Gson();
			Reader reader = new InputStreamReader(data, "UTF-8");
			User user = gson.fromJson(reader, User.class);
				
			User userlogueado = userCommonService.findUser(user.getVerticalName(), user);
			
			if (userlogueado != null){
				result = new Gson().toJson(userlogueado);
				return Response.status(Status.OK).entity(result).build();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("UserName or Password incorrecto").build();
	}
	
	@GET
	@Path(value="/all/{verticalName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllUsers(@PathParam("verticalName") String verticalName){
		String result = null;
		
		try {
							
			List<User> users = userCommonService.findAllUser(verticalName);
			
			result = new Gson().toJson(users);
			
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	} 
	
	
	@GET
	@Path(value="/{username}/servicios")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getServiciosProveedor(@PathParam("username") String username,@HeaderParam("verticalName") String vertical){
		String result = null;
		try {
			List<Servicio> servicios = userCommonService.findServiciosUser(vertical, username);
			HistorialCabezal historial = new HistorialCabezal(servicios);
			result = new Gson().toJson(historial);
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	} 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="/payment/{accesToken}")
	public Response payPaypal(@PathParam("accesToken") String accesToken,@QueryParam("paymentId")String paymentId,@QueryParam("PayerID")String payerId,@QueryParam("token") String token) throws IOException{
		System.out.println("recibo paypal: "+paymentId+"/"+payerId+"/"+token);
		URL url;
		try {
			url = new URL("https://api.sandbox.paypal.com/v1/payments/payment/" + paymentId + "/execute");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setDoInput (true);
		    conn.setDoOutput (true);    
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Accept", "application/json");
		    conn.setRequestProperty("Content-Type", "application/json");
		    conn.setRequestProperty("Authorization", "Bearer " + accesToken );
		    javax.json.JsonObject paymentData;
		    paymentData = Json.createObjectBuilder()
                    .add("payer_id", payerId)
                    .build();
		    String postData = paymentData.toString();
		    conn.setRequestProperty("Content-Length", String.valueOf(postData.length()));
		    DataOutputStream dostream = new DataOutputStream(conn.getOutputStream());
		     dostream.writeBytes(postData);
		     dostream.flush();
		     dostream.close();
		     if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {
		         String errMsg = conn.getResponseMessage();
		         System.out.println("EXPLOTO PAYPAL!, paypal devovlio error:" + conn.getResponseCode());
		         throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + "  And now Error Message is : " + errMsg);
		     }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("EXPLOTO PAYPAL: " + e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
			return Response.status(Status.OK).entity("ok").build();
		}
	
}
