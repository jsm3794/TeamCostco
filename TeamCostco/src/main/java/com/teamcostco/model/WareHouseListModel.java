package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
	SELECT storage_id, main_name, midium_name, small_name, product_name, current_inventory FROM storage s
INNER JOIN product p ON p.product_code = s.product_id
INNER JOIN maincategory main ON main.main_id = p.main_id
INNER JOIN MIDIUMCATEGORY midi ON midi.midium_id = p.medium_id
INNER JOIN smallcategory small ON small.small_id = p.small_id
WHERE product_name LIKE '%불%' AND main_name = '즉석식품' AND midium_name = '즉석식품' AND small_name = '햄버거';
 */
public class WareHouseListModel {
	private String product_id;
	private String main_name;
	private String medium_name;
	private String small_name;
	private String product_name;
	private Integer current_inventory;
	
	public WareHouseListModel(ResultSet rs) throws SQLException {
		product_id = rs.getString("product_id");
		main_name = rs.getString("main_name");
		medium_name = rs.getString("medium_name");
		small_name = rs.getString("small_name");
		product_name = rs.getString("product_name");
		current_inventory = rs.getInt("current_inventory");
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getMain_name() {
		return main_name;
	}

	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}

	public String getMedium_name() {
		return medium_name;
	}

	public void setMidium_name(String medium_name) {
		this.medium_name = medium_name;
	}

	public String getSmall_name() {
		return small_name;
	}

	public void setSmall_name(String small_name) {
		this.small_name = small_name;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Integer getCurrent_inventory() {
		return current_inventory;
	}

	public void setCurrent_inventory(Integer current_inventory) {
		this.current_inventory = current_inventory;
	}

	@Override
	public String toString() {
		return "WareHouseListModel [product_id=" + product_id + ", main_name=" + main_name + ", medium_name="
				+ medium_name + ", small_name=" + small_name + ", product_name=" + product_name + ", current_inventory="
				+ current_inventory + "]";
	}
	
	
}
