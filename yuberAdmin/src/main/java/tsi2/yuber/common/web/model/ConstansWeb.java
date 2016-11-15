package tsi2.yuber.common.web.model;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import tsi2.yuber.common.config.ConstantsConfig;

@ManagedBean(name=ConstantsConfig.CONSTANTS_WEB)
@ApplicationScoped
public class ConstansWeb {

	public static final String LOGIN = "login";
	public static final String REGISTRO = "registro";
	public static final String INICIO = "inicio";
	public static final String PREFERENCIAS = "preferencias";
	public static final String ALTA_ADMINISTRADOR = "altaAdministrador";
	public static final String ALTA_VERTICAL = "altaVertical";
	
	public final String registro(){
		return REGISTRO;
	}
	
}
