package main.java.com.teamcostco.model;

public class SmallCategory {
	String small_id;
	String samll_name;
	String midium_id;
	
	public String getSmall_id() {
		return small_id;
	}
	public void setSmall_id(String small_id) {
		this.small_id = small_id;
	}
	public String getSamll_name() {
		return samll_name;
	}
	public void setSamll_name(String samll_name) {
		this.samll_name = samll_name;
	}
	public String getMidium_id() {
		return midium_id;
	}
	public void setMidium_id(String midium_id) {
		this.midium_id = midium_id;
	}
	
	@Override
	public String toString() {
		return "SmallCategory [small_id=" + small_id + ", samll_name=" + samll_name + ", midium_id=" + midium_id + "]";
	}
	
	
}
