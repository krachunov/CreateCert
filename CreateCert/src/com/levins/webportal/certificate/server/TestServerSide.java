package com.levins.webportal.certificate.server;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.levins.webportal.certificate.data.CertificateInfo;

@Deprecated
public class TestServerSide {

	private static void chekFileExist(String fileName) {
		File f = new File(fileName);
		if (f.exists() && !f.isDirectory()) {
			System.out.println("EXIST");
		}
	}

	public static void main(String[] args) {
		Set<CertificateInfo> testList = new HashSet<CertificateInfo>();

//		testList.add(new CertificateInfo("W0001", "Hristo", "krachunov",
//				235234, "krachunov@lev-ins.com", "\\24_12_2015"));
//		testList.add(new CertificateInfo("W0001", "Hristo", "Ivanov", 234,
//				"tivanov@lev-ins.com", "\\24_12_2015"));
//		testList.add(new CertificateInfo("W0001", "Hristo", "Drugov",
//				235124134, "krac@lev-ins.com", "\\24_12_2015"));
		// CreateCertServer.writeCsvFile("resources\\oldCer.csv");
		System.out.println(testList.size());

		String fileName = "resources/oldCer.csv";
		chekFileExist(fileName);

	}
}
