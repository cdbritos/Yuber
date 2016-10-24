package tsi2.yuber.services;

import java.util.List;

import javax.ejb.Remote;

import tsi2.yuber.model.entities.User;



@Remote
public interface IUserCommonServiceRemote {

	public void saveUser(User user);
	
	public User findUser(User user);
	
	public List<User> findAllUser();
	
}
