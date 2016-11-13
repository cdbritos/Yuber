package tsi2.yuber.admin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import tsi2.yuber.admin.constants.Constants;

@Entity
@Table(name="tipoVertical")
@NamedQuery(
		name="listTipoVertical",
		query="select c from TipoVertical c"
		) 
public class TipoVertical {
	
	@Id
	@Column(name="id")
	private Integer tipoVerticalId;
	
	@Column(name="name",length=Constants.COLUMN_LENGTH)
	private String tipoVerticalName;

	public Integer getTipoVerticalId() {
		return tipoVerticalId;
	}

	public void setTipoVerticalId(Integer tipoVerticalId) {
		this.tipoVerticalId = tipoVerticalId;
	}

	public String getTipoVerticalName() {
		return tipoVerticalName;
	}

	public void setTipoVerticalName(String tipoVerticalName) {
		this.tipoVerticalName = tipoVerticalName;
	}

	public TipoVertical(Integer tipoVerticalId, String tipoVerticalName) {
		this();
		this.tipoVerticalId = tipoVerticalId;
		this.tipoVerticalName = tipoVerticalName;
	}

	public TipoVertical() {
		super();
	}
	
	
	

}
