package tsi2.yuber.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="servicios")
public class Servicio {

	@Id 
	@Column(name="servicioId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private short servicioId;
	
	@Column
	private String proveedor;
	@Column
	private String cliente;
	@Column
	private String status;
	@Column
	private Date startTime;
	@Column
	private Date finishTime;
	@Column
	private int reviewProveedor;
	@Column
	private int reviewCliente;
	@Column
	private Double cost;
	@Column
	private long duration;
	
	@Size(max = 500)
	@Column
	private String customData;

	
	
	
	public Servicio(String proveedor, String cliente, String status, Date startTime, Date finishTime,
			int reviewProveedor, int reviewCliente, Double cost, long duration, String customData) {
		super();
		this.proveedor = proveedor;
		this.cliente = cliente;
		this.status = status;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.reviewProveedor = reviewProveedor;
		this.reviewCliente = reviewCliente;
		this.cost = cost;
		this.duration = duration;
		this.customData = customData;
	}




	public short getServicioId() {
		return servicioId;
	}




	public void setServicioId(short servicioId) {
		this.servicioId = servicioId;
	}




	public String getProveedor() {
		return proveedor;
	}




	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}




	public String getCliente() {
		return cliente;
	}




	public void setCliente(String cliente) {
		this.cliente = cliente;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public Date getStartTime() {
		return startTime;
	}




	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}




	public Date getFinishTime() {
		return finishTime;
	}




	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}




	public int getReviewProveedor() {
		return reviewProveedor;
	}




	public void setReviewProveedor(int reviewProveedor) {
		this.reviewProveedor = reviewProveedor;
	}




	public int getReviewCliente() {
		return reviewCliente;
	}




	public void setReviewCliente(int reviewCliente) {
		this.reviewCliente = reviewCliente;
	}




	public Double getCost() {
		return cost;
	}




	public void setCost(Double cost) {
		this.cost = cost;
	}




	public long getDuration() {
		return duration;
	}




	public void setDuration(long duration) {
		this.duration = duration;
	}




	public String getCustomData() {
		return customData;
	}




	public void setCustomData(String customData) {
		this.customData = customData;
	}




	public Servicio() {
	
	}


	
	
}
