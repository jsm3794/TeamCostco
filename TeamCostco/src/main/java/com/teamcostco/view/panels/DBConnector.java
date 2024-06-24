package main.java.com.teamcostco.view.panels;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	private static String driverPath = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@3.34.139.200:1521:xe";
	private String user;
	private String password;
	
	static {
		try {
			Class.forName(driverPath);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 못 찾음");
		}
	}
	
	public DBConnector(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	public Connection getConnection() throws SQLException  {
		return DriverManager.getConnection(url, user, password);
	}
}

