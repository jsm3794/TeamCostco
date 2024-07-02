package main.java.com.teamcostco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.model.DefectProductModel;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;

public class DefectProductDAO {

	public static boolean insertDefectProduct(String disposal_method, String product_code, int amount) {
		String sql = "INSERT INTO defectproduct VALUES (0, ?, ?, ?)";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, disposal_method);
			pstmt.setString(2, product_code);
			pstmt.setInt(3, amount);

			int row = pstmt.executeUpdate();
			return row > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
