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

		System.out.println(testList.size());

		String fileName = "resources/oldCer.csv";
		chekFileExist(fileName);

	}
}
