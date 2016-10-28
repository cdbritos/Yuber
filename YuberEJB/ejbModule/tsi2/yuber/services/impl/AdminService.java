package tsi2.yuber.services.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import tsi2.yuber.services.IAdminServiceRemote;

@Remote(IAdminServiceRemote.class)
@Stateless
public class AdminService implements IAdminServiceRemote{

	@Override
	public void crearVertical() {
		System.out.println("HELLLOOOOO");
	}

}
