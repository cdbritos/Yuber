package tsi2.yuber.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import tsi2.yuber.model.entities.Proveedor;
import tsi2.yuber.model.entities.Servicio;
import tsi2.yuber.services.IServiciosServiceLocal;

@Stateless
public class ServiciosService extends AbstractService implements IServiciosServiceLocal {

	@Override
	public void saveServicio(Servicio serv, String vertical) {
		// TODO Auto-generated method stub
		getEntityManager(vertical).persist(serv);
	}

	@Override
	public Servicio find(String vertical, Servicio serv) {
		// TODO Auto-generated method stub
		return getEntityManager(vertical).find(Servicio.class,serv.getServicioId());
	}

	@Override
	public List<Servicio> findAll(String verticalName) {
		String q = "SELECT u from " + Servicio.class.getName() + " u";
	    Query query = getEntityManager(verticalName).createQuery(q);
	    List<Servicio> servicios = query.getResultList();
		return servicios;
	}
	
	@Override
	public Integer getCount(String verticalName) {
		String q = "SELECT count(t) from " + Servicio.class.getName() + " t";
		
		Query query = getEntityManager(verticalName).createQuery(q);
				
		return ((Long) query.getSingleResult()).intValue();
	}

}
