package com.levins.webportal.certificate.client;

public class RunClientApp {
	public static void main(String[] args) {
		String userSender = "krachunov";
		String passwordSender = "Cipokrilo";
		String pathToCertFile = "\\\\172.20.10.103\\cert\\";
		String host = "localhost";

		//String host = "172.20.10.103";

		Client client = new Client(host,userSender, passwordSender, pathToCertFile);
		client.start();
	}

}
