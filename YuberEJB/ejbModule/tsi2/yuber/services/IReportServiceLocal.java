package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.dataReport.DataReportGananciaMensual;
import tsi2.yuber.model.dataReport.DataReportProveedoresGanancias;
import tsi2.yuber.model.dataReport.DataReportProveedoresReviews;
import tsi2.yuber.model.dataReport.DataReportUsuarioProveedor;
import tsi2.yuber.model.dataReport.DataReportUsuariosCantidadServicios;
import tsi2.yuber.model.dataReport.DataReportUsuariosReviews;

public interface IReportServiceLocal {

	public List<DataReportUsuarioProveedor> reportUsuarioProveedores();
	
	public List<DataReportGananciaMensual> reportGananciaMensual(String vertical);
	
	public List<DataReportProveedoresReviews> reportProveedoresReviews(String vertical);
	
	public List<DataReportProveedoresGanancias> reportProveedoresGanancias(String vertical);
	
	public List<DataReportUsuariosCantidadServicios> reportUsuariosCantidadServicios(String vertical);
	
	public List<DataReportUsuariosReviews> reportUsuariosReviews(String vertical);

}
