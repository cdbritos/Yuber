package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.entities.Vertical;

public interface IVerticalServiceLocal {

	public void createDataBase(String verticalName);
	
	public void saveVertical(Vertical vertical);
	
	public List<Vertical> findAll();

}
