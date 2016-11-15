package tsi2.yuber.common.data;

public class LookAndFeel {
	
	private String backGround = "e5e7ea";
	private Theme theme = new Theme(26, "Redmond", "redmond");
	
	public String getBackGround() {
		return "#" + backGround;
	}

	public void setBackGround(String backGround) {
		this.backGround = backGround;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

}
