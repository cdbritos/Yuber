package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.entities.User;
import tsi2.yuber.model.entities.Servicio;

public interface IUserCommonServiceLocal {

	public void saveUser(String verticalName, User user);
	
	public User findUser(String verticalName, User user);
	
	public User login(String verticalName,User user);
	
	public List<User> findAllUser(String verticalName);
	
	public Integer getCount(String verticalName);
	
	public void populate(String verticalName);

	public List<Servicio> findServiciosUser(String vertical, String username);
	
}
