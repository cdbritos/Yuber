package tsi2.yuber.common.web.controller;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import tsi2.yuber.admin.beans.AdminBean;
import tsi2.yuber.admin.entities.Administrador;
import tsi2.yuber.common.config.ConstantsConfig;
import tsi2.yuber.common.exception.ServiceException;
import tsi2.yuber.common.services.Facade.ICommonService;
import tsi2.yuber.common.web.model.ConstansWeb;

@ManagedBean(name=ConstantsConfig.LOGIN_CONTROLLER)
@RequestScoped
public class LoginController extends CommonController {
	
	@EJB
	private ICommonService commonService;
	
	@ManagedProperty(value="#{adminBean}")
    private AdminBean adminBean;
	
	private String username;
	private String contrasenia;
		
	public String doLogin() {
		
		try {

			Administrador admin = commonService.doLogin(username,contrasenia);
			
			this.adminBean.setAdministrador(admin);
			
			return ConstansWeb.INICIO;
			
		} catch (ServiceException e) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					"Please enter correct data"));
			return ConstansWeb.LOGIN;
			
		} catch (Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Data",
					"Please enter correct data"));
			return ConstansWeb.LOGIN;
		}	
	}
	
	public String doLogout() {
		try {
			commonService.doLogout();			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Data",
					"Please enter correct data"));
		}	
		return ConstansWeb.LOGIN;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public AdminBean getAdminBean() {
		return adminBean;
	}

	public void setAdminBean(AdminBean adminBean) {
		this.adminBean = adminBean;
	}

		
}
