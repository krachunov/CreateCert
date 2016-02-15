package com.levins.webportal.certificate.client.UI.searchTable;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OtherRecipientWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	OtherRecipientWindow thisWindows;

	public OtherRecipientWindow(final SearchViewUI parrentWindow) {
		this.thisWindows=this;
		setTitle("Other recipient");

		JLabel lblOtherEmail = new JLabel("Other Email:");
		getContentPane().add(lblOtherEmail, BorderLayout.WEST);

	

		textField = new JTextField();
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parrentWindow.sendRow(textField.getText());
				thisWindows.dispose();
				
			}
		});
		getContentPane().add(btnSend, BorderLayout.EAST);

	}

}
