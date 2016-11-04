package com.yuber.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.json.JsonNumber;


public class Match {
	private String proveedorId;
	private String clienteId;
	private String status;
	private Boolean clienteAccept;
	private Boolean proveedorAccept;
	private Date startTime;
	private Date finishTime;
	private int reviewProveedor;
	private int reviewCliente;
	private JsonNumber startLat;
	private JsonNumber startLng;
	private JsonNumber finishLat;
	private JsonNumber finishLng;
	private Double cost;
	private long duration;
	private Double disTotal;
	
	
	public Double getDisTotal() {
		return disTotal;
	}

	public void setDisTotal(Double disTotal) {
		this.disTotal = disTotal;
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

	public void setDuration(long tiempoServicio) {
		this.duration = tiempoServicio;
	}
	List<Position> positions = new LinkedList<Position>();
	
	public void addPosition(JsonNumber lat, JsonNumber lng){
		Position pos = new Position(lat,lng);
		positions.add(pos);
	}
	
	public void clearPostions(){
		positions.clear();
	}
	
	
	
	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public JsonNumber getStartLat() {
		return startLat;
	}
	public void setStartLat(JsonNumber startLat) {
		this.startLat = startLat;
	}
	public JsonNumber getStartLng() {
		return startLng;
	}
	public void setStartLng(JsonNumber startLng) {
		this.startLng = startLng;
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
	public Integer getReviewProveedor() {
		return reviewProveedor;
	}
	public void setReviewProveedor(int reviewProveedor) {
		this.reviewProveedor = reviewProveedor;
	}
	public Integer getReviewCliente() {
		return reviewCliente;
	}
	public void setReviewCliente(int reviewCliente) {
		this.reviewCliente = reviewCliente;
	}
	public JsonNumber getFinishLat() {
		return finishLat;
	}
	public void setFinishLat(JsonNumber finishLat) {
		this.finishLat = finishLat;
	}
	public JsonNumber getFinishLng() {
		return finishLng;
	}
	public void setFinishLng(JsonNumber finishLng) {
		this.finishLng = finishLng;
	}
	public String getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(String proveedorId) {
		this.proveedorId = proveedorId;
	}
	public String getClienteId() {
		return clienteId;
	}
	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getClienteAccept() {
		return clienteAccept;
	}
	public void setClienteAccept(Boolean clienteAccept) {
		this.clienteAccept = clienteAccept;
	}
	public Boolean getProveedorAccept() {
		return proveedorAccept;
	}
	public void setProveedorAccept(Boolean proveedorAccept) {
		this.proveedorAccept = proveedorAccept;
	}
	public Match(String proveedorId, String clienteId, String status, Boolean clienteAccept, Boolean proveedorAccept) {
		super();
		this.proveedorId = proveedorId;
		this.clienteId = clienteId;
		this.status = status;
		this.clienteAccept = clienteAccept;
		this.proveedorAccept = proveedorAccept;
		this.startTime= null;
		this.finishTime = null;
		this.reviewProveedor =0;
		this.reviewCliente = 0;
		this.finishLat = null;
		this.finishLng = null;
	}
	
	

	

	@Override
	public String toString() {
		return "Match [proveedorId=" + proveedorId + ", clienteId=" + clienteId + ", status=" + status
				+ ", clienteAccept=" + clienteAccept + ", proveedorAccept=" + proveedorAccept + ", startTime="
				+ startTime + ", finishTime=" + finishTime + ", reviewProveedor=" + reviewProveedor + ", reviewCliente="
				+ reviewCliente + ", startLat=" + startLat + ", startLng=" + startLng + ", finishLat=" + finishLat
				+ ", finishLng=" + finishLng + ", cost=" + cost + ", duration=" + duration + ", disTotal=" + disTotal
				+ ", positions=" + positions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
		result = prime * result + ((proveedorId == null) ? 0 : proveedorId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (clienteId == null) {
			if (other.clienteId != null)
				return false;
		} else if (!clienteId.equals(other.clienteId))
			return false;
		if (proveedorId == null) {
			if (other.proveedorId != null)
				return false;
		} else if (!proveedorId.equals(other.proveedorId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
	
}
