package com.levins.webportal.certificate.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

import com.levins.webportal.certificate.data.CertificateInfo;
import com.levins.webportal.certificate.data.UserToken;

class CertificateCreateThread extends Thread {
	private int CLIENT_REQUEST_TIMEOUT = 15 * 60 * 1000; // 15 min.
	private Socket connection;
	private DataInputStream in;
	private DataOutputStream out;
	private CreateNewBatFile batGenerator;

	 private static final int MAIL = 3;

	public CertificateCreateThread(Socket connection) throws IOException {
		this.connection = connection;
		this.connection.setSoTimeout(CLIENT_REQUEST_TIMEOUT);
		in = new DataInputStream(connection.getInputStream());
		out = new DataOutputStream(connection.getOutputStream());
		batGenerator = new CreateNewBatFile();
	}

	public void run() {
		try {
			out.writeUTF(CreateCertServer.GREETING_MESSAGE_TO_CLIENT);
			out.flush();
			String result = null;
			CreateCertServer.certificationListOnlyFromCurrentSession = new HashMap<String, CertificateInfo>();
			while (!isInterrupted()) {
				String input = in.readUTF();
				String[] currentInfo = input.replace("\"", "").split(";");
				if (hasUserExist(currentInfo)) {
					CertificateInfo certificate = CreateCertServer.getCertificationList().get(currentInfo[UserToken.USERPORTAL]);

					if (!hasSameMail(certificate, currentInfo)) {
						certificate.setEmail(currentInfo[MAIL]);
					}
					result = certificate.toString();
				} else {
					CertificateInfo certificate = batGenerator
							.generateCert(input);
					CreateCertServer.getCertificationList().put(
							certificate.getUserName(), certificate);
					CreateCertServer
							.getCertificationListOnlyFromCurrentSession().put(
									certificate.getUserName(), certificate);
					result = certificate.toString();
				}
				out.writeUTF(result);
				out.flush();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			String systemMessageWhenConnectionLost = String.format(
					"%s : Connection lost  : %s:%s\n", new Date(), connection
							.getInetAddress().getHostAddress(), connection
							.getPort());
			CreateCertServer
					.writeCsvFile(CreateCertServer.fileNameRecoveredRecords);
			System.out.println(systemMessageWhenConnectionLost);
		}

	}

	private boolean hasSameMail(CertificateInfo certificate,
			String[] currentInfo) {
		String newMail = currentInfo[MAIL];
		String currentMail = certificate.getEmail();
		return currentMail.equals(newMail);
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
}