package tsi2.yuber.common.web.controller;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import tsi2.yuber.admin.beans.AdminBean;
import tsi2.yuber.common.config.ConstantsConfig;
import tsi2.yuber.common.data.Vertical;
import tsi2.yuber.common.exception.DataBaseCreationException;
import tsi2.yuber.common.exception.ServiceException;
import tsi2.yuber.common.services.Facade.ICommonService;
import tsi2.yuber.common.web.model.ConstansWeb;

@ManagedBean(name=ConstantsConfig.VERTICAL_CONTROLLER)
public class VerticalController extends CommonController {

	@EJB
	private ICommonService commonService;
	
	private Vertical vertical; 
	
	@ManagedProperty(value="#{adminBean}")
    private AdminBean adminBean;
	
	@ManagedProperty(value="#{facesContext.externalContext.context}")
	private ServletContext context;
	
	
	public VerticalController() {
		this.vertical = new Vertical(); 
	}
	
	public String doAltaVertical() {
		
		try {
			commonService.doAltaVertical(this.vertical, adminBean.getAdministrador().getUsuarioBluemix(), 
					adminBean.getAdministrador().getPasswordBluemix(), adminBean.getAdministrador().getSpaceBluemix(), 
					adminBean.getAdministrador().getOrgBluemix(),context);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("dataBaseCreada");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("nuevaVertical");
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					 "Vertical dada de alta correctamente",
					"Vertical dada de alta correctamente"));
			
			return ConstansWeb.INICIO;
			
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(),
					"Error al dar de alta la vertical"));
			return ConstansWeb.ALTA_VERTICAL;
			
		} catch (DataBaseCreationException e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					 "Vertical dada de alta correctamente",
					"Vertical dada de alta correctamente"));
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(),
					"No se pudo crear data base para la nueva vertical"));
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataBaseCreada", false);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nuevaVertical", this.vertical);
			
			return ConstansWeb.INICIO;
		} catch (Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error inesperado",
					"Please enter correct data"));
			return ConstansWeb.INICIO;
		}
	}
	
	public String doCrearDataBase(){
		
			
			Vertical vertical = (Vertical) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nuevaVertical");
			
			try {
				commonService.doCreateDataBase(vertical);
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("dataBaseCreada");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("nuevaVertical");
				
				return ConstansWeb.INICIO;
			} catch (DataBaseCreationException e) {		
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						e.getMessage(),	"No se pudo crear data base para la nueva vertical"));
				
				return ConstansWeb.INICIO;
			}
			
			

	}

	public Vertical getVertical() {
		return vertical;
	}

	public void setVertical(Vertical vertical) {
		this.vertical = vertical;
	}
	
	public AdminBean getAdminBean() {
		return adminBean;
	}

	public void setAdminBean(AdminBean adminBean) {
		this.adminBean = adminBean;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

}
