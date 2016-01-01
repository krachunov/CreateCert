package com.levins.webportal.certificate.client.UI;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class FromInsis extends JFrame {
	private JTextField serverIPAddresstextField;
	private JTextField dataBaseNameTextField;
	private JTextField userTextField;
	private JTextField passwordTextField;
	private JButton btnNewButton;
	private JButton btnListOfUsers;
	private JButton btnStart;
	private JCheckBox chckbxSave;
	private JLabel lblSaveSettings;
	public FromInsis() {
		setTitle("Create certificate with info from INSIS ");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 173, 138, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblServerIpAddress = new JLabel("Server IP address");
		GridBagConstraints gbc_lblServerIpAddress = new GridBagConstraints();
		gbc_lblServerIpAddress.anchor = GridBagConstraints.EAST;
		gbc_lblServerIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerIpAddress.gridx = 0;
		gbc_lblServerIpAddress.gridy = 1;
		getContentPane().add(lblServerIpAddress, gbc_lblServerIpAddress);
		
		serverIPAddresstextField = new JTextField();
		GridBagConstraints gbc_serverIPAddresstextField = new GridBagConstraints();
		gbc_serverIPAddresstextField.anchor = GridBagConstraints.WEST;
		gbc_serverIPAddresstextField.insets = new Insets(0, 0, 5, 5);
		gbc_serverIPAddresstextField.gridx = 1;
		gbc_serverIPAddresstextField.gridy = 1;
		getContentPane().add(serverIPAddresstextField, gbc_serverIPAddresstextField);
		serverIPAddresstextField.setColumns(10);
		
		JLabel lblDataBaseName = new JLabel("Data Base Name");
		GridBagConstraints gbc_lblDataBaseName = new GridBagConstraints();
		gbc_lblDataBaseName.anchor = GridBagConstraints.EAST;
		gbc_lblDataBaseName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataBaseName.gridx = 0;
		gbc_lblDataBaseName.gridy = 2;
		getContentPane().add(lblDataBaseName, gbc_lblDataBaseName);
		
		dataBaseNameTextField = new JTextField();
		GridBagConstraints gbc_dataBaseNameTextField = new GridBagConstraints();
		gbc_dataBaseNameTextField.anchor = GridBagConstraints.WEST;
		gbc_dataBaseNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_dataBaseNameTextField.gridx = 1;
		gbc_dataBaseNameTextField.gridy = 2;
		getContentPane().add(dataBaseNameTextField, gbc_dataBaseNameTextField);
		dataBaseNameTextField.setColumns(10);
		
		JLabel lblUser = new JLabel("User");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.anchor = GridBagConstraints.EAST;
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 0;
		gbc_lblUser.gridy = 3;
		getContentPane().add(lblUser, gbc_lblUser);
		
		userTextField = new JTextField();
		GridBagConstraints gbc_userTextField = new GridBagConstraints();
		gbc_userTextField.anchor = GridBagConstraints.WEST;
		gbc_userTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userTextField.gridx = 1;
		gbc_userTextField.gridy = 3;
		getContentPane().add(userTextField, gbc_userTextField);
		userTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 4;
		getContentPane().add(lblPassword, gbc_lblPassword);
		
		passwordTextField = new JTextField();
		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordTextField.anchor = GridBagConstraints.WEST;
		gbc_passwordTextField.gridx = 1;
		gbc_passwordTextField.gridy = 4;
		getContentPane().add(passwordTextField, gbc_passwordTextField);
		passwordTextField.setColumns(10);
		
		btnNewButton = new JButton("Single user");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		lblSaveSettings = new JLabel("Save Settings");
		GridBagConstraints gbc_lblSaveSettings = new GridBagConstraints();
		gbc_lblSaveSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaveSettings.gridx = 0;
		gbc_lblSaveSettings.gridy = 5;
		getContentPane().add(lblSaveSettings, gbc_lblSaveSettings);
		
		chckbxSave = new JCheckBox("Save");
		GridBagConstraints gbc_chckbxSave = new GridBagConstraints();
		gbc_chckbxSave.anchor = GridBagConstraints.WEST;
		gbc_chckbxSave.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSave.gridx = 1;
		gbc_chckbxSave.gridy = 5;
		getContentPane().add(chckbxSave, gbc_chckbxSave);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 6;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		btnListOfUsers = new JButton("List Of Users");
		GridBagConstraints gbc_btnListOfUsers = new GridBagConstraints();
		gbc_btnListOfUsers.anchor = GridBagConstraints.WEST;
		gbc_btnListOfUsers.insets = new Insets(0, 0, 0, 5);
		gbc_btnListOfUsers.gridx = 0;
		gbc_btnListOfUsers.gridy = 7;
		getContentPane().add(btnListOfUsers, gbc_btnListOfUsers);
		
		btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridx = 2;
		gbc_btnStart.gridy = 7;
		getContentPane().add(btnStart, gbc_btnStart);
	}

}
