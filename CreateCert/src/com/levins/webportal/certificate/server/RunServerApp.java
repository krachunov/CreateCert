package com.levins.webportal.certificate.server;


public class RunServerApp {

	public static void main(String[] args) {
		String fileToRestore = "resources/oldCer.csv";
		CreateCertServer server = new CreateCertServer(fileToRestore);
		server.start();

	}

}
