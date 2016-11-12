package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.entities.Servicio;;

public interface IServiciosServiceLocal {
	public void saveServicio(Servicio serv,String Vertical);
	
	public Servicio find(String verticalName, Servicio serv);
	
	public List<Servicio> findAll(String verticalName);
	
	public Integer getCount(String verticalName);
}
