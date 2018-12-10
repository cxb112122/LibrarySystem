package Library.ui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class LibraryTableModel extends AbstractTableModel{

	private ArrayList<Article> data = null;
	private String[] columnNames = {"Title", "ID", "Author","availability"};
	public LibraryTableModel(ArrayList<Article> data) {
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.data.get(rowIndex).getValue(this.columnNames[columnIndex]);
	}
	
	@Override
	public String getColumnName(int index) {
		return this.columnNames[index];
	}
	

}
