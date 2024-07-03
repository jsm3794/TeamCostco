package main.java.com.teamcostco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.model.DefectProductModel;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;

public class ProductInspectionDAO {

	public static boolean delete(int productInspectionId) {
		String sql = "delete from productinspection where PRODUCT_INSPECTION_ID = ?";

		try (
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setInt(1, productInspectionId);

			int row = pstmt.executeUpdate();
			return row > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
