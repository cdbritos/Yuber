package tsi2.yuber.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import tsi2.yuber.model.dataReport.DataReportGananciaMensual;
import tsi2.yuber.model.dataReport.DataReportReviews;
import tsi2.yuber.model.dataReport.DataReportServicios;
import tsi2.yuber.model.dataReport.DataReportUsuarioProveedor;
import tsi2.yuber.model.entities.Servicio;
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
				dataItem.setCantidadUsuarios(userService.getCount(vertical.getVerticalName()));
				dataItem.setCantidadProveedores(proveedorService.getCount(vertical.getVerticalName()));
				dataList.add(dataItem);
			}
		}
		
		return dataList;
	}

	@Override
	public List<DataReportGananciaMensual> reportGananciaMensual(String vertical) {
		List<DataReportGananciaMensual> dataList = new ArrayList<DataReportGananciaMensual>();
		
		String q = "select EXTRACT(YEAR FROM srv.startTime) as anio, EXTRACT(MONTH FROM srv.startTime) as mes, sum(srv.cost) as ganancia "
				+  "from " + Servicio.class.getName() +" srv"
				+ " group by anio, mes "
				+ " order by anio desc, mes desc";
		 
	    Query query = getEntityManager(vertical).createQuery(q);
	    
	    List<Object[]> dataResult = query.getResultList();
	    
	    for (Object[] object : dataResult) {
	    	DataReportGananciaMensual item = new DataReportGananciaMensual((Double)object[0], (Double)object[1], (Double)object[2]);
	    	dataList.add(item);
		}
	    
		return dataList;
	}
	
	
	@Override
	public List<DataReportReviews> reportProveedoresReviews(String vertical) {
		List<DataReportReviews> dataList = new ArrayList<DataReportReviews>();
		
		String q = "select srv.proveedor, avg(srv.reviewProveedor) as review, count(srv) as cantidadServicios "
				+  "from " + Servicio.class.getName() +" srv"
				+ " group by srv.proveedor"
				+ " order by review desc";
		 
	    Query query = getEntityManager(vertical).createQuery(q);
	    
	    List<Object[]> dataResult = query.getResultList();
	    
	    for (Object[] object : dataResult) {
	    	DataReportReviews item = new DataReportReviews((String)object[0], (Double)object[1], (Long)object[2]);
	    	dataList.add(item);
		}
	    
		return dataList;
		
	}
	
	@Override
	public List<DataReportServicios> reportProveedoresGanancias(String vertical) {
		List<DataReportServicios> dataList = new ArrayList<DataReportServicios>();
		
		String q = "select srv.proveedor, sum(srv.cost) as gastos, count(srv) as cantidadServicios "
				+  "from " + Servicio.class.getName() +" srv"
				+ " group by srv.proveedor"
				+ " order by cantidadServicios desc";
		 
	    Query query = getEntityManager(vertical).createQuery(q);
	    
	    List<Object[]> dataResult = query.getResultList();
	    
	    for (Object[] object : dataResult) {
	    	DataReportServicios item = new DataReportServicios((String)object[0], (Double)object[1], (Long)object[2]);
	    	dataList.add(item);
		}
	    
		return dataList;
	}

	@Override
	public List<DataReportServicios> reportUsuariosCantidadServicios(String vertical) {
		List<DataReportServicios> dataList = new ArrayList<DataReportServicios>();
			
		String q = "select srv.cliente, sum(srv.cost) as gastos, count(srv) as cantidadServicios "
				+  "from " + Servicio.class.getName() +" srv"
				+ " group by srv.cliente"
				+ " order by cantidadServicios desc";
		 
	    Query query = getEntityManager(vertical).createQuery(q);
	    
	    List<Object[]> dataResult = query.getResultList();
	    
	    for (Object[] object : dataResult) {
	    	DataReportServicios item = new DataReportServicios((String)object[0], (Double)object[1], (Long)object[2]);
	    	dataList.add(item);
		}
	    
		return dataList;
		
	}


	@Override
	public List<DataReportReviews> reportUsuariosReviews(String vertical) {
		List<DataReportReviews> dataList = new ArrayList<DataReportReviews>();
		
		String q = "select srv.cliente, avg(srv.reviewCliente) as review, count(srv) as cantidadServicios "
				+  "from " + Servicio.class.getName() +" srv"
				+ " group by srv.cliente"
				+ " order by review desc";
		 
	    Query query = getEntityManager(vertical).createQuery(q);
	    
	    List<Object[]> dataResult = query.getResultList();
	    
	    for (Object[] object : dataResult) {
	    	DataReportReviews item = new DataReportReviews((String)object[0], (Double)object[1], (Long)object[2]);
	    	dataList.add(item);
		}
	    
		return dataList;
	}
		
}

