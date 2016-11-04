package tsi2.yuber.services.rest;


import java.io.IOException;
import java.io.OutputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import tsi2.yuber.report.Bar3DChartReport;
import tsi2.yuber.services.IReportServiceLocal;

@Path("/report")
public class ReportServiceRest extends AbstractServiceRest{

	IReportServiceLocal reportService;
		
	public ReportServiceRest() {
	
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			reportService = (IReportServiceLocal) ctx.lookup("java:global/" + getAppName() + "/YuberEJB/ReportService!tsi2.yuber.services.IReportServiceLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@GET
	@Path(value="/1")
	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	public Response createReport1(){
	
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				new Bar3DChartReport(output);
			}
		};
            
		try {
			return Response.ok(stream).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
	} 

}
