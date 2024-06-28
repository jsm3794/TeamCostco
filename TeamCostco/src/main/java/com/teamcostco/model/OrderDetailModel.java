package main.java.com.teamcostco.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailModel {

	private String client; // 거래처
	private String item_number; // 품번
	private String product_name; // 품명
	private String category_name;
	private int order_quantity; // 발주수량
	private int quantity_of_wh; // 입고수량
	private int remaining_capacity; // 잔량
	private Date order_date; // 발주일자

	public OrderDetailModel(ResultSet rs) throws SQLException {

		this.client = rs.getString("client_name");
		this.item_number = rs.getString("product_id");
		this.product_name = rs.getString("product_name");
		this.category_name = rs.getString("categori_name");
		this.order_quantity = rs.getInt("order_quantity");
		this.quantity_of_wh = rs.getInt("quantity_of_wh");
		this.order_date = rs.getDate("request_date");
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getItem_number() {
		return item_number;
	}

	public void setItem_number(String item_number) {
		this.item_number = item_number;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}

	public int getQuantity_of_wh() {
		return quantity_of_wh;
	}

	public void setQuantity_of_wh(int quantity_of_wh) {
		this.quantity_of_wh = quantity_of_wh;
	}

	public int getRemaining_capacity() {
		return remaining_capacity;
	}

	public void setRemaining_capacity(int remaining_capacity) {
		this.remaining_capacity = remaining_capacity;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    String lineSeparator = "\n";
	    sb.append("거래처=").append(client).append(lineSeparator);
	    sb.append("발주번호=").append(item_number).append(lineSeparator);
	    sb.append("상품명=").append(product_name).append(lineSeparator);
	    sb.append("카테고리=").append(category_name).append(lineSeparator);
	    sb.append("발주수량=").append(order_quantity).append(lineSeparator);
	    sb.append("입고수량=").append(quantity_of_wh).append(lineSeparator);
	    sb.append("발주일자=").append(order_date);
	    return sb.toString();
	}

}
