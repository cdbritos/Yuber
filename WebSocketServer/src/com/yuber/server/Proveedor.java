package com.yuber.server;

import javax.json.JsonNumber;

public class Proveedor {
	private String sessionId;
	private String userName;
	private JsonNumber lat;
	private JsonNumber lng;
	private String clientePendienteMatchId;
	private String telefono;
	
	
	
	
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getClientePendienteMatchId() {
		return clientePendienteMatchId;
	}
	public void setClientePendienteMatchId(String clientePendienteMatchId) {
		this.clientePendienteMatchId = clientePendienteMatchId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public JsonNumber getLat() {
		return lat;
	}
	public void setLat(JsonNumber lat) {
		this.lat = lat;
	}
	public JsonNumber getLng() {
		return lng;
	}
	public void setLng(JsonNumber lng) {
		this.lng = lng;
	}
	public Proveedor(String sessionId, String userName, JsonNumber lat, JsonNumber lng) {
		super();
		this.sessionId = sessionId;
		this.userName = userName;
		this.lat = lat;
		this.lng = lng;
		this.clientePendienteMatchId = "";
		this.telefono ="";
	}
	
	

	public Proveedor(String sessionId, String userName, JsonNumber lat, JsonNumber lng, String clientePendienteMatchId,
			String telefono) {
		super();
		this.sessionId = sessionId;
		this.userName = userName;
		this.lat = lat;
		this.lng = lng;
		this.clientePendienteMatchId = clientePendienteMatchId;
		this.telefono = telefono;
	}
	@Override
	public String toString() {
		return "Proveedor [sessionId=" + sessionId + ", userName=" + userName + ", lat=" + lat + ", lng=" + lng
				+ ", clientePendienteMatchId=" + clientePendienteMatchId + ", telefono=" + telefono + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
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
		Proveedor other = (Proveedor) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}
	
	
	
	
}
