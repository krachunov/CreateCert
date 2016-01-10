//package com.levins.webportal.certificate.client.UI.searchTable;
//import java.util.List;
//import java.util.Queue;
//
//import javax.swing.table.AbstractTableModel;
//
//@SuppressWarnings("serial")
//public class TableModel extends AbstractTableModel {
//	private static final int COLUMNS_COUNT = 7;
//	private List<Animal> listToTable;
//
//	public List<Animal> getListToTable() {
//		return listToTable;
//	}
//
//	public void setListToTable(List<Animal> listToTable) {
//		this.listToTable = listToTable;
//		fireTableDataChanged();
//	}
//
//	@Override
//	public int getColumnCount() {
//		return COLUMNS_COUNT;
//	}
//
//	@Override
//	public int getRowCount() {
//
//		return (listToTable != null ? listToTable.size() : 0);
//	}
//
//	@Override
//	public Object getValueAt(int rowIndex, int columnIndex) {
//		List list = getListToTable();
//		Animal singleAnimal = listToTable.get(rowIndex);
//
//		switch (columnIndex) {
//		case 0:
//			return singleAnimal.getDate();
//		case 1:
//			return singleAnimal.getColor();
//		case 2:
//			return singleAnimal.getBreed();
//		case 3:
//			return singleAnimal.getGender();
//		case 4:
//			return singleAnimal.getName();
//		case 5:
//			return singleAnimal.getEntry();
//		case 6:
//			return singleAnimal.getDate();
//		}
//		return null;
//	}
//
//	public void deleteRecord(int index) {
//		listToTable.remove(index);
//		fireTableDataChanged();
//	}
//}
