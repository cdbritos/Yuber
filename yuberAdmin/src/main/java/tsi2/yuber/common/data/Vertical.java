package tsi2.yuber.common.data;

import java.io.Serializable;
import java.util.Date;

import tsi.tuber.admin.enums.TipoVerticalEnum;

public class Vertical implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private TipoVerticalEnum tipoVertical = TipoVerticalEnum.TRANSPORT;
	
	private String verticalName;
	
	private Double tarifaBase;
	
	private Double precio;
	
	private Date fechaCreacion;
	
	private String urlLogo;
	
	private String color;
	
	private String urlWeb;

	public TipoVerticalEnum getTipoVertical() {
		return tipoVertical;
	}

	public void setTipoVertical(TipoVerticalEnum tipoVertical) {
		this.tipoVertical = tipoVertical;
	}

	public String getVerticalName() {
		return verticalName;
	}

	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}

	public Double getTarifaBase() {
		return tarifaBase;
	}

	public void setTarifaBase(Double tarifaBase) {
		this.tarifaBase = tarifaBase;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUrlWeb() {
		return urlWeb;
	}

	public void setUrlWeb(String urlWeb) {
		this.urlWeb = urlWeb;
	}
	
	

}
