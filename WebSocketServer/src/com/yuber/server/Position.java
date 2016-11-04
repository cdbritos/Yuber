package com.yuber.server;

import javax.json.JsonNumber;

public class Position {
	private JsonNumber lat;
	private JsonNumber lng;
	
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
	
	public Position(JsonNumber lat, JsonNumber lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "Position [lat=" + lat + ", lng=" + lng + "]";
	}
	
	
	
	
	
	
}
