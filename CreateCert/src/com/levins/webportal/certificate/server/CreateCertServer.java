package com.levins.webportal.certificate.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.levins.webportal.certificate.data.CertificateInfo;
import com.levins.webportal.certificate.data.UserToken;

public class CreateCertServer extends Thread {
	public static final int LISTENING_PORT = 3333;
	final static String STAR_SERVER_MESSAGE = "Server started listening on TCP port ";
	final static String GREETING_MESSAGE_TO_CLIENT = "You are connected to server.\n";

	// W00000001_01;firstName;lastName;password;mail;pathToCurrentCertificateFile
	// private static final String NEW_LINE_SEPARATOR = "\n";

	// private static HashMap<String, CertificateInfo> certificationList;
	// protected static HashMap<String, CertificateInfo>
	// certificationListOnlyFromCurrentSession;

	@Override
	public void run() {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(LISTENING_PORT);
			System.out.println(STAR_SERVER_MESSAGE + LISTENING_PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				CertificateCreateThread certificateCreateClientThread = new CertificateCreateThread(
						socket);
				certificateCreateClientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method return HashMap<String, CertificateInfo>, created by csv file
	 * 
	 * @param fileName
	 *            - file which retain all users created before
	 */
	public static HashMap<String, CertificateInfo> readCsvFile(String fileName) {
		BufferedReader fileReader = null;
		HashMap<String, CertificateInfo> restoretdList = new HashMap<String, CertificateInfo>();
		String line = "";
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			// Skip first header line
			fileReader.readLine();

			while ((line = fileReader.readLine()) != null) {
				String commaDelimiter = ";";
				String[] tokens = line.split(commaDelimiter);
				if (tokens.length > 0) {
					createCertificateInfoObject(restoretdList, tokens);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
			}
		}
		return restoretdList;
	}

	/**
	 * 
	 * @param restoretdList
	 *            - read records are retained
	 * @param tokens
	 */
	private static void createCertificateInfoObject(
			HashMap<String, CertificateInfo> restoretdList, String[] tokens) {
		CertificateInfo restoredCert = new CertificateInfo(
				tokens[UserToken.USERPORTAL], tokens[UserToken.FIRSTNAME],
				tokens[UserToken.LASTNAME], tokens[UserToken.PASSWORD],
				tokens[UserToken.MAIL], tokens[UserToken.PATHTOCERT],
				tokens[UserToken.EGN]);
		restoretdList.put(tokens[UserToken.USERPORTAL], restoredCert);
	}

}
