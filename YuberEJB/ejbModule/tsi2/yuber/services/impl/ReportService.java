package tsi2.yuber.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import tsi2.yuber.model.dataReport.DataReportGananciaMensual;
import tsi2.yuber.model.dataReport.DataReportProveedoresGanancias;
import tsi2.yuber.model.dataReport.DataReportProveedoresReviews;
import tsi2.yuber.model.dataReport.DataReportUsuarioProveedor;
import tsi2.yuber.model.dataReport.DataReportUsuariosCantidadServicios;
import tsi2.yuber.model.dataReport.DataReportUsuariosReviews;
import tsi2.yuber.model.entities.Vertical;
import tsi2.yuber.services.IProveedorCommonServiceLocal;
import tsi2.yuber.services.IReportServiceLocal;
import tsi2.yuber.services.IServiciosServiceLocal;
import tsi2.yuber.services.IUserCommonServiceLocal;
import tsi2.yuber.services.IVerticalServiceLocal;

@Stateless
@Local(IReportServiceLocal.class)
@LocalBean
public class ReportService extends AbstractService implements IReportServiceLocal {

	
	@EJB
	IVerticalServiceLocal verticalService;
	
	@EJB
	IUserCommonServiceLocal userService;
	
	@EJB
	IProveedorCommonServiceLocal proveedorService;
	
	@EJB
	IServiciosServiceLocal servicioService;
		
	public ReportService() {
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS) 
	public List<DataReportUsuarioProveedor> reportUsuarioProveedores() {
		
		List<DataReportUsuarioProveedor> dataList = new ArrayList<DataReportUsuarioProveedor>();
		List<Vertical> verticales = verticalService.findAll();
		
		if (verticales != null){
			for (Vertical vertical : verticales) {
				DataReportUsuarioProveedor dataItem = new DataReportUsuarioProveedor();
				dataItem.setVerticalName(vertical.getVerticalName());
				dataItem.setCantidadUsuarios(userService.findAllUser(vertical.getVerticalName()).size());
				dataItem.setCantidadProveedores(proveedorService.findAllProveedor(vertical.getVerticalName()).size());
				dataList.add(dataItem);
			}
		}
		
		return dataList;
	}

	/*
	 * 
	 select ano, mes, sum(ganancia) as ganancia from (
  		select EXTRACT(YEAR FROM dt_transferencia_solicitud) as ano, EXTRACT(MONTH FROM dt_transferencia_solicitud) as mes, n_transferencia_devengado as ganancia from ENT_TRANSFERENCIA
		)
	group by 
  		ano, mes
	order by 
  		ano desc, mes desc

	 * 
	 * 
	 * */
	
	@Override
	public List<DataReportGananciaMensual> reportGananciaMensual(String vertical) {
		List<DataReportGananciaMensual> dataList = new ArrayList<DataReportGananciaMensual>();
		return dataList;
	}
	
	/*
	select proveedor, sum(ganancia) as renta from (
			  select id_empresa as proveedor, n_transferencia_devengado as ganancia from ENT_TRANSFERENCIA
			)
			group by 
			  proveedor
			order by 
			  renta desc
	*/
	
	@Override
	public List<DataReportProveedoresReviews> reportProveedoresReviews(String vertical) {
		List<DataReportProveedoresReviews> dataList = new ArrayList<DataReportProveedoresReviews>();
		return dataList;
	}

	
	/*
	 		select proveedor, sum(ganancia) as renta from (
			  select id_empresa as proveedor, n_transferencia_devengado as ganancia from ENT_TRANSFERENCIA
			)
			group by 
			  proveedor
			order by 
			  renta desc
	*/
	
	@Override
	public List<DataReportProveedoresGanancias> reportProveedoresGanancias(String vertical) {
		List<DataReportProveedoresGanancias> dataList = new ArrayList<DataReportProveedoresGanancias>();
		return dataList;
	}

	@Override
	public List<DataReportUsuariosCantidadServicios> reportUsuariosCantidadServicios(String vertical) {
		List<DataReportUsuariosCantidadServicios> dataList = new ArrayList<DataReportUsuariosCantidadServicios>();
		return dataList;
	}

	@Override
	public List<DataReportUsuariosReviews> reportUsuariosReviews(String vertical) {
		List<DataReportUsuariosReviews> dataList = new ArrayList<DataReportUsuariosReviews>();
		return dataList;
	}
	
	
}

