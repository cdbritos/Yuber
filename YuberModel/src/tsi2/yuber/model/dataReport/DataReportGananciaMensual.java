package tsi2.yuber.model.dataReport;

public class DataReportGananciaMensual extends DataReport {

	private Integer anio;
	private Integer mes;
	private Double ganancia;
	
	public DataReportGananciaMensual(Integer anio, Integer mes, Double ganancia) {
		super();
		this.anio = anio;
		this.mes = mes;
		this.ganancia = ganancia;
	}
	
	public DataReportGananciaMensual(Double anio, Double mes, Double ganancia2) {
		this(anio.intValue(),mes.intValue(),ganancia2);
	}

	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Double getGanancia() {
		return ganancia;
	}
	public void setGanancia(Double ganancia) {
		this.ganancia = ganancia;
	}

	
	
	
}
