package my.application;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import javax.transaction.UserTransaction;


/**
 * Servlet implementation class CustomerCredit
 */
@WebServlet("/DropTable")
@Resource(name = "jdbc/MyDataSourceDB2", type = javax.sql.DataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class DropTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@PersistenceUnit(unitName = "CustomerQueryDB2")
	EntityManagerFactory emf;	

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
		
		boolean isMySQL = Boolean.parseBoolean(request.getParameter("isMySQL"));

		EntityManager em = emf.createEntityManager();
				
		try {
			UserTransaction userTran = getUserTrans();
			userTran.begin();
			em.joinTransaction();
			Query q = em.createNativeQuery("DROP TABLE CUSTOMERACCT");
			q.executeUpdate();
			printResults(out);
			userTran.commit();
			em.close();
		} catch (PersistenceException t) {
			// eat the exception, if the table is there it gets deleted if not
			// we get an exception and who cares
		} // end try
		catch (Exception t) {
			// eat the exception, if the table is there it gets deleted if not
			// we get an exception and who cares
		} // end try
	}

	private void printResults(PrintWriter out) {
		String title = "Drop table Results";

		out.println("<HTML><HEAD><link rel=\"stylesheet\" href=\"css/style.css\"><TITLE>");
		out.println(title);
		out.println("</TITLE></HEAD><body><div class = 'container'>");
		out.println("<H1 align=\"center\">" + title + "</H1>");
		out.println("<BR><BR><BR>");

		out.println("<TABLE align='center' width = 600px>");
		out.println("<TBODY align = 'left'>");
		out.println("<TR>");
		out.println("<TH>Table dropped</TH>");
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
