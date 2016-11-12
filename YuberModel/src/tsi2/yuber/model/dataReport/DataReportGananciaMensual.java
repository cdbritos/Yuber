package tsi2.yuber.model.dataReport;

public class DataReportGananciaMensual extends DataReport {

	private String fecha;
	private Double ganancia;
	
	public DataReportGananciaMensual(String fecha, Double ganancia) {
		super();
		this.fecha = fecha;
		this.ganancia = ganancia;
	}
	
	public DataReportGananciaMensual(Double double1, Double double2, Double double3) {
		this.fecha= ""+double1.intValue()+ (String.valueOf(double2.intValue()).length() == 1 ? "0" :"") + String.valueOf(double2.intValue()) ; 
		this.ganancia=double3;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Double getGanancia() {
		return ganancia;
	}
	public void setGanancia(Double ganancia) {
		this.ganancia = ganancia;
	}

	
	
	
}
