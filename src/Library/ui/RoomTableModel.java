package Library.ui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class RoomTableModel extends AbstractTableModel{
	private ArrayList<Room> data = null;
	private String[] columnNames = {"RoomID", "StartTime", "EndTime","Date"};
	public RoomTableModel(ArrayList<Room> data) {
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
