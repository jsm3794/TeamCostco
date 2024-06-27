package main.java.com.teamcostco.model;

public class MidiumCategory {
	String midium_id;
	String midium_name;
	String main_id;
	
	public String getMidium_id() {
		return midium_id;
	}
	public void setMidium_id(String midium_id) {
		this.midium_id = midium_id;
	}
	public String getMidium_name() {
		return midium_name;
	}
	public void setMidium_name(String midium_name) {
		this.midium_name = midium_name;
	}
	public String getMain_id() {
		return main_id;
	}
	public void setMain_id(String main_id) {
		this.main_id = main_id;
	}
	@Override
	public String toString() {
		return "MidiumCategory [midium_id=" + midium_id + ", midium_name=" + midium_name + ", main_id=" + main_id + "]";
	}
	
	
}
