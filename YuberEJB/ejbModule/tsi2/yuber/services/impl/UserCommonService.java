package tsi2.yuber.services.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import tsi2.yuber.model.entities.User;
import tsi2.yuber.services.IUserCommonServiceLocal;

@Stateless
@Local(IUserCommonServiceLocal.class)
@LocalBean
public class UserCommonService extends AbstractService implements IUserCommonServiceLocal {

		
	public UserCommonService() {
		
	}
	
	@Override
	public void saveUser(String verticalName, User user) {
		getEntityManager(verticalName).persist(user);
	}


	@Override
	public User findUser(String verticalName, User user) {
		User userDB = getEntityManager(verticalName).find(User.class, user.getUserName());
		
		return userDB;
	}


	@Override
	public List<User> findAllUser(String verticalName) {
		String q = "SELECT u from " + User.class.getName() + " u";
		
	    Query query = getEntityManager(verticalName).createQuery(q);
	    List<User> users = query.getResultList();
		return users;
	}
	
	@Override
	public Integer getCount(String verticalName) {
		String q = "SELECT count(t) from " + User.class.getName() + " t";
		
		Query query = getEntityManager(verticalName).createQuery(q);
				
		return ((Long) query.getSingleResult()).intValue();
	}

	@Override
	public void populate(String verticalName) {
		for (int j=0; j<10; j++){
			User u = new User("cliente"+j, "nombre"+j, "apellido"+j, "telefono"+j, "direccion"+j, "password"+j);
			saveUser(verticalName, u);
		}
	}

	@Override
	public User login(String verticalName, User user) {
		User userDB = findUser(verticalName, user);
		
		if (userDB != null && userDB.getPassword().equals(user.getPassword()))
			return userDB;
		
		return null;
	}
	
	

}

