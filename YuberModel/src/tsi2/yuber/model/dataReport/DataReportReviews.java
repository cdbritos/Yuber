package tsi2.yuber.model.dataReport;

public class DataReportReviews extends DataReport {

	private String name;
	private Double reviewPromedio;
	private Integer cantidadServicios;
	
	public DataReportReviews(String name, Double reviewPromedio, Integer cantidadServicios) {
		super();
		this.name = name;
		this.reviewPromedio = reviewPromedio;
		this.cantidadServicios = cantidadServicios;
	}
	
	public DataReportReviews(String name, Double reviewPromedio, Long cantidadServicios) {
		super();
		this.name = name;
		this.reviewPromedio = reviewPromedio;
		this.cantidadServicios = cantidadServicios.intValue();
	}
	
	
	public String getProveedor() {
		return name;
	}
	public void setProveedor(String proveedor) {
		this.name = proveedor;
	}
	public Double getReviewPromedio() {
		return reviewPromedio;
	}
	public void setReviewPromedio(Double reviewPromedio) {
		this.reviewPromedio = reviewPromedio;
	}
	public Integer getCantidadServicios() {
		return cantidadServicios;
	}
	public void setCantidadServicios(Integer cantidadServicios) {
		this.cantidadServicios = cantidadServicios;
	}
	
	
	
	
	
}
