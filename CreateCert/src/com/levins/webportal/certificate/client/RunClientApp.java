package com.levins.webportal.certificate.client;

import java.io.IOException;

import com.levins.webportal.certificate.client.UI.ClientPanel;
import com.levins.webportal.certificate.data.UserToken;

public class RunClientApp {

	public static void main(String[] args) throws IOException {

		ClientPanel clientPanel = new ClientPanel();
		clientPanel.setVisible(true);
		
	}
}
