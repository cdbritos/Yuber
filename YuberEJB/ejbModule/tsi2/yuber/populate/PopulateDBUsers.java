package tsi2.yuber.populate;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import tsi2.yuber.model.entities.Proveedor;
import tsi2.yuber.model.entities.User;
import tsi2.yuber.services.IProveedorCommonServiceLocal;
import tsi2.yuber.services.IUserCommonServiceLocal;

//@Startup
@Singleton
public class PopulateDBUsers extends PopulateService{
	
	@EJB
	IUserCommonServiceLocal userService;
	
	@EJB
	IProveedorCommonServiceLocal proveedorService;
		
	@PostConstruct
	private void initDB(){
		
		String vertical = getVertical();
		
		for (int j=0; j<10; j++){
			User u = new User("cliente"+j, "nombre"+j, "apellido"+j, "telefono"+j, "direccion"+j, "password"+j);
			userService.saveUser(vertical, u);
		}
		
		for (int j=0; j<10; j++){
			Proveedor p = new Proveedor("proveedor"+j, "nombre"+j, "apellido"+j, "telefono"+j, "direccion"+j, "password"+j);
			proveedorService.saveProveedor(vertical, p);
		}
	}

}
