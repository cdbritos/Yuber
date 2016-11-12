package tsi2.yuber.services.rest;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import tsi2.yuber.model.dataReport.DataReportGananciaMensual;
import tsi2.yuber.model.dataReport.DataReportReviews;
import tsi2.yuber.model.dataReport.DataReportServicios;
import tsi2.yuber.model.dataReport.DataReportUsuarioProveedor;
import tsi2.yuber.model.entities.Servicio;
import tsi2.yuber.services.IReportServiceLocal;

@Path("/report")
public class ReportServiceRest extends AbstractServiceRest {


	IReportServiceLocal reportService;
	
	public ReportServiceRest() {
	
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			reportService = (IReportServiceLocal) ctx.lookup("java:global/" + getAppName() +  "/YuberServices/ReportService!tsi2.yuber.services.IReportServiceLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GET
	@Path(value="/report1")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportUsuariosProveedores(){
		String result = null;
		
		try {
			
			List<DataReportUsuarioProveedor> dataList = reportService.reportUsuarioProveedores();
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	} 
	
	
	@GET
	@Path(value="/report2/{verticalName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportGananciaMensual(@PathParam("verticalName") String verticalName){
		String result = null;
		
		try {
			
			List<DataReportGananciaMensual> dataList = reportService.reportGananciaMensual(verticalName);
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path(value="/report3/{verticalName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportProveedoresReviews(@PathParam("verticalName") String verticalName){
		String result = null;
		
		try {
			
			List<DataReportReviews> dataList = reportService.reportProveedoresReviews(verticalName);
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
		
	
	@GET
	@Path(value="/report4/{verticalName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportProveedoresGanancias(@PathParam("verticalName") String verticalName){
		String result = null;
		
		try {
			
			List<DataReportServicios> dataList = reportService.reportProveedoresGanancias(verticalName);
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	
	@GET
	@Path(value="/report5/{verticalName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportUsuariosCantidadServicios(@PathParam("verticalName") String verticalName){
		String result = null;
		
		try {
			
			List<DataReportServicios> dataList = reportService.reportUsuariosCantidadServicios(verticalName);
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path(value="/report6/{verticalName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportUsuariosReviews(@PathParam("verticalName") String verticalName){
		String result = null;
		
		try {
			
			List<DataReportReviews> dataList = reportService.reportUsuariosReviews(verticalName);
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path(value="/user/{verticalName}/{userName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportServicesByUser(@PathParam("verticalName") String verticalName, @PathParam("userName") String userName){
		String result = null;
		
		try {
			
			List<Servicio> dataList = reportService.reportServicesByUser(verticalName, userName);
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	
	@GET
	@Path(value="/proveedor/{verticalName}/{proveedorName}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response reportServicesByProveedor(@PathParam("verticalName") String verticalName, @PathParam("proveedorName") String proveedorName){
		String result = null;
		
		try {
			
			List<Servicio> dataList = reportService.reportServicesByProveedor(verticalName, proveedorName);
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	
}
