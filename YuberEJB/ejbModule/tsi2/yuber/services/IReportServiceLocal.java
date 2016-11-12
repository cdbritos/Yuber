package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.dataReport.DataReportGananciaMensual;
import tsi2.yuber.model.dataReport.DataReportReviews;
import tsi2.yuber.model.dataReport.DataReportServicios;
import tsi2.yuber.model.dataReport.DataReportUsuarioProveedor;

public interface IReportServiceLocal {

	public List<DataReportUsuarioProveedor> reportUsuarioProveedores();
	
	public List<DataReportGananciaMensual> reportGananciaMensual(String vertical);
	
	public List<DataReportReviews> reportProveedoresReviews(String vertical);
	
	public List<DataReportServicios> reportProveedoresGanancias(String vertical);
	
	public List<DataReportServicios> reportUsuariosCantidadServicios(String vertical);
	
	public List<DataReportReviews> reportUsuariosReviews(String vertical);

}
