package com.levins.webportal.certificate.client.UI.searchTable;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JButton;

import com.levins.webportal.certificate.client.UI.ClientPanel;
import com.levins.webportal.certificate.client.UI.popUp.PopUpWindow;
import com.levins.webportal.certificate.data.DataValidator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OtherRecipientWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	OtherRecipientWindow thisWindows;
	ClientPanel clientPanel;

	public OtherRecipientWindow(final SearchViewUI parrentWindow,
			final ClientPanel clientPanel) {
		this.thisWindows = this;
		this.clientPanel = clientPanel;
		setTitle(clientPanel.getCurrentBundle().getString("Other recipient"));
		setBounds(100, 100, 300, 75);
		setAlwaysOnTop(true);
		setResizable(false);
		setVisible(true);

		JLabel lblOtherEmail = new JLabel(clientPanel.getCurrentBundle()
				.getString("Other Email"));
		getContentPane().add(lblOtherEmail, BorderLayout.WEST);

		textField = new JTextField();
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);

		JButton btnSend = new JButton(clientPanel.getCurrentBundle().getString(
				"Send"));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!parrentWindow.getTableModel().isEmpty()) {
					if (DataValidator.validateMail(textField.getText())) {
						parrentWindow.sendRow(textField.getText());
						thisWindows.dispose();
					} else {
						PopUpWindow popUpMessage = new PopUpWindow();
						popUpMessage.popUpMessageText(clientPanel
								.getCurrentBundle().getString("Invalid email"));
					}
				} else {
					PopUpWindow popUpMessage = new PopUpWindow();
					popUpMessage.popUpMessageText(clientPanel
							.getCurrentBundle().getString(
									"Please, first select the record"));
				}

			}
		});
		getContentPane().add(btnSend, BorderLayout.EAST);

	}

}
