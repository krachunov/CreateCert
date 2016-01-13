package com.levins.webportal.certificate.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	static int count = 0;
	private List<String> errorLog;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	// TODO REGEX MAIL
	/**
	 * http://www.mkyong.com/regular-expressions/how-to-validate-email-address-
	 * with-regular-expression/
	 */
	// private static final String EMAIL_PATTERN =
	// "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	// + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
		// String user = "s0000";
		// String pass = "r3p0rts";
		// String findUser = "W%";

		FromInsisData insis = new FromInsisData(host, port, dataBaseName, user,
				pass);
		// System.out.println(insis.hasRecordExistsOnCurrentField("EGN","3103083902"));
		// insis.updateInToDB("EGN", "6306244048", "PATH", "13_01_2016\\");

		// List<String> searchFromDataBase = insis.searchFromDataBase("W1%",
		// "%");
		// System.out.println(searchFromDataBase.size());
		//
		// insis.insertInToDB();
		// List<String> a = insis.resultFromDataBase(findUser);
		// System.out.println(a.size());
		// for (String string : a) {
		// System.out.println(string);
		// }
	}

	/**
	 * 
	 * @param findingName
	 * @return
	 * @throws SQLException
	 */
	public List<String> resultFromDataBase(String findingName)
			throws SQLException {
		String queryPortal = String
				.format("Select pp.name, pp.egn, ps.user_email, ps.security_id from p_people pp, p_staff ps where pp.man_id=ps.man_id and ps.security_id like '%s'",
						findingName);
		Connection conn = createConnectionToServer();

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);

		ResultSet result = preStatement.executeQuery();

		List<String> allRecordsFromServer = new ArrayList<String>();
		dataProcessing(result, allRecordsFromServer);
		return allRecordsFromServer;
	}

	public List<String> searchFromDataBase(String userName, String egn)
			throws SQLException {
		String queryPortal = String
				.format("SELECT * FROM LEV_USERS_PORTAL p where p.egn like '%s' and p.security_id like '%s'",
						egn, userName);
		Connection conn = createConnectionToServer();

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);

		ResultSet result = preStatement.executeQuery();

		List<String> allRecordsFromServer = new ArrayList<String>();
		dataProcessingCrateList(result, allRecordsFromServer);
		return allRecordsFromServer;
	}

	/**
	 * UPDATE LEV_USERS_PORTAL SET "columnValue"="value" WHERE
	 * "columnWhere"="valueWhere"
	 * 
	 * @param columnWhere
	 *            - column to filter
	 * @param valueWhere
	 *            - value on which to filter
	 * @param columnValue
	 *            - column to update
	 * @param value
	 *            - value to be submitted
	 * @return
	 */
	public boolean updateInToDB(String columnWhere, String valueWhere,
			String columnValue, String value) {
		String queryUP = String.format(
				"UPDATE LEV_USERS_PORTAL SET %s='%s' WHERE %s='%s'",
				columnValue, value, columnWhere, valueWhere);
		Connection conn = null;
		try {
			conn = createConnectionToServer();
		} catch (SQLException e) {
			String erroMessage = "Problem with connection on server FromInsisData.class line:108";
			ClientPanel.popUpMessageException(e, erroMessage);
			e.printStackTrace();
		}
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(queryUP);
		} catch (SQLException e) {
			ClientPanel.popUpMessageException(e);
			e.printStackTrace();
		}
		try {
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			String errorMessage = "Problem with execute Query";
			ClientPanel.popUpMessageException(e, errorMessage);
			e.printStackTrace();
		}

		return false;
	}

	public boolean insertInToDB() {

		String queryUP = String
				.format("INSERT INTO LEV_USERS_PORTAL (SECURITY_ID,EGN) VALUES ('w000000','1234567890')");

		Connection conn = null;
		try {
			conn = createConnectionToServer();
		} catch (SQLException e) {
			ClientPanel
					.popUpMessageException(e,
							"Problem with connection on server FromInsisData.class line:108");
			e.printStackTrace();
		}
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(queryUP);
		} catch (SQLException e) {
			ClientPanel.popUpMessageException(e);
			e.printStackTrace();
		}
		// execute insert SQL stetement
		try {
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			ClientPanel.popUpMessageException(e, "Problem with execute Query");
			e.printStackTrace();
		}

		return false;
	}

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

	private void dataProcessingCrateList(ResultSet result,
			List<String> listWithUsers) throws SQLException {
		while (result.next()) {
			final String userName = result.getString("SECURITY_ID");
			final String name = result.getString("NAME");
			final String pass = result.getString("CERT_PASS");
			final String mail = result.getString("USEREMAIL");
			final String path = result.getString("PATH");
			final String egn = result.getString("EGN");

			if (userName == null || name == null || mail == null
					|| !validateMail(mail) || egn == null) {
				errorLog.add(String.format("%s;%s;%s;%s", userName, name, mail,
						egn));
				continue;
			}
			count++;
			String nameEng = convertToEng(name);
			nameEng = splitCamelCase(nameEng);
			String regexSplitedName = " +";
			String[] splitFirstLastName = nameEng.split(regexSplitedName);
			String firstName = splitFirstLastName[0];
			String secondName = splitFirstLastName[1];
			String newRecord = String.format("%s;%s;%s;%s;%s;%s;%s", userName,firstName, secondName, mail,pass,  path, egn);
			listWithUsers.add(newRecord);
		}
	}

	private void dataProcessing(ResultSet result, List<String> listWithUsers)
			throws SQLException {
		while (result.next()) {
			final String userName = result.getString("SECURITY_ID");
			final String name = result.getString("NAME");
			final String mail = result.getString("USER_EMAIL");
			final String pass = result.getString("CERT_PASS");
			final String path = result.getString("PATH");
			final String egn = result.getString("EGN");

			if (userName == null || name == null || mail == null
					|| !validateMail(mail) || egn == null) {
				errorLog.add(String.format("%s;%s;%s;%s", userName, name, mail,
						egn));
				continue;
			}
			// TODO add another index stuf
			count++;
			String nameEng = convertToEng(name);
			String[] splitFirstLastName = nameEng.split(" ");
			String firstName = splitFirstLastName[0];
			String secondName = splitFirstLastName[1];
			String newRecord = String.format("%s;%s;%s;%s;%s", userName,
					firstName, secondName, mail, pass, path, egn);
			listWithUsers.add(newRecord);

			// USERPORTAL_VALUE(0),
			// FIRSTNAME_VALUE(1),
			// LASTNAME_VALUE(2),
			// MAIL_VALUE(3),
			// PASSWORD_VALUE(4),
			// PATHTOCERT_VALUE(5),
			// EGN_VALUE(6);
		}
	}

	/**
	 * This method checks if mail is valid
	 * 
	 * @param emailStr
	 *            - mail who want to check
	 * @return - true if mail is valid or false the otherwise
	 */
	private static boolean validateMail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	/**
	 * Query - "SELECT * FROM LEV_USERS_PORTAL  where %s like '%s'"
	 * 
	 * @param field
	 *            - enter the field who want to search
	 * @param searchingValue
	 *            - value who want to search
	 * @return true if exists or false
	 * @throws SQLException
	 */
	public boolean hasRecordExistsOnCurrentField(String field,
			String searchingValue) throws SQLException {
		String queryPortal = String.format(
				"SELECT * FROM LEV_USERS_PORTAL  where %s like '%s'", field,
				searchingValue);
		Connection conn = createConnectionToServer();

		PreparedStatement preStatement = conn.prepareStatement(queryPortal);

		ResultSet result = preStatement.executeQuery();

		String userName = null;
		while (result.next()) {
			userName = result.getString(field);
			if (searchingValue.equals(userName)) {
				return true;
			}
		}
		return false;

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

	static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s",
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