package com.levins.webportal.certificate.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class FromInsis {

	static String query = "select * from policy p where p.username like 'W00088061_13'";
	static String queryPortal = "Select d.username from dba_users d where d.username like 'W19029015_%'";

	public static void main(String[] args) throws SQLException {

		// URL of Oracle database server
		String url = "jdbc:oracle:thin:@172.20.10.8:1521:INSISDB";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "s0000");
		props.setProperty("password", "r3s3rv3");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);

		String sql = "'select sysdate as current_day from dual";

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(query);

		ResultSet result = preStatement.executeQuery();

		while (result.next()) {
			System.out.println("RESULT : " + result.getString("USERNAME"));

		}
		System.out.println("done");
	}
}
