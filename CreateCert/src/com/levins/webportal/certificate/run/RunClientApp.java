package com.levins.webportal.certificate.run;

import java.io.IOException;

import com.levins.webportal.certificate.client.UI.ClientPanel;

public class RunClientApp {

	public static void main(String[] args) throws IOException {

		ClientPanel clientPanel = new ClientPanel();
		clientPanel.setVisible(true);
		// from home comment

		ClientPanel clientPanel2 = new ClientPanel();
		clientPanel2.setVisible(true);
	}
}
