package tsi2.yuber.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import tsi2.yuber.model.entities.Proveedor;
import tsi2.yuber.model.entities.Servicio;
import tsi2.yuber.model.entities.User;
import tsi2.yuber.model.entities.Vertical;
import tsi2.yuber.services.IProveedorCommonServiceLocal;
import tsi2.yuber.services.IServiciosServiceLocal;
import tsi2.yuber.services.IUserCommonServiceLocal;
import tsi2.yuber.services.IVerticalServiceLocal;

@Stateless
@Local(IVerticalServiceLocal.class)
@LocalBean
public class VerticalService extends AbstractService implements IVerticalServiceLocal {
	
	@EJB
	IUserCommonServiceLocal userService;
	
	@EJB
	IProveedorCommonServiceLocal proveedorService;
		
	@EJB
	IServiciosServiceLocal servicioService;
	
	public VerticalService() {
		
	}
		
	
	@Override
	public List<Vertical> findAll() {
		String q = "SELECT u from " + Vertical.class.getName() + " u";
		
	    Query query = getEntityManager().createQuery(q);
	    List<Vertical> verticales = query.getResultList();
		return verticales;
	}

	@Override
	public void saveVertical(Vertical vertical) {
		getEntityManager().persist(vertical);
	}

	
	private void populateUsers(String verticalName) {
		userService.populate(verticalName);
	}

	
	private void populateProveedores(String verticalName) {
		proveedorService.populate(verticalName);
	}

	@Override
	public void populateVerticalServices(String vertical) {
		
		List<User> users = userService.findAllUser(vertical);
		List<Proveedor> proveedores = proveedorService.findAllProveedor(vertical);
		
		for (int i = 0; i <100; i++){
			String u = users.get(i % users.size()).getUserName();
			String p = proveedores.get(i % proveedores.size()).getUserName();
			Calendar gc = GregorianCalendar.getInstance();
			gc.setTime(new Date());
			gc.set(Calendar.MONTH, i % 12);
			Date st = gc.getTime();
			Date ft = gc.getTime();
			
			Servicio servicio = new Servicio(p, u, "prueba", 
											 st, ft, new Random().nextInt(5), 
											 new Random().nextInt(5), new Random().nextDouble() * new Random().nextInt(100),
											 10, null);
			servicioService.saveServicio(servicio, vertical);
			System.out.println(servicio.getCliente() + "#" + servicio.getProveedor() + "#" + servicio.getReviewCliente() + "#" + servicio.getReviewProveedor());
		}
	}

	@Override
	public void populateVerticalUsers(String verticalName) {
		
		populateUsers(verticalName);
		
		populateProveedores(verticalName);
		
	}

	
}

