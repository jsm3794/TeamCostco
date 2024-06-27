package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * "SELECT main_name, midium_name, small_name "
                   + "FROM maincategory "
                   + "INNER JOIN midiumcategory USING (main_id) "
                   + "INNER JOIN smallcategory USING (midium_id)";

 */

public class AllCategoryJoin {
	String main_name;
	String midium_name;
	String small_name;
	
	public AllCategoryJoin(ResultSet rs) throws SQLException {
		main_name = rs.getString("main_name");		
		midium_name = rs.getString("midium_name");		
		small_name = rs.getString("small_name");
		
	}

	public String getMain_name() {
		return main_name;
	}

	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}

	public String getMidium_name() {
		return midium_name;
	}

	public void setMidium_name(String midium_name) {
		this.midium_name = midium_name;
	}

	public String getSmall_name() {
		return small_name;
	}

	public void setSmall_name(String small_name) {
		this.small_name = small_name;
	}

	@Override
	public String toString() {
		return "AllCategoryJoin [main_name=" + main_name + ", midium_name=" + midium_name + ", small_name=" + small_name
				+ "]";
	}

	
	
	
}
