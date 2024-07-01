package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryModel {

	public class MainCategory {
		String main_id;
		String main_name;
		
		public MainCategory(ResultSet rs) throws SQLException {
			this.main_id = rs.getString("main_id");
			this.main_name = rs.getString("main_name");
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
	}

	public class MidiumCategory {
		String midium_id;
		String midium_name;
		String main_id;
		
		public MidiumCategory(ResultSet rs2) throws SQLException {
			this.midium_id = rs2.getString("midium_id");
			this.midium_name = rs2.getString("midium_name");
			this.main_id = rs2.getString("main_id");
		}
		
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
		
	}
	
	public class SmallCategory {
		String small_id;
		String small_name;
		String midium_id;
		
		public SmallCategory(ResultSet rs3) throws SQLException {
		this.small_id = rs3.getString("small_id");
		this.small_name = rs3.getString("small_name");
		this.midium_id = rs3.getString("midium_id");
		}
		
		public String getSmall_id() {
			return small_id;
		}
		public void setSmall_id(String small_id) {
			this.small_id = small_id;
		}
		public String getSmall_name() {
			return small_name;
		}
		public void setSmall_name(String small_name) {
			this.small_name = small_name;
		}
		public String getMidium_id() {
			return midium_id;
		}
		public void setMidium_id(String midium_id) {
			this.midium_id = midium_id;
		}
	}


}
