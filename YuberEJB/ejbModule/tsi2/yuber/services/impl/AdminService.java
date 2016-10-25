package tsi2.yuber.services.impl;

import javax.ejb.Stateless;

import tsi2.yuber.services.IAdminServiceRemote;

@Stateless
public class AdminService implements IAdminServiceRemote{

	@Override
	public void crearVertical() {
		System.out.println("HELLLOOOOO");
	}

}
