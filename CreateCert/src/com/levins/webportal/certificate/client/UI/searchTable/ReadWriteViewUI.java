package com.levins.webportal.certificate.client.UI.searchTable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTable;

import com.levins.webportal.certificate.client.Client;
import com.levins.webportal.certificate.client.UI.ClientPanel;
import com.levins.webportal.certificate.client.UI.FromInsisPanel;
import com.levins.webportal.certificate.data.CertificateInfo;
import com.levins.webportal.certificate.data.DataValidator;
import com.levins.webportal.certificate.data.FromInsisData;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JTextField;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ReadWriteViewUI extends JFrame {
	private ReadWriteModel model;
	private JPanel contentPane;
	private JTable table;
	private TableModel tableModel;
	private JButton btnSend;
	private List<CertificateInfo> listToTable;
	private JTextField searchUserTextField;
	private JLabel lblUserPortal;
	private JLabel lblUserEgn;
	private JTextField egnTextField;
	private ResourceBundle currentBundle;
	// private FromInsisData

	private ClientPanel currentClient;

	public TableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public List<CertificateInfo> getListToTable() {
		return listToTable;
	}

	/**
	 * Create the frame.
	 */
	public ReadWriteViewUI(final ClientPanel thisClient) {
		this.currentClient = thisClient;
		currentBundle = thisClient.getCurrentBundle();

		model = new ReadWriteModel();
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 94, 208, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		tableModel = new TableModel();
		JButton btnFind = new JButton(currentBundle.getString("Find"));
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InsisDBConnectionWindow conn = null;
				List<String> resultFromDataBase = null;
				if (!DataValidator
						.chekFileExist(FromInsisPanel.FILE_TO_LOAD_INSIS_SETTINGS)) {
					conn = new InsisDBConnectionWindow(currentClient);
					conn.setVisible(true);
					System.out.println("nqma");
				} else {
					System.out.println("IMA");
					conn = new InsisDBConnectionWindow(currentClient);
				}

				final String ip = conn.getServerIPAddresstextField().getText();

				final String port = conn.getServerPortTextField().getText();

				final String DBName = conn.getDataBaseNameTextField().getText();

				final String insisUser = conn.getInsisUserTextField().getText();

				final String insisPass = String.copyValueOf(conn
						.getInsisPasswordTextField().getPassword());

				FromInsisData insis = new FromInsisData(ip, port, DBName,
						insisUser, insisPass);
				try {
					String searchingPortal = searchUserTextField.getText()
							.trim().equals("") ? "%" : searchUserTextField
							.getText();
					String searchingEgn = egnTextField.getText().trim()
							.equals("") ? "%" : egnTextField.getText();
					resultFromDataBase = insis.searchFromDataBase(
							searchingPortal, searchingEgn);
				} catch (SQLException e1) {
					ClientPanel.popUpMessageException(e1);
				}
				tableModel.setListToTable(ReadWriteModel
						.readString(resultFromDataBase));
				ClientPanel.popUpMessageText(currentBundle
						.getString("Search done"));
			}
		});

		lblUserPortal = new JLabel(currentBundle.getString("User WebPortal"));
		GridBagConstraints gbc_lblUserPortal = new GridBagConstraints();
		gbc_lblUserPortal.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserPortal.anchor = GridBagConstraints.EAST;
		gbc_lblUserPortal.gridx = 0;
		gbc_lblUserPortal.gridy = 0;
		contentPane.add(lblUserPortal, gbc_lblUserPortal);

		searchUserTextField = new JTextField();
		GridBagConstraints gbc_searchUserTextField = new GridBagConstraints();
		gbc_searchUserTextField.insets = new Insets(0, 0, 5, 5);
		gbc_searchUserTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchUserTextField.gridx = 1;
		gbc_searchUserTextField.gridy = 0;
		contentPane.add(searchUserTextField, gbc_searchUserTextField);
		searchUserTextField.setColumns(10);
		GridBagConstraints gbc_btnFind = new GridBagConstraints();
		gbc_btnFind.anchor = GridBagConstraints.WEST;
		gbc_btnFind.insets = new Insets(0, 0, 5, 0);
		gbc_btnFind.gridx = 2;
		gbc_btnFind.gridy = 0;
		contentPane.add(btnFind, gbc_btnFind);

		lblUserEgn = new JLabel(currentBundle.getString("User EGN"));
		GridBagConstraints gbc_lblUserEgn = new GridBagConstraints();
		gbc_lblUserEgn.anchor = GridBagConstraints.EAST;
		gbc_lblUserEgn.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserEgn.gridx = 0;
		gbc_lblUserEgn.gridy = 1;
		contentPane.add(lblUserEgn, gbc_lblUserEgn);

		egnTextField = new JTextField();
		GridBagConstraints gbc_egnTextField = new GridBagConstraints();
		gbc_egnTextField.insets = new Insets(0, 0, 5, 5);
		gbc_egnTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_egnTextField.gridx = 1;
		gbc_egnTextField.gridy = 1;
		contentPane.add(egnTextField, gbc_egnTextField);
		egnTextField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);

		table = new JTable(tableModel);
		scrollPane.setViewportView(table);

		btnSend = new JButton(currentBundle.getString("Send"));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendRow();
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.gridwidth = 2;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 3;
		contentPane.add(btnSend, gbc_btnSend);
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

	public void sendRow() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {

			String inputSingleUser = tableModel.getRecord(selectedRow);
			// TODO Remove
			System.out.println(inputSingleUser);
			Client client = createNewClientObject(currentClient);

			client.setUserSender(currentClient.getUserNameTextField().getText());
			client.setPasswordSender(String.copyValueOf(currentClient
					.getPasswordTextField().getPassword()));
			client.setHost(currentClient.getServerHostTextField().getText());
			client.setOption(Client.SINGLE_USER);
			client.setInputSingleUser(inputSingleUser);
			client.setPathToCertFile(currentClient.getPath());
			client.start();
			ClientPanel.getOutputConsoleArea().append(inputSingleUser);
		}
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

	public void saveFile() throws IOException {

		model.writeNewFile(tableModel.getListToTable(), openFile("Save"));
	}

}
