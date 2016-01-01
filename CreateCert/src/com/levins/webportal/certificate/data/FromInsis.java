package com.levins.webportal.certificate.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class FromInsis {

	public static void main(String[] args) throws SQLException {

		String hostAndPort = "172.20.10.8:1521";
		String dataBaseName = "INSISDB";
		String user = "insis";
		String pass = "change2015";

		String findUser = "W%";
		printDataBaseResult(hostAndPort, dataBaseName, user, pass, findUser);
		System.out.println("done");
	}

	/**
	 * 
	 * @param hostAndPort
	 * @param dataBaseName
	 * @param userDataBase
	 * @param passwordDataBase
	 * @param findingName
	 * @throws SQLException
	 */
	private static void printDataBaseResult(String hostAndPort,
			String dataBaseName, String userDataBase, String passwordDataBase,
			String findingName) throws SQLException {
		String queryPortal = String
				.format("Select d.username,(select pp.name from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) »Ã≈,(select pp1.egn from p_people pp1, p_staff ps1 where ps1.man_id=pp1.man_id and ps1.security_id=d.username) EGN,(select ps.user_email from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) EMAIL from dba_users d where d.username like '%s'",
						findingName);
		// URL of Oracle database server
		String url = String.format("jdbc:oracle:thin:@%s:%s", hostAndPort,
				dataBaseName);

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", userDataBase);
		props.setProperty("password", passwordDataBase);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);

		ResultSet result = preStatement.executeQuery();

		while (result.next()) {
			printResult(result);
		}
	}

	private static void printResult(ResultSet result) throws SQLException {
		final String userName = result.getString("USERNAME");
		final String name = result.getString("»Ã≈");
		String egn = result.getString("EGN");
		final String mail = result.getString("EMAIL");

		System.out.printf("User Portal:%s Name: %s EGN:%s - mail: %s  \n",
				userName, name, egn, mail);
	}
}
