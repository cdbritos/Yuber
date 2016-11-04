package tsi2.yuber.services.rest;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import tsi2.yuber.model.entities.User;
import tsi2.yuber.services.IUserCommonServiceLocal;

@Path("/user")
public class UserServiceRest extends AbstractServiceRest{

	IUserCommonServiceLocal userCommonService;
	
	public UserServiceRest() {
	
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			userCommonService = (IUserCommonServiceLocal) ctx.lookup("java:global/" + getAppName() + "/YuberEJB/UserCommonService!tsi2.yuber.services.IUserCommonServiceLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@POST
	@Path(value="/register")
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
	
	
}
