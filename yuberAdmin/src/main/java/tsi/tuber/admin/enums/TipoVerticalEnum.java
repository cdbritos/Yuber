package tsi.tuber.admin.enums;

public enum TipoVerticalEnum {

	TRANSPORT(1),
	ONSITE(2),
	REMOTE(3);
	
	private int id;

	private TipoVerticalEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
