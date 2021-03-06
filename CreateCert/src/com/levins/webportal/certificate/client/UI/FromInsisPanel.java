package com.levins.webportal.certificate.client.UI;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JCheckBox;

import com.levins.webportal.certificate.client.Client;
import com.levins.webportal.certificate.client.UI.popUp.PopUpWindow;
import com.levins.webportal.certificate.connection.FromInsisData;
import com.levins.webportal.certificate.data.DataValidator;

public class FromInsisPanel extends JFrame implements Serializable,
		CreateCertificateInterface {

	private static final long serialVersionUID = -3564265605924594950L;
	public static final String FILE_TO_LOAD_INSIS_SETTINGS = "insisDBSetings";
	private JTextField serverIPAddresstextField;
	private JTextField serverPortTextField;
	private JTextField dataBaseNameTextField;
	private JTextField insisUserTextField;
	private JPasswordField insisPasswordTextField;
	private JButton btnStart;
	private JCheckBox chckbxSave;
	private JLabel lblSaveSettings;
	private Map<String, Object> restorSettings;
	private JButton btnClearSettings;
	private JLabel lblPort;
	@SuppressWarnings("unused")
	private ClientPanel clientPanel;
	private JLabel lblSingleUser;
	private JTextField singleWebPortalUsertextField;
	private ResourceBundle currentBundle;

	public FromInsisPanel(final ClientPanel clientPanel) {
		this.clientPanel = clientPanel;
		this.restorSettings = deserializeInfoInsisForm();
		this.currentBundle = clientPanel.getCurrentBundle();

		setTitle(currentBundle
				.getString("Create certificate with info from INSIS"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 137, 68, 52, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		System.out.println(currentBundle.getLocale());
		JLabel lblServerIpAddress = new JLabel(
				currentBundle.getString("Server IP address"));

		GridBagConstraints gbc_lblServerIpAddress = new GridBagConstraints();
		gbc_lblServerIpAddress.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblServerIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerIpAddress.gridx = 0;
		gbc_lblServerIpAddress.gridy = 1;
		getContentPane().add(lblServerIpAddress, gbc_lblServerIpAddress);

		serverIPAddresstextField = restoreField("serverIPAddresstextField");

		GridBagConstraints gbc_serverIPAddresstextField = new GridBagConstraints();
		gbc_serverIPAddresstextField.anchor = GridBagConstraints.WEST;
		gbc_serverIPAddresstextField.insets = new Insets(0, 0, 5, 5);
		gbc_serverIPAddresstextField.gridx = 1;
		gbc_serverIPAddresstextField.gridy = 1;
		getContentPane().add(serverIPAddresstextField,
				gbc_serverIPAddresstextField);
		serverIPAddresstextField.setColumns(10);

		lblPort = new JLabel(currentBundle.getString("Port*"));
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 2;
		gbc_lblPort.gridy = 1;
		getContentPane().add(lblPort, gbc_lblPort);

		// TODO - add restrict onli digit
		serverPortTextField = restoreField("portTextField");
		GridBagConstraints gbc_portTextField = new GridBagConstraints();
		gbc_portTextField.anchor = GridBagConstraints.WEST;
		gbc_portTextField.insets = new Insets(0, 0, 5, 0);
		gbc_portTextField.gridx = 3;
		gbc_portTextField.gridy = 1;
		getContentPane().add(serverPortTextField, gbc_portTextField);
		serverPortTextField.setColumns(10);

		JLabel lblDataBaseName = new JLabel(
				currentBundle.getString("Data Base Name"));
		GridBagConstraints gbc_lblDataBaseName = new GridBagConstraints();
		gbc_lblDataBaseName.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDataBaseName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataBaseName.gridx = 0;
		gbc_lblDataBaseName.gridy = 2;
		getContentPane().add(lblDataBaseName, gbc_lblDataBaseName);

		dataBaseNameTextField = restoreField("dataBaseNameTextField");

		GridBagConstraints gbc_dataBaseNameTextField = new GridBagConstraints();
		gbc_dataBaseNameTextField.anchor = GridBagConstraints.WEST;
		gbc_dataBaseNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_dataBaseNameTextField.gridx = 1;
		gbc_dataBaseNameTextField.gridy = 2;
		getContentPane().add(dataBaseNameTextField, gbc_dataBaseNameTextField);
		dataBaseNameTextField.setColumns(10);

		JLabel lblUser = new JLabel(currentBundle.getString("User"));
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 0;
		gbc_lblUser.gridy = 3;
		getContentPane().add(lblUser, gbc_lblUser);

		insisUserTextField = restoreField("insisUserTextField");

		GridBagConstraints gbc_userTextField = new GridBagConstraints();
		gbc_userTextField.anchor = GridBagConstraints.WEST;
		gbc_userTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userTextField.gridx = 1;
		gbc_userTextField.gridy = 3;
		getContentPane().add(insisUserTextField, gbc_userTextField);
		insisUserTextField.setColumns(10);

		btnClearSettings = new JButton(
				currentBundle.getString("Clear Settings"));
		btnClearSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearSettings();
			}

			private void clearSettings() {
				File fileToDelete = new File(FILE_TO_LOAD_INSIS_SETTINGS);
				serverIPAddresstextField.setText("");
				dataBaseNameTextField.setText("");
				insisUserTextField.setText("");
				insisPasswordTextField.setText("");
				serverPortTextField.setText("");

				if (fileToDelete.delete()) {
					ClientPanel
							.getOutputConsoleArea()
							.append(currentBundle
									.getString("Settings to connect to Insis server is clear")
									+ "\n");
				} else {
					ClientPanel
							.getOutputConsoleArea()
							.append(currentBundle
									.getString("Settings to connect to Insis server isn't clear")
									+ "\n");
				}
			}
		});
		GridBagConstraints gbc_btnClearSettings = new GridBagConstraints();
		gbc_btnClearSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnClearSettings.gridx = 3;
		gbc_btnClearSettings.gridy = 3;
		getContentPane().add(btnClearSettings, gbc_btnClearSettings);

		JLabel lblPassword = new JLabel(currentBundle.getString("Password"));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 4;
		getContentPane().add(lblPassword, gbc_lblPassword);

		restoreAndSavePasswordPreviewSession();

		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordTextField.anchor = GridBagConstraints.WEST;
		gbc_passwordTextField.gridx = 1;
		gbc_passwordTextField.gridy = 4;
		getContentPane().add(insisPasswordTextField, gbc_passwordTextField);
		insisPasswordTextField.setColumns(10);

		lblSaveSettings = new JLabel(currentBundle.getString("Save Settings"));
		GridBagConstraints gbc_lblSaveSettings = new GridBagConstraints();
		gbc_lblSaveSettings.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSaveSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaveSettings.gridx = 0;
		gbc_lblSaveSettings.gridy = 5;
		getContentPane().add(lblSaveSettings, gbc_lblSaveSettings);

		chckbxSave = new JCheckBox(currentBundle.getString("Save"));
		restoreChekBoxSettingsPreviewSession();

		GridBagConstraints gbc_chckbxSave = new GridBagConstraints();
		gbc_chckbxSave.anchor = GridBagConstraints.WEST;
		gbc_chckbxSave.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSave.gridx = 1;
		gbc_chckbxSave.gridy = 5;
		getContentPane().add(chckbxSave, gbc_chckbxSave);

		lblSingleUser = new JLabel(currentBundle.getString("Single User"));
		GridBagConstraints gbc_lblSingleUser = new GridBagConstraints();
		gbc_lblSingleUser.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSingleUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblSingleUser.gridx = 0;
		gbc_lblSingleUser.gridy = 6;
		getContentPane().add(lblSingleUser, gbc_lblSingleUser);

		singleWebPortalUsertextField = new JTextField();
		GridBagConstraints gbc_singleWebPortalUsertextField = new GridBagConstraints();
		gbc_singleWebPortalUsertextField.insets = new Insets(0, 0, 5, 5);
		gbc_singleWebPortalUsertextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_singleWebPortalUsertextField.gridx = 1;
		gbc_singleWebPortalUsertextField.gridy = 6;
		getContentPane().add(singleWebPortalUsertextField,
				gbc_singleWebPortalUsertextField);
		singleWebPortalUsertextField.setColumns(10);

		btnStart = new JButton(currentBundle.getString("Start"));
		if (!DataValidator.chekFileExist(FILE_TO_LOAD_INSIS_SETTINGS)) {
			btnStart.setEnabled(false);
		}
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				checkSerialize();

				FromInsisData insis = createObjectInsisData();

				List<String> resultFromDataBase = insis.getResultFromInsis(
						insis, singleWebPortalUsertextField.getText());

				Client client = new Client();
				client.setUserSender(clientPanel.getUserNameTextField()
						.getText());
				client.setPasswordSender(String.copyValueOf(clientPanel
						.getPasswordTextField().getPassword()));
				client.setHost(clientPanel.getServerHostTextField().getText());
				client.setOption(Client.LIST_USER);
				client.setListWithUsers(resultFromDataBase);
				client.setPathToCertFile(clientPanel.getPath());

				client.start();

			}

			/**
			 * If checkbox is selected - save connection settings
			 */
			private void checkSerialize() {
				if (chckbxSave.isSelected()) {
					try {
						serialize(restorSettings);
					} catch (IOException e1) {
						PopUpWindow popUp = new PopUpWindow();
						popUp.popUpMessageException(e1,
								"Problem with serialize");
					}
				}
			}

			private FromInsisData createObjectInsisData() {
				final String ip = getServerIPAddresstextField().getText();
				final String port = getServerPortTextField().getText();
				final String DBName = getDataBaseNameTextField().getText();
				final String insisUser = getInsisUserTextField().getText();
				final String insisPass = String
						.copyValueOf(getInsisPasswordTextField().getPassword());
				FromInsisData insis = new FromInsisData(ip, port, DBName,
						insisUser, insisPass);
				return insis;
			}
		});

		DocumentListenerClient listenerConnectionToServer = new DocumentListenerClient(
				btnStart);
		listenerConnectionToServer.addTextField(serverIPAddresstextField);
		listenerConnectionToServer.addTextField(serverPortTextField);
		listenerConnectionToServer.addTextField(dataBaseNameTextField);
		listenerConnectionToServer.addTextField(insisUserTextField);
		listenerConnectionToServer.addTextField(insisPasswordTextField);

		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 0, 5);
		gbc_btnStart.gridx = 2;
		gbc_btnStart.gridy = 7;
		getContentPane().add(btnStart, gbc_btnStart);
		pack();
	}

	private void restoreChekBoxSettingsPreviewSession() {
		if (DataValidator.chekFileExist(FILE_TO_LOAD_INSIS_SETTINGS)) {
			chckbxSave.setSelected(true);
		}
	}

	private Map<String, Object> deserializeInfoInsisForm() {
		Map<String, Object> restorSettings = null;
		if (DataValidator.chekFileExist(FILE_TO_LOAD_INSIS_SETTINGS)) {
			try {
				restorSettings = deserialize(FILE_TO_LOAD_INSIS_SETTINGS);
			} catch (FileNotFoundException e1) {
				PopUpWindow popUp = new PopUpWindow();
				popUp.popUpMessageException(e1);
			} catch (ClassNotFoundException e1) {
				PopUpWindow popUp = new PopUpWindow();
				popUp.popUpMessageException(e1);
			} catch (IOException e1) {
				PopUpWindow popUp = new PopUpWindow();
				popUp.popUpMessageException(e1);
			}
		} else {
			restorSettings = new HashMap<String, Object>();
		}
		return restorSettings;
	}

	/**
	 * Save settings if check box is mark
	 * 
	 * @param fieldTSave
	 */
	@SuppressWarnings("unused")
	private void saveSettings(JTextField fieldTSave, String fileName) {
		if (chckbxSave.isSelected()) {
			this.restorSettings.put(fileName, fieldTSave);
		}
	}

	private JTextField restoreField(String fieldName) {
		JTextField field;
		if (DataValidator.chekFileExist(FILE_TO_LOAD_INSIS_SETTINGS)
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
		final String passwordKey = "insisPasswordTextField";
		if (!DataValidator.chekFileExist(FILE_TO_LOAD_INSIS_SETTINGS)
				&& !restorSettings.containsKey("passwordTextField")) {
			insisPasswordTextField = new JPasswordField();
		} else {
			JPasswordField restoredValue = (JPasswordField) restorSettings
					.get(passwordKey);
			insisPasswordTextField = new JPasswordField(
					String.copyValueOf(restoredValue.getPassword()), 20);
		}
		restorSettings.put(passwordKey, insisPasswordTextField);
	}

	public void serialize(Map<String, Object> client) throws IOException {
		File file = new File(FILE_TO_LOAD_INSIS_SETTINGS);
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

	public JTextField getServerIPAddresstextField() {
		return serverIPAddresstextField;
	}

	public void setServerIPAddresstextField(JTextField serverIPAddresstextField) {
		this.serverIPAddresstextField = serverIPAddresstextField;
	}

	public JTextField getServerPortTextField() {
		return serverPortTextField;
	}

	public JTextField getDataBaseNameTextField() {
		return dataBaseNameTextField;
	}

	public JTextField getInsisUserTextField() {
		return insisUserTextField;
	}

	public JPasswordField getInsisPasswordTextField() {
		return insisPasswordTextField;
	}

	public void setCurrentBundle(ResourceBundle currentBundle) {
		this.currentBundle = currentBundle;
	}

}
