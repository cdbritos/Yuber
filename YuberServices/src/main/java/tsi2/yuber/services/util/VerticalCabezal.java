package tsi2.yuber.services.util;

import java.util.List;

import tsi2.yuber.model.entities.Vertical;

public class VerticalCabezal {
	public List<Vertical> verticales;

	public List<Vertical> getVerticales() {
		return verticales;
	}

	public void setVerticales(List<Vertical> verticales) {
		this.verticales = verticales;
	}

	public VerticalCabezal(List<Vertical> verticales) {
		super();
		this.verticales = verticales;
	}
	
}
