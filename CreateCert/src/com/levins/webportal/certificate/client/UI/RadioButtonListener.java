package com.levins.webportal.certificate.client.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JRadioButton;

import com.levins.webportal.certificate.client.UI.i18n.SwingLocaleChangedListener;

public class RadioButtonListener implements ActionListener {
	private ResourceBundle bundle;
	private JRadioButton radioButton;
	private SwingLocaleChangedListener localeChangedListener;

	public RadioButtonListener(JRadioButton radioButton,
			SwingLocaleChangedListener localeChangedListener) {
		this.radioButton = radioButton;
		this.localeChangedListener = localeChangedListener;
	}

	public void actionPerformed(ActionEvent e) {
		changeBundle();
	}

	private void changeBundle() {

		if (radioButton.getText().equals("En") && radioButton.isSelected()) {
			bundle = ResourceBundle.getBundle(ClientPanel.BUNDLE_NAME,
					Locale.UK);
			localeChangedListener.localeChanged(bundle);
		} else {
			Locale bgLocale = new Locale("bg", "BG");
			bundle = ResourceBundle
					.getBundle(ClientPanel.BUNDLE_NAME, bgLocale);
			localeChangedListener.localeChanged(bundle);
		}
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public JRadioButton getRadioButton() {
		return radioButton;
	}

	public SwingLocaleChangedListener getLocaleChangedListener() {
		return localeChangedListener;
	}


}
