package tsi2.yuber.services.util;

import java.util.List;

import tsi2.yuber.model.entities.Servicio;

public class HistorialCabezal {
	public List<Servicio> historial;

	public List<Servicio> getHistorial() {
		return historial;
	}

	public void setHistorial(List<Servicio> h) {
		this.historial = h;
	}

	public HistorialCabezal(List<Servicio> h) {
		super();
		this.historial = h;
	}
	
	
}