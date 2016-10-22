package my.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import myjpa.CustomerAcct;

/**
 * Servlet implementation class CustomerCredit
 */
@WebServlet("/PopulateDB")
@Resource(name = "jdbc/yuberDB1", type = javax.sql.DataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class PopulateDB extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@PersistenceUnit(unitName = "CustomerQueryDB2")
	EntityManagerFactory emf;

	String causeMsg;

	@EJB
	PopulateDBEJB populateEJB;

	private String[] name = { "Ben Franklin", "Albert Einstein",
			"Groucho Marx", "Harpo Marx", "Chico Marx", "Zeppo Marx",
			"Eleanor Roosevelt", "Jean-Paul Sartre" };
	private double[] money = { 100.00, 186282.00, 1.00, 2.00, 3.00, 4.00,
			101118.84, 0.00 };

	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PopulateDB() {
		super();
		// TODO Auto-generated constructor stub
	}

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

		

		if (ejb) {
			try {
				output = populateEJB.populate(input);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				output.setStatus("Status: FAILED - " + e.getMessage());
				throw new ServletException(
						"exception creating table(EJB Path): " + e.getMessage(),
						e);
			}
			printResults(out);
		} else {
			System.out.println("in Servlet path of PopulateDB ");
			EntityManager em = emf.createEntityManager();

			CustomerAcct ca = null;

			UserTransaction userTran = null;
			try {
				userTran = getUserTrans();
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Query q = null;

			try {
				userTran.begin();
				em.joinTransaction();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (short j = 0; j < 8; j++) {
				ca = new CustomerAcct();
				ca.setCustomerAcct((short) (j + 1));
				ca.setCustomerMoney(BigDecimal.valueOf(money[j]));
				ca.setCustomerName(name[j]);
				em.persist(ca);
			}

			printResults(out);

			/* */
			System.out.println("Listing out customers from Servlet(could be after call to EJB or just inline servlet");
			System.out.println("em being used for listCustomers query in PopulateDB: "+ em);
			q = em.createNamedQuery("listCustomers");

			Collection coll = null;
			coll = q.getResultList();

			if (coll != null) {
				Iterator it = coll.iterator();

				while (it.hasNext()) {
					CustomerAcct customer = (CustomerAcct) it.next();
					System.out.println("customer is:"
							+ customer.getCustomerName());
				}

			}
			/*  */

			try {
				userTran.commit();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicMixedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			em.close();
		}

	}

	private void printResults(PrintWriter out) {
		String title = "Database Population Results";

		out.println("<HTML><HEAD><link rel=\"stylesheet\" href=\"css/style.css\"><TITLE>");
		out.println(title);
		out.println("</TITLE></HEAD><body><div class = 'container'>");
		out.println("<H1 align=\"center\">" + title + "</H1>");
		out.println("<BR><BR><BR>");

		out.println("<div align = 'center'><B>Database Population: Successful</B></div>");

		out.println("</div>");
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
