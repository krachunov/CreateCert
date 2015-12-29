package com.levins.webportal.certificate.client.UI;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.levins.webportal.certificate.client.Client;

import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class ClientPanel extends JFrame {
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JTextField serverHostTextField;
	private JCheckBox chckbxSave;
	private JButton btnStart;

	private String option;
	private String path;
	private File file;

	public ClientPanel() {
		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Client_Window");
		setBounds(100, 100, 400, 250);
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 78, 162, 86, 75 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
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

		crateUserNameTextField();

		JLabel lblSendersPassword = new JLabel("Sender's password");
		GridBagConstraints gbc_lblSendersPassword = new GridBagConstraints();
		gbc_lblSendersPassword.anchor = GridBagConstraints.EAST;
		gbc_lblSendersPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblSendersPassword.gridx = 0;
		gbc_lblSendersPassword.gridy = 2;
		getContentPane().add(lblSendersPassword, gbc_lblSendersPassword);

		cratePasswordTextField();
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("resources\\levins.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JLabel picLabel  = new JLabel(new ImageIcon(myPicture));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 3;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 2;
		getContentPane().add(picLabel , gbc_lblNewLabel);

		JLabel lblServerAddress = new JLabel("Server address");
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.anchor = GridBagConstraints.EAST;
		gbc_lblServerAddress.gridx = 0;
		gbc_lblServerAddress.gridy = 3;
		getContentPane().add(lblServerAddress, gbc_lblServerAddress);

		crateHostTextField();

		JLabel lblSaveSetings = new JLabel("Save setings");
		GridBagConstraints gbc_lblSaveSetings = new GridBagConstraints();
		gbc_lblSaveSetings.anchor = GridBagConstraints.EAST;
		gbc_lblSaveSetings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaveSetings.gridx = 0;
		gbc_lblSaveSetings.gridy = 4;
		getContentPane().add(lblSaveSetings, gbc_lblSaveSetings);

		chckbxSave = new JCheckBox("Save");
		GridBagConstraints gbc_chckbxSave = new GridBagConstraints();
		gbc_chckbxSave.anchor = GridBagConstraints.WEST;
		gbc_chckbxSave.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSave.gridx = 1;
		gbc_chckbxSave.gridy = 4;
		getContentPane().add(chckbxSave, gbc_chckbxSave);
		System.out.println(chckbxSave.isSelected());

		JLabel lblPathToCertificate = new JLabel(
				"Path to certificate root directory");
		GridBagConstraints gbc_lblPathToCertificate = new GridBagConstraints();
		gbc_lblPathToCertificate.anchor = GridBagConstraints.EAST;
		gbc_lblPathToCertificate.insets = new Insets(0, 0, 5, 5);
		gbc_lblPathToCertificate.gridx = 0;
		gbc_lblPathToCertificate.gridy = 5;
		getContentPane().add(lblPathToCertificate, gbc_lblPathToCertificate);

		JButton btnSelectDirectory = new JButton("Select Directory");
		btnSelectDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				path = choosDirectory("Choose Directory").toString();
			}
		});
		GridBagConstraints gbc_btnSelectDirectory = new GridBagConstraints();
		gbc_btnSelectDirectory.gridwidth = 2;
		gbc_btnSelectDirectory.anchor = GridBagConstraints.WEST;
		gbc_btnSelectDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectDirectory.gridx = 1;
		gbc_btnSelectDirectory.gridy = 5;
		getContentPane().add(btnSelectDirectory, gbc_btnSelectDirectory);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("START");
				Client client = new Client();

				client.setUserSender(userNameTextField.getText());
				client.setPasswordSender(String.copyValueOf(passwordTextField
						.getPassword()));
				client.setHost(serverHostTextField.getText());
				client.setOption(option);
				client.setPathToCertFile(path);
				client.setFile(file);

				System.out.println("user " + client.getUserSender());
				System.out.println("pass " + client.getPasswordSender());
				System.out.println("adres " + client.getHost());
				System.out.println("cert Home " + client.getPathToCertFile());
				System.out.println("from file " + client.getFile().toString());
				client.start();
			}
		});

		JButton btnListOfUsers = new JButton("List of Users");

		btnListOfUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option = Client.LIST_USER;
				file = openFile("Choos CSV file");
			}
		});

		JLabel lblChooseFileWith = new JLabel("Choose file with new users");
		GridBagConstraints gbc_lblChooseFileWith = new GridBagConstraints();
		gbc_lblChooseFileWith.anchor = GridBagConstraints.EAST;
		gbc_lblChooseFileWith.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseFileWith.gridx = 0;
		gbc_lblChooseFileWith.gridy = 6;
		getContentPane().add(lblChooseFileWith, gbc_lblChooseFileWith);
		GridBagConstraints gbc_btnListOfUsers = new GridBagConstraints();
		gbc_btnListOfUsers.anchor = GridBagConstraints.WEST;
		gbc_btnListOfUsers.insets = new Insets(0, 0, 5, 5);
		gbc_btnListOfUsers.gridx = 1;
		gbc_btnListOfUsers.gridy = 6;
		getContentPane().add(btnListOfUsers, gbc_btnListOfUsers);

		JButton btnSingleUser = new JButton("Single User");
		btnSingleUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO
				NewSingleCertificate singleUserCreator = new NewSingleCertificate();
				singleUserCreator.setAlwaysOnTop(true);
			}
		});

		JLabel lblCreateOneNew = new JLabel("Create one new user");
		GridBagConstraints gbc_lblCreateOneNew = new GridBagConstraints();
		gbc_lblCreateOneNew.anchor = GridBagConstraints.EAST;
		gbc_lblCreateOneNew.insets = new Insets(0, 0, 5, 5);
		gbc_lblCreateOneNew.gridx = 0;
		gbc_lblCreateOneNew.gridy = 7;
		getContentPane().add(lblCreateOneNew, gbc_lblCreateOneNew);
		GridBagConstraints gbc_btnSingleUser = new GridBagConstraints();
		gbc_btnSingleUser.anchor = GridBagConstraints.WEST;
		gbc_btnSingleUser.insets = new Insets(0, 0, 5, 5);
		gbc_btnSingleUser.gridx = 1;
		gbc_btnSingleUser.gridy = 7;
		getContentPane().add(btnSingleUser, gbc_btnSingleUser);

		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 8;
		getContentPane().add(btnSearch, gbc_btnSearch);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.gridx = 2;
		gbc_btnStart.gridy = 8;
		getContentPane().add(btnStart, gbc_btnStart);
		this.pack();

	}

	private void crateHostTextField() {
		serverHostTextField = new JTextField();
		GridBagConstraints gbc_serverAddressTextField = new GridBagConstraints();
		gbc_serverAddressTextField.anchor = GridBagConstraints.WEST;
		gbc_serverAddressTextField.insets = new Insets(0, 0, 5, 5);
		gbc_serverAddressTextField.gridx = 1;
		gbc_serverAddressTextField.gridy = 3;
		getContentPane().add(serverHostTextField, gbc_serverAddressTextField);
		serverHostTextField.setColumns(10);
	}

	private void cratePasswordTextField() {
		passwordTextField = new JPasswordField();
		passwordTextField.setColumns(10);
		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.anchor = GridBagConstraints.WEST;
		gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordTextField.gridx = 1;
		gbc_passwordTextField.gridy = 2;
		getContentPane().add(passwordTextField, gbc_passwordTextField);
	}

	private void crateUserNameTextField() {
		userNameTextField = new JTextField("", 20);
		userNameTextField.setToolTipText("");
		GridBagConstraints gbc_userNameTextField = new GridBagConstraints();
		gbc_userNameTextField.anchor = GridBagConstraints.WEST;
		gbc_userNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userNameTextField.gridx = 1;
		gbc_userNameTextField.gridy = 1;
		getContentPane().add(userNameTextField, gbc_userNameTextField);
		userNameTextField.setColumns(10);
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"CSV FILES", "csv");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showDialog(this, textToButton);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file;
		}
		fileChooser.setVisible(true);
		return null;
	}

}