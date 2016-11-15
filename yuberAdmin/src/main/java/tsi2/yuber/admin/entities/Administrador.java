package tsi2.yuber.admin.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import tsi2.yuber.admin.constants.Constants;

@Entity
@Table(name="administrador")
@NamedQuery(
		name="listAdministrador",
		query="select c from Administrador c"
		) 
public class Administrador {
	
	@Id
	@Basic
	@Column(length=Constants.COLUMN_LENGTH)
	private String adminName;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGTH)
	private String password;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGTH)
	private String usuarioBluemix;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGTH)
	private String passwordBluemix;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGTH)
	private String spaceBluemix;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGTH)
	private String orgBluemix;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getUsuarioBluemix() {
		return usuarioBluemix;
	}

	public void setUsuarioBluemix(String usuarioBluemix) {
		this.usuarioBluemix = usuarioBluemix;
	}

	public String getPasswordBluemix() {
		return passwordBluemix;
	}

	public void setPasswordBluemix(String passwordBluemix) {
		this.passwordBluemix = passwordBluemix;
	}

	public String getSpaceBluemix() {
		return spaceBluemix;
	}

	public void setSpaceBluemix(String spaceBluemix) {
		this.spaceBluemix = spaceBluemix;
	}

	public String getOrgBluemix() {
		return orgBluemix;
	}

	public void setOrgBluemix(String orgBluemix) {
		this.orgBluemix = orgBluemix;
	}
	
}
