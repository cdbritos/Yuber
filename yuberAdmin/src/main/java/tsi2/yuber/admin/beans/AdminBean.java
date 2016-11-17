package tsi2.yuber.admin.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import tsi2.yuber.admin.entities.Administrador;
import tsi2.yuber.common.config.ConstantsConfig;

@ManagedBean(name=ConstantsConfig.ADMIN_BEAN)
@SessionScoped
public class AdminBean implements Serializable{

	private static final long serialVersionUID = 4703944310557973975L;

	private Administrador administrador;

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
	
}
