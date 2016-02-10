package com.levins.webportal.certificate.client.UI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentListenerClient implements DocumentListener {
	private List<JTextField> textFilds = new ArrayList<JTextField>();
	private JButton button;

	public DocumentListenerClient(JButton button) {
		this.button = button;
	}

	public void addTextField(JTextField field) {
		textFilds.add(field);
		field.getDocument().addDocumentListener(this);
	}

	public boolean isDataEntered() {
		for (JTextField textField : textFilds) {
			if (textField.getText().trim().length() == 0)
				return false;
		}

		return true;
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
		button.setEnabled(isDataEntered());
	}
}
