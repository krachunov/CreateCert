package com.levins.webportal.certificate.run;

import com.levins.webportal.certificate.server.CreateCertServer;

public class RunServerApp {

	public static void main(String[] args) {
		CreateCertServer server = new CreateCertServer();
		server.start();
	}

}
