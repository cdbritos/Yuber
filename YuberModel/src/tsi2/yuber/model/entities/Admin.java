package tsi2.yuber.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="admin")
@NamedQuery(
		name="listAdmin",
		query="select c from Admin c"
		) 
public class Admin {

	@Id 
	@Column(name="adminId")
	private short adminId;
	
	@Basic
	@Column(name="adminName")
	private String adminName;

	public short getAdminId() {
		return adminId;
	}

	public void setAdminId(short adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	public Admin() {
	
	}

	public Admin(short adminId, String adminName) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
	}
	
	
}
