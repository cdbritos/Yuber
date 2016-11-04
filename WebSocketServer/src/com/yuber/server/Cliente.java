package com.yuber.server;

import javax.json.JsonNumber;

public class Cliente {
	private String sessionId;
	private String userName;
	private JsonNumber lat;
	private JsonNumber lng;
	private String address;
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Cliente(String sessionId, String userName, JsonNumber lat, JsonNumber lng,String address) {
		super();
		this.sessionId = sessionId;
		this.userName = userName;
		this.lat = lat;
		this.lng = lng;
		this.address = address;
	}
	@Override
	public String toString() {
		return "Cliente [sessionId=" + sessionId + ", userName=" + userName + ", lat=" + lat + ", lng=" + lng + "]";
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
		Cliente other = (Cliente) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}
	
	
}
