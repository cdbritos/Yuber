package tsi2.yuber.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import tsi2.yuber.model.constant.Constants;

@Entity
@Table(name="proveedor")
public class Proveedor extends AbstractEntity {

	@Basic
	@Column(length=Constants.COLUMN_LENGHT)
	@Id
	private String userName;

	@Basic
	@Column(length=Constants.COLUMN_LENGHT)
	private String nombre;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGHT)
	private String apellido;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGHT)
	private String telefono;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGHT)
	private String direccion;
	
	@Basic
	@Column(length=Constants.COLUMN_LENGHT)
	private String password;

	public Proveedor() {
		super();
	}
	
	public Proveedor(String userName, String nombre, String apellido, String telefono, String direccion, String password) {
		this();
		this.userName = userName;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}


