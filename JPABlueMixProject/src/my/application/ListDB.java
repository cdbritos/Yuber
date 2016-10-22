package my.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myjpa.CustomerAcct;

/**
 * Servlet implementation class CustomerCredit
 */
@WebServlet("/ListDB")
@Resource(name = "jdbc/MyDataSourceDB2", type = javax.sql.DataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class ListDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	@PersistenceUnit(unitName = "CustomerQueryDB2")
	EntityManagerFactory emf;	

	CustomerAcct customer = null;
	private boolean isMySQL;
	

	@EJB
	ListDBEJB listEJB;

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

		printResultsStart(out);

		boolean ejb = (new Boolean(request.getParameter("ejb"))).booleanValue();		
		
		
		if (ejb) {
			System.out.println("ListDB in EJB Path!!!");
			try {
				output = listEJB.list(input);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				output.setStatus("Status: FAILED - " + e.getMessage());
				throw new ServletException(
						"exception creating table(EJB Path): " + e.getMessage(),
						e);
			}
			
			CustomerAcct[] ca = output.getCustomerAccts();
			System.out.println("ca.length="+ca.length);
			
			for (int i = 0; i < ca.length; i++)
				printResultsMiddle(out, ca[i]);
		} else {
			System.out.println("ListDB in Servlet Path!!!");

			
			try {
				EntityManager em = emf.createEntityManager();
				
				System.out.println("em being used for listCustomers query in ListDB: "+em);

				Query q = em.createNamedQuery("listCustomers");

				Collection coll = null;
				coll = q.getResultList();

				if (coll != null) {
					Iterator it = coll.iterator();

					while (it.hasNext()) {
						customer = (CustomerAcct) it.next();
						printResultsMiddle(out, customer);
					}

				}

				printResultsEnd(out);

			} catch (PersistenceException t) {
				t.printStackTrace();
				Throwable c = t.getCause();
				if (c != null) {
					System.out.println(c.getMessage());
					String causeMsg = t.getCause().getMessage();
					if (causeMsg.contains("-204"))
						throw new ServletException(
								"table does not exist please run populate DB function from web page: "
										+ t.getCause().getMessage(),
								t.getCause());
					else
						throw new ServletException(
								"persistence exception obtaining customer acct: "
										+ t.getCause().getMessage(),
								t.getCause());
				}
				throw new ServletException(
						"persistence exception obtaining customer acct: "
								+ t.getMessage(), t);

			} // end try

			catch (Exception t) {
				t.printStackTrace();
				throw new ServletException("error locating customer account", t);
			} // end try
		}
	}

	private void printResultsStart(PrintWriter out) {
		String title = "Database Contents";

		out.println("<HTML><HEAD><link rel=\"stylesheet\" href=\"css/style.css\"><TITLE>");
		out.println(title);
		out.println("</TITLE></HEAD><body><div class = 'container'>");
		out.println("<H1 align=\"center\">" + title + "</H1>");
		out.println("<BR><BR><BR>");

		out.println("<TABLE align='center' width = 600px>");
		out.println("<TBODY align = 'left'>");
		out.println("<TR>");
		out.println("<TH width = '20%'>ID</TH>");
		out.println("<TH width = '40%'>Name </TH>");
		out.println("<TH width = '40%'>Account balance </TH>");
		out.println("</TR>");
	}

	private void printResultsMiddle(PrintWriter out, CustomerAcct ca) {
		out.println("<TR>");
		out.println("<TD>" + ca.getCustomerAcct() + "</TD>");
		out.println("<TD>" + ca.getCustomerName() + "</TD>");
		out.println("<TD>$" + ca.getCustomerMoney() + "</TD>");
		out.println("</TR>");
	}

	private void printResultsEnd(PrintWriter out) {
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
