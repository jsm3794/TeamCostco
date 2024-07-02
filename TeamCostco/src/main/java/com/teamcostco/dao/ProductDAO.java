package main.java.com.teamcostco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;

public class ProductDAO {
	public static Product getProductByCode(String product_code) {
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM product WHERE product_code = ?")) {

			pstmt.setString(1, product_code);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Product(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
