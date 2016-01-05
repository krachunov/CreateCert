package com.levins.webportal.certificate.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import com.levins.webportal.certificate.data.CertificateInfo;

public class CreateCertServer extends Thread {
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

	private static HashMap<String, CertificateInfo> certificationList;
	protected static HashMap<String, CertificateInfo> certificationListOnlyFromCurrentSession;
	public static String fileNameRecoveredRecords;

	
	@SuppressWarnings("static-access")
	public CreateCertServer(String fileTorecoveryOldRecords) {
		this.fileNameRecoveredRecords = fileTorecoveryOldRecords;
	}

	@Override
	public void run() {

		// Load older record
		if (chekFileExist(fileNameRecoveredRecords)) {
			HashMap<String, CertificateInfo> restoredList = readCsvFile(fileNameRecoveredRecords);
			setCertificationList(restoredList);
		} else {
			certificationList = new HashMap<String, CertificateInfo>();
		}

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

	public static HashMap<String, CertificateInfo> getCertificationList() {
		return certificationList;
	}

	public static void setCertificationList(
			HashMap<String, CertificateInfo> certificationList) {
		CreateCertServer.certificationList = certificationList;
	}

	public static HashMap<String, CertificateInfo> getCertificationListOnlyFromCurrentSession() {
		return certificationListOnlyFromCurrentSession;
	}

	public static void setCertificationListOnlyFromCurrentSession(
			HashMap<String, CertificateInfo> certificationListOnlyFromCurrentSession) {
		CreateCertServer.certificationListOnlyFromCurrentSession = certificationListOnlyFromCurrentSession;
	}

	/**
	 * 
	 * @param fileName
	 * @return true if file exist
	 */
	private static boolean chekFileExist(String fileName) {
		File file = new File(fileName);
		if (file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
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
		CertificateInfo restoredCert = new CertificateInfo(tokens[USER_PORTAL],
				tokens[FIRST_NAME], tokens[LAST_NAME],
				Integer.valueOf(tokens[PASSWORD]), tokens[MAIL],
				tokens[PATH_TO_CERT]);
		restoretdList.put(tokens[USER_PORTAL], restoredCert);
	}

	public static void writeCsvFile(String fileName) {
		String FILE_HEADER = "user;firstName;lastName;password;mail;path";
		FileWriter fileWriter = null;

		try {
			if (!chekFileExist(fileName)) {
				fileWriter = addHeader(fileName, FILE_HEADER);
			} else {
				fileWriter = new FileWriter(fileName, true);
			}
			for (Entry<String, CertificateInfo> certificateInfo : certificationListOnlyFromCurrentSession
					.entrySet()) {
				final CertificateInfo currentRecord = certificateInfo
						.getValue();

				addNewRecordInFile(fileWriter, currentRecord);
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

	private static FileWriter addHeader(String fileName, String FILE_HEADER)
			throws IOException {
		FileWriter fileWriter;
		fileWriter = new FileWriter(fileName);
		fileWriter.append(FILE_HEADER.toString());
		fileWriter.append(NEW_LINE_SEPARATOR);
		return fileWriter;
	}

	private static void addNewRecordInFile(FileWriter fileWriter,
			final CertificateInfo currentRecord) throws IOException {
		fileWriter.append(String.valueOf(currentRecord.getUserName()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(currentRecord.getFirstName());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(currentRecord.getLastName());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(currentRecord.getPassword());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(String.valueOf(currentRecord.getEmail()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(String.valueOf(currentRecord
				.getPathToCertificateFile()));
		fileWriter.append(NEW_LINE_SEPARATOR);
	}

}
