package tsi2.yuber.common.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import tsi2.yuber.common.config.ConstantsConfig;

@ManagedBean(name=ConstantsConfig.MENU_CONTROLLER)
@ApplicationScoped
public class MenuBuilder implements Serializable {
	
	private static final long serialVersionUID = 568731618792619182L;

	public List<Menu> getMenu(){
		List<Menu> menu = new ArrayList<Menu>();
		
		menu.add(new Menu("ADMINISTRACION_YUBER",
			new MenuItem("ALTA_ADMINISTRADOR"),
			new MenuItem("ALTA_VERTICAL")));
		menu.add(new Menu("REPORTES",
			new MenuItem("REPORTE_USUARIOS_ACTIVOS"),
			new MenuItem("REPORTE_GANANCIAS_MENSUAL"),
			new MenuItem("REPORTE_PROVEEDORES_BY_REVIEW"),
			new MenuItem("REPORTE_PROVEEDORES_BY_GANANCIA"),
			new MenuItem("REPORTE_USUARIOS_BY_REVIEW"),
			new MenuItem("REPORTE_USUARIOS_BY_SERVICIOS")));
		
		return menu;
	}
	
	public class Menu{
		
		private String nombre;
		
		private MenuItem[] menuItems;

		public Menu(String nombre, MenuItem... menuItems) {
			super();
			this.nombre = nombre;
			this.menuItems = menuItems;
		}

		public MenuItem[] getMenuItems() {
			return menuItems;
		}

		public void setMenuItems(MenuItem[] menuItems) {
			this.menuItems = menuItems;
		}



		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
	}
	
	public class MenuItem {
		
		private String code;
		private Integer nivel;
		
		public MenuItem(String code) {
			super();
			this.code = code;
			this.nivel = 0;
		}
		
		public MenuItem(String code, Integer nivel) {
			super();
			this.code = code;
			this.nivel = nivel;
		}
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Integer getNivel() {
			return nivel;
		}
		public void setNivel(Integer nivel) {
			this.nivel = nivel;
		}
		
		
	}
	
}


