package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.entities.Proveedor;

public interface IProveedorCommonServiceLocal {

	public void saveProveedor(String verticalName, Proveedor proveedor);
	
	public Proveedor findProveedor(String verticalName, Proveedor proveedor);
	
	public List<Proveedor> findAllProveedor(String verticalName);
	
	public Integer getCount(String verticalName);
	
}
