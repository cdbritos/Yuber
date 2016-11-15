package tsi2.yuber.common.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.ApplicationStats;
import org.cloudfoundry.client.lib.domain.CloudService;

import com.google.gson.Gson;

import tsi2.yuber.admin.entities.Administrador;
import tsi2.yuber.common.data.Vertical;
import tsi2.yuber.common.exception.DataBaseCreationException;
import tsi2.yuber.common.exception.ServiceException;
import tsi2.yuber.common.services.Facade.ICommonService;

@Stateless
@Local(value=ICommonService.class)
@LocalBean
public class CommonService implements ICommonService {

	@PersistenceContext(unitName="yuberAdmin")
	EntityManager em;
	
		
	@Override
	public Administrador doLogin(String name, String password) throws ServiceException {
			
		Administrador admin = em.find(Administrador.class, name);
		
		if (admin == null)
			throw new ServiceException("No existe administrador");
		
		if (admin.getPassword().equals(password))
			return admin;
		
		throw new ServiceException("Password Invalido");
	}


	@Override
	public void doLogout() throws ServiceException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doAltaAdministrador(Administrador admin) throws ServiceException {
		
		try {
			em.persist(admin);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
	}


	@Override
	public void doAltaVertical(Vertical vertical,String userBluemix, String passBluemix, String spaceBluemix, String orgBluemix, ServletContext context) throws ServiceException, DataBaseCreationException {
		
		CloudCredentials credentials = new CloudCredentials(userBluemix, passBluemix);
		
		URL target;
		try {
			target = URI.create("https://api.ng.bluemix.net").toURL();
		} catch (MalformedURLException e) {
			throw new ServiceException(e.getMessage());
		}
		
		CloudFoundryClient client = new CloudFoundryClient(credentials, target, orgBluemix, spaceBluemix);
		
		client.login();

		String appBackendName = "yyuberBackend" + vertical.getTipoVertical().getId();
		String serviceName = "yyuberDB" + vertical.getTipoVertical().getId() + "_"  + vertical.getVerticalName();
		
		// agrego vertical a tabla de backend
		HttpClient clientRest = HttpClientBuilder.create().build();
		HttpPost requestRest = new HttpPost("http://"+ appBackendName + ".mybluemix.net/YuberServices/rest/vertical/new" );
		
		StringEntity strEntity = null;
		try {
			strEntity = new StringEntity(new Gson().toJson(vertical));
		} catch (UnsupportedEncodingException e) {
			throw new ServiceException(e.getMessage());
		}finally {
			client.logout();
		}
		
		requestRest.setEntity(strEntity);
		requestRest.setHeader("Content-type", "application/json");
		try {
			HttpResponse respRest = clientRest.execute(requestRest);
			
			System.out.println(respRest.getStatusLine().getStatusCode());
			
			if (Status.OK.getStatusCode() != respRest.getStatusLine().getStatusCode())
				throw new ServiceException("No se pudo agregar Vertical a Base de datos del Backend");
				
		} catch (ClientProtocolException e) {
			throw new ServiceException(e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}finally {
			client.logout();
		}
		
		//creo servicio
		CloudService service = new CloudService();
		service.setLabel("compose-for-postgresql");
		service.setName(serviceName);
		service.setPlan("Standard");

		client.createService(service);
		
		client.bindService(appBackendName, serviceName);
		
		try {
			client.uploadApplication(appBackendName	, context.getRealPath("WEB-INF") + "/apps/YuberBackend.ear");
			client.restartApplication(appBackendName);
			
		} catch (IOException e) {
			//ACA DEBERIA eliminar el servicio que cree recien
			//ACA DEBERIA eliminar de la base el registro de la vertical nueva
			client.logout();
			throw new ServiceException(e.getMessage());
		}
		
		
		try {
			HttpGet requestCreateDataBase = new HttpGet("http://"+ appBackendName + ".mybluemix.net/YuberServices/rest/vertical/createDatabase/" + vertical.getVerticalName() );
			System.out.println("http://"+ appBackendName + ".mybluemix.net/YuberServices/rest/vertical/createDatabase/" + vertical.getVerticalName() );
			HttpResponse respRest = clientRest.execute(requestCreateDataBase);
			System.out.println(respRest.getStatusLine().getStatusCode());
			if (Status.OK.getStatusCode() != respRest.getStatusLine().getStatusCode())
				throw new DataBaseCreationException("No se pudo crear la base de datos para la nueva vertical");
			
		} catch (ClientProtocolException e) {
			client.logout();
			throw new DataBaseCreationException("No se pudo crear la base de datos para la nueva vertical");
		} catch (IOException e) {
			client.logout();
			throw new DataBaseCreationException("No se pudo crear la base de datos para la nueva vertical");
		}
		
		client.logout();
		
	}


	@Override
	public void doCreateDataBase(Vertical vertical) throws DataBaseCreationException {
		
		String appBackendName = "yyuberBackend" + vertical.getTipoVertical().getId();
		
		try {
			HttpClient clientRest = HttpClientBuilder.create().build();
			System.out.println("http://"+ appBackendName + ".mybluemix.net/YuberServices/rest/vertical/createDatabase/" + vertical.getVerticalName() );
			
			HttpGet requestCreateDataBase = new HttpGet("http://"+ appBackendName + ".mybluemix.net/YuberServices/rest/vertical/createDatabase/" + vertical.getVerticalName() );
			
			HttpResponse respRest = clientRest.execute(requestCreateDataBase);
			System.out.println(respRest.getStatusLine().getStatusCode());

			if (Status.OK.getStatusCode() != respRest.getStatusLine().getStatusCode())
				throw new DataBaseCreationException("No se pudo crear la base de datos para la nueva vertical");
			
		} catch (ClientProtocolException e) {
			throw new DataBaseCreationException("No se pudo crear la base de datos para la nueva vertical");
		} catch (IOException e) {
			throw new DataBaseCreationException("No se pudo crear la base de datos para la nueva vertical");
		}
		
	}


}
