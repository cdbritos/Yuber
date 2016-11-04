package tsi2.yuber.services.rest;

public class AbstractServiceRest {
	
	protected String getAppName(){
		String appName = System.getenv("appName");
		if ( appName == null)
			return "myapp";
		return appName;
	}

}
