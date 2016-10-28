package tsi2.yuber.init;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import tsi2.yuber.model.entities.Admin;
import tsi2.yuber.model.entities.User;


@Singleton
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
//@Startup
public class InitDB {

	public InitDB() {
    }
   
    
   // @PostConstruct
    private void initDB(){
    	EntityManager em;
    	
    		
    	
			EntityManagerFactory managerFactory = null;
			Map<String, String> persistenceMap = new HashMap<String, String>();
			
			String dataSource = System.getenv("dataSource");
			if (dataSource == null)
				dataSource = "yuberDB1";
			
			persistenceMap.put("javax.persistence.jtaDataSource", "jdbc/" + dataSource);

			managerFactory = Persistence.createEntityManagerFactory("PU",persistenceMap);
			
			em = managerFactory.createEntityManager();
			
			User user = null;
	    	
	    	for(int i =0; i<10; i++){
	    		user = new User("userName"+i, "nombre"+i, "apellido"+i, "telefono"+i, "direccion"+i, "password"+i);
	    		em.persist(user);
	    	}
	    	
	    	Admin admin = null;
	    	
	    	for(int i =0; i<10; i++){
	    		admin = new Admin((short)i, "adminName" +i);
	    		em.persist(admin);
	    	}
	    	
	    	em.close();
    	
    }

}
