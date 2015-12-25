package com.levins.webportal.certificate.server;

import java.util.Set;

import com.levins.webportal.certificate.data.CertificateInfo;

public class TestServerSide {

	public static void main(String[] args) {
		Set<CertificateInfo> testList = CreateCertServer.getCertificationList();

		testList.add(new CertificateInfo("W0001", "Hristo", "krachunov",
				235234, "krachunov@lev-ins.com", "\\24_12_2015"));
		testList.add(new CertificateInfo("W0002", "Tedi", "Ivanov", 234,
				"tivanov@lev-ins.com", "\\24_12_2015"));
		testList.add(new CertificateInfo("W0003", "Drug", "Drugov", 235124134,
				"krac@lev-ins.com", "\\24_12_2015"));

		CreateCertServer.writeCsvFile("resources\\oldCer.csv");

	}
}
