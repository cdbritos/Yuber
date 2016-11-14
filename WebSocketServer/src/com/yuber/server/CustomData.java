package com.yuber.server;

import java.util.Date;
import java.util.List;

import javax.json.JsonNumber;

public class CustomData {
	private JsonNumber startLat;
	private JsonNumber startLng;
	private JsonNumber finishLat;
	private JsonNumber finishLng;
	private Double disTotal;
	List<Position> travel;
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
	public Double getDisTotal() {
		return disTotal;
	}
	public void setDisTotal(Double disTotal) {
		this.disTotal = disTotal;
	}
	public List<Position> getTravel() {
		return travel;
	}
	public void setTravel(List<Position> travel) {
		this.travel = travel;
	}
	public CustomData(JsonNumber startLat, JsonNumber startLng, JsonNumber finishLat, JsonNumber finishLng,
			Double disTotal, List<Position> travel) {
		super();
		this.startLat = startLat;
		this.startLng = startLng;
		this.finishLat = finishLat;
		this.finishLng = finishLng;
		this.disTotal = disTotal;
		this.travel = travel;
	}
	
	

}
