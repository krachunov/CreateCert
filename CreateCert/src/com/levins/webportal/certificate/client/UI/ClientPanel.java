package com.levins.webportal.certificate.client.UI;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.levins.webportal.certificate.client.Client;

import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientPanel extends JFrame {
	private Client client;
	private JTextField UsernameTextField;
	private JPasswordField passwordField;
	private JTextField serverAddressField;

	public static void main(String[] args) {
		ClientPanel panel = new ClientPanel();
		panel.setVisible(true);
	}

	public ClientPanel() {
		client = new Client();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Client_Window");
		setBounds(100, 100, 450, 300);
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblSenderUswerName = new JLabel("Sender User name");
		GridBagConstraints gbc_lblSenderUswerName = new GridBagConstraints();
		gbc_lblSenderUswerName.anchor = GridBagConstraints.EAST;
		gbc_lblSenderUswerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenderUswerName.gridx = 0;
		gbc_lblSenderUswerName.gridy = 1;
		getContentPane().add(lblSenderUswerName, gbc_lblSenderUswerName);

		UsernameTextField = new JTextField();
		GridBagConstraints gbc_UsernameTextField = new GridBagConstraints();
		gbc_UsernameTextField.anchor = GridBagConstraints.WEST;
		gbc_UsernameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_UsernameTextField.gridx = 1;
		gbc_UsernameTextField.gridy = 1;
		getContentPane().add(UsernameTextField, gbc_UsernameTextField);
		UsernameTextField.setColumns(10);

		JLabel lblSendersPassword = new JLabel("Sender's password");
		GridBagConstraints gbc_lblSendersPassword = new GridBagConstraints();
		gbc_lblSendersPassword.anchor = GridBagConstraints.EAST;
		gbc_lblSendersPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblSendersPassword.gridx = 0;
		gbc_lblSendersPassword.gridy = 2;
		getContentPane().add(lblSendersPassword, gbc_lblSendersPassword);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 2;
		getContentPane().add(passwordField, gbc_passwordField);

		JLabel lblPathToCertificate = new JLabel(
				"Path to certificate root directory");
		GridBagConstraints gbc_lblPathToCertificate = new GridBagConstraints();
		gbc_lblPathToCertificate.insets = new Insets(0, 0, 5, 5);
		gbc_lblPathToCertificate.gridx = 0;
		gbc_lblPathToCertificate.gridy = 4;
		getContentPane().add(lblPathToCertificate, gbc_lblPathToCertificate);

		JButton btnSelectDirectory = new JButton("Select Directory");
		btnSelectDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final File path = choosDirectory("Choose Directory");
				client.setPathToCertFile(path.toString());
				System.out.println(client.getPathToCertFile());
			}
		});
		GridBagConstraints gbc_btnSelectDirectory = new GridBagConstraints();
		gbc_btnSelectDirectory.anchor = GridBagConstraints.WEST;
		gbc_btnSelectDirectory.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectDirectory.gridx = 1;
		gbc_btnSelectDirectory.gridy = 4;
		getContentPane().add(btnSelectDirectory, gbc_btnSelectDirectory);

		JLabel lblServerAddress = new JLabel("Server address");
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.anchor = GridBagConstraints.EAST;
		gbc_lblServerAddress.gridx = 0;
		gbc_lblServerAddress.gridy = 5;
		getContentPane().add(lblServerAddress, gbc_lblServerAddress);

		serverAddressField = new JTextField();
		GridBagConstraints gbc_serverAddressField = new GridBagConstraints();
		gbc_serverAddressField.insets = new Insets(0, 0, 5, 0);
		gbc_serverAddressField.fill = GridBagConstraints.HORIZONTAL;
		gbc_serverAddressField.gridx = 1;
		gbc_serverAddressField.gridy = 5;
		getContentPane().add(serverAddressField, gbc_serverAddressField);
		serverAddressField.setColumns(10);

		JButton btnSingleUser = new JButton("Single User");
		GridBagConstraints gbc_btnSingleUser = new GridBagConstraints();
		gbc_btnSingleUser.anchor = GridBagConstraints.EAST;
		gbc_btnSingleUser.insets = new Insets(0, 0, 5, 5);
		gbc_btnSingleUser.gridx = 0;
		gbc_btnSingleUser.gridy = 6;
		getContentPane().add(btnSingleUser, gbc_btnSingleUser);

		JButton btnListOfUsers = new JButton("List of Users");
		GridBagConstraints gbc_btnListOfUsers = new GridBagConstraints();
		gbc_btnListOfUsers.anchor = GridBagConstraints.EAST;
		gbc_btnListOfUsers.insets = new Insets(0, 0, 5, 5);
		gbc_btnListOfUsers.gridx = 0;
		gbc_btnListOfUsers.gridy = 7;
		getContentPane().add(btnListOfUsers, gbc_btnListOfUsers);

		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.EAST;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 8;
		getContentPane().add(btnSearch, gbc_btnSearch);
	}

	public File choosDirectory(String textToButton) {
		JFileChooser directoryChooser = new JFileChooser();
		directoryChooser.setAcceptAllFileFilterUsed(false);
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = directoryChooser.showDialog(this, textToButton);
		directoryChooser.setVisible(true);
		File file = directoryChooser.getCurrentDirectory();
		return file;
	}
}
