package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MemberModel {
	private String member_id;
	private String id;
	private String pw;
	private String name;
	private String email;
	private String phone_number;
	private Date create_date;
	private int member_point;
	private int membership_level;

	public MemberModel(ResultSet rs) throws SQLException {
		this.member_id = rs.getString("employee_id");
		this.id = rs.getString("login_id");
		this.pw = rs.getString("login_pw");
		this.name = rs.getString("employee_name");
		this.email = rs.getString("employee_email");
		this.phone_number = rs.getString("employee_phone_number");
		this.create_date = rs.getTimestamp("create_date");
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public int getMember_point() {
		return member_point;
	}

	public void setMember_point(int member_point) {
		this.member_point = member_point;
	}

	public int getMembership_level() {
		return membership_level;
	}

	public void setMembership_level(int membership_level) {
		this.membership_level = membership_level;
	}

	@Override
	public String toString() {
		return "MemberModel [member_id=" + member_id + ", id=" + id + ", pw=" + pw + ", name=" + name + ", email="
				+ email + ", phone_number=" + phone_number + ", create_date=" + create_date + ", member_point="
				+ member_point + ", membership_level=" + membership_level + "]";
	}

}
