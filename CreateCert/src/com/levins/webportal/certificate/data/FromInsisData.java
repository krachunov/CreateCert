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

		// System.out.println(convertToEng("Чочо Яворов"));

		String host = "172.20.10.8";
		String port = "1521";
		String dataBaseName = "INSISDB";
		String user = "insis";
		String pass = "change2015";
		// String user = "s0000";
		// String pass = "r3p0rts";

		String findUser = "W%";
		FromInsisData insis = new FromInsisData(host, port, dataBaseName, user,
				pass);
		insis.insertInToDB();
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
		// USERNAME IME EGN EMAIL
		// String queryPortal = String
		// .format("Select d.username,(select pp.name from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) ИМЕ,(select pp1.egn from p_people pp1, p_staff ps1 where ps1.man_id=pp1.man_id and ps1.security_id=d.username) EGN,(select ps.user_email from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) EMAIL from dba_users d where d.username like '%s'",
		// findingName);

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

	public boolean insertInToDB() {

		String queryUP = String
				.format("INSERT INTO LEV_USERS_PORTAL (SECURITY_ID) VALUES ('w000000')");

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

	private void dataProcessing(ResultSet result, List<String> listWithUsers)
			throws SQLException {
		while (result.next()) {
			final String userName = result.getString("SECURITY_ID");
			final String name = result.getString("NAME");
			final String mail = result.getString("USER_EMAIL");
			final String egn = result.getString("EGN");

			if (userName == null || name == null || mail == null
					|| !validateMail(mail) || egn == null) {
				errorLog.add(String.format("%s;%s;%s;%s", userName, name, mail,
						egn));
				continue;
			}
			count++;
			String nameEng = convertToEng(name);
			String[] splitFirstLastName = nameEng.split(" ");
			String firstName = splitFirstLastName[0];
			String secondName = splitFirstLastName[1];
			String newRecord = String.format("%s;%s;%s;%s;%s", userName,
					firstName, secondName, mail, egn);
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
	private static boolean validateMail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
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