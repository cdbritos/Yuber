package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.entities.Proveedor;
import tsi2.yuber.model.entities.Servicio;

public interface IProveedorCommonServiceLocal {

	public void saveProveedor(String verticalName, Proveedor proveedor);
	
	public Proveedor findProveedor(String verticalName, Proveedor proveedor);
	
	public Proveedor login(String verticalName, Proveedor proveedor);
	
	public List<Proveedor> findAllProveedor(String verticalName);
	
	public Integer getCount(String verticalName);
	
	public void populate(String verticalName);

	public List<Servicio> findServiciosProveedor(String vertical, String username);

	public Proveedor findProveedorByUsername(String verticalName, String username);
	
}
