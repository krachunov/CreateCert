package com.levins.webportal.certificate.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FromInsisData {
	static int count = 0;

	public static void main(String[] args) throws SQLException {

		// System.out.println(convertToEng("Чочо Яворов"));

		String hostAndPort = "172.20.10.8:1521";
		String dataBaseName = "INSISDB";
		String user = "insis";
		String pass = "change2015";

		String findUser = "W%";
		printDataBaseResult(hostAndPort, dataBaseName, user, pass, findUser);
		System.out.println("done count: " + count);
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
				.format("Select d.username,(select pp.name from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) ИМЕ,(select pp1.egn from p_people pp1, p_staff ps1 where ps1.man_id=pp1.man_id and ps1.security_id=d.username) EGN,(select ps.user_email from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) EMAIL from dba_users d where d.username like '%s'",
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
		List<String> allRecordsFromServer = new ArrayList<String>();
		// TODO
		while (result.next()) {
			final String userName = result.getString("USERNAME");
			final String name = result.getString("ИМЕ");
			final String mail = result.getString("EMAIL");
			List<String> errorLog = new ArrayList<String>();

			if (userName == null || name == null || mail == null) {
				errorLog.add(String.format("%s;%s;%s", userName, name, mail));
				continue;
			}
			// String[] splitFirstLastName = name.split(" ");
			String res = String.format("%s;%s;%s", userName, name, mail);
			// System.out.println(res);
			printResult(result);
		}
	}

	private static void printResult(ResultSet result) throws SQLException {
		count++;
		final String userName = result.getString("USERNAME");
		final String name = result.getString("ИМЕ");
		String egn = result.getString("EGN");
		final String mail = result.getString("EMAIL");

		System.out.printf("User Portal:%s Name: %s EGN:%s - mail: %s  \n",
				userName, name, egn, mail);
	}

	private static String convertToEng(String input) {
		char[] wordByLetter = input.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordByLetter.length; i++) {
			char tempLetter = wordByLetter[i];
			sb.append(convertBulgarianLetterToEnglish(tempLetter));
		}
		return sb.toString();
	}

	private static String convertBulgarianLetterToEnglish(char latter) {
		switch (latter) {
		case 'А':
		case 'а':
			return "a";
		case 'Б':
		case 'б':
			return "b";
		case 'В':
		case 'в':
			return "v";
		case 'Г':
		case 'г':
			return "g";
		case 'Д':
		case 'д':
			return "d";
		case 'Е':
		case 'е':
			return "e";
		case 'Ж':
		case 'ж':
			return "zh";
		case 'З':
		case 'з':
			return "z";
		case 'И':
		case 'и':
			return "i";
		case 'Й':
		case 'й':
			return "j";
		case 'К':
		case 'к':
			return "k";
		case 'Л':
		case 'л':
			return "l";
		case 'М':
		case 'м':
			return "m";
		case 'Н':
		case 'н':
			return "n";
		case 'О':
		case 'о':
			return "o";
		case 'П':
		case 'п':
			return "p";
		case 'Р':
		case 'р':
			return "r";
		case 'С':
		case 'с':
			return "s";
		case 'Т':
		case 'т':
			return "t";
		case 'У':
		case 'у':
			return "u";
		case 'Ф':
		case 'ф':
			return "f";
		case 'Х':
		case 'х':
			return "h";
		case 'Ц':
		case 'ц':
			return "tz";
		case 'Ч':
		case 'ч':
			return "ch";
		case 'Ш':
		case 'ш':
			return "sh";
		case 'Щ':
		case 'щ':
			return "sht";
		case 'Ъ':
		case 'ъ':
			return "a";
		case 'Ь':
		case 'ь':
			return "y";
		case 'Ю':
		case 'ю':
			return "yu";
		case 'Я':
		case 'я':
			return "ya";
		case ' ':
			return " ";
		default:
			break;
		}
		return String.valueOf(latter);
	}
}
