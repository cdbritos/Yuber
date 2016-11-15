package tsi2.yuber.services.rest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import tsi2.yuber.model.entities.Proveedor;
import tsi2.yuber.model.entities.Servicio;
import tsi2.yuber.services.IProveedorCommonServiceLocal;
import tsi2.yuber.services.util.HistorialCabezal;


@Path("/proveedor")
public class ProveedorServiceRest extends AbstractServiceRest {


	IProveedorCommonServiceLocal proveedorCommonService;
	
	public ProveedorServiceRest() {
	
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			proveedorCommonService = (IProveedorCommonServiceLocal) ctx.lookup("java:global/" + getAppName() +  "/YuberServices/ProveedorCommonService!tsi2.yuber.services.IProveedorCommonServiceLocal");
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
			Proveedor proveedor = gson.fromJson(reader, Proveedor.class);
				
			proveedorCommonService.saveProveedor(proveedor.getVerticalName(), proveedor);
			result = "Proveedor registrado correctamente";
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR al registrar proveedor.";
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
			Proveedor proveedor = gson.fromJson(reader, Proveedor.class);
				
			Proveedor proveedorlogueado = proveedorCommonService.findProveedor(proveedor.getVerticalName(), proveedor);
			
			if (proveedorlogueado != null){
				result = new Gson().toJson(proveedorlogueado);
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
							
			List<Proveedor> proveedores = proveedorCommonService.findAllProveedor(verticalName);
			
			result = new Gson().toJson(proveedores);
			
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
			List<Servicio> servicios = proveedorCommonService.findServiciosProveedor(vertical, username);
			HistorialCabezal historial = new HistorialCabezal(servicios);
			result = new Gson().toJson(historial);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
}
