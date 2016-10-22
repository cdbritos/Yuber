package my.application;

import java.math.BigDecimal;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myjpa.CustomerAcct;

/**
 * Servlet implementation class CustomerCredit
 */
@Stateless
@LocalBean
@Resource(name = "jdbc/MyDataSourceDB2", type = javax.sql.DataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class CustomerCreditEJB {

	@PersistenceUnit(unitName = "CustomerQueryDB2")
	EntityManagerFactory emf;

	CustomerAcct customer = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public OutBean credit(InBean in) {

		OutBean ob = new OutBean();

		System.out.println("in EJB path of Customer Credit ");

		
		EntityManager em = emf.createEntityManager();
		short customerNumber = Short.valueOf(in.getCustomerNumber());
		double moneyToCredit = Double.valueOf(in.getCustomerAmount());

		try {
			em.joinTransaction();

			CustomerAcct customerAcct = em.find(CustomerAcct.class,
					customerNumber);
			if (customerAcct == null) {
				throw new EJBException(
						"customer not found, customer account number="
								+ customerNumber);
			}
			double money = customerAcct.getCustomerMoney().doubleValue()
					+ moneyToCredit; // add credit to existing balance
			customerAcct.setCustomerMoney(new BigDecimal(money)); // set new
																	// existing
																	// balance

			ob.setCreditAmount(BigDecimal.valueOf(moneyToCredit)
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			ob.setCustomerName(customerAcct.getCustomerName());
			ob.setBalance(customerAcct.getCustomerMoney().setScale(2,
					BigDecimal.ROUND_HALF_UP));

			em.close();

		} catch (PersistenceException t) {
			t.printStackTrace();
			System.out.println(t.getCause().getMessage());
			String causeMsg = t.getCause().getMessage();
			if (causeMsg.contains("-204"))
				throw new EJBException(
						"table does not exist please run populate DB function from web page: "
								+ t);
			else
				throw new EJBException(
						"persistence exception obtaining customer acct: " + t);
		} // end try

		catch (Exception t) {
			t.printStackTrace();
			throw new EJBException("error locating customer account", t);
		} // end try
		return ob;

	}

}
