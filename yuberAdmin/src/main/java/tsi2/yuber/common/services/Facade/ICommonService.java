package tsi2.yuber.common.services.Facade;

import javax.servlet.ServletContext;

import tsi2.yuber.admin.entities.Administrador;
import tsi2.yuber.common.data.Vertical;
import tsi2.yuber.common.exception.DataBaseCreationException;
import tsi2.yuber.common.exception.ServiceException;

public interface ICommonService {

	public Administrador doLogin(String name, String password) throws ServiceException;
	
	public void doAltaAdministrador(Administrador admin) throws ServiceException;
	
	public void doAltaVertical(Vertical vertical, String userBluemix, String passBluemix, String spaceBluemix, String orgBluemix, ServletContext context) throws ServiceException, DataBaseCreationException;
	
	public void doLogout() throws ServiceException;

	public void doCreateDataBase(Vertical vertical) throws DataBaseCreationException;
}
