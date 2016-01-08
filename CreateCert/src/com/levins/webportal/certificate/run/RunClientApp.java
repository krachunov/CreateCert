package com.levins.webportal.certificate.run;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import com.levins.webportal.certificate.client.UI.ClientPanel;
import com.levins.webportal.certificate.data.UserToken;

public class RunClientApp {

	public static void main(String[] args) throws IOException {

//		ClientPanel clientPanel = new ClientPanel();
//		clientPanel.setVisible(true);
		
		System.out.println(UserToken.EGN);
		DayOfWeek a = LocalDate.of(1985, 11, 14).getDayOfWeek();
				
	}
}
