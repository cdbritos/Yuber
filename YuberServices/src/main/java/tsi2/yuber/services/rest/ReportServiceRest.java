package tsi2.yuber.services.rest;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import tsi2.yuber.model.dataReport.DataReport1;
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
	public Response report1(){
		String result = null;
		
		try {
			
			List<DataReport1> dataList = reportService.createReport1();
						
			result = new Gson().toJson(dataList);
			
			return Response.status(Status.OK).entity(result).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
	} 
	
}
