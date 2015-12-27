package com.levins.webportal.certificate.client.UI;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.levins.webportal.certificate.client.Client;

public class DocumentListenerClient implements DocumentListener {
	private JTextField fild;
	private Client client;

	public DocumentListenerClient(JTextField fild, Client client) {
		this.fild = fild;
		this.client = client;

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

	private void warn() {

	}

}
