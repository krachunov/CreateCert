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
	static int count = 0;
	private List<String> errorLog;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public FromInsisData(String host, String port, String dataBaseName,
			String user, String pass) {
		this.insisHost = host;
		this.insisPort = port;
		this.dataBaseName = dataBaseName;
		this.insisUser = user;
		this.insisPass = pass;
		this.errorLog = new ArrayList<String>();
	}

	public static void main(String[] args) throws SQLException {

		// System.out.println(convertToEng("Чочо Яворов"));

		String host = "172.20.10.8";
		String port = "1521";
		String dataBaseName = "INSISDB";
		String user = "insis";
		String pass = "change2015";

		String findUser = "W0008%";
		FromInsisData insis = new FromInsisData(host, port, dataBaseName, user,
				pass);
		List<String> a = insis.resultFromDataBase(findUser);
	for (String string : a) {
		System.out.println(string);
	};
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
				.format("Select d.username,(select pp.name from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) ИМЕ,(select pp1.egn from p_people pp1, p_staff ps1 where ps1.man_id=pp1.man_id and ps1.security_id=d.username) EGN,(select ps.user_email from p_people pp, p_staff ps where ps.man_id=pp.man_id and ps.security_id=d.username) EMAIL from dba_users d where d.username like '%s'",
						findingName);
		Connection conn = createConnectionToServer();

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(queryPortal);

		ResultSet result = preStatement.executeQuery();

		List<String> allRecordsFromServer = new ArrayList<String>();
		dataProcessing(result, allRecordsFromServer);
		return allRecordsFromServer;
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
			ClientPanel.popUpMessageException(e,"problem with oracle driver");
		}
		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	}

	private void dataProcessing(ResultSet result, List<String> listWithUsers)
			throws SQLException {
		while (result.next()) {
			final String userName = result.getString("USERNAME");
			final String name = result.getString("ИМЕ");
			final String mail = result.getString("EMAIL");

			if (userName == null || name == null || mail == null
					|| !validateMail(mail)) {
				errorLog.add(String.format("%s;%s;%s", userName, name, mail));
				continue;
			}
			count++;
			String nameEng = convertToEng(name);
			String[] splitFirstLastName = nameEng.split(" ");
			String firstName = splitFirstLastName[0];
			String secondName = splitFirstLastName[1];
			String newRecord = String.format("%s;%s;%s;%s", userName,
					firstName, secondName, mail);
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
