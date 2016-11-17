package tsi2.yuber.common.enums;

public enum TipoReporte {
	
		REPORTE_USUARIOS_ACTIVOS("1"),
		REPORTE_GANANCIAS_MENSUAL("2"),
		REPORTE_PROVEEDORES_BY_REVIEW("3"),
		REPORTE_PROVEEDORES_BY_GANANCIA("4"),
		REPORTE_USUARIOS_BY_SERVICIOS("5"),
		REPORTE_USUARIOS_BY_REVIEW("6");
	
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		private TipoReporte(String id) {
			this.id=id;
		}
	
	
}
