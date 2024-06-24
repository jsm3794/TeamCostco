package main.java.com.teamcostco.model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.model.MemberModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;

public class AuthManager {
	private static AuthManager instance;
	private boolean isLoggedIn = false;
	private MemberModel memberModel;

	private AuthManager() {
		this.isLoggedIn = false;
		this.memberModel = null;
	}

	public static synchronized AuthManager getInstance() {
		if (instance == null) {
			instance = new AuthManager();
		}
		return instance;
	}

	public boolean login(String id, String pw) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT * FROM members WHERE login_id = ? AND login_pw = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();

			if (rs.next()) {
				this.isLoggedIn = true;
				memberModel = new MemberModel(rs);
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DatabaseUtil.close(conn, ps, rs);
		}
	}

	public void logout() {
		this.isLoggedIn = false;
		this.memberModel = null;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public MemberModel getCurrentUserModel() {
		return memberModel;
	}
}
