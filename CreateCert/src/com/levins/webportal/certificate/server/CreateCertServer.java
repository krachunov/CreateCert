package com.levins.webportal.certificate.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.levins.webportal.certificate.data.CertificateInfo;

public class CreateCertServer {
	public static final int LISTENING_PORT = 3333;
	final static String STAR_SERVER_MESSAGE = "Server started listening on TCP port ";
	final static String GREETING_MESSAGE_TO_CLIENT = "You are connected to server.\n";

	// W00000001_01;firstName;lastName;password;mail;pathToCurrentCertificateFile
	private static final int USER_PORTAL = 0;
	private static final int FIRST_NAME = 1;
	private static final int LAST_NAME = 2;
	private static final int PASSWORD = 3;
	private static final int MAIL = 4;
	private static final int PATH_TO_CERT = 5;
	private static final String COMMA_DELIMITER = ";";
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static List<CertificateInfo> certificationList = new ArrayList<CertificateInfo>();

	public static void main(String[] args) throws IOException {

		String fileName = System.getProperty("resources/oldCer.csv");
		// readCsvFile(fileName);
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
		} finally {
			serverSocket.close();
		}

	}

	public static List<CertificateInfo> getCertificationList() {
		return certificationList;
	}

	public static void setCertificationList(
			List<CertificateInfo> certificationList) {
		CreateCertServer.certificationList = certificationList;
	}

	/**
	 * 
	 * @param fileName
	 *            - file which retain all users created before
	 */
	public static void readCsvFile(String fileName) {
		BufferedReader fileReader = null;
		List<CertificateInfo> restoretdList = getCertificationList();
		String line = "";
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			fileReader.readLine();

			while ((line = fileReader.readLine()) != null) {
				String commaDelimiter = ";";
				String[] tokens = line.split(commaDelimiter);
				if (tokens.length > 0) {
					CertificateInfo restoredCert = new CertificateInfo(
							tokens[USER_PORTAL], tokens[FIRST_NAME],
							tokens[LAST_NAME],
							Integer.valueOf(tokens[PASSWORD]), tokens[MAIL],
							tokens[PATH_TO_CERT]);
					restoretdList.add(restoredCert);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeCsvFile(String fileName) {
		String FILE_HEADER = "user;firstName;lastName;password;mail;path";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName,true);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (CertificateInfo certificateInfo : certificationList) {
				fileWriter
						.append(String.valueOf(certificateInfo.getUserName()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(certificateInfo.getFirstName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(certificateInfo.getLastName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(certificateInfo.getPassword());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(certificateInfo.getEmail()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(certificateInfo
						.getPathToCertificateFile()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

		} catch (IOException e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out
						.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

}
