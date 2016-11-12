package tsi2.yuber.model.dataReport;

public class DataReportServicios extends DataReport{

	private String name;
	private Double gastos;
	private Integer cantidadServicios;


	public DataReportServicios(String name, Double gastos, Long cantidadServicios) {
		super();
		this.name = name;
		this.gastos = gastos;
		this.cantidadServicios = cantidadServicios.intValue();
	}
	
	public DataReportServicios(String name, Double gastos, Integer cantidadServicios) {
		super();
		this.name = name;
		this.gastos = gastos;
		this.cantidadServicios = cantidadServicios;
	}
	
	public String getCliente() {
		return name;
	}
	public void setCliente(String cliente) {
		this.name = cliente;
	}
	public Double getGastos() {
		return gastos;
	}
	public void setGastos(Double gastos) {
		this.gastos = gastos;
	}
	public Integer getCantidadServicios() {
		return cantidadServicios;
	}
	public void setCantidadServicios(Integer cantidadServicios) {
		this.cantidadServicios = cantidadServicios;
	}	
	
}
