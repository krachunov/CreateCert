package com.levins.webportal.certificate.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import com.levins.webportal.certificate.data.CertificateInfo;
import com.levins.webportal.certificate.data.FromInsisData;
import com.levins.webportal.certificate.data.UserToken;

class CertificateCreateThread extends Thread {
	private int CLIENT_REQUEST_TIMEOUT = 15 * 60 * 1000; // 15 min.
	private Socket connection;
	private DataInputStream in;
	private DataOutputStream out;
	private CreateNewBatFile batGenerator;


	public CertificateCreateThread(Socket connection) throws IOException {
		this.connection = connection;
		this.connection.setSoTimeout(CLIENT_REQUEST_TIMEOUT);
		in = new DataInputStream(connection.getInputStream());
		out = new DataOutputStream(connection.getOutputStream());
		batGenerator = new CreateNewBatFile();
	}

	public void run() {
		FromInsisData connection = createConnection();

		try {
			out.writeUTF(CreateCertServer.GREETING_MESSAGE_TO_CLIENT);
			out.flush();
			String result = null;
			CreateCertServer.certificationListOnlyFromCurrentSession = new HashMap<String, CertificateInfo>();
			while (!isInterrupted()) {
				String input = in.readUTF();

				String[] currentInfo = input.replace("\"", "").split(";");

				if (!hasUserExistOnDataBase(currentInfo, connection)
						&& !hasEGNExistOnDataBase(currentInfo, connection)) {
					System.out.println("IMAA");
				}

				if (hasUserExist(currentInfo)) {
					CertificateInfo certificate = CreateCertServer
							.getCertificationList().get(
									currentInfo[UserToken.USERPORTAL]);

					CreateCertServer
							.getCertificationListOnlyFromCurrentSession().put(
									certificate.getUserName(), certificate);
					result = certificate.toString();
				} else {
					result = createNewCertificate(input);
				}
				out.writeUTF(result);
				out.flush();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			printSystemExitMessage();
			CreateCertServer
					.writeCsvFile(CreateCertServer.fileNameRecoveredRecords);
		}

	}

	private void printSystemExitMessage() {
		String systemMessageWhenConnectionLost = String.format(
				"%s : Connection lost  : %s:%s\n", new Date(), connection
						.getInetAddress().getHostAddress(), connection
						.getPort());
		System.out.println(systemMessageWhenConnectionLost);
	}

	/**
	 * 
	 * @param input
	 *            - Incoming info from client
	 * @return - String with info for new certificate (call toString)
	 * @throws IOException
	 */
	private String createNewCertificate(String input) throws IOException {
		String result;
		CertificateInfo certificate = batGenerator.generateCert(input);
		CreateCertServer.getCertificationList().put(certificate.getUserName(),
				certificate);
		CreateCertServer.getCertificationListOnlyFromCurrentSession().put(
				certificate.getUserName(), certificate);
		result = certificate.toString();
		return result;
	}

	/**
	 * Check whether the user was ever created u
	 * 
	 * @param currentInfo
	 *            - array from String with spited element
	 * @return true if user exist or false is not
	 */
	private boolean hasUserExist(String[] currentInfo) {
		return CreateCertServer.getCertificationList().containsKey(
				currentInfo[UserToken.USERPORTAL]);
	}

	private boolean hasUserExistOnDataBase(String[] currentInfo,
			FromInsisData connection) throws SQLException {
		String searchingName = currentInfo[UserToken.USERPORTAL];
		String fildInDatabase = "security_ID";

		return connection.hasRecordExistsOnCurrentField(fildInDatabase,
				searchingName);
	}

	private boolean hasEGNExistOnDataBase(String[] currentInfo,
			FromInsisData connection) throws SQLException {
		String searchingEGN = currentInfo[UserToken.EGN];
		String fildInDatabase = "EGN";

		return connection.hasRecordExistsOnCurrentField(fildInDatabase,searchingEGN);
	}

	private FromInsisData createConnection() {
		String host = "172.20.10.8";
		String port = "1521";
		String dataBaseName = "INSISDB";
		String user = "insis";
		String pass = "change2015";

		FromInsisData conn = new FromInsisData(host, port, dataBaseName, user,
				pass);
		return conn;
	}
}