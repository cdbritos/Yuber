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
	@Column(name="id")
	private int adminId;
	
	@Basic
	@Column(name="name",length=Constants.COLUMN_LENGTH)
	private String adminName;
	
	@Basic
	@Column(name="password",length=Constants.COLUMN_LENGTH)
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
}
