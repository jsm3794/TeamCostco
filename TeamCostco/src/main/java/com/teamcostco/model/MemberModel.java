package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MemberModel {
	private String member_id;
	private String id;
	private String pw;
	private String email;
	private String phone_number;
	private Date create_date;
	private int member_point;
	private int membership_level;
	
	public MemberModel(ResultSet rs) throws SQLException {
		this.member_id = rs.getString("employee_id");
		this.id = rs.getString("login_id");
		this.pw = rs.getString("login_pw");
		this.email = rs.getString("employee_email");
		this.phone_number = rs.getString("employee_phone_number");
		this.create_date = rs.getTimestamp("create_date");
		//this.member_point = rs.getInt("member_point");
		//this.membership_level = rs.getInt("membership_level");
	}

	
	
	public String getMember_id() {
		return member_id;
	}



	public String getId() {
		return id;
	}



	public String getPw() {
		return pw;
	}



	public String getEmail() {
		return email;
	}


	public String getPhone_number() {
		return phone_number;
	}



	public Date getCreate_date() {
		return create_date;
	}



	public int getMember_point() {
		return member_point;
	}



	public int getMembership_level() {
		return membership_level;
	}



	@Override
	public String toString() {
		return "MemberModel [member_id=" + member_id + ", id=" + id + ", pw=" + pw + ", email=" + email
				+ ", phone_number=" + phone_number + ", create_date=" + create_date
				+ ", member_point=" + member_point + ", membership_level=" + membership_level + "]";
	}


}
