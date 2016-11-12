package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.entities.Vertical;

public interface IVerticalServiceLocal {

	public void saveVertical(Vertical vertical);
	
	public List<Vertical> findAll();
	
	public void populateVerticalUsers(String verticalName);

	public void populateVerticalServices(String verticalName);
}
