package com.levins.webportal.certificate.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.levins.webportal.certificate.client.UI.ClientPanel;

public class FromInsisData {
	private String insisHost;
	private String insisPort;
	private String dataBaseName;
	private String insisUser;
	private String insisPass;
	private List<String> errorLog;

	public static final String NAME_FIELD = "NAME";
	public static final String USEREMAIL = "USEREMAIL";
	public static final String EGN = "EGN";
	public static final String SECURITY_ID = "SECURITY_ID";
	public static final String PATH = "PATH";
	public static final String CERT_PASS = "CERT_PASS";
	public static final String CERT_USER = "CERT_USER";

	public FromInsisData(String host, String port, String dataBaseName,
			String user, String pass) {
		this.insisHost = host;
		this.insisPort = port;
		this.dataBaseName = dataBaseName;
		this.insisUser = user;
		this.insisPass = pass;
		this.errorLog = new ArrayList<String>();
	}

	// TODO REMOVE
	public static void main(String[] args) throws SQLException {

		String host = "172.20.10.8";
		String port = "1521";
		String dataBaseName = "INSISDB";
		String user = "insis";
		String pass = "change2015";

		FromInsisData insis = new FromInsisData(host, port, dataBaseName, user,
				pass);
	}

	public List<String> selectWebPortalUserFromDataBase(String findingName)
			throws SQLException {
		String queryPortal = String
				.format("Select pp.name, pp.egn, ps.user_email, ps.security_id from p_people pp, p_staff ps where pp.man_id=ps.man_id and ps.security_id like '%s'",
						findingName);
		Connection conn = createConnectionToServer();

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);

		ResultSet result = preStatement.executeQuery();

