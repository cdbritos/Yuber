package tsi2.yuber.common.web.controller;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import tsi2.yuber.admin.entities.Administrador;
import tsi2.yuber.common.config.ConstantsConfig;
import tsi2.yuber.common.exception.ServiceException;
import tsi2.yuber.common.services.Facade.ICommonService;
import tsi2.yuber.common.web.model.ConstansWeb;

@ManagedBean(name=ConstantsConfig.ADMIN_CONTROLLER)
@RequestScoped
public class AdminController extends CommonController {

	@EJB
	private ICommonService commonService;
	
	private Administrador administrador; 
	
	public AdminController() {
		this.administrador = new Administrador(); 
	}
	
	public String doAltaAdministrador() {
		
		try {
			
			commonService.doAltaAdministrador(this.administrador);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Alta de Administrador Realizada Correctamente",
					"Alta de Administrador Realizada Correctamente"));
			
			return ConstansWeb.INICIO;
		} catch (ServiceException e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					"Please enter correct data"));
			return ConstansWeb.ALTA_ADMINISTRADOR;
		}
		
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

}
