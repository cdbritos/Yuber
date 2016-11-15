package tsi2.yuber.services.rest;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
import tsi2.yuber.services.util.VCAPUtils;
import tsi2.yuber.services.util.VerticalCabezal;



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
			result = new Gson().toJson(new VerticalCabezal(verticales));
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
			
			
			System.out.println("-------- PostgreSQL "
					+ "JDBC Connection Testing ------------");

			try {

				Class.forName("org.postgresql.Driver");

			} catch (ClassNotFoundException e) {

				System.out.println("Where is your PostgreSQL JDBC Driver? "
						+ "Include in your library path!");
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

			}

			System.out.println("PostgreSQL JDBC Driver Registered!");

			Connection connection = null;
			Statement stmt = null;
			try {
				String uri_jdbc = "jdbc:postgresql://" + VCAPUtils.getHostDBConnection(verticalName) +
						":" + VCAPUtils.getPortDBConnection(verticalName) + "/compose";
				
				System.out.println("CONECTANDO A: " + uri_jdbc);
						
				connection = DriverManager.getConnection(uri_jdbc,VCAPUtils.getUserDBConnection(verticalName), VCAPUtils.getPasswordDBConnection(verticalName));
						
				stmt = connection.createStatement();
							
				String dataBaseName = VCAPUtils.getDataBaseNameDBConnection(verticalName);
				
				String sql = "CREATE DATABASE " +  dataBaseName;
				System.out.println(sql);
				
				stmt.executeUpdate(sql);
				
				
			} catch (SQLException e) {

				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

			} finally{//finally block used to close resources
				if (connection != null) {
					System.out.println("You made it, take control your database now!");
				} else {
					System.out.println("Failed to make connection!");
				}

				
				try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(connection!=null)
			            connection.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			}

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
