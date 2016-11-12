package tsi2.yuber.services.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import tsi2.yuber.model.entities.Proveedor;
import tsi2.yuber.services.IProveedorCommonServiceLocal;

@Stateless
@Local(IProveedorCommonServiceLocal.class)
@LocalBean
public class ProveedorCommonService extends AbstractService implements IProveedorCommonServiceLocal {

		
	public ProveedorCommonService() {
		
	}

	@Override
	public void saveProveedor(String verticalName, Proveedor proveedor) {
		getEntityManager(verticalName).persist(proveedor);
	}

	@Override
	public Proveedor findProveedor(String verticalName, Proveedor proveedor) {
		Proveedor proveedorDB = getEntityManager(verticalName).find(Proveedor.class, proveedor.getUserName());
		if (proveedorDB.getPassword().equals(proveedor.getPassword()))
			return proveedorDB;
		else return null;
	}

	@Override
	public List<Proveedor> findAllProveedor(String verticalName) {
		String q = "SELECT u from " + Proveedor.class.getName() + " u";
		
	    Query query = getEntityManager(verticalName).createQuery(q);
	    List<Proveedor> proveedores = query.getResultList();
		return proveedores;
	}
	
	@Override
	public Integer getCount(String verticalName) {
		String q = "SELECT count(t) from " + Proveedor.class.getName() + " t";
		
		Query query = getEntityManager(verticalName).createQuery(q);
				
		return ((Long) query.getSingleResult()).intValue();
	}

	@Override
	public void populate(String verticalName) {
		for (int j=0; j<10; j++){
			Proveedor p = new Proveedor("proveedor"+j, "nombre"+j, "apellido"+j, "telefono"+j, "direccion"+j, "password"+j);
			saveProveedor(verticalName, p);
		}
	}
}

