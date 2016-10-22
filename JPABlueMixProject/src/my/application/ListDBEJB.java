package my.application;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
//import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import myjpa.CustomerAcct;

/**
 * Servlet implementation class CustomerCredit
 */
@Stateless
@LocalBean
@Resource(name = "jdbc/MyDataSourceDB2", type = javax.sql.DataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class ListDBEJB {
	
	@PersistenceUnit(unitName = "CustomerQueryDB2")
	EntityManagerFactory emf;	

	CustomerAcct customer = null;
		

	
	public OutBean list(InBean in) {

		// TODO Auto-generated method stub
		OutBean ob = new OutBean();
		
		System.out.println("in ListDB EJB");
		
		try {

			
			EntityManager em = emf.createEntityManager();

			Query q = em.createNamedQuery("listCustomers");

			Collection coll = null;
			coll = q.getResultList();

			if (coll != null) {
				Iterator it = coll.iterator();
				CustomerAcct[] cas = new CustomerAcct[8];
				int i = 0;
				CustomerAcct ca = null;
				System.out.println("before getting customers from result set: "+ca);
				while (it.hasNext()) {
					ca = (CustomerAcct) it.next();
					System.out.println("customer acct from result set: "+ca);
					cas[i] = ca;
					i++;
				}
				System.out.println("after getting customers from result set: "+ca);
				ob.setCustomerAccts(cas);
			}

		} catch (PersistenceException t) {
			t.printStackTrace();
			Throwable c = t.getCause();
			if (c != null) {
				System.out.println(c.getMessage());
				String causeMsg = t.getCause().getMessage();
				if (causeMsg.contains("-204"))
					throw new EJBException(
							"table does not exist please run populate DB function from web page: "
									+ t.getCause().getMessage(), t);
				else
					throw new EJBException(
							"persistence exception obtaining customer acct: "
									+ t.getCause().getMessage(), t);
			}
			throw new EJBException(
					"persistence exception obtaining customer acct: "
							+ t.getMessage(), t);

		}
		CustomerAcct[] cas=ob.getCustomerAccts();
		System.out.println("list of customers from ListDB EJB");
		for (int p=0;p<8;p++) {
			System.out.println("cas["+p+"]="+cas[p]);			
		}
		return ob;

	}

}
