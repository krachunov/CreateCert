package com.levins.webportal.certificate.client.UI.popUp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JOptionPane;

import com.levins.webportal.certificate.data.ErrorLog;

public class PopUpWindow {
	/**
	 * Show in to the user information message
	 * 
	 * @param message
	 */
	public void popUpMessageText(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Show in to the user information message about exception
	 * 
	 * @param e
	 * @param message
	 *            - optional
	 */
	public void popUpMessageException(Exception e, String... message) {
		ErrorLog logger = new ErrorLog();
		Writer writer = logger.createLogFile(e, ErrorLog.ERROR_LOG_FILE_NAME);

		if (message.length > 0) {
			JOptionPane.showMessageDialog(null, message, "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(new PrintWriter(new BufferedWriter(writer)));
		} else {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(new PrintWriter(new BufferedWriter(writer)));
		}
		try {
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace(new PrintWriter(new BufferedWriter(writer)));
		}
	}
}
