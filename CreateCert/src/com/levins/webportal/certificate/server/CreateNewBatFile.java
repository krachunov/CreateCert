package com.levins.webportal.certificate.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.levins.webportal.certificate.data.CertificateInfo;

public class CreateNewBatFile {
	private static final String PATH = "C:\\distr\\cert\\";
	private static final String BAT_FILE_NAME = "newCertificate.bat";
	/**
	 * COMMAND_BAT_FILE - content userName;password;firstName;lastName
	 */
	private static String COMMAND_BAT_FILE = "call generateClientCertificate %s %d \"%s %s\" lev-ins ssl4Ever!";

	/**
	 * 
	 * @param inputInfo
	 *            - creates a file that is created a new certificate file, and
	 *            then move each of them into folder of the current day
	 * @throws IOException
	 */
	public CertificateInfo generateCert(String inputInfo) throws IOException {

		String[] currentInfo = inputInfo.split(";");
		String userName = currentInfo[0].replace("\"", "");
		String firstName = currentInfo[1].replace("\"", "");
		String lastName = currentInfo[2].replace("\"", "");
		String email = currentInfo[3].replace("\"", "");
		int password = generatePassword();

		String contentBatFile = String.format(COMMAND_BAT_FILE, userName,
				password, firstName, lastName);
		String absolutePathToBatFile = String.format(PATH + BAT_FILE_NAME);
		File outputFile = new File(absolutePathToBatFile);

		writeNewFile(contentBatFile, outputFile);
		runBatFile(absolutePathToBatFile);
		// wait a few seconds to create the file
		try {
			// TODO - try with less second
			Thread.sleep(4000); // 1000 milliseconds is one second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		// TODO set path to find cert file

		String currentCertificatFileDestination = moveCertFileIntoTodayFolder(userName);
		CertificateInfo newUserCert = new CertificateInfo(userName, firstName,
				lastName, password, email, currentCertificatFileDestination);

		return newUserCert;
	}

	private int generatePassword() {
		int bound = 20000;
		int minimumValue = 1000;
		return Math.abs((new Random().nextInt(bound) + minimumValue));
	}

	private void runBatFile(String fileToRun) throws IOException {
		System.out.println("run option start");
		try {
			Runtime.getRuntime().exec("cmd /c start " + fileToRun);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("run option done");
	}

	/**
	 * 
	 * @param toSave
	 *            - String that need to save
	 * @param file
	 *            - the new created file
	 * @throws IOException
	 */
	private void writeNewFile(String toSave, File file) throws IOException {
		PrintWriter bufferWrite = new PrintWriter(new FileWriter(file));
		bufferWrite.println(toSave);
		bufferWrite.close();
	}

	/**
	 * This method moved file into folder with
	 * name current day and return String with current day
	 * 
	 * @param certName
	 * @throws IOException
	 * @return the new path location
	 */
	public String moveCertFileIntoTodayFolder(String certName)
			throws IOException {
		String newPathLocation = PATH + createdDate() + "\\";
		new File(newPathLocation).mkdirs();
		String fileExtension = ".pfx";
		String fileName = certName + fileExtension;
		File fileToMove = new File(PATH + fileName);
		fileToMove.renameTo(new File(newPathLocation + fileToMove.getName()));
		fileToMove.delete();
		System.out.println("Move option done");
		return createdDate() + "\\";
	}

	/**
	 * Create string by date and use to create folder by current day name
	 * 
	 * @return String with format dd_mm_yyy
	 */
	private String createdDate() {
		DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;
	}
}
