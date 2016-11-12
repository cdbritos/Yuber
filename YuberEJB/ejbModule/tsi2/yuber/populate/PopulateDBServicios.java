package tsi2.yuber.populate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import tsi2.yuber.model.entities.Proveedor;
import tsi2.yuber.model.entities.Servicio;
import tsi2.yuber.model.entities.User;
import tsi2.yuber.services.IProveedorCommonServiceLocal;
import tsi2.yuber.services.IServiciosServiceLocal;
import tsi2.yuber.services.IUserCommonServiceLocal;

//@Startup
@Singleton
@DependsOn("PopulateDBUsers")
public class PopulateDBServicios extends PopulateService{

	@EJB
	IUserCommonServiceLocal userService;
	
	@EJB
	IProveedorCommonServiceLocal proveedorService;
	
	@EJB
	IServiciosServiceLocal servicioService;
	
	@PostConstruct
	private void initDB(){
		
		String vertical = getVertical();
			
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
	
}
