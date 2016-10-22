package my.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;

import javax.persistence.EntityManagerFactory;

import myjpa.CustomerAcct;

/**
 * Servlet implementation class CustomerCredit
 */
@WebServlet("/CustomerCredit")
@Resource(name = "jdbc/MyDataSourceDB2", type = javax.sql.DataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class CustomerCredit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	@PersistenceUnit(unitName = "CustomerQueryDB2")
	EntityManagerFactory emf;

	CustomerAcct customer = null;

	@EJB
	CustomerCreditEJB ccEJB;

	private UserTransaction getUserTrans() throws NamingException {
		InitialContext ctx = new InitialContext();
		UserTransaction tx = (UserTransaction) ctx
				.lookup("java:comp/UserTransaction");
		return tx;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
						
		OutBean output = null;
		InBean input = new InBean();
		
		boolean ejb = (new Boolean(request.getParameter("ejb"))).booleanValue();
		
		
		EntityManager em = emf.createEntityManager();
		short customerNumber = Short.valueOf(request
				.getParameter("customerNumber"));
		double moneyToCredit = Double.valueOf(request
				.getParameter("customerAmount"));		
		
		input.setCustomerNumber(customerNumber);
		input.setCustomerAmount(moneyToCredit);
		
		

		if (ejb) {
			try {
				output = ccEJB.credit(input);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				output.setStatus("Status: FAILED - " + e.getMessage());
				throw new ServletException(
						"exception creating table(EJB Path): " + e.getMessage(),
						e);
			}
			customer = new CustomerAcct();
			customer.setCustomerName(output.getCustomerName());
			customer.setCustomerMoney(output.getBalance());
			printResults(out,moneyToCredit,customer);
		} else {
			System.out.println("in Servlet path of Customer Credit ");

			try {

				UserTransaction userTran = getUserTrans();

				userTran.begin();
				em.joinTransaction();

				CustomerAcct customerAcct = em.find(CustomerAcct.class,
						customerNumber);
				if (customerAcct == null) {
					userTran.rollback();
					throw new ServletException(
							"customer not found, customer account number="
									+ customerNumber);
				}
				double money = customerAcct.getCustomerMoney().doubleValue()
						+ moneyToCredit; // add credit to existing balance
				customerAcct.setCustomerMoney(new BigDecimal(money)); // set new
																		// existing
																		// balance
				printResults(out, moneyToCredit, customerAcct);

				userTran.commit();
				em.close();

			} catch (PersistenceException t) {
				t.printStackTrace();
				System.out.println(t.getCause().getMessage());
				String causeMsg = t.getCause().getMessage();
				if (causeMsg.contains("-204"))
					throw new ServletException(
							"table does not exist please run populate DB function from web page: "
									+ t.getCause().getMessage(), t.getCause());
				else
					throw new ServletException(
							"persistence exception obtaining customer acct: "
									+ t.getCause().getMessage(), t.getCause());
			} // end try

			catch (Exception t) {
				t.printStackTrace();
				throw new ServletException("error locating customer account", t);
			} // end try
		}

	}

	private void printResults(PrintWriter out, double moneyToCredit,
			CustomerAcct customerAcct) {
		String title = "Customer Credit Results";

		out.println("<HTML><HEAD><link rel=\"stylesheet\" href=\"css/style.css\"><TITLE>");
		out.println(title);
		out.println("</TITLE></HEAD><body><div class = 'container'>");
		out.println("<H1 align=\"center\">" + title + "</H1>");
		out.println("<BR><BR><BR>");

		out.println("<TABLE align='center' width = 600px>");
		out.println("<TBODY align = 'left'>");
		out.println("<TR>");
		out.println("<TH>Customer</TH>");
		out.println("<TH>Credit Applied </TH>");
		out.println("<TH>New Account balance </TH>");

		out.println("</TR>");
		out.println("<TR>");
		out.println("<TD>" + customerAcct.getCustomerName() + "</TD>");
		out.println("<TD>$"
				+ BigDecimal.valueOf(moneyToCredit).setScale(2,
						BigDecimal.ROUND_HALF_UP) + "</TH>");
		out.println("<TD>$"
				+ customerAcct.getCustomerMoney().setScale(2,
						BigDecimal.ROUND_HALF_UP) + "</TD>");

		out.println("</TR>");

		out.println("</TBODY>");
		out.println("</TABLE>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
