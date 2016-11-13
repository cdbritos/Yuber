package tsi2.yuber.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudService;

import com.google.gson.Gson;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/VerticalServlet")
public class VerticalServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	@Resource private UserTransaction userTrx;
	
	@PersistenceContext(unitName = "yuberAdmin")
	EntityManager em;
    
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String user = request.getParameter("userBluemix");
		String password = request.getParameter("passBluemix");
		String orgName = request.getParameter("orgBluemix");
		String spaceName = request.getParameter("spaceBluemix");

		// login
		CloudCredentials credentials = new CloudCredentials(user, password);
		URL target = URI.create("https://api.ng.bluemix.net").toURL();
		CloudFoundryClient client = new CloudFoundryClient(credentials, target, orgName, spaceName);
		client.login();

		String appBackendName = "yuberBackend" + request.getParameter("tipoVerticalId");
		String serviceName = "yuberDB" + request.getParameter("tipoVerticalId") + "_"  + request.getParameter("name");
		
		// agrego vertical a tabla de backend
		HttpClient clientRest = HttpClientBuilder.create().build();
		HttpPost requestRest = new HttpPost("http://"+ appBackendName + ".mybluemix.net/YuberServices/rest/vertical/new" );
		
		Vertical vertical = new Vertical(request);
		StringEntity strEntity = new StringEntity(new Gson().toJson(vertical));
		requestRest.setEntity(strEntity);
		requestRest.setHeader("Content-type", "application/json");
		clientRest.execute(requestRest);
		
		//creo servicio
		CloudService service = new CloudService();
		service.setLabel("compose-for-postgresql");
		service.setName(serviceName);
		service.setPlan("Standard");

		client.createService(service);
		
		client.bindService(appBackendName, serviceName);
		
		
			
		
	
		System.out.println("START"); 
    	
		client.uploadApplication(appBackendName	, getServletConfig().getServletContext().getRealPath("WEB-INF") + "/apps/YuberBackend.ear");
		client.restartApplication(appBackendName);
		
    	// invocar via rest backend, para crear base de datos para la administracion de la vertical
		// invocar via rest backend, para agregar nueva vertical en la base del backend

    	printResults(out);
	
    }
    
    private void printResults(PrintWriter out) {
		String title = "Database Population Results";

		out.println(title);
		out.println("</TITLE></HEAD><body><div class = 'container'>");
		out.println("<H1 align=\"center\">" + title + "</H1>");
		out.println("<BR><BR><BR>");

		out.println("<div align = 'center'><B>Vertical Creada: Successful</B></div>");

		out.println("</div>");
	}	

    protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

    class Vertical{
    	
    	private String verticalName;
    	private Double tarifaBase;
    	private Double precio;
    	private Date fechaCreacion;
    	private String urlLogo;
    	private String color;
    	private String urlWeb;
    	
    	public Vertical(HttpServletRequest request){
    		this.verticalName = request.getParameter("name");
    		this.tarifaBase = Double.valueOf(request.getParameter("tarifaBase"));
    		this.precio = Double.valueOf(request.getParameter("precio"));
    		this.color = request.getParameter("color");
    		this.urlLogo = request.getParameter("urlLogo");
    		this.urlWeb = request.getParameter("urlWeb");
    	}

		public String getVerticalName() {
			return verticalName;
		}

		public void setVerticalName(String verticalName) {
			this.verticalName = verticalName;
		}

		public Double getTarifaBase() {
			return tarifaBase;
		}

		public void setTarifaBase(Double tarifaBase) {
			this.tarifaBase = tarifaBase;
		}

		public Double getPrecio() {
			return precio;
		}

		public void setPrecio(Double precio) {
			this.precio = precio;
		}

		public Date getFechaCreacion() {
			return fechaCreacion;
		}

		public void setFechaCreacion(Date fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}

		public String getUrlLogo() {
			return urlLogo;
		}

		public void setUrlLogo(String urlLogo) {
			this.urlLogo = urlLogo;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getUrlWeb() {
			return urlWeb;
		}

		public void setUrlWeb(String urlWeb) {
			this.urlWeb = urlWeb;
		}
    	
    	
    	
    }
}
