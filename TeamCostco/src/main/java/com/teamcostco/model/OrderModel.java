package main.java.com.teamcostco.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {

	private String client; // 거래처
	private String item_number; // 품번
	private String product_name; // 품명
	private int order_quantity; // 발주수량
	private int quantity_of_wh; // 입고수량
	private int remaining_capacity; // 잔량
	private Date order_date; // 발주일자

	public OrderModel(ResultSet rs) throws SQLException {

		this.client = rs.getString("client_name");
		this.item_number = rs.getString("product_id");
		this.product_name = rs.getString("product_name");
		this.order_quantity = rs.getInt("order_num");
		this.quantity_of_wh = rs.getInt("quantity_of_wh");
		this.remaining_capacity = rs.getInt("remaining_capacity");
		this.order_date = rs.getDate("request_date");
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
		return "거래처=" + client + "\n 발주번호=" + item_number + "\n 상품명=" + product_name
				+ "\n 발주수량=" + order_quantity + "\n 창고잔량=" + quantity_of_wh + "\n 입고수량="
				+ remaining_capacity + "\n 발주일자=" + order_date;
	}

	

}
