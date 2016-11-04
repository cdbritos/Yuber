package com.yuber.server;

import javax.json.JsonNumber;

public class ServiceLocation {
	private String command;
	private String userName;
    private JsonNumber lat;
    private JsonNumber lng;
    private String address;
    private String comment;
    private Integer rating;
    private String Telefono;
    private Double costService;
    
    
    
    
	
    public ServiceLocation(String command, String userName, JsonNumber lat, JsonNumber lng, String address,
			String comment, Integer rating, Double costService2) {
		super();
		this.command = command;
		this.userName = userName;
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.comment = comment;
		this.rating = rating;
		this.costService = costService2;
	}
    
    
	public String getTelefono() {
		return Telefono;
	}


	public void setTelefono(String telefono) {
		Telefono = telefono;
	}


	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public  Double getCostService() {
		return costService;
	}
	public void setCostService(Double costService) {
		this.costService = costService;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public ServiceLocation() {
		super();
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
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
	public ServiceLocation(String command, String userName, JsonNumber lat2, JsonNumber lng2,String address) {
		super();
		this.command = command;
		this.userName = userName;
		this.lat = lat2;
		this.lng = lng2;
		this.address = address;
	}
	public ServiceLocation(String command, String userName, JsonNumber lat, JsonNumber lng) {
		super();
		this.command = command;
		this.userName = userName;
		this.lat = lat;
		this.lng = lng;
	}
	
    
    
    
}
