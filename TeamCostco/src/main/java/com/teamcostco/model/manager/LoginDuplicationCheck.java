package main.java.com.teamcostco.model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.view.panels.DBConnector;

public class LoginDuplicationCheck {

    public static boolean isDuplicateID(String id, DBConnector connector) {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
        try (
            Connection con = connector.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("중복 검사 실패");
        }
        return false;
    }
}
