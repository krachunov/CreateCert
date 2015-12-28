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

@SuppressWarnings("serial")
public class ClientPanel extends JFrame {
	private Client client;
	private String userName;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JTextField serverAddressTextField;
	private JButton btnStart;

	public ClientPanel(final Client client) {
		// TODO
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Client_Window");
		setBounds(100, 100, 400, 250);
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 78, 162, 86, 75 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblSenderUswerName = new JLabel("Sender User name");
		GridBagConstraints gbc_lblSenderUswerName = new GridBagConstraints();
		gbc_lblSenderUswerName.anchor = GridBagConstraints.EAST;
		gbc_lblSenderUswerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenderUswerName.gridx = 0;
		gbc_lblSenderUswerName.gridy = 1;
		getContentPane().add(lblSenderUswerName, gbc_lblSenderUswerName);

		userNameTextField = new JTextField("", 20);
		userNameTextField.setToolTipText("");
		GridBagConstraints gbc_userNameTextField = new GridBagConstraints();
		gbc_userNameTextField.anchor = GridBagConstraints.WEST;
		gbc_userNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userNameTextField.gridx = 1;
		gbc_userNameTextField.gridy = 1;
		getContentPane().add(userNameTextField, gbc_userNameTextField);
		userNameTextField.setColumns(10);
		// TODO LISTEner

		// DocumentListenerClient text = new
		// DocumentListenerClient(userNameTextField, userName);
		// userNameTextField.getDocument().addDocumentListener(text);
		// System.out.println("THIS "+userName);

		JLabel lblSendersPassword = new JLabel("Sender's password");
		GridBagConstraints gbc_lblSendersPassword = new GridBagConstraints();
		gbc_lblSendersPassword.anchor = GridBagConstraints.EAST;
		gbc_lblSendersPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblSendersPassword.gridx = 0;
		gbc_lblSendersPassword.gridy = 2;
		getContentPane().add(lblSendersPassword, gbc_lblSendersPassword);

		passwordTextField = new JPasswordField();
		passwordTextField.setColumns(10);
		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.anchor = GridBagConstraints.WEST;
		gbc_passwordTextField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordTextField.gridx = 1;
		gbc_passwordTextField.gridy = 2;
		getContentPane().add(passwordTextField, gbc_passwordTextField);
		client.setPasswordSender(passwordTextField.toString());

		JLabel lblServerAddress = new JLabel("Server address");
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.anchor = GridBagConstraints.EAST;
		gbc_lblServerAddress.gridx = 0;
		gbc_lblServerAddress.gridy = 3;
		getContentPane().add(lblServerAddress, gbc_lblServerAddress);

		serverAddressTextField = new JTextField();
		GridBagConstraints gbc_serverAddressTextField = new GridBagConstraints();
		gbc_serverAddressTextField.anchor = GridBagConstraints.WEST;
		gbc_serverAddressTextField.insets = new Insets(0, 0, 5, 0);
		gbc_serverAddressTextField.gridx = 1;
		gbc_serverAddressTextField.gridy = 3;
		getContentPane()
				.add(serverAddressTextField, gbc_serverAddressTextField);
		serverAddressTextField.setColumns(10);
		client.setHost("\\\\" + serverAddressTextField.getText());

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
				// TODO remove syso print
				System.out.println(client.getPathToCertFile());
			}
		});
		GridBagConstraints gbc_btnSelectDirectory = new GridBagConstraints();
		gbc_btnSelectDirectory.gridwidth = 2;
		gbc_btnSelectDirectory.anchor = GridBagConstraints.WEST;
		gbc_btnSelectDirectory.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectDirectory.gridx = 2;
		gbc_btnSelectDirectory.gridy = 4;
		getContentPane().add(btnSelectDirectory, gbc_btnSelectDirectory);

		JButton btnListOfUsers = new JButton("List of Users");
		btnListOfUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.setOption(Client.LIST_USER);
				client.setFile(openFile("Choos CSV file"));
				// TODO remove syso print
				System.out.println(client.getFile().toString());
			}
		});
		GridBagConstraints gbc_btnListOfUsers = new GridBagConstraints();
		gbc_btnListOfUsers.anchor = GridBagConstraints.WEST;
		gbc_btnListOfUsers.insets = new Insets(0, 0, 5, 5);
		gbc_btnListOfUsers.gridx = 0;
		gbc_btnListOfUsers.gridy = 5;
		getContentPane().add(btnListOfUsers, gbc_btnListOfUsers);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("START");
				client.setUserSender(userNameTextField.getText());
				client.setPasswordSender(String.copyValueOf(passwordTextField
						.getPassword()));
			
				client.setHost(serverAddressTextField.getText());
				System.out.println("user " + client.getUserSender());
				System.out.println("pass " + client.getPasswordSender());
				System.out.println("adres " + client.getHost());
				System.out.println("cert Home " + client.getPathToCertFile());
				System.out.println("from file " + client.getFile().toString());
				client.start();
			}
		});

		JButton btnSingleUser = new JButton("Single User");
		btnSingleUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewSingleCertificate singleUserCreator = new NewSingleCertificate(
						client);
				singleUserCreator.setAlwaysOnTop(true);
			}
		});
		GridBagConstraints gbc_btnSingleUser = new GridBagConstraints();
		gbc_btnSingleUser.anchor = GridBagConstraints.WEST;
		gbc_btnSingleUser.insets = new Insets(0, 0, 5, 5);
		gbc_btnSingleUser.gridx = 0;
		gbc_btnSingleUser.gridy = 6;
		getContentPane().add(btnSingleUser, gbc_btnSingleUser);

		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 7;
		getContentPane().add(btnSearch, gbc_btnSearch);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.gridx = 2;
		gbc_btnStart.gridy = 7;
		getContentPane().add(btnStart, gbc_btnStart);
		this.pack();

	}

	public File choosDirectory(String textToButton) {
		JFileChooser directoryChooser = new JFileChooser();
		directoryChooser.setAcceptAllFileFilterUsed(false);
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		directoryChooser.showDialog(this, textToButton);
		directoryChooser.setVisible(true);
		File file = directoryChooser.getSelectedFile();
		return file;
	}

	public File openFile(String textToButton) {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showDialog(this, textToButton);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file;
		}
		fileChooser.setVisible(true);
		return null;
	}
}
