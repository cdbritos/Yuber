package tsi2.yuber.services.util;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VCAPUtils {

	private static final String VCAP_SERVICES = "VCAP_SERVICES";
	
	public static final String getTipoVertical(){
		return System.getenv("tipoVertical");
	}
	
	public static final String getDataBasePrefix(){
		String prefix = System.getenv("dataBasePrefix");
		if (prefix == null)
			prefix = "yuberDB";
		return prefix;
	}

		
	public static String getVCAP_SERVICES(){
		return System.getenv(VCAP_SERVICES);
	}
	
	public static String getServicePostgreName(String vertical){
		return getDataBasePrefix() + getTipoVertical() + "_" + vertical;
	}
	

	private static final JsonElement getJSONPostgreDB(String vertical){
	
		JsonElement jsonElem = new JsonParser().parse(getVCAP_SERVICES());
		JsonObject jsonObj = jsonElem.getAsJsonObject();
		JsonArray jarray = jsonObj.getAsJsonArray("compose-for-postgresql");
	
		for (JsonElement jsonElement : jarray) {
			if (jsonElement.getAsJsonObject().get("name").getAsString().equals(getServicePostgreName(vertical)))
				jsonElem = jsonElement;
		}
		
		return jsonElem;
	}
	
	
	public static String getHostDBConnection(String vertical){
		
		JsonElement j = getJSONPostgreDB(vertical).getAsJsonObject().get("credentials");
		String uri_cli = j.getAsJsonObject().get("uri_cli").getAsString();
		
		return StringUtils.substringBetween(uri_cli, "host=", " ");
	}
	
	public static String getPortDBConnection(String vertical){
		JsonElement j = getJSONPostgreDB(vertical).getAsJsonObject().get("credentials");
		String uri_cli = j.getAsJsonObject().get("uri_cli").getAsString();
		
		return StringUtils.substringBetween(uri_cli, "port=", " ");
	}
	
	public static String getPasswordDBConnection(String vertical){

		JsonElement j = getJSONPostgreDB(vertical).getAsJsonObject().get("credentials");
		String uri = j.getAsJsonObject().get("uri").getAsString();
		
		return StringUtils.substringBetween(uri, "admin:", "@");
	}
	
	public static String getUserDBConnection(String vertical){
		return "admin";
	}
	
	public static String getDataBaseNameDBConnection(String vertical){
		
		JsonElement j = getJSONPostgreDB(vertical).getAsJsonObject().get("credentials");
		return j.getAsJsonObject().get("name").getAsString();

	}

}
