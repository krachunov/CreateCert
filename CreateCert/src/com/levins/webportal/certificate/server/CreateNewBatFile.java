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

	public void generateBatFile(String info) throws IOException {

		String[] currentInfo = info.split(";");
		String userName = currentInfo[0];
		String firstName = currentInfo[1];
		String lastName = currentInfo[2];
		int password = Math.abs((new Random().nextInt(20000) + 1000));

		String batFileName = "newCertificate.bat";

		String absolutePathToBatFile = String.format("%s%s", PATH, batFileName);
		File outputFile = new File(absolutePathToBatFile);

		String contentToBatFile = String
				.format("generateClientCertificate %s %d \"%s %s\" lev-ins ssl4Ever!\n",
						userName, password, firstName, lastName);
		writeNewFile(contentToBatFile, outputFile);
		runBath(absolutePathToBatFile);
		moveCertFileIntoTodayFolder(userName + ".pfx");
	}

	private void runBath(String fileToRun) throws IOException {
		try {
			Runtime.getRuntime().exec("cmd /c start " + fileToRun);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeNewFile(String toSave, File file) throws IOException {
		try (PrintWriter bufferWrite = new PrintWriter(new FileWriter(file))) {
			bufferWrite.println(toSave);
		}
	}

	public void moveCertFileIntoTodayFolder(String certName) throws IOException {
		String newPathLocation = PATH + createdDate() + "\\";
		new File(newPathLocation).mkdirs();

		File sorce = new File(PATH + certName);
		sorce.renameTo(new File(newPathLocation + sorce.getName()));
		sorce.delete();
	}

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
