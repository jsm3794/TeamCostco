package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainCategory {
	String main_id;
	String main_name;
	
	public MainCategory(ResultSet rs) throws SQLException {
		main_id = rs.getString("main_id");
		main_name = rs.getString("main_name");
	}
	
	public String getMain_id() {
		return main_id;
	}
	public void setMain_id(String main_id) {
		this.main_id = main_id;
	}
	public String getMain_name() {
		return main_name;
	}
	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}
	@Override
	public String toString() {
		return "MainCategory [main_id=" + main_id + ", main_name=" + main_name + "]";
	}
	
	
	
}
