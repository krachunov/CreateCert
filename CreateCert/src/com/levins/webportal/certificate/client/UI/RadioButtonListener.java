package com.levins.webportal.certificate.client.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JRadioButton;

import com.levins.webportal.certificate.client.UI.i18n.SwingLocaleChangedListener;

public class RadioButtonListener implements ActionListener {
	ResourceBundle bundle;
	JRadioButton radioButton;
	SwingLocaleChangedListener localeChangedListener;

	public RadioButtonListener(ResourceBundle currentBundle,
			JRadioButton radioButton,
			SwingLocaleChangedListener localeChangedListener) {
		this.bundle = currentBundle;
		this.radioButton = radioButton;
		this.localeChangedListener = localeChangedListener;
	}

	public void actionPerformed(ActionEvent e) {
		String localName = "MyProperties";
		Locale bgLocale = new Locale("bg", "BG");
		if (radioButton.getText().equals("En") && radioButton.isSelected()) {
			bundle = ResourceBundle.getBundle(localName, Locale.UK);
			localeChangedListener.localeChanged(bundle);
		} else {
			bundle = ResourceBundle.getBundle(localName, bgLocale);
			localeChangedListener.localeChanged(bundle);
		}
	}

}
