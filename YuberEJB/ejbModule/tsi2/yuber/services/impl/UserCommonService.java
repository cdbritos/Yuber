package tsi2.yuber.services.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
		if (userDB.getPassword().equals(user.getPassword()))
			return userDB;
		else return null;
	}


	@Override
	public List<User> findAllUser(String verticalName) {
		String q = "SELECT u from " + User.class.getName() + " u";
		
	    Query query = getEntityManager(verticalName).createQuery(q);
	    List<User> users = query.getResultList();
		return users;
	}
	
	
	

}

