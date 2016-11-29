package tsi2.yuber.common.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import tsi2.yuber.common.config.ConstantsConfig;
import tsi2.yuber.common.data.Report;
import tsi2.yuber.common.exception.ServiceException;
import tsi2.yuber.common.services.Facade.ICommonService;
import tsi2.yuber.common.web.model.ConstansWeb;
import tsi2.yuber.report.Bar3DChartReport;
import tsi2.yuber.report.LayeredBarChartReport;
import tsi2.yuber.report.Pie3DChartReport;
import tsi2.yuber.report.StackedBar3DChartReport;

@ManagedBean(name=ConstantsConfig.REPORT_CONTROLLER)
@ViewScoped
public class ReportController {

	@EJB
	private ICommonService commonService;
	
	private Report report;

	public ReportController() {
		this.report = new Report();
	}
	
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
	
	public String doReport() throws IOException{
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=Reporte" + this.report.getTipoReporte().getId() + "_" + this.report.getVerticalName() + ".pdf");
		
		OutputStream out = response.getOutputStream();
		
		System.out.println("attachment;filename=Reporte" + this.report.getTipoReporte().getId() + "_" + this.report.getVerticalName() + ".pdf");
		System.out.println(this.report.getTipoVertical().name() + "###"+ this.report.getTipoVertical().getId());
		switch (this.report.getTipoReporte().getId()){
		case "1":// Datos por Vertical
			new Bar3DChartReport(out,this.report.getTipoVertical(),this.report.getTipoReporte().getId());
			break;
		case "2":// Datos de ganancias mensuales
			new Pie3DChartReport(out,this.report.getVerticalName(),this.report.getTipoVertical(),this.report.getTipoReporte().getId());
			break;
		case "3":// Datos de Review
		case "6":
			new StackedBar3DChartReport(out,this.report.getVerticalName(),this.report.getTipoVertical(),this.report.getTipoReporte().getId());
			break;
		case "4"://Datos del Servicio Cantidad - Ganancia
		case "5":
			new LayeredBarChartReport(out,this.report.getVerticalName(),this.report.getTipoVertical(),this.report.getTipoReporte().getId());
			break;
		default:
			System.out.println("NO EXISTE REPORTE");
		}
			
		out.close();
		
		return ConstansWeb.INICIO;
		
	}
	
	public String doPopulate() throws IOException{
	
		try {
			commonService.popularBase(this.report.getTipoVertical(), this.report.getVerticalName());
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(),
					"Error al popular la vertical"));
			return ConstansWeb.INICIO;
		}
		
		return ConstansWeb.INICIO;
	}
	
	public List<String> getVerticales(){
		List<String> verticales = commonService.getVerticales(this.report.getTipoVertical());
		if (verticales != null && !verticales.isEmpty())
			this.report.setVerticalName(verticales.get(0));
		
		return verticales;
		
	}
	
}
