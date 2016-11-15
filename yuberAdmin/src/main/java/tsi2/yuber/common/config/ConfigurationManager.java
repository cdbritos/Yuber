package tsi2.yuber.common.config;

import java.util.Locale;

import javax.ejb.Singleton;

import tsi2.yuber.common.data.LookAndFeel;
import tsi2.yuber.common.enums.TipoUsuario;


@Singleton(name=ConstantsConfig.CONFIGURATION_MANAGER)
public class ConfigurationManager{

	public static TipoUsuario tipoUsuario = TipoUsuario.YUBERADMIN;
	public static Locale locale = new Locale("es", "ES");
	public static LookAndFeel lookAndFeel = new LookAndFeel();
	
}