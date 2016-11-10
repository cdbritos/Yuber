package tsi2.yuber.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

@Entity
@Table(name="vertical")
public class Vertical implements Serializable {
	
	private static final long serialVersionUID = 1889826641961523824L;

	@Id
	private String verticalName;
	
	@Column
	private Double tarifaBase;
	
	@Column
	private Double precio;
	
	@Column 
	private Date fechaCreacion;
	
	@Column
	private String urlLogo;
	
	@Column
	private String color;
	
	@Column
	private String urlWeb;

	public Vertical() {
		super();
	}
	
	
	public Vertical(HttpServletRequest request){
		this.verticalName = request.getParameter("name");
		this.tarifaBase = Double.valueOf(request.getParameter("tarifaBase"));
		this.precio = Double.valueOf(request.getParameter("precio"));
		this.color = request.getParameter("color");
		this.urlLogo = request.getParameter("urlLogo");
		this.urlWeb = request.getParameter("urlWeb");
	}
	
	public Vertical(String verticalName, Double tarifaBase, Double precio, Date fechaCreacion, String urlLogo, String color, String urlWeb) {
		this.verticalName = verticalName;
		this.tarifaBase = tarifaBase;
		this.precio = precio;
		this.fechaCreacion = fechaCreacion;
		this.urlLogo = urlLogo;
		this.color = color;
		this.urlWeb = urlWeb;
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


