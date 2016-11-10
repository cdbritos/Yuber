package tsi2.yuber.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import tsi2.yuber.model.dataReport.DataReport1;
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
	public List<DataReport1> createReport1() {
		
		List<DataReport1> dataList = new ArrayList<>();
		List<Vertical> verticales = verticalService.findAll();
		
		if (verticales != null){
			for (Vertical vertical : verticales) {
				DataReport1 dataItem = new DataReport1();
				dataItem.setVerticalName(vertical.getVerticalName());
				dataItem.setCantidadUsuarios(userService.findAllUser(vertical.getVerticalName()).size());
				dataItem.setCantidadProveedores(proveedorService.findAllProveedor(vertical.getVerticalName()).size());
				dataList.add(dataItem);
			}
		}
		
		return dataList;
	}
	
	
}

