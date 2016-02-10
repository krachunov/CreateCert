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
	CreateCertificateInterface currentPanel;

	public RadioButtonListener(JRadioButton radioButton,
			SwingLocaleChangedListener localeChangedListener,
			CreateCertificateInterface clientPanel) {
		this.radioButton = radioButton;
		this.localeChangedListener = localeChangedListener;
		this.currentPanel = clientPanel;
	}

	public void actionPerformed(ActionEvent e) {
		changeBundle();
	}

	private void changeBundle() {

		if (radioButton.getText().equals("En") && radioButton.isSelected()) {
			bundle = ResourceBundle.getBundle(ClientPanel.BUNDLE_NAME,
					Locale.UK);
			localeChangedListener.localeChanged(bundle);
			currentPanel.setCurrentBundle(bundle);
			currentPanel.pack();
		} else {
			Locale bgLocale = new Locale("bg", "BG");
			bundle = ResourceBundle
					.getBundle(ClientPanel.BUNDLE_NAME, bgLocale);
			localeChangedListener.localeChanged(bundle);
			currentPanel.setCurrentBundle(bundle);
			currentPanel.pack();
		}
	}

}
