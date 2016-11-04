package tsi2.yuber.services.impl;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import tsi2.yuber.services.IReportServiceLocal;

@Stateless
@Local(IReportServiceLocal.class)
@LocalBean
public class ReportService extends AbstractService implements IReportServiceLocal {

		
	public ReportService() {
		
	}

	@Override
	public void createReport1() {
		
	}
		
}

