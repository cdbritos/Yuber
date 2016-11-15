package tsi2.yuber.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AbstractService {
	
	protected EntityManager getEntityManager(String verticalName){
		
		EntityManagerFactory managerFactory = null;
		Map<String, String> persistenceMap = new HashMap<String, String>();
		
		String DSJNDI_Name = "jdbc/" + getDataBasePrefix() + getTipoVertical() + "_" + verticalName;
		
		persistenceMap.put("javax.persistence.jtaDataSource", DSJNDI_Name);	
		
		managerFactory = Persistence.createEntityManagerFactory("PU",persistenceMap);
		
		return managerFactory.createEntityManager();
		
	}
	
	protected EntityManager getEntityManager(){
		
		EntityManagerFactory managerFactory = null;
		Map<String, String> persistenceMap = new HashMap<String, String>();
		
		String DSJNDI_Name = "jdbc/" + getDataBasePrefix() + getTipoVertical();
		
		persistenceMap.put("javax.persistence.jtaDataSource", DSJNDI_Name);	
		
		managerFactory = Persistence.createEntityManagerFactory("DB",persistenceMap);
		
		return managerFactory.createEntityManager();
		
	}
	
	protected String getTipoVertical(){
		return System.getenv("tipoVertical");
	}
	
	protected String getDataBasePrefix(){
		String prefix = System.getenv("dataBasePrefix");
		if (prefix == null)
			prefix = "yuberDB";
		return prefix;
	}

	
}
