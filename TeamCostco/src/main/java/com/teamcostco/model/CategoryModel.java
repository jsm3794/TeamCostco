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

	public class MediumCategory {
		String medium_id;
		String medium_name;
		String main_id;

		public MediumCategory(ResultSet rs2) throws SQLException {
			this.medium_id = rs2.getString("medium_id");
			this.medium_name = rs2.getString("medium_name");
			this.main_id = rs2.getString("main_id");
		}

		public String getMedium_id() {
			return medium_id;
		}

		public void setMedium_id(String midium_id) {
			this.medium_id = midium_id;
		}

		public String getMedium_name() {
			return medium_name;
		}

		public void setMedium_name(String medium_name) {
			this.medium_name = medium_name;
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
			this.midium_id = rs3.getString("medium_id");
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
