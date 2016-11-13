package tsi2.yuber.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tsi.tuber.admin.enums.TipoVerticalEnum;
import tsi2.yuber.report.Bar3DChartReport;
import tsi2.yuber.report.LayeredBarChartReport;
import tsi2.yuber.report.Pie3DChartReport;
import tsi2.yuber.report.StackedBar3DChartReport;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		TipoVerticalEnum tve = TipoVerticalEnum.valueOf(req.getParameter("tipoVertical"));
		String vertical = req.getParameter("verticalName");
		String tipoReporte = req.getParameter("tipoReporteId");
		
		resp.setContentType("application/pdf");
		resp.setHeader("Content-Disposition", "attachment;filename=Reporte" + tipoReporte + ".pdf");
		OutputStream out = resp.getOutputStream();
		
		switch (tipoReporte){
		case "1":// Datos por Vertical
			new Bar3DChartReport(out,tve,tipoReporte);
			break;
		case "2":// Datos de ganancias mensuales
			new Pie3DChartReport(out,vertical,tve,tipoReporte);
			break;
		case "3":// Datos de Review
		case "6":
			new StackedBar3DChartReport(out,vertical,tve,tipoReporte);
			break;
		case "4"://Datos del Servicio Cantidad - Ganancia
		case "5":
			new LayeredBarChartReport(out,vertical,tve,tipoReporte);
			break;
		default:
			System.out.println("NO EXISTE REPORTE");
		}
			
		out.close();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
}
