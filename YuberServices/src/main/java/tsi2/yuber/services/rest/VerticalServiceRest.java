package tsi2.yuber.services.rest;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tsi2.yuber.services.IVerticalServiceLocal;

@Path("/vertical")
public class VerticalServiceRest extends AbstractServiceRest{

	IVerticalServiceLocal verticalService;
	
	public VerticalServiceRest() {
	
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			verticalService = (IVerticalServiceLocal) ctx.lookup("java:global/" + getAppName() + "/YuberEJB/VerticalService!tsi2.yuber.services.IVerticalServiceLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*@GET
	@Path(value="/all")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllUsers(){
		String result = null;
		
		try {
							
			List<Vertical> users = verticalService.findAll();
			
			result = new Gson().toJson(users);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	} */
	

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

}
