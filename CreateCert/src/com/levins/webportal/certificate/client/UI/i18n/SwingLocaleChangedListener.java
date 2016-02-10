package com.levins.webportal.certificate.client.UI.i18n;

import java.awt.ComponentOrientation;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.JLabel;

public class SwingLocaleChangedListener implements LocaleChangedListener {

	private ArrayList<AbstractButton> abstractButtons;
	private ArrayList<JLabel> abstractLabel;

	public void localeChanged(ResourceBundle rb) {

		if(abstractLabel!=null&&abstractLabel.size()>0){
			for (JLabel b : abstractLabel) {
				b.setText(rb.getString(b.getText()));
				b.setComponentOrientation(ComponentOrientation.getOrientation(rb.getLocale())); // EDIT: Line added
			}
		}
	
		if(abstractButtons!=null&&abstractButtons.size()>0){
			for (AbstractButton b : abstractButtons) {
				b.setText(rb.getString(b.getText()));
				b.setComponentOrientation(ComponentOrientation.getOrientation(rb.getLocale())); // EDIT: Line added
			}
			
		}

	}

	public boolean addButtons(AbstractButton b) {
		initAbstractButtons();
		return abstractButtons.add(b);
	}

	private void initAbstractButtons() {
		if (abstractButtons == null) {
			this.abstractButtons = new ArrayList<AbstractButton>();
		}
	}

	public boolean addLabel(JLabel b) {
		initAbstractLabel();
		return abstractLabel.add(b);
	}

	private void initAbstractLabel() {
		if (abstractLabel == null) {
			this.abstractLabel = new ArrayList<JLabel>();
		}
	}
}