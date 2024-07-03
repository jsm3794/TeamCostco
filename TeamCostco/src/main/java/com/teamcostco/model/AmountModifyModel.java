package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/*
	SELECT p.product_name, c.categori_name, amount, unit_price, p.product_id, storage_id FROM product p 
	INNER JOIN categori c USING (categori_id)
	INNER JOIN orderrequest o ON p.product_id = o.product_id
	INNER JOIN storage s ON s.product_Id = p.product_Id
	ORDER BY product_name ASC, categori_name ASC;
 */

public class AmountModifyModel {
	private String product_name;
	private String categori_name;
	private Integer amount;
	private Integer unit_price;
	private String product_id;
	private String storage_id;
	private Date warehousing_date;
	

	public AmountModifyModel(ResultSet rs) throws SQLException {
		product_name = rs.getString(1);
		categori_name = rs.getString(2);
		amount = rs.getInt(3);
		unit_price = rs.getInt(4);
		product_id = rs.getString(5);
		storage_id = rs.getString(6);
		warehousing_date = rs.getDate(7);
	}
	public Date getWarehousing_date() {
		return warehousing_date;
	}
	
	public void setWarehousing_date(Date warehousing_date) {
		this.warehousing_date = warehousing_date;
	}
	
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getCategori_name() {
		return categori_name;
	}
	public void setCategori_name(String categori_name) {
		this.categori_name = categori_name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(Integer unit_price) {
		this.unit_price = unit_price;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getStorage_id() {
		return storage_id;
	}
	public void setStorage_id(String storage_id) {
		this.storage_id = storage_id;
	}
	@Override
	public String toString() {
		return "AmountModifyModel [product_name=" + product_name + ", categori_name=" + categori_name + ", amount="
				+ amount + ", unit_price=" + unit_price + ", product_id=" + product_id + ", storage_id=" + storage_id
				+ ", warehousing_date=" + warehousing_date + "]";
	}
	
	
	
	
}
