package tsi2.yuber.admin.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import tsi2.yuber.common.config.ConstantsConfig;
import tsi2.yuber.common.data.Report;

@ManagedBean(name=ConstantsConfig.REPORT_BEAN)
@SessionScoped
public class ReportBean implements Serializable{

	private static final long serialVersionUID = 4703944310557973975L;

	private Report report;

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
	
}
