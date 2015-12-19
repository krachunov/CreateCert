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

public class CreateNewBatFile {
	private static final String PATH = "C:\\distr\\cert\\";
	static final String BAT_FILE_NAME = "newCertificate.bat";

	/**
	 * 
	 * @param info
	 *            - creates a file that is created a new certificate file, and
	 *            then move each of them into folder of the current day
	 * @throws IOException
	 */
	public void generateBatFile(String info) throws IOException {

		String[] currentInfo = info.split(";");
		String userName = currentInfo[0];

		String firstName = currentInfo[1];
		String lastName = currentInfo[2];
		int password = Math.abs((new Random().nextInt(20000) + 1000));
		String contentToBatFile = String
				.format("call generateClientCertificate %s %d \"%s %s\" lev-ins ssl4Ever!",
						userName, password, firstName, lastName);
		String absolutePathToBatFile = String.format("%s%s", PATH,
				BAT_FILE_NAME);
		File outputFile = new File(absolutePathToBatFile);

		writeNewFile(contentToBatFile, outputFile);
		System.out.println("Path:" + absolutePathToBatFile);
		runBatFile(absolutePathToBatFile);
		moveCertFileIntoTodayFolder(userName);
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
	 * After certificate is created, this method moved file into folder with
	 * name current day
	 * 
	 * @param certName
	 * @throws IOException
	 */
	public void moveCertFileIntoTodayFolder(String certName) throws IOException {
		String newPathLocation = PATH + createdDate() + "\\";
		new File(newPathLocation).mkdirs();
		String fileExtension = ".pfx";
		String fileName = certName + fileExtension;
		File sorce = new File(PATH + fileName);
		sorce.renameTo(new File(newPathLocation + sorce.getName()));
		sorce.delete();
		System.out.println("Move option done");
	}

	/**
	 * 
	 * @return String with format dd_mm_yyy
	 */
	private String createdDate() {
		DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String reportDate = df.format(today);
		return reportDate;
	}

}
