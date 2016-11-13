package tsi2.yuber.report;

import tsi.tuber.admin.enums.TipoVerticalEnum;

public abstract class AbstractReport {
	
	protected TipoVerticalEnum tve;
	protected String vertical;
	protected String tipoReporte;
	
	public AbstractReport() {
	
	}
	
	public AbstractReport(String vertical2, TipoVerticalEnum tve2, String tipoReporte) {
		super();
		this.vertical = vertical2;
		this.tve = tve2;
		this.tipoReporte = tipoReporte;
	}
	
	public AbstractReport(TipoVerticalEnum tve2,String tipoReporte) {
		this(null, tve2,tipoReporte);
	}
	

	protected String getHost(TipoVerticalEnum tv){
		return Boolean.valueOf(System.getenv("isLocal")).booleanValue() ? "localhost:9080" : "yuberbackend" + tv.getId() + ".mybluemix.net" ;
	}

	protected String getUrl(){
		String url = "http://" + getHost(this.tve) + "/YuberServices/rest/report/report" + this.tipoReporte ; 
		if (!tipoReporte.equals("1"))
			url += "/" + this.vertical;
		
		return url;
	} 
}
