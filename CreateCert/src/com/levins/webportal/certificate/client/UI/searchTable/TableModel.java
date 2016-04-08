package com.levins.webportal.certificate.client.UI.searchTable;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.levins.webportal.certificate.data.CertificateInfo;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {
	private static final int COLUMNS_COUNT = 7;
	private List<CertificateInfo> listToTable;

	public List<CertificateInfo> getListToTable() {
		return listToTable;
	}

	public void setListToTable(List<CertificateInfo> listToTable) {
		this.listToTable = listToTable;
		fireTableDataChanged();
	}

	public int getColumnCount() {
		return COLUMNS_COUNT;
	}

	public int getRowCount() {

		return (listToTable != null ? listToTable.size() : 0);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		CertificateInfo singleCert = listToTable.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return singleCert.getUserName();
		case 1:
			return singleCert.getFirstName();
		case 2:
			return singleCert.getLastName();
		case 3:
			return singleCert.getEmail();
		case 4:
			return singleCert.getPassword();
		case 5:
			return singleCert.getPathToCertificateFile();
		case 6:
			return singleCert.getEgn();
		}
		return null;

	}
	@Override
	public String getColumnName(int column) {
		switch (column) {
		// case 0:
		// return "ID";
		case 0:
			return "Portal User";
		case 1:
			return "First name";
		case 2:
			return "Last Name";
		case 3:
			return "E-mail";
		case 4:
			return "Certificate password";
		case 5:
			return "Date create";
		case 6:
			return "EGN";

		default:
			break;
		}
		return null;
	}

	public String getRecord(int index) {
		CertificateInfo record = listToTable.get(index);
		fireTableDataChanged();
		return record.toString();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
