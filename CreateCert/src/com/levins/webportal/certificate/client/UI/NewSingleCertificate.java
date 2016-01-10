package com.levins.webportal.certificate.client.UI;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JButton;

import com.levins.webportal.certificate.client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class NewSingleCertificate extends JFrame {
	private JTextField userNameField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JTextField EGNtextField;

	public NewSingleCertificate(final ClientPanel currentClient) {
		setResizable(false);
		setVisible(true);
		setBounds(100, 100, 300, 300);
		setTitle("Single user Creator");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblUserName = new JLabel("User Name*");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.EAST;
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 1;
		getContentPane().add(lblUserName, gbc_lblUserName);

		userNameField = new JTextField();
		userNameField.setToolTipText("Webportal username");
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.gridwidth = 2;
		gbc_userNameField.anchor = GridBagConstraints.WEST;
		gbc_userNameField.insets = new Insets(0, 0, 5, 0);
		gbc_userNameField.gridx = 1;
		gbc_userNameField.gridy = 1;
		getContentPane().add(userNameField, gbc_userNameField);
		userNameField.setColumns(10);

		JLabel lblFistName = new JLabel("Fist Name");
		GridBagConstraints gbc_lblFistName = new GridBagConstraints();
		gbc_lblFistName.anchor = GridBagConstraints.EAST;
		gbc_lblFistName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFistName.gridx = 0;
		gbc_lblFistName.gridy = 2;
		getContentPane().add(lblFistName, gbc_lblFistName);

		firstNameField = new JTextField();
		GridBagConstraints gbc_firstNameField = new GridBagConstraints();
		gbc_firstNameField.gridwidth = 2;
		gbc_firstNameField.anchor = GridBagConstraints.WEST;
		gbc_firstNameField.insets = new Insets(0, 0, 5, 0);
		gbc_firstNameField.gridx = 1;
		gbc_firstNameField.gridy = 2;
		getContentPane().add(firstNameField, gbc_firstNameField);
		firstNameField.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 3;
		getContentPane().add(lblLastName, gbc_lblLastName);

		lastNameField = new JTextField();
		GridBagConstraints gbc_lastNameField = new GridBagConstraints();
		gbc_lastNameField.gridwidth = 2;
		gbc_lastNameField.anchor = GridBagConstraints.WEST;
		gbc_lastNameField.insets = new Insets(0, 0, 5, 0);
		gbc_lastNameField.gridx = 1;
		gbc_lastNameField.gridy = 3;
		getContentPane().add(lastNameField, gbc_lastNameField);
		lastNameField.setColumns(10);

		JLabel lblEgn = new JLabel("EGN");
		GridBagConstraints gbc_lblEgn = new GridBagConstraints();
		gbc_lblEgn.anchor = GridBagConstraints.EAST;
		gbc_lblEgn.insets = new Insets(0, 0, 5, 5);
		gbc_lblEgn.gridx = 0;
		gbc_lblEgn.gridy = 4;
		getContentPane().add(lblEgn, gbc_lblEgn);

		EGNtextField = new JTextField();
		GridBagConstraints gbc_EGNtextField = new GridBagConstraints();
		gbc_EGNtextField.gridwidth = 2;
		gbc_EGNtextField.anchor = GridBagConstraints.WEST;
		gbc_EGNtextField.insets = new Insets(0, 0, 5, 0);
		gbc_EGNtextField.gridx = 1;
		gbc_EGNtextField.gridy = 4;
		getContentPane().add(EGNtextField, gbc_EGNtextField);
		EGNtextField.setColumns(10);

		JLabel lblEmail = new JLabel("E-mail*");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 5;
		getContentPane().add(lblEmail, gbc_lblEmail);

		emailField = new JTextField();
		emailField.setToolTipText("Recipient mail");
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.gridwidth = 2;
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.anchor = GridBagConstraints.WEST;
		gbc_emailField.gridx = 1;
		gbc_emailField.gridy = 5;
		getContentPane().add(emailField, gbc_emailField);
		emailField.setColumns(10);

		JButton btnCreateAndSend = new JButton("Create and Send");
		btnCreateAndSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (currentClient.getChckbxSave().isSelected()) {
					try {
						currentClient.serialize(currentClient
								.getRestorSettings());
					} catch (IOException e1) {
						ClientPanel
								.popUpMessageException(e1,
										"Problem with serialize in NewSingleCertificate.class");
					}
				}
				Client client = createNewClientObject(currentClient);

				String inputSingleUser = String.format("%s;%s;%s;%s;%s",
						userNameField.getText(), firstNameField.getText(),
						lastNameField.getText(), emailField.getText(),
						EGNtextField.getText());
				client.setInputSingleUser(inputSingleUser);
				client.start();
				ClientPanel.getOutputConsoleArea().append(inputSingleUser);
				ClientPanel.getOutputConsoleArea().append("\n");
				clearUserSettings();
				ClientPanel.popUpMessageText("Done");
			}

			private void clearUserSettings() {
				userNameField.setText("");
				firstNameField.setText("");
				lastNameField.setText("");
				emailField.setText("");
				EGNtextField.setText("");

			}

			private Client createNewClientObject(final ClientPanel currentClient) {
				String sender = currentClient.getUserNameTextField().getText();
				String passwordSender = String.copyValueOf(currentClient
						.getPasswordTextField().getPassword());
				String host = currentClient.getServerHostTextField().getText();
				String choose = Client.SINGLE_USER;

				Client client = new Client();
				client.setUserSender(sender);
				client.setPasswordSender(passwordSender);
				client.setHost(host);
				client.setOption(choose);
				client.setPathToCertFile(currentClient.getPath());
				return client;
			}
		});
		btnCreateAndSend.setEnabled(false);
		DocumentListenerClient listenerToSingleCreator = new DocumentListenerClient(
				btnCreateAndSend);
		listenerToSingleCreator.addTextField(userNameField);
		listenerToSingleCreator.addTextField(emailField);
		
		JCheckBox replaceCheckBox = new JCheckBox("Replace");
		replaceCheckBox.setToolTipText("Overwrites existing user info");
		GridBagConstraints gbc_replaceCheckBox = new GridBagConstraints();
		gbc_replaceCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_replaceCheckBox.gridx = 1;
		gbc_replaceCheckBox.gridy = 6;
		getContentPane().add(replaceCheckBox, gbc_replaceCheckBox);

		GridBagConstraints gbc_btnCreateAndSend = new GridBagConstraints();
		gbc_btnCreateAndSend.gridx = 2;
		gbc_btnCreateAndSend.gridy = 6;
		getContentPane().add(btnCreateAndSend, gbc_btnCreateAndSend);
	}
}
