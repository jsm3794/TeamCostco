package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DefectProductModel {

	private int defect_id;
	private String disposal_method;
	private String product_code;
	private int defect_amount;

	public DefectProductModel(ResultSet rs) throws SQLException {
		this.defect_id = rs.getInt("defect_id");
		this.disposal_method = rs.getString("disposal_method");
		this.product_code = rs.getString("product_code");
		this.defect_amount = rs.getInt("defect_amount");
	}
	

	public int getDefect_amount() {
		return defect_amount;
	}


	public void setDefect_amount(int defect_amount) {
		this.defect_amount = defect_amount;
	}


	public int getDefect_id() {
		return defect_id;
	}

	public void setDefect_id(int defect_id) {
		this.defect_id = defect_id;
	}

	public String getDisposal_method() {
		return disposal_method;
	}

	public void setDisposal_method(String disposal_method) {
		this.disposal_method = disposal_method;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

}
