package tsi2.yuber.common.data;

import java.io.Serializable;

import tsi2.yuber.admin.enums.TipoVerticalEnum;
import tsi2.yuber.common.enums.TipoReporte;

public class Report implements Serializable{

	private static final long serialVersionUID = -411004013285015368L;
	
	private String verticalName;
	private TipoVerticalEnum tipoVertical = TipoVerticalEnum.TRANSPORT;
	private TipoReporte tipoReporte = TipoReporte.REPORTE_USUARIOS_ACTIVOS;

	public String getVerticalName() {
		return verticalName;
	}
	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}
	public TipoVerticalEnum getTipoVertical() {
		return tipoVertical;
	}
	public void setTipoVertical(TipoVerticalEnum tipoVertical) {
		this.tipoVertical = tipoVertical;
	}
	public TipoReporte getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(TipoReporte tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
}
