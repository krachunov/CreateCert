package com.levins.webportal.certificate.client.UI.searchTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.levins.webportal.certificate.data.CertificateInfo;
import com.levins.webportal.certificate.data.UserToken;

public class ReadWriteModel {
	private List<CertificateInfo> listOfAnimal;

	public List<CertificateInfo> getListOfAnimal() {
		return listOfAnimal;
	}

	public void setListOfAnimal(List<CertificateInfo> singleLine) {
		this.listOfAnimal = singleLine;
	}

	public static List<CertificateInfo> read(File inputFilePath)
			throws FileNotFoundException, IOException {
		String currentLine;
		List<CertificateInfo> lineList = new ArrayList<CertificateInfo>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFilePath));
			while ((currentLine = br.readLine()) != null) {
				String[] certificate = currentLine.split(";");
				CertificateInfo newCert = new CertificateInfo(
						certificate[UserToken.USERPORTAL],
						certificate[UserToken.FIRSTNAME],
						certificate[UserToken.LASTNAME],
						Integer.valueOf(certificate[UserToken.PASSWORD]),
						certificate[UserToken.MAIL],
						certificate[UserToken.PATHTOCERT],
						certificate[UserToken.EGN]);
				lineList.add(newCert);
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}

		return lineList;
	}

	/**
	 * Read List of string and convert into list of CertificateInfo
	 * 
	 * @param list
	 *            of String
	 * @return
	 */
	public static List<CertificateInfo> readString(List<String> list) {
		List<CertificateInfo> lineList = new ArrayList<CertificateInfo>();
		for (String record : list) {
			String[] certificate = record.split(";");
			CertificateInfo newCert = new CertificateInfo(
					certificate[UserToken.USERPORTAL],
					certificate[UserToken.FIRSTNAME],
					certificate[UserToken.LASTNAME],
					Integer.valueOf(certificate[UserToken.PASSWORD]),
					certificate[UserToken.MAIL],
					certificate[UserToken.PATHTOCERT],
					certificate[UserToken.EGN]);
			lineList.add(newCert);
		}
		return lineList;
	}

	public static void writeNewFile(Queue<String> sorceToWrite,
			String outputFile) throws IOException {
		BufferedWriter bufferWrite = null;
		try {
			bufferWrite = new BufferedWriter(new FileWriter(outputFile));
			while (sorceToWrite.size() > 0) {
				bufferWrite.write(sorceToWrite.poll());
			}
		} finally {
			if (bufferWrite != null) {
				bufferWrite.close();

			}
		}
	}

	// write only one line
	public void writeNewFile(List<CertificateInfo> listToSave, File file)
			throws IOException {
		PrintWriter bufferWrite = null;
		try {
			bufferWrite = new PrintWriter(new FileWriter(file));
			for (CertificateInfo cert : listToSave) {
				bufferWrite.println(cert.toString());

			}
		} finally {
			if (bufferWrite != null) {
				bufferWrite.close();
			}
		}
	}
}
