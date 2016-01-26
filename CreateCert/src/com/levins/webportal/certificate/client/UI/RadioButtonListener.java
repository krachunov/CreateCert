package com.levins.webportal.certificate.client.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JRadioButton;

public class RadioButtonListener implements ActionListener {
	ResourceBundle bundle;
	JRadioButton radioButton;

	public RadioButtonListener(ResourceBundle currentBundle,
			JRadioButton radioButton) {
		this.bundle = currentBundle;
		this.radioButton = radioButton;
	}

	public void actionPerformed(ActionEvent e) {
		String localName = "MyProperties";
		Locale bgLocale = new Locale("bg", "BG");
		if (radioButton.getText().equals("En") && radioButton.isSelected()) {
			bundle = ResourceBundle.getBundle(localName, Locale.UK);
		} else {
			bundle = ResourceBundle.getBundle(localName, bgLocale);
		}

	}

}
