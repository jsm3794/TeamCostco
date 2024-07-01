package main.java.com.teamcostco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;

public class OrderDetailDAO {

	public static OrderDetailModel getOrderDetailModel(int order_request_id) {
		String sql = "SELECT " + "order_request_id, " + "o.product_code, " + "p.product_name AS product_name, "
				+ "m.main_name AS main_name, " + "orderemployee_id, " + "order_quantity, " + "request_date, "
				+ "request_status, " + "client_name, " + "quantity_of_wh " + "FROM ORDERREQUEST o "
				+ "INNER JOIN product p ON p.product_code = o.product_code "
				+ "INNER JOIN maincategory m ON p.main_id = m.main_id " + "WHERE order_request_id = ? ";
		
		try(
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setInt(1, order_request_id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return new OrderDetailModel(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
