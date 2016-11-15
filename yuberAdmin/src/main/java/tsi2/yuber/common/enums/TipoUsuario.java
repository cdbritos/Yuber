package tsi2.yuber.common.enums;

public enum TipoUsuario {

	CLIENTE("C"),
	PROOVEDOR("P"),
	YUBERADMIN("Y");
	
	private TipoUsuario(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static TipoUsuario getByCode(String code) {
		for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
			if (tipoUsuario.getCode().equals(code)){
				return tipoUsuario;
			}
		}
		return TipoUsuario.CLIENTE;
	}
	
	
}
