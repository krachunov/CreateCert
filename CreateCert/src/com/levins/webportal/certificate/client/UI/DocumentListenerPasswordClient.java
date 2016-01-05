package com.levins.webportal.certificate.client.UI;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentListenerPasswordClient implements DocumentListener {

	private JPasswordField fild;
	private JButton button;

	public DocumentListenerPasswordClient(JButton button, JPasswordField fild) {
		this.button = button;
		this.fild = fild;
	}

	public void insertUpdate(DocumentEvent e) {
		warn();
	}

	public void removeUpdate(DocumentEvent e) {
		warn();
	}

	public void changedUpdate(DocumentEvent e) {
		warn();
	}

	public void warn() {

		if (fild.getPassword().length != 0) {
			button.setEnabled(true);
		} else {
			button.setEnabled(false);
		}
	}

}
