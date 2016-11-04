package tsi2.yuber.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

public class AbstractService {
	
	protected EntityManager getEntityManager(String verticalName){
		
		EntityManagerFactory managerFactory = null;
		Map<String, String> persistenceMap = new HashMap<String, String>();
		
		String DSJNDI_Name = "jdbc/yuberDB" + getTipoVertical() + "_" + verticalName;
		
		persistenceMap.put("javax.persistence.jtaDataSource", DSJNDI_Name);	
		persistenceMap.put("javax.persistence.jdbc.url", "jdbc:postgresql://sl-us-dal-9-portal.0.dblayer.com:16477/compose");
		
		
		managerFactory = Persistence.createEntityManagerFactory("PU",persistenceMap);
		
		return managerFactory.createEntityManager();
		
	}
	
	protected String getTipoVertical(){
		return System.getenv("tipoVertical");
	}

	
}
