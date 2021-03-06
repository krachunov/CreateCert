package com.levins.webportal.certificate.client.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.levins.webportal.certificate.client.Client;
import com.levins.webportal.certificate.client.MailSender;
import com.levins.webportal.certificate.client.UI.i18n.SwingLocaleChangedListener;
import com.levins.webportal.certificate.client.UI.popUp.PopUpWindow;
import com.levins.webportal.certificate.client.UI.searchTable.SearchViewUI;
import com.levins.webportal.certificate.connection.FromInsisData;
import com.levins.webportal.certificate.connection.InsisDBConnectionWindow;
import com.levins.webportal.certificate.data.DataValidator;
import com.levins.webportal.certificate.data.ErrorLog;
import com.levins.webportal.certificate.data.UserGenerator;

public class ClientPanel extends JFrame implements Serializable,
		CreateCertificateInterface {
	private static final long serialVersionUID = -6241120844430201231L;
	static final String FILE_TO_LOAD_SETTINGS = "clientSetings";
	private static final String PATH_LOGO = "levins.jpg";
	private static final String VERSION = "v.0.4";
	protected static final String BUNDLE_NAME = "MyProperties";

	protected Map<String, Object> restorSettings;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JTextField serverHostTextField;
	private JCheckBox chckbxSave;
	private JButton createUserFromFileBtn;
	private JButton btnClearSettings;
	private static JTextArea outputConsoleArea;
	private JScrollPane scrollBar;
	protected ResourceBundle currentBundle;
	private JRadioButton rdbtnBg;
	private JRadioButton rdbtnEn;

	private String option;
	private String path;
	private File file;
	private final JLabel lblV = new JLabel(VERSION);
	private SwingLocaleChangedListener changedResourceBundle;

	public ClientPanel() {

		deserializeInfo();
		currentBundle = ResourceBundle.getBundle(ClientPanel.BUNDLE_NAME,
				Locale.UK);
		changedResourceBundle = new SwingLocaleChangedListener(this);

		final ClientPanel parentComponent = this;
		outputConsoleArea = new JTextArea(5, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String title = "Client_Window";
		setTitle(title);

		setBounds(100, 100, 640, 500);
		Dimension dimension = new Dimension(640, 380);
		setMinimumSize(dimension);
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 78, 74, 86, 39 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		rdbtnEn = new JRadioButton("En");
		GridBagConstraints gbc_rdbtnEn = new GridBagConstraints();
		gbc_rdbtnEn.anchor = GridBagConstraints.EAST;
		gbc_rdbtnEn.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnEn.gridx = 0;
		gbc_rdbtnEn.gridy = 0;
		getContentPane().add(rdbtnEn, gbc_rdbtnEn);
		// TODO Fix listener
		rdbtnEn.addActionListener(new RadioButtonListener(rdbtnEn,
				changedResourceBundle));
		rdbtnEn.setSelected(true);

		rdbtnBg = new JRadioButton("Bg");
		GridBagConstraints gbc_rdbtnBg = new GridBagConstraints();
		gbc_rdbtnBg.anchor = GridBagConstraints.WEST;
		gbc_rdbtnBg.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnBg.gridx = 1;
		gbc_rdbtnBg.gridy = 0;
		getContentPane().add(rdbtnBg, gbc_rdbtnBg);
		rdbtnBg.addActionListener(new RadioButtonListener(rdbtnBg,
				changedResourceBundle));

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnEn);
		buttonGroup.add(rdbtnBg);

		JButton btnSendErrorLog = new JButton("Send Error Log");
		btnSendErrorLog.setEnabled(false);

		btnSendErrorLog.setForeground(SystemColor.inactiveCaptionBorder);
		btnSendErrorLog.setBackground(SystemColor.textHighlight);
		btnSendErrorLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (DataValidator.chekFileExist(ErrorLog.ERROR_LOG_FILE_NAME)) {

					MailSender sendLog = new MailSender();
					sendLog.sendErrorLog(userNameTextField.getText(),
							String.copyValueOf(passwordTextField.getPassword()));
					File erroLogFile = new File(ErrorLog.ERROR_LOG_FILE_NAME);
					erroLogFile.delete();
				} else {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageText(currentBundle
							.getString("There is no have error log"));
				}

			}
		});
		changedResourceBundle.addButtons(btnSendErrorLog);

		GridBagConstraints gbc_btnSendErrorLog = new GridBagConstraints();
		gbc_btnSendErrorLog.anchor = GridBagConstraints.EAST;
		gbc_btnSendErrorLog.insets = new Insets(0, 0, 5, 0);
		gbc_btnSendErrorLog.gridx = 3;
		gbc_btnSendErrorLog.gridy = 0;
		getContentPane().add(btnSendErrorLog, gbc_btnSendErrorLog);

		JLabel lblSenderUswerName = new JLabel("Sender User name*");
		changedResourceBundle.addLabel(lblSenderUswerName);

		GridBagConstraints gbc_lblSenderUswerName = new GridBagConstraints();
		gbc_lblSenderUswerName.anchor = GridBagConstraints.WEST;
		gbc_lblSenderUswerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenderUswerName.gridx = 0;
		gbc_lblSenderUswerName.gridy = 1;
		getContentPane().add(lblSenderUswerName, gbc_lblSenderUswerName);

		userNameTextField = restoreAndSavePreviewSession("userNameTextField");

		String userTips = currentBundle
				.getString("Enter the username for your mail, without domain");
		userNameTextField.setToolTipText(userTips);
		GridBagConstraints gbc_userNameTextField = new GridBagConstraints();
		gbc_userNameTextField.anchor = GridBagConstraints.WEST;
		gbc_userNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userNameTextField.gridx = 1;
		gbc_userNameTextField.gridy = 1;
		getContentPane().add(userNameTextField, gbc_userNameTextField);
		userNameTextField.setColumns(10);

		btnClearSettings = new JButton("Clear Settings");
		btnClearSettings.setForeground(SystemColor.inactiveCaptionBorder);
		btnClearSettings.setBackground(SystemColor.textHighlight);
		changedResourceBundle.addButtons(btnClearSettings);

		btnClearSettings.addActionListener(new ActionListener() {
			@Override
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
					final String messageClearSettings = "Settings to connect to Insis server is clear";
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageText(messageClearSettings);
					getOutputConsoleArea().append(messageClearSettings + "\n");
				} else {
					final String messageCantClearSettings = "Settings to connect to Insis server isn't clear";
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageText(messageCantClearSettings);
					getOutputConsoleArea().append(
							messageCantClearSettings + "\n");
				}
			}
		});
		GridBagConstraints gbc_btnClearSettings = new GridBagConstraints();
		gbc_btnClearSettings.gridwidth = 2;
		gbc_btnClearSettings.anchor = GridBagConstraints.EAST;
		gbc_btnClearSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnClearSettings.gridx = 2;
		gbc_btnClearSettings.gridy = 1;
		getContentPane().add(btnClearSettings, gbc_btnClearSettings);

		JLabel lblSendersPassword = new JLabel("Sender's password*");
		changedResourceBundle.addLabel(lblSendersPassword);
		GridBagConstraints gbc_lblSendersPassword = new GridBagConstraints();
		gbc_lblSendersPassword.anchor = GridBagConstraints.WEST;
		gbc_lblSendersPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblSendersPassword.gridx = 0;
		gbc_lblSendersPassword.gridy = 2;
		getContentPane().add(lblSendersPassword, gbc_lblSendersPassword);

		restoreAndSavePasswordPreviewSession();

		DocumentListenerClient listenerSendErrorLogBtn = new DocumentListenerClient(
				btnSendErrorLog);
		listenerSendErrorLogBtn.addTextField(userNameTextField);
		listenerSendErrorLogBtn.addTextField(passwordTextField);

		String passwordTips = "Enter the password for your mail";
		passwordTextField.setToolTipText(currentBundle.getString(passwordTips));
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
			gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblNewLabel.gridheight = 3;
			gbc_lblNewLabel.gridwidth = 2;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridx = 2;
			gbc_lblNewLabel.gridy = 2;
			getContentPane().add(picLabel, gbc_lblNewLabel);
		} catch (IOException e1) {
			PopUpWindow popUp = new PopUpWindow();
			popUp.popUpMessageException(e1, "Error with logo");
		}

		JLabel lblServerAddress = new JLabel("Server address*");
		changedResourceBundle.addLabel(lblServerAddress);

		lblServerAddress
				.setToolTipText("IP address of the server that creates certificates");
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.anchor = GridBagConstraints.WEST;
		gbc_lblServerAddress.gridx = 0;
		gbc_lblServerAddress.gridy = 3;
		getContentPane().add(lblServerAddress, gbc_lblServerAddress);

		serverHostTextField = restoreAndSavePreviewSession("serverHostTextField");

		String serverTips = "Enter the IP address of the server that creates certificates";
		serverHostTextField.setToolTipText(serverTips);
		GridBagConstraints gbc_serverAddressTextField = new GridBagConstraints();
		gbc_serverAddressTextField.anchor = GridBagConstraints.WEST;
		gbc_serverAddressTextField.insets = new Insets(0, 0, 5, 5);
		gbc_serverAddressTextField.gridx = 1;
		gbc_serverAddressTextField.gridy = 3;
		getContentPane().add(serverHostTextField, gbc_serverAddressTextField);
		serverHostTextField.setColumns(10);

		JLabel lblSaveSetings = new JLabel("Save setings");
		changedResourceBundle.addLabel(lblSaveSetings);
		GridBagConstraints gbc_lblSaveSetings = new GridBagConstraints();
		gbc_lblSaveSetings.anchor = GridBagConstraints.WEST;
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

		JLabel lblCreateFromFile = new JLabel("Create from File");
		lblCreateFromFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblCreateFromFile = new GridBagConstraints();
		gbc_lblCreateFromFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblCreateFromFile.gridx = 2;
		gbc_lblCreateFromFile.gridy = 5;
		getContentPane().add(lblCreateFromFile, gbc_lblCreateFromFile);

		JLabel lblCreateFromInsis = new JLabel("Create From Insis");
		lblCreateFromInsis.setFont(new Font("Tahoma", Font.PLAIN, 16));
		changedResourceBundle.addLabel(lblCreateFromInsis);
		GridBagConstraints gbc_lblCreateFromInsis = new GridBagConstraints();
		gbc_lblCreateFromInsis.insets = new Insets(0, 0, 5, 0);
		gbc_lblCreateFromInsis.gridx = 3;
		gbc_lblCreateFromInsis.gridy = 5;
		getContentPane().add(lblCreateFromInsis, gbc_lblCreateFromInsis);

		JLabel lblPathToCertificate = new JLabel(
				"<html>Path to certificate<br>root directory*</html>");
		changedResourceBundle.addLabel(lblPathToCertificate);

		GridBagConstraints gbc_lblPathToCertificate = new GridBagConstraints();
		gbc_lblPathToCertificate.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPathToCertificate.insets = new Insets(0, 0, 5, 5);
		gbc_lblPathToCertificate.gridx = 0;
		gbc_lblPathToCertificate.gridy = 6;
		getContentPane().add(lblPathToCertificate, gbc_lblPathToCertificate);

		path = (String) restorSettings.get("path");
		JButton btnListOfUsers = new JButton("List of Users");
		changedResourceBundle.addButtons(btnListOfUsers);

		btnListOfUsers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				option = Client.FILE_WITH_USERS;

				if (!DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
					file = openFile(currentBundle.getString("Choos CSV file"),
							null);
				} else {
					String restoredValue = (String) restorSettings.get("file");
					file = openFile(currentBundle.getString("Choos CSV file"),
							restoredValue);
				}
				restorSettings.put("file", file.getPath());

			}
		});
		JButton btnSelectDirectory = new JButton("Select Directory");
		changedResourceBundle.addButtons(btnSelectDirectory);

		btnSelectDirectory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
					path = choosDirectory(
							currentBundle.getString("Choose Directory"), null)
							.toString();
					restorSettings.put("path", path);

				} else {
					String restoredValue = (String) restorSettings.get("path");
					path = choosDirectory(
							currentBundle.getString("Choose Directory"),
							restoredValue).toString();
					restorSettings.put("path", path);
				}
			}
		});
		GridBagConstraints gbc_btnSelectDirectory = new GridBagConstraints();
		gbc_btnSelectDirectory.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSelectDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectDirectory.gridx = 1;
		gbc_btnSelectDirectory.gridy = 6;
		getContentPane().add(btnSelectDirectory, gbc_btnSelectDirectory);

		JButton btnFromInsis = new JButton("Single user From Insis");
		btnFromInsis.setForeground(SystemColor.inactiveCaptionBorder);
		btnFromInsis.setBackground(SystemColor.activeCaption);
		changedResourceBundle.addButtons(btnFromInsis);

		btnFromInsis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serialize(restorSettings);
				} catch (IOException e1) {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageException(e1, "Error with serialize");
				}
				FromInsisPanel insifForm = new FromInsisPanel(parentComponent);
				insifForm.setVisible(true);
			}
		});

		createUserFromFileBtn = new JButton("Create users from file");
		createUserFromFileBtn.setBackground(SystemColor.info);
		changedResourceBundle.addButtons(createUserFromFileBtn);

		createUserFromFileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getOutputConsoleArea().append("START");
				getOutputConsoleArea().append("\n");
				if (chckbxSave.isSelected()) {
					try {
						serialize(restorSettings);
					} catch (IOException e1) {
						PopUpWindow popUp = new PopUpWindow();
						popUp.popUpMessageException(e1, "Error with serialize");
					}
				}
				System.out.println("START button press");
				createClientAndRun();
			}

			// TODO remove syso
			private void createClientAndRun() {
				Client client = new Client();
				client.setUserSender(userNameTextField.getText());
				client.setPasswordSender(String.copyValueOf(passwordTextField
						.getPassword()));
				client.setHost(serverHostTextField.getText());
				client.setOption(option);

				System.out.println("Sender " + client.getUserSender());
				System.out.println("Sender pass" + client.getPasswordSender());
				System.out.println("host " + client.getHost());
				System.out.println("option " + client.getOption());

				if (DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
					String restoredValue = (String) restorSettings.get("path");
					client.setPathToCertFile(restoredValue);
					System.out.println("if file exist path "
							+ client.getPathToCertFile());
				} else {
					client.setPathToCertFile(path);
					System.out.println("if file does not exist path "
							+ client.getPathToCertFile());
				}

				client.setFile(file);
				client.start();
			}
		});

		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.anchor = GridBagConstraints.NORTH;
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 2;
		gbc_btnStart.gridy = 6;
		getContentPane().add(createUserFromFileBtn, gbc_btnStart);
		GridBagConstraints gbc_btnFromInsis = new GridBagConstraints();
		gbc_btnFromInsis.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnFromInsis.insets = new Insets(0, 0, 5, 0);
		gbc_btnFromInsis.gridx = 3;
		gbc_btnFromInsis.gridy = 6;
		getContentPane().add(btnFromInsis, gbc_btnFromInsis);
		if (!DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
			btnFromInsis.setEnabled(false);
		}
		DocumentListenerClient listenerToInsis = new DocumentListenerClient(
				btnFromInsis);
		listenerToInsis.addTextField(userNameTextField);
		listenerToInsis.addTextField(passwordTextField);
		listenerToInsis.addTextField(serverHostTextField);

		JLabel lblChooseFileWith = new JLabel(
				"<html>Choose file<br>with new users</html>");
		lblChooseFileWith.setHorizontalAlignment(SwingConstants.RIGHT);
		changedResourceBundle.addLabel(lblChooseFileWith);
		GridBagConstraints gbc_lblChooseFileWith = new GridBagConstraints();
		gbc_lblChooseFileWith.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblChooseFileWith.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseFileWith.gridx = 0;
		gbc_lblChooseFileWith.gridy = 7;
		getContentPane().add(lblChooseFileWith, gbc_lblChooseFileWith);
		GridBagConstraints gbc_btnListOfUsers = new GridBagConstraints();
		gbc_btnListOfUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnListOfUsers.insets = new Insets(0, 0, 5, 5);
		gbc_btnListOfUsers.gridx = 1;
		gbc_btnListOfUsers.gridy = 7;
		getContentPane().add(btnListOfUsers, gbc_btnListOfUsers);

		JButton btnSearch = new JButton("Search");
		changedResourceBundle.addButtons(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serialize(restorSettings);
				} catch (IOException e1) {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageException(e1, "Error with serialize");
				}
				FromInsisData insis = parentComponent.createFromInsisData();

				SearchViewUI searchTable = new SearchViewUI(parentComponent,
						insis);
				searchTable.setVisible(true);
			}

		});

		JButton btnMultipleUserFrom = new JButton("Multiple user from INSIS");
		// TODO
		if (!DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
			btnMultipleUserFrom.setEnabled(false);
		}
		DocumentListenerClient listenerMultipleUser = new DocumentListenerClient(
				btnMultipleUserFrom);
		listenerMultipleUser.addTextField(userNameTextField);
		listenerMultipleUser.addTextField(passwordTextField);
		listenerMultipleUser.addTextField(serverHostTextField);

		btnMultipleUserFrom.setForeground(SystemColor.inactiveCaptionBorder);
		btnMultipleUserFrom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serialize(restorSettings);
				} catch (IOException e1) {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageException(e1, "Error with serialize");
				}

				List<String> createListOfUserFromFile = null;
				FromInsisData insis = parentComponent.createFromInsisData();
				List<String> resultFromDataBase = new ArrayList<String>();

				if (parentComponent.getFile() != null) {
					UserGenerator userGenerator = new UserGenerator();
					try {
						createListOfUserFromFile = userGenerator
								.createListOfUserFromFile(parentComponent
										.getFile());
						for (String currentUser : createListOfUserFromFile) {
							try {
								resultFromDataBase.addAll(insis
										.selectWebPortalUserFromDataBase(currentUser));

							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}

					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				} else {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageText(currentBundle
							.getString("There is no selected file"));
				}

				if (resultFromDataBase.size() > 0 && resultFromDataBase != null) {
					Client client = new Client();
					client.setUserSender(parentComponent.getUserNameTextField()
							.getText());
					client.setPasswordSender(String.copyValueOf(parentComponent
							.getPasswordTextField().getPassword()));
					client.setHost(parentComponent.getServerHostTextField()
							.getText());
					client.setOption(Client.LIST_USER);
					client.setListWithUsers(resultFromDataBase);
					client.setPathToCertFile(parentComponent.getPath());

					client.start();
				} else {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageText(currentBundle
							.getString("Users or user do not exist in the database"));
				}

			}
		});
		changedResourceBundle.addButtons(btnMultipleUserFrom);
		btnMultipleUserFrom.setBackground(SystemColor.activeCaption);
		GridBagConstraints gbc_btnMultipleUserFrom = new GridBagConstraints();
		gbc_btnMultipleUserFrom.anchor = GridBagConstraints.EAST;
		gbc_btnMultipleUserFrom.insets = new Insets(0, 0, 5, 0);
		gbc_btnMultipleUserFrom.gridx = 3;
		gbc_btnMultipleUserFrom.gridy = 7;
		getContentPane().add(btnMultipleUserFrom, gbc_btnMultipleUserFrom);

		Panel panel = new Panel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 8;
		getContentPane().add(panel, gbc_panel);

		JButton btnOnlyCreate = new JButton("Only Create");
		btnOnlyCreate.setForeground(SystemColor.inactiveCaptionBorder);
		btnOnlyCreate.setBackground(SystemColor.inactiveCaption);
		DocumentListenerClient listenerOnlyCreator = new DocumentListenerClient(
				btnOnlyCreate);
		listenerOnlyCreator.addTextField(userNameTextField);
		listenerOnlyCreator.addTextField(passwordTextField);
		listenerOnlyCreator.addTextField(serverHostTextField);

		btnOnlyCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serialize(restorSettings);
				} catch (IOException e1) {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageException(e1, "Error with serialize");
				}

				List<String> createListOfUserFromFile = null;
				FromInsisData insis = parentComponent.createFromInsisData();
				List<String> resultFromDataBase = new ArrayList<String>();

				if (parentComponent.getFile() != null) {
					UserGenerator userGenerator = new UserGenerator();
					try {
						createListOfUserFromFile = userGenerator
								.createListOfUserFromFile(parentComponent
										.getFile());
						for (String currentUser : createListOfUserFromFile) {
							try {
								resultFromDataBase.addAll(insis
										.selectWebPortalUserFromDataBase(currentUser));

							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}

					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				} else {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageText(currentBundle
							.getString("There is no selected file"));
				}

				if (resultFromDataBase.size() > 0 && resultFromDataBase != null) {
					Client client = new Client();
					client.setUserSender(parentComponent.getUserNameTextField()
							.getText());
					client.setPasswordSender(String.copyValueOf(parentComponent
							.getPasswordTextField().getPassword()));
					client.setHost(parentComponent.getServerHostTextField()
							.getText());
					client.setOption(Client.FILE_WITH_USERS_ONLY_CREATE);
					client.setListWithUsers(resultFromDataBase);
					client.setPathToCertFile(parentComponent.getPath());

					client.start();
				} else {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageText(currentBundle
							.getString("Users or user do not exist in the database"));
				}

			}
		});
		changedResourceBundle.addButtons(btnOnlyCreate);
		GridBagConstraints gbc_btnOnlyCreate = new GridBagConstraints();
		gbc_btnOnlyCreate.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnOnlyCreate.insets = new Insets(0, 0, 5, 0);
		gbc_btnOnlyCreate.gridx = 3;
		gbc_btnOnlyCreate.gridy = 9;
		getContentPane().add(btnOnlyCreate, gbc_btnOnlyCreate);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBackground(Color.YELLOW);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 10;
		getContentPane().add(horizontalStrut, gbc_horizontalStrut);

		JLabel lblSearchExistUsers = new JLabel("Search exist users");
		changedResourceBundle.addLabel(lblSearchExistUsers);
		GridBagConstraints gbc_lblSearchExistUsers = new GridBagConstraints();
		gbc_lblSearchExistUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSearchExistUsers.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearchExistUsers.gridx = 0;
		gbc_lblSearchExistUsers.gridy = 11;
		getContentPane().add(lblSearchExistUsers, gbc_lblSearchExistUsers);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 11;
		getContentPane().add(btnSearch, gbc_btnSearch);

		if (!DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
			createUserFromFileBtn.setEnabled(false);
		}

		DocumentListenerClient listenerCreateUserFromFile = new DocumentListenerClient(
				createUserFromFileBtn);
		listenerCreateUserFromFile.addTextField(userNameTextField);
		listenerCreateUserFromFile.addTextField(passwordTextField);
		listenerCreateUserFromFile.addTextField(serverHostTextField);
		GridBagConstraints gbc_lblV = new GridBagConstraints();
		gbc_lblV.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblV.insets = new Insets(0, 0, 5, 0);
		gbc_lblV.gridx = 3;
		gbc_lblV.gridy = 11;
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
		gbc_scrollBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollBar.gridwidth = 4;
		gbc_scrollBar.gridx = 0;
		gbc_scrollBar.gridy = 13;
		getContentPane().add(scrollBar, gbc_scrollBar);
		parentComponent.pack();

	}

	private void deserializeInfo() {
		if (DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
			PopUpWindow popUp = null;
			try {
				restorSettings = deserialize(FILE_TO_LOAD_SETTINGS);
			} catch (FileNotFoundException e1) {
				popUp = new PopUpWindow();
				popUp.popUpMessageException(e1,
						"Error with deserialize - file not fond");
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				popUp = new PopUpWindow();
				popUp.popUpMessageException(e1,
						"Error with deserialize - ClassNotFoundException");
			} catch (IOException e1) {
				popUp = new PopUpWindow();
				popUp.popUpMessageException(e1,
						"Error with deserialize - IOException");
			}
		} else {
			restorSettings = new HashMap<String, Object>();
		}
	}

	private void restoreChekBoxSettingsPreviewSession() {
		if (DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)) {
			JCheckBox restoredValue = (JCheckBox) restorSettings
					.get("chckbxSave");
			chckbxSave = restoredValue;
			chckbxSave.setSelected(true);
		}
	}

	private JTextField restoreAndSavePreviewSession(String fieldName) {
		JTextField field = null;
		if (DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)
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
		if (!DataValidator.chekFileExist(FILE_TO_LOAD_SETTINGS)
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
		int returnVal = directoryChooser.showDialog(this, textToButton);
		// TODO check
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.exit(1);
		}
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
		} else {
			fileChooser.setVisible(false);
		}
		fileChooser.setVisible(true);
		return null;
	}

	private FromInsisData createFromInsisData() {

		InsisDBConnectionWindow conn = checkConnectionToDB(this);

		final String ip = conn.getServerIPAddresstextField().getText();

		final String port = conn.getServerPortTextField().getText();

		final String DBName = conn.getDataBaseNameTextField().getText();

		final String insisUser = conn.getInsisUserTextField().getText();

		final String insisPass = String.copyValueOf(conn
				.getInsisPasswordTextField().getPassword());

		FromInsisData insis = new FromInsisData(ip, port, DBName, insisUser,
				insisPass);
		return insis;
	}

	private static InsisDBConnectionWindow checkConnectionToDB(
			final ClientPanel parentComponent) {
		InsisDBConnectionWindow conn = null;
		if (!DataValidator
				.chekFileExist(FromInsisPanel.FILE_TO_LOAD_INSIS_SETTINGS)) {
			conn = new InsisDBConnectionWindow(parentComponent);
			conn.setVisible(true);
			conn.setAlwaysOnTop(true);
		} else {
			conn = new InsisDBConnectionWindow(parentComponent);
		}
		return conn;
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

	public ResourceBundle getCurrentBundle() {
		return currentBundle;
	}

	@Override
	public void setCurrentBundle(ResourceBundle currentBundle) {
		this.currentBundle = currentBundle;
	}

	public SwingLocaleChangedListener getChangedResourceBundle() {
		return changedResourceBundle;
	}

	public void setChangedResourceBundle(
			SwingLocaleChangedListener changedResourceBundle) {
		this.changedResourceBundle = changedResourceBundle;
	}

	public File getFile() {
		return file;
	}

}