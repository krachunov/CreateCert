package com.levins.webportal.certificate.client.UI.searchTable;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.levins.webportal.certificate.client.Client;
import com.levins.webportal.certificate.client.UI.ClientPanel;
import com.levins.webportal.certificate.client.UI.popUp.PopUpWindow;
import com.levins.webportal.certificate.connection.FromInsisData;
import com.levins.webportal.certificate.data.CertificateInfo;

@SuppressWarnings("serial")
public class SearchViewUI extends JFrame {
	private SearchModel model;
	private JPanel contentPane;
	private JTable table;
	private TableModel tableModel;
	private JButton btnSend;
	private List<CertificateInfo> listToTable;
	private AutoCompleteTextField searchUserTextField;
	private JLabel lblUserPortal;
	private JLabel lblUserEgn;
	private AutoCompleteTextField egnTextField;
	private ResourceBundle currentBundle;
	// private FromInsisData insis;

	private ClientPanel currentClient;
	private JButton btnSendOther;

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
	 * 
	 * @param insis
	 */
	public SearchViewUI(final ClientPanel thisClient, final FromInsisData insis) {
		this.currentClient = thisClient;
		// this.insis = insis;
		SearchViewUI parrentWindow = this;

		currentBundle = thisClient.getCurrentBundle();

		model = new SearchModel();
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

			@Override
			public void actionPerformed(ActionEvent arg0) {

				searchUserTextField.addPossibility(searchUserTextField
						.getText());
				egnTextField.addPossibility(egnTextField.getText());

				try {
					currentClient.serialize(currentClient.getRestorSettings());
				} catch (IOException e) {
					e.printStackTrace();
				}
				List<String> resultFromDataBase = null;

				try {
					String searchingPortal = searchUserTextField.getText()
							.trim().equals("") ? "%" : searchUserTextField
							.getText().trim();
					String searchingEgn = egnTextField.getText().trim()
							.equals("") ? "%" : egnTextField.getText().trim();
					resultFromDataBase = insis.searchFromDataBase(
							searchingPortal, searchingEgn);
				} catch (SQLException e1) {
					PopUpWindow popUp = new PopUpWindow();
					popUp.popUpMessageException(e1);
				}

				tableModel.setListToTable(SearchModel
						.readString(resultFromDataBase));
				PopUpWindow popUp = new PopUpWindow();
				popUp.popUpMessageText(currentBundle.getString("Search done"));
			}
		});

		lblUserPortal = new JLabel(currentBundle.getString("User WebPortal"));
		GridBagConstraints gbc_lblUserPortal = new GridBagConstraints();
		gbc_lblUserPortal.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserPortal.anchor = GridBagConstraints.EAST;
		gbc_lblUserPortal.gridx = 0;
		gbc_lblUserPortal.gridy = 0;
		contentPane.add(lblUserPortal, gbc_lblUserPortal);

		searchUserTextField = new AutoCompleteTextField();

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

		egnTextField = new AutoCompleteTextField();
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendRow();
			}
		});

		btnSendOther = new JButton("Send Other");
		btnSendOther.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				OtherRecipientWindow sendOther = new OtherRecipientWindow(
						parrentWindow, currentClient);
			}
		});
		GridBagConstraints gbc_btnSendOther = new GridBagConstraints();
		gbc_btnSendOther.insets = new Insets(0, 0, 0, 5);
		gbc_btnSendOther.gridx = 0;
		gbc_btnSendOther.gridy = 3;
		contentPane.add(btnSendOther, gbc_btnSendOther);
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

	public void sendRow(String... otherMail) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {

			String inputSingleUser = tableModel.getRecord(selectedRow);
			// TODO Remove
			System.out.println(inputSingleUser);

			Client client = createNewClientObject(currentClient);

			if (otherMail != null && otherMail.length > 0) {
				client.setOtherRecipient(otherMail[0]);
			}
			client.setOption(Client.SINGLE_USER);
			client.setUserSender(currentClient.getUserNameTextField().getText());
			client.setPasswordSender(String.copyValueOf(currentClient
					.getPasswordTextField().getPassword()));
			client.setHost(currentClient.getServerHostTextField().getText());
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
