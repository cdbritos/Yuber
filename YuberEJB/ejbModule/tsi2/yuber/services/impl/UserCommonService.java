package tsi2.yuber.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tsi2.yuber.model.entities.User;
import tsi2.yuber.services.IUserCommonServiceRemote;

@Stateless
public class UserCommonService implements IUserCommonServiceRemote {

	
	@PersistenceContext(unitName = "yuberAdmin")
    private EntityManager em;
     
	
	public UserCommonService() {
		
	}
	
	@Override
	public void saveUser(User user) {
		em.persist(user);
	}


	@Override
	public User findUser(User user) {
		return em.find(User.class, user.getUserName());
	}


	@Override
	public List<User> findAllUser() {
		String q = "SELECT u from " + User.class.getName() + " u";
	    Query query = em.createQuery(q);
	    List<User> users = query.getResultList();
		return users;
	}

}

