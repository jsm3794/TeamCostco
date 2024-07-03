package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Productinspection {
	
	private int PRODUCT_INSPECTION_ID;
	private String productcode; //제품번호
	private String main_name; // 카테고리명
	private String product_name; //제품명
	private int orderquantity; //발주수량 
	private int checkquantity; //확인된 수량
	private Date dateofreceipt; //입고일
	
	public Productinspection(ResultSet rs) throws SQLException {
		this.PRODUCT_INSPECTION_ID = rs.getInt("PRODUCT_INSPECTION_ID");
		this.productcode = rs.getString("product_code");
		this.main_name = rs.getString("main_name");
		this.product_name = rs.getString("product_name");
		this.orderquantity = rs.getInt("ORDER_QUANTITY");
		this.checkquantity = rs.getInt("CHECK_QUANTITY");
		this.dateofreceipt = rs.getDate("DATE_OF_RECEIPT");
	}
	






	public int getPRODUCT_INSPECTION_ID() {
		return PRODUCT_INSPECTION_ID;
	}







	public void setPRODUCT_INSPECTION_ID(int pRODUCT_INSPECTION_ID) {
		PRODUCT_INSPECTION_ID = pRODUCT_INSPECTION_ID;
	}







	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getMain_name() {
		return main_name;
	}

	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}

	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public int getOrderquantity() {
		return orderquantity;
	}
	public void setOrderquantity(int orderquantity) {
		this.orderquantity = orderquantity;
	}
	public int getCheckquantity() {
		return checkquantity;
	}
	public void setCheckquantity(int checkquantity) {
		this.checkquantity = checkquantity;
	}
	public Date getDateofreceipt() {
		return dateofreceipt;
	}
	public void setDateofreceipt(Date dateofreceipt) {
		this.dateofreceipt = dateofreceipt;
	}
	

	

}
