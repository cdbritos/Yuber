package tsi2.yuber.services.rest;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
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

import tsi2.yuber.model.entities.Vertical;
import tsi2.yuber.services.IVerticalServiceLocal;

@Path("/vertical")
public class VerticalServiceRest extends AbstractServiceRest{

	IVerticalServiceLocal verticalService;
	
	public VerticalServiceRest() {
	
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			verticalService = (IVerticalServiceLocal) ctx.lookup("java:global/" + getAppName() + "/YuberServices/VerticalService!tsi2.yuber.services.IVerticalServiceLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GET
	@Path(value="/all")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllVerticals(){
		String result = null;
		
		try {
			List<Vertical> verticales = verticalService.findAll();
			result = new Gson().toJson(verticales);
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	

	@POST
	@Path(value="/new")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	public Response newVertical(InputStream data) {
		
		String result = null;
		
		try {
			Gson gson = new Gson();
			Reader reader = new InputStreamReader(data, "UTF-8");
			Vertical vertical = gson.fromJson(reader, Vertical.class);
			vertical.setFechaCreacion(new Date());
			
			verticalService.saveVertical(vertical);
			result = "Vertical guardada correctamente";
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR al guardar la vertical";
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
		}
	}
	
	
	@GET
	@Path(value="/createDatabase/{verticalName}")
	@Produces({MediaType.TEXT_PLAIN})
	public Response createDataBase(@PathParam("verticalName") String verticalName){
	
		try {
			verticalService.createDataBase(verticalName);
			return Response.status(Status.OK).entity("OK").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
	} 

	@GET
	@Path(value="/populate/users/{verticalName}")
	@Produces({MediaType.TEXT_PLAIN})
	public Response populateUsers(@PathParam("verticalName") String verticalName){
	
		try {
			verticalService.populateVerticalUsers(verticalName);
			return Response.status(Status.OK).entity("OK").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
	}
	
	
	@GET
	@Path(value="/populate/services/{verticalName}")
	@Produces({MediaType.TEXT_PLAIN})
	public Response populateServices(@PathParam("verticalName") String verticalName){
	
		try {
			verticalService.populateVerticalServices(verticalName);
			return Response.status(Status.OK).entity("OK").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
	}
	
}
