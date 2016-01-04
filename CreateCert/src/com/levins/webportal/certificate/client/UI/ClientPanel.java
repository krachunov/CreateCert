package com.levins.webportal.certificate.client.UI;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.levins.webportal.certificate.client.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class ClientPanel extends JFrame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241120844430201231L;
	static final String FILE_TO_LOAD_SETTINGS = "clientSetings";
	private static final String PATH_LOGO = "levins.jpg";

	protected Map<String, Object> restorSettings;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JTextField serverHostTextField;
	private JCheckBox chckbxSave;
	private JButton btnStart;
	private static JTextArea outputConsoleArea;
	private JScrollPane scrollBar;

	private String option;
	private String path;
	private File file;
	private final JLabel lblV = new JLabel("v.0.1");

	public ClientPanel() {
		deserializeInfo();
		final ClientPanel parentComponent = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Client_Window");
		setBounds(100, 100, 400, 250);
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 78, 162, 86, 39 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblSenderUswerName = new JLabel("Sender User name*");
		GridBagConstraints gbc_lblSenderUswerName = new GridBagConstraints();
		gbc_lblSenderUswerName.anchor = GridBagConstraints.EAST;
		gbc_lblSenderUswerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenderUswerName.gridx = 0;
		gbc_lblSenderUswerName.gridy = 1;
		getContentPane().add(lblSenderUswerName, gbc_lblSenderUswerName);

		userNameTextField = restoreAndSavePreviewSession("userNameTextField");
		outputConsoleArea = new JTextArea(5, 50);
		String userTips = "Enter the username for your mail, without domain";
		userNameTextField.setToolTipText(userTips);
		GridBagConstraints gbc_userNameTextField = new GridBagConstraints();
		gbc_userNameTextField.anchor = GridBagConstraints.WEST;
		gbc_userNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userNameTextField.gridx = 1;
		gbc_userNameTextField.gridy = 1;
		getContentPane().add(userNameTextField, gbc_userNameTextField);
		userNameTextField.setColumns(10);

		JButton btnClearSettings = new JButton("Clear Settings");
		btnClearSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearUserSettings();
			}

			private void clearUserSettings() {
				File fileToDelete = new File(FILE_TO_LOAD_SETTINGS);
				userNameTextField.setText("");
				passwordTextField.setText("");
				serverHostTextField.setText("");
				chckbxSave.setSelected(false);
				if (fileToDelete.delete()) {
					popUpMessageText("Settings to connect to Insis server is clear");
					ClientPanel.getOutputConsoleArea().append(
							"Settings to connect to Insis server is clear\n");
				} else {
					popUpMessageText("Settings to connect to Insis server isn't clear");
					ClientPanel
							.getOutputConsoleArea()
							.append("Settings to connect to Insis server isn't clear\n");
				}
			}
		});
		GridBagConstraints gbc_btnClearSettings = new GridBagConstraints();
		gbc_btnClearSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnClearSettings.gridx = 3;
		gbc_btnClearSettings.gridy = 1;
		getContentPane().add(btnClearSettings, gbc_btnClearSettings);
		// TODO - add listener to listen when field has text and when is empty.

		JLabel lblSendersPassword = new JLabel("Sender's password*");
		GridBagConstraints gbc_lblSendersPassword = new GridBagConstraints();
		gbc_lblSendersPassword.anchor = GridBagConstraints.EAST;
		gbc_lblSendersPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblSendersPassword.gridx = 0;
		gbc_lblSendersPassword.gridy = 2;
		getContentPane().add(lblSendersPassword, gbc_lblSendersPassword);

		restoreAndSavePasswordPreviewSession();

		String passwordTips = "Enter the password for your mail";
		passwordTextField.setToolTipText(passwordTips);
		passwordTextField.setColumns(10);
		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.anchor = GridBagConstraints.WEST;
		gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordTextField.gridx = 1;
		gbc_passwordTextField.gridy = 2;
		getContentPane().add(passwordTextField, gbc_passwordTextField);

		try {
			BufferedImage myPicture = ImageIO.read(new File(PATH_LOGO));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridheight = 3;
			gbc_lblNewLabel.gridwidth = 2;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridx = 2;
			gbc_lblNewLabel.gridy = 2;
			getContentPane().add(picLabel, gbc_lblNewLabel);
		} catch (IOException e1) {
			popUpMessageException(e1);
		}

		JLabel lblServerAddress = new JLabel("Server address*");
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.anchor = GridBagConstraints.EAST;
		gbc_lblServerAddress.gridx = 0;
		gbc_lblServerAddress.gridy = 3;
		getContentPane().add(lblServerAddress, gbc_lblServerAddress);

		serverHostTextField = restoreAndSavePreviewSession("serverHostTextField");

		String serverTips = "Enter the IP address of the server";
		serverHostTextField.setToolTipText(serverTips);
		GridBagConstraints gbc_serverAddressTextField = new GridBagConstraints();
		gbc_serverAddressTextField.anchor = GridBagConstraints.WEST;
		gbc_serverAddressTextField.insets = new Insets(0, 0, 5, 5);
		gbc_serverAddressTextField.gridx = 1;
		gbc_serverAddressTextField.gridy = 3;
		getContentPane().add(serverHostTextField, gbc_serverAddressTextField);
		serverHostTextField.setColumns(10);

		JLabel lblSaveSetings = new JLabel("Save setings");
		GridBagConstraints gbc_lblSaveSetings = new GridBagConstraints();
		gbc_lblSaveSetings.anchor = GridBagConstraints.EAST;
		gbc_lblSaveSetings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaveSetings.gridx = 0;
		gbc_lblSaveSetings.gridy = 4;
		getContentPane().add(lblSaveSetings, gbc_lblSaveSetings);

		chckbxSave = new JCheckBox("Save");
		restoreChekBoxSettingsPreviewSession();

		restorSettings.put("chckbxSave", chckbxSave);

		String checkBoxTips = "If check box is selected it will keep your last used settings ";
		chckbxSave.setToolTipText(checkBoxTips);
		GridBagConstraints gbc_chckbxSave = new GridBagConstraints();
		gbc_chckbxSave.anchor = GridBagConstraints.WEST;
		gbc_chckbxSave.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSave.gridx = 1;
		gbc_chckbxSave.gridy = 4;
		getContentPane().add(chckbxSave, gbc_chckbxSave);

		JLabel lblPathToCertificate = new JLabel(
				"Path to certificate root directory*");
		GridBagConstraints gbc_lblPathToCertificate = new GridBagConstraints();
		gbc_lblPathToCertificate.anchor = GridBagConstraints.EAST;
		gbc_lblPathToCertificate.insets = new Insets(0, 0, 5, 5);
		gbc_lblPathToCertificate.gridx = 0;
		gbc_lblPathToCertificate.gridy = 5;
		getContentPane().add(lblPathToCertificate, gbc_lblPathToCertificate);

		// TODO fix problem with directory choose when settings is saved
		JButton btnSelectDirectory = new JButton("Select Directory");
		btnSelectDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!chekFileExist(FILE_TO_LOAD_SETTINGS)) {
					path = choosDirectory("Choose Directory", null).toString();
				} else {
					String restoredValue = (String) restorSettings.get("path");

					path = choosDirectory("Choose Directory", restoredValue)
							.toString();
				}
				restorSettings.put("path", path);

			}
		});
		GridBagConstraints gbc_btnSelectDirectory = new GridBagConstraints();
		gbc_btnSelectDirectory.anchor = GridBagConstraints.WEST;
		gbc_btnSelectDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectDirectory.gridx = 1;
		gbc_btnSelectDirectory.gridy = 5;
		getContentPane().add(btnSelectDirectory, gbc_btnSelectDirectory);
		JButton btnListOfUsers = new JButton("List of Users");

		btnListOfUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option = Client.FILE_WITH_USERS;

				if (!chekFileExist(FILE_TO_LOAD_SETTINGS)) {
					file = openFile("Choos CSV file", null);
				} else {
					String restoredValue = (String) restorSettings.get("file");
					file = openFile("Choos CSV file", restoredValue);
				}
				restorSettings.put("file", file.getPath());

			}
		});
		JButton btnFromInsis = new JButton("From Insis");
		final ClientPanel thisClient = parentComponent;
		btnFromInsis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FromInsisPanel insifForm = new FromInsisPanel(thisClient);
				insifForm.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnFromInsis = new GridBagConstraints();
		gbc_btnFromInsis.insets = new Insets(0, 0, 5, 5);
		gbc_btnFromInsis.gridx = 2;
		gbc_btnFromInsis.gridy = 5;
		getContentPane().add(btnFromInsis, gbc_btnFromInsis);

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
		btnSingleUser
				.setToolTipText("Create and send or only send single user");
		final ClientPanel thisClientForSingleUser = parentComponent;
		btnSingleUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO - create single option
				NewSingleCertificate singleUserCreator = new NewSingleCertificate(
						thisClientForSingleUser);
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
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 8;
		getContentPane().add(btnSearch, gbc_btnSearch);

		btnStart = new JButton("Start");
		// TODO -add checking whether other fields is fill up

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getOutputConsoleArea().append("START");
				getOutputConsoleArea().append("\n");
				if (chckbxSave.isSelected()) {
					try {
						serialize(restorSettings);
					} catch (IOException e1) {
						popUpMessageException(e1);
					}
				}
				createClientAndRun();
			}

			private void createClientAndRun() {
				Client client = new Client();
				client.setUserSender(userNameTextField.getText());
				client.setPasswordSender(String.copyValueOf(passwordTextField
						.getPassword()));
				client.setHost(serverHostTextField.getText());
				client.setOption(option);

				if (chekFileExist(FILE_TO_LOAD_SETTINGS)) {
					String restoredValue = (String) restorSettings.get("path");
					client.setPathToCertFile(restoredValue);
				} else {
					client.setPathToCertFile(path);
				}

				client.setFile(file);
				client.start();
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 2;
		gbc_btnStart.gridy = 8;
		getContentPane().add(btnStart, gbc_btnStart);
		GridBagConstraints gbc_lblV = new GridBagConstraints();
		gbc_lblV.insets = new Insets(0, 0, 5, 0);
		gbc_lblV.gridx = 3;
		gbc_lblV.gridy = 8;
		getContentPane().add(lblV, gbc_lblV);

		outputConsoleArea.setLineWrap(true);
		outputConsoleArea.setWrapStyleWord(true);
		outputConsoleArea.setEditable(false);
		outputConsoleArea.setDropMode(DropMode.INSERT);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 30;
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 9;
		getContentPane().add(outputConsoleArea, gbc_textArea);

		scrollBar = new JScrollPane(outputConsoleArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		gbc_scrollBar.gridwidth = 4;
		gbc_scrollBar.anchor = GridBagConstraints.WEST;
		gbc_scrollBar.gridx = 0;
		gbc_scrollBar.gridy = 9;
		getContentPane().add(scrollBar, gbc_scrollBar);
		parentComponent.pack();

	}

	private void deserializeInfo() {
		if (chekFileExist(FILE_TO_LOAD_SETTINGS)) {
			try {
				restorSettings = deserialize(FILE_TO_LOAD_SETTINGS);
			} catch (FileNotFoundException e1) {
				popUpMessageException(e1);
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				popUpMessageException(e1);
			} catch (IOException e1) {
				popUpMessageException(e1);
			}
		} else {
			restorSettings = new HashMap<String, Object>();
		}
	}

	private void popUpMessageText(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public static void popUpMessageException(Exception e) {
		JOptionPane.showMessageDialog(null, e.toString(), "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void restoreChekBoxSettingsPreviewSession() {
		if (chekFileExist(FILE_TO_LOAD_SETTINGS)) {
			JCheckBox restoredValue = (JCheckBox) restorSettings
					.get("chckbxSave");
			chckbxSave = restoredValue;
			chckbxSave.setSelected(true);
		}
	}

	private JTextField restoreAndSavePreviewSession(String fieldName) {
		JTextField field = null;
		if (chekFileExist(FILE_TO_LOAD_SETTINGS)
				&& restorSettings.containsKey(fieldName)) {
			JTextField restoredValue = (JTextField) restorSettings
					.get(fieldName);
			field = new JTextField(restoredValue.getText(), 20);
		} else {
			field = new JTextField("", 20);
		}
		restorSettings.put(fieldName, field);
		return field;
	}

	private void restoreAndSavePasswordPreviewSession() {
		if (!chekFileExist(FILE_TO_LOAD_SETTINGS)
				&& !restorSettings.containsKey("passwordTextField")) {
			passwordTextField = new JPasswordField();
		} else {
			JPasswordField restoredValue = (JPasswordField) restorSettings
					.get("passwordTextField");
			passwordTextField = new JPasswordField(
					String.copyValueOf(restoredValue.getPassword()), 20);
		}
		restorSettings.put("passwordTextField", passwordTextField);
	}

	public File choosDirectory(String textToButton, String defaultLocation) {
		JFileChooser directoryChooser = new JFileChooser(defaultLocation);
		directoryChooser.setAcceptAllFileFilterUsed(false);
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		directoryChooser.showDialog(this, textToButton);
		directoryChooser.setVisible(true);
		File file = directoryChooser.getSelectedFile();
		return file;
	}

	public File openFile(String textToButton, String defaultLocation) {
		JFileChooser fileChooser = new JFileChooser(defaultLocation);
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

	static boolean chekFileExist(String fileName) {
		File file = new File(fileName);
		if (file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
	}

	public void serialize(Map<String, Object> client) throws IOException {
		File file = new File(FILE_TO_LOAD_SETTINGS);
		FileOutputStream fileOutput = new FileOutputStream(file);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

		try {
			objectOutput.writeObject(client);
		} finally {
			if (fileOutput != null) {
				fileOutput.close();
			}
			if (objectOutput != null) {
				objectOutput.close();
			}
		}
	}

	public static Map<String, Object> deserialize(String fileToDeserialize)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		FileInputStream fileInput = new FileInputStream(fileToDeserialize);
		ObjectInputStream objectImput = new ObjectInputStream(fileInput);
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> deserializeClient = (Map<String, Object>) objectImput
					.readObject();
			return deserializeClient;
		} finally {
			if (fileInput != null) {
				fileInput.close();
			}
			if (objectImput != null) {
				objectImput.close();
			}
		}
	}

	public JTextField getUserNameTextField() {
		return userNameTextField;
	}

	public void setUserNameTextField(JTextField userNameTextField) {
		this.userNameTextField = userNameTextField;
	}

	public JPasswordField getPasswordTextField() {
		return passwordTextField;
	}

	public void setPasswordTextField(JPasswordField passwordTextField) {
		this.passwordTextField = passwordTextField;
	}

	public JTextField getServerHostTextField() {
		return serverHostTextField;
	}

	public void setServerHostTextField(JTextField serverHostTextField) {
		this.serverHostTextField = serverHostTextField;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Object> getRestorSettings() {
		return restorSettings;
	}

	public void setRestorSettings(Map<String, Object> restorSettings) {
		this.restorSettings = restorSettings;
	}

	public JCheckBox getChckbxSave() {
		return chckbxSave;
	}

	public void setChckbxSave(JCheckBox chckbxSave) {
		this.chckbxSave = chckbxSave;
	}

	public static JTextArea getOutputConsoleArea() {
		return outputConsoleArea;
	}

	public static void setOutputConsoleArea(JTextArea outputConsoleArea) {
		ClientPanel.outputConsoleArea = outputConsoleArea;
	}

}