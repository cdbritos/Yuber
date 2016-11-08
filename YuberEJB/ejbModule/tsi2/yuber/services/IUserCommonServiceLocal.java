package tsi2.yuber.services;

import java.util.List;

import tsi2.yuber.model.entities.User;

public interface IUserCommonServiceLocal {

	public void saveUser(String verticalName, User user);
	
	public User findUser(String verticalName, User user);
	
	public List<User> findAllUser(String verticalName);
	
}