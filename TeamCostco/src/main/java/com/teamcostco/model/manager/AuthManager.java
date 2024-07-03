package main.java.com.teamcostco.model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.MemberModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;

public class AuthManager {
	private static AuthManager instance;
	private boolean isLoggedIn;
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

	public void updateAuth() {
		if (!isLoggedIn || memberModel == null) {
			return;
		}

		String sql = "SELECT * FROM employees WHERE employee_id = ?";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, memberModel.getMember_id());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					MemberModel updatedMember = new MemberModel(rs);

					this.memberModel = updatedMember;
				} else {
					logout();
					throw new RuntimeException("User information not found. Logged out.");
				}
			}
		} catch (SQLException e) {
			handleUpdateAuthException(e);
		}
	}

	private void handleUpdateAuthException(SQLException e) {
		e.printStackTrace();
		System.out.println("Failed to update authentication information: " + e.getMessage());
	}

	public boolean login(String id, String pw) {
		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = prepareLoginStatement(conn, id, pw);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				this.isLoggedIn = true;
				memberModel = new MemberModel(rs);
				initializeAutoLogout();
				return true;
			}
		} catch (SQLException e) {
			handleLoginException(e);
		}
		return false;
	}

	private PreparedStatement prepareLoginStatement(Connection conn, String id, String pw) throws SQLException {
		String sql = "SELECT * FROM employees WHERE login_id = ? AND login_pw = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		return ps;
	}

	private void initializeAutoLogout() {
		AutoLogoutManager.getInstance().initialize(MainForm.frame);
	}

	private void handleLoginException(SQLException e) {
		e.printStackTrace();
		// 여기에 로깅이나 사용자에게 오류 메시지를 보여주는 코드를 추가할 수 있습니다.
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