		List<String> allRecordsFromServer = new ArrayList<String>();
		dataProcessingFromInsis(result, allRecordsFromServer);
		return allRecordsFromServer;
	}

	/**
	 * SELECT * FROM LEV_USERS_PORTAL p where p.egn like '%s' and p.security_id
	 * like '%s
	 * 
	 * @param userName
	 *            - first param
	 * @param egn
	 *            second param
	 * @return list with result
	 * @throws SQLException
	 */
	public List<String> searchFromDataBase(String userName, String egn)
			throws SQLException {
		String queryPortal = String
				.format("SELECT * FROM LEV_USERS_PORTAL where EGN like '%s' and SECURITY_ID like '%s'",
						egn, userName);
		Connection conn = createConnectionToServer();

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);

		ResultSet result = preStatement.executeQuery();

		List<String> allRecordsFromServer = dataProcessingCrateList(result);

		return allRecordsFromServer;
	}

	/**
	 * 
	 * @param securityID
	 *            - where clause
	 * @param egn
	 *            - where clause
	 * @param columnName
	 *            - column who wants to update
	 * @param value
	 *            - value who wants to update
	 * @return
	 */
	public boolean updateInToDB(String securityID, String egn,
			String columnName, String value) {
		String queryUP = String.format(
				"UPDATE LEV_USERS_PORTAL SET %s='%s' WHERE SECURITY_ID='%s'",
				columnName, value, securityID, egn);
		Connection conn = null;
		try {
			conn = createConnectionToServer();
		} catch (SQLException e) {
			String erroMessage = "Problem with connection on server FromInsisData.class line:108";
			ClientPanel.popUpMessageException(e, erroMessage);
			e.printStackTrace();
			return false;
		}
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(queryUP);
		} catch (SQLException e) {
			ClientPanel.popUpMessageException(e);
			e.printStackTrace();
			return false;
		}
		try {
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			String errorMessage = "Problem with execute Query";
			ClientPanel.popUpMessageException(e, errorMessage);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method who insert value into LEV_USERS_PORTAL table
	 * 
	 * @param user
	 * @param firstName
	 * @param lastName
	 * @param mail
	 * @param password
	 * @param path
	 * @param egn
	 * @return
	 */
	public boolean insertInToDB(String user, String firstName, String lastName,
			String mail, String password, String path, String egn) {
		String fullName = firstName + " " + lastName;

		String queryUP = String
				.format("INSERT INTO LEV_USERS_PORTAL (NAME,EGN,USEREMAIL,SECURITY_ID,PATH,CERT_PASS) VALUES ('%s','%s','%s','%s','%s','%s')",
						fullName, egn, mail, user, path, password);

		Connection conn = null;
		try {
			conn = createConnectionToServer();
		} catch (SQLException e) {
			ClientPanel
					.popUpMessageException(e,
							"Problem with connection on server FromInsisData.class line:108");
			e.printStackTrace();
			return false;
		}
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(queryUP);
		} catch (SQLException e) {
			ClientPanel.popUpMessageException(e);
			e.printStackTrace();
			return false;
		}
		try {
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			ClientPanel.popUpMessageException(e, "Problem with execute Query");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Create connection to INSIS IS
	 * 
	 * @return Object type Connection
	 * @throws SQLException
	 */
	private Connection createConnectionToServer() throws SQLException {
		// URL of Oracle database server
		String url = String.format("jdbc:oracle:thin:@%s:%s:%s",
				getInsisHost(), getInsisPort(), dataBaseName);

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", getInsisUser());
		props.setProperty("password", getInsisPassword());
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			ClientPanel.popUpMessageException(e, "Problem with oracle driver");
		}
		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	}

	/**
	 * This method processing data incoming from LEV_USERS_PORTAL
	 * 
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	private List<String> dataProcessingCrateList(ResultSet result)
			throws SQLException {
		List<String> listWithUsers = new ArrayList<String>();
		while (result.next()) {
			final String userName = result.getString("SECURITY_ID");
			final String name = result.getString("NAME");
			final String pass = result.getString("CERT_PASS");
			final String mail = result.getString("USEREMAIL");
			final String path = result.getString("PATH");
			final String egn = result.getString("EGN");

			if (userName == null || name == null || mail == null
					|| !DataValidator.validateMail(mail) || egn == null) {
				errorLog.add(String.format("%s;%s;%s;%s", userName, name, mail,
						egn));
				continue;
			}
			String nameEng = convertToEng(name);
			nameEng = splitCamelCase(nameEng);
			String regexSplitedName = " +";
			String[] splitFirstLastName = nameEng.split(regexSplitedName);
			String firstName = splitFirstLastName[0];
			String secondName = splitFirstLastName[1];
			String newRecord = String.format("%s;%s;%s;%s;%s;%s;%s", userName,
					firstName, secondName, mail, pass, path, egn);
			listWithUsers.add(newRecord);
		}
		return listWithUsers;
	}

	/**
	 * This method use incoming information from INSIS Tables
	 * 
	 * Get result from SQL query and return string with format:
	 * userName;fistName;lastName;mail;password;path;egn
	 * 
	 * @param result
	 * @param listWithUsers
	 * @throws SQLException
	 */
	private void dataProcessingFromInsis(ResultSet result,
			List<String> listWithUsers) throws SQLException {
		while (result.next()) {
			final String userName = result.getString("SECURITY_ID");
			final String name = result.getString("NAME");
			final String mail = result.getString("USER_EMAIL");
			final String egn = result.getString("EGN");

			if (userName == null || name == null || mail == null
					|| !DataValidator.validateMail(mail) || egn == null) {
				errorLog.add(String.format("%s;%s;%s;%s", userName, name, mail,
						egn));
				continue;
			}
			String nameEng = convertToEng(name);
			String[] splitFirstLastName = nameEng.split(" ");
			String firstName = splitFirstLastName[0];
			String secondName = splitFirstLastName[1];
			String emptyCells = "";
			String newRecord = String.format("%s;%s;%s;%s;%s;%s;%s", userName,
					firstName, secondName, mail, egn, emptyCells, emptyCells);
			listWithUsers.add(newRecord);
		}
	}

	/**
	 * This method checks if mail is valid
	 * 
	 * @param emailStr
	 *            - mail who want to check
	 * @return - true if mail is valid or false the otherwise
	 */

	/**
	 * Check if record exist into LEV_USERS_PORTAL table and return true if
	 * exist or false if isn't
	 * 
	 * @param searchingSecurityId
	 * @param searchingEgn
	 * @return - true or false
	 * @throws SQLException
	 */
	public boolean hasRecordExistsOnDataBase(String searchingSecurityId,
			String searchingEgn) throws SQLException {

		String queryPortal = String
				.format("SELECT (CASE WHEN EXISTS (SELECT * FROM LEV_USERS_PORTAL WHERE SECURITY_ID = '%s'and EGN = '%s') THEN '1' ELSE '0' end) from DUAL",
						searchingSecurityId, searchingEgn);

		Connection conn = createConnectionToServer();
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);
		ResultSet result = preStatement.executeQuery();
		boolean exists = false;
		if (result.next()) {

			exists = result.getBoolean(1);
		}
		return exists;
	}

	/**
	 * Check if record exist into LEV_USERS_PORTAL table and return true if
	 * exist or false if isn't
	 * 
	 * @param searchingSecurityId
	 * @return
	 * @throws SQLException
	 */
	public boolean hasRecordExistsOnINSIS(String searchingSecurityId)
			throws SQLException {

		String queryPortal = String
				.format("SELECT (CASE WHEN EXISTS (Select pp.name, pp.egn, ps.user_email, ps.security_id from p_people pp, p_staff ps where pp.man_id=ps.man_id and ps.security_id like '%s') THEN '1' ELSE '0' end) from DUAL",
						searchingSecurityId);

		Connection conn = createConnectionToServer();
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);
		ResultSet result = preStatement.executeQuery();
		boolean exists = false;
		if (result.next()) {

			exists = result.getBoolean(1);
		}
		return exists;
	}

	/**
	 * Convert cyrillic String in to English. If has some unrecognized symbol
	 * get the same symbol and put it in same place
	 * 
	 * @return - converted String
	 */
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
			return "A";
		case 'а':
			return "a";
		case 'Б':
			return "B";
		case 'б':
			return "b";
		case 'В':
			return "V";
		case 'в':
			return "v";
		case 'Г':
			return "G";
		case 'г':
			return "g";
		case 'Д':
			return "D";
		case 'д':
			return "d";
		case 'Е':
			return "E";
		case 'е':
			return "e";
		case 'Ж':
			return "Zh";
		case 'ж':
			return "zh";
		case 'З':
			return "Z";
		case 'з':
			return "z";
		case 'И':
			return "I";
		case 'и':
			return "i";
		case 'Й':
			return "J";
		case 'й':
			return "j";
		case 'К':
			return "K";
		case 'к':
			return "k";
		case 'Л':
			return "L";
		case 'л':
			return "l";
		case 'М':
			return "M";
		case 'м':
			return "m";
		case 'Н':
			return "N";
		case 'н':
			return "n";
		case 'О':
			return "O";
		case 'о':
			return "o";
		case 'П':
			return "P";
		case 'п':
			return "p";
		case 'Р':
			return "R";
		case 'р':
			return "r";
		case 'С':
			return "S";
		case 'с':
			return "s";
		case 'Т':
			return "T";
		case 'т':
			return "t";
		case 'У':
			return "U";
		case 'у':
			return "u";
		case 'Ф':
			return "F";
		case 'ф':
			return "f";
		case 'Х':
			return "H";
		case 'х':
			return "h";
		case 'Ц':
			return "Tz";
		case 'ц':
			return "tz";
		case 'Ч':
			return "Ch";
		case 'ч':
			return "ch";
		case 'Ш':
			return "Sh";
		case 'ш':
			return "sh";
		case 'Щ':
			return "Sht";
		case 'щ':
			return "sht";
		case 'Ъ':
			return "A";
		case 'ъ':
			return "a";
		case 'Ь':
			return "Y";
		case 'ь':
			return "y";
		case 'Ю':
			return "Yu";
		case 'ю':
			return "yu";
		case 'Я':
			return "Ya";
		case 'я':
			return "ya";
		case ' ':
			return " ";
		default:
			break;
		}
		return String.valueOf(latter);
	}

	/**
	 * if glued to one another name them into major principle of small letters
	 * 
	 * @param stringToSplit
	 * @return
	 */
	static String splitCamelCase(String stringToSplit) {
		return stringToSplit.replaceAll(String.format("%s|%s|%s",
				"(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}

	public String getInsisHost() {
		return insisHost;
	}

	public void setInsisHost(String insisHost) {
		this.insisHost = insisHost;
	}

	public String getInsisPort() {
		return insisPort;
	}

	public void setInsisPort(String insisPort) {
		this.insisPort = insisPort;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getInsisUser() {
		return insisUser;
	}

	public void setInsisUser(String insisUser) {
		this.insisUser = insisUser;
	}

	public String getInsisPassword() {
		return insisPass;
	}

	public void setInsisPass(String insisPass) {
		this.insisPass = insisPass;
	}

}