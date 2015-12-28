package com.levins.webportal.certificate.client;

import java.io.File;

import com.levins.webportal.certificate.client.UI.ClientPanel;

public class RunClientApp {
	public static String path;

	public static void main(String[] args) {
		// String userSender = "krachunov";
		// String passwordSender = "Cipokrilo";
		// String pathToCertFile = "\\\\172.20.10.103\\cert\\";
		// String host = "localhost";
		String host = "172.20.10.103";
		// Client client = new Client(host,userSender, passwordSender,
		// pathToCertFile);

		Client client = new Client();
		ClientPanel clientPanel = new ClientPanel(client);
		clientPanel.setVisible(true);

		// Client client = new Client();
		// client.setUserSender("krachunov");
		// client.setPasswordSender("Cipokrilo");
		// client.setPathToCertFile("Q:\\");
		// client.setHost(host);
		// client.setOption("");
		// client.setFile(new File("D:\\Book1.csv"));
		// client.start();

	}

}
