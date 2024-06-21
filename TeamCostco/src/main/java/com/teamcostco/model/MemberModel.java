package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MemberModel {
	private String member_id;
	private String id;
	private String pw;
	private String email;
	private int role_level;
	private Timestamp created_at;
	private Timestamp updated_at;

	public MemberModel(ResultSet rs) throws SQLException {
		this.member_id = rs.getString("member_id");
		this.id = rs.getString("id");
		this.pw = rs.getString("pw");
		this.email = rs.getString("email");
		this.role_level = rs.getInt("role_level");
		this.created_at = rs.getTimestamp("created_at");
		this.updated_at = rs.getTimestamp("updated_at");
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRole_level() {
		return role_level;
	}

	public void setRole_level(int role_level) {
		this.role_level = role_level;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "MemberModel [member_id=" + member_id + ", id=" + id + ", pw=" + pw + ", email=" + email
				+ ", role_level=" + role_level + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
	
	

}
