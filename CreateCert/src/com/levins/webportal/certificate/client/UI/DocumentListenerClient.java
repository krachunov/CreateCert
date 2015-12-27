package com.levins.webportal.certificate.client.UI;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.levins.webportal.certificate.client.Client;

public class DocumentListenerClient implements DocumentListener {
	private JTextField fild;
	private Client client;
	private JButton button;

	public DocumentListenerClient(JTextField fild, JButton button, Client client) {
		this.fild = fild;
		this.client = client;
		this.button=button;

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

		if ((fild.getText().length()) <= 0) {
			button.setEnabled(false);
		
		} else {
			button.setEnabled(true);
		}
	}

}
