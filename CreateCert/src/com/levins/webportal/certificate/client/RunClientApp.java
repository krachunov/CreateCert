package com.levins.webportal.certificate.client;

import java.io.IOException;

import com.levins.webportal.certificate.client.UI.ClientPanel;

public class RunClientApp {
	public static String path;

	public static void main(String[] args) throws IOException {

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
