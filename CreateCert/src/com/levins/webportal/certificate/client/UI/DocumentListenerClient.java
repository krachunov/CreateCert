package com.levins.webportal.certificate.client.UI;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentListenerClient implements DocumentListener {
	private JTextField fild;
	private String textContent;

	public DocumentListenerClient(JTextField fild) {
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

		if ((fild.getText().length()) <= 0) {
		} else {

		}
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

}
