package tsi2.yuber.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import tsi2.yuber.model.entities.User;
import tsi2.yuber.services.IUserCommonServiceLocal;
import tsi2.yuber.services.IVerticalServiceLocal;

@Stateless
@Local(IVerticalServiceLocal.class)
@LocalBean
public class VerticalService extends AbstractService implements IVerticalServiceLocal {

		
	public VerticalService() {
		
	}
		
	@Override
	@TransactionAttribute(value=TransactionAttributeType.NEVER)
	public void createDataBase(String verticalName) {
		String DSJNDI_Name = "jdbc/yuberDB" + super.getTipoVertical() + "_" + verticalName;
		Statement stmt = null;
		Connection conn = null;
		try {
			InitialContext ctx = new InitialContext();
			
			DataSource ds =  (DataSource) ctx.lookup(DSJNDI_Name);
			
			
			conn = ds.getConnection();
			//conn.setAutoCommit(true);
			stmt = conn.createStatement();
			String dataBaseName = null; // el nombre lo saca de VCAPSERVICES
			String sql = "CREATE DATABASE IF NOT EXISTS " +  dataBaseName;
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}

	}

}

