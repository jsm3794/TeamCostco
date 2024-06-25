package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EmployeeModel {
	private String emp_id;
	private String login_id;
	private String login_pw;
	private String emp_email;
	private String emp_phone_number;
	private Date emp_create_date;
	
	public EmployeeModel(ResultSet rs) throws SQLException {
		this.emp_id = rs.getString("employee_id");
		this.login_id = rs.getString("login_id");
		this.login_pw = rs.getString("login_pw");
		this.emp_email = rs.getString("employee_email");
		this.emp_phone_number = rs.getString("employee_phone_number");
		this.emp_create_date = rs.getTimestamp("create_date");
	}

	public String getEmp_id() {
		return emp_id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public String getLogin_pw() {
		return login_pw;
	}

	public String getEmp_email() {
		return emp_email;
	}


	public String getEmp_phone_number() {
		return emp_phone_number;
	}

	public Date getEmp_create_date() {
		return emp_create_date;
	}

	@Override
	public String toString() {
		return "MemberModel [emp_id=" + emp_id + ", login_id=" + login_id + ", login_pw=" + login_pw + ", emp_email="
				+ emp_email + ", emp_phone_number=" + emp_phone_number + ", emp_create_date=" + emp_create_date + "]";
	}







}
