package my.application;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import myjpa.CustomerAcct;

/**
 * Servlet implementation class CustomerCredit
 */
@Stateless
@LocalBean
@Resource(name = "jdbc/MyDataSourceDB2", type = javax.sql.DataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class PopulateDBEJB {
	@Resource
	private SessionContext ctx;	

	@PersistenceUnit(unitName = "CustomerQueryDB2")
	EntityManagerFactory emf;	

	String causeMsg;

	private String[] name = { "Ben Franklin", "Albert Einstein",
			"Groucho Marx", "Harpo Marx", "Chico Marx", "Zeppo Marx",
			"Eleanor Roosevelt", "Jean-Paul Sartre" };
	private double[] money = { 100.00, 186282.00, 1.00, 2.00, 3.00, 4.00,
			101118.84, 0.00 };	
	

	public OutBean populate(InBean in) {
		// TODO Auto-generated method stub
		

		OutBean ob = new OutBean();
		
		System.out.println("in PopulateDB EJB");

		
		EntityManager em = emf.createEntityManager();
		

		CustomerAcct ca = null;
		em.joinTransaction();

		

		Query q = null;
		


		for (short j = 0; j < 8; j++) {
			ca = new CustomerAcct();
			ca.setCustomerAcct((short) (j + 1));
			ca.setCustomerMoney(BigDecimal.valueOf(money[j]));
			ca.setCustomerName(name[j]);
			em.persist(ca);
		}

		ob.setStatus("Database Population: Successful");

		/* */
		q = em.createNamedQuery("listCustomers");

		Collection coll = null;
		coll = q.getResultList();

		if (coll != null) {
			Iterator it = coll.iterator();

			while (it.hasNext()) {
				CustomerAcct customer = (CustomerAcct) it.next();
				System.out.println("customer is:" + customer.getCustomerName());
			}

		}
		/*  */

		em.close();
		return ob;

	}

}
