package my.application;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ListEnvVar
 */
@WebServlet("/ListEnvVar")
public class ListEnvVar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListEnvVar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String icap_services = System.getenv("ICAP_SERVICES");
	    System.out.println("ICAP Services var in JVM=" + icap_services);
	    java.io.PrintWriter out = response.getWriter();
	    out.println("ICAP Services var in JVM=" + icap_services);
	    InitialContext ic = null;
	    try {
            ic = new InitialContext();
            javax.sql.DataSource ds1= (DataSource) ic.lookup("java:comp/env/MyDataSource");
            System.out.println("ds1="+ds1.toString());
            out.println("ds1=" + ds1.toString());
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    try {
            javax.sql.DataSource ds2= (DataSource) ic.lookup("MyDataSource");   
            System.out.println("ds2="+ds2.toString());
            out.println("ds2=" + ds2.toString());
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    doGet(request,response);
	}

}
