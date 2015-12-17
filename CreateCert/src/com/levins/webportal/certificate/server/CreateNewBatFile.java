package com.levins.webportal.certificate.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

		String fileName = "newCertificate.bat";

		String absolutePath = String.format("%s%s", PATH, fileName);
		File outputFile = new File(absolutePath);

		String stringToSave = String
				.format("generateClientCertificate %s %d \"%s %s\" lev-ins ssl4Ever!\n",
						userName, password, firstName, lastName);
		writeNewFile(stringToSave, outputFile);
		moveCertFileIntoTodayFolder(userName + ".pfx");
	}

	private void writeNewFile(String toSave, File file) throws IOException {
		try (PrintWriter bufferWrite = new PrintWriter(new FileWriter(file))) {
			bufferWrite.println(toSave);
		}
	}

	public void moveCertFileIntoTodayFolder(String certName) throws IOException {
		String newPathLocation = PATH + createdDate()+"\\";
		new File(newPathLocation).mkdirs();

		Path destination = Paths.get(newPathLocation);
		Path sorce = Paths.get(PATH + certName);

		Files.move(sorce, destination, StandardCopyOption.REPLACE_EXISTING);
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

	public static void main(String[] args) throws IOException {
		CreateNewBatFile d = new CreateNewBatFile();
		d.moveCertFileIntoTodayFolder("krach.pfx");
	}
}
