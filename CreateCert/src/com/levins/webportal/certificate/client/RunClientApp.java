package com.levins.webportal.certificate.client;

import java.io.IOException;

import com.levins.webportal.certificate.client.UI.ClientPanel;

public class RunClientApp {
	public static String path;

	public static void main(String[] args) throws IOException {
		// String userSender = "krachunov";
		// String passwordSender = "Cipokrilo";
		// String pathToCertFile = "\\\\172.20.10.103\\cert\\";
		// String host = "localhost";
		// String host = "172.20.10.103";
		// Client client = new Client(host,userSender, passwordSender,
		// pathToCertFile);
		
		ClientPanel clientPanel = new ClientPanel();
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
