package Library.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import Library.services.ArticleService;
import Library.services.RoomService;


public class ReserveRoomPanel extends JPanel {

	private RoomService rService = null;
	private JComboBox<String> roomName;
	//private JTextField userID;
	private JComboBox<String> year;
	private JComboBox<String> Month;
	private JComboBox<String> date;
	private JComboBox<String> startTime;
	private JComboBox<String> endTime;
	private JTable RTable=null;
	public ReserveRoomPanel(RoomService rService) {
		this.rService = rService;
		this.setLayout(new BorderLayout());
		JPanel createReserveRoom = createReserveRoomPanel();
		this.add(createReserveRoom, BorderLayout.NORTH);
		
		JScrollPane tablePane = generateRoomTable();
		this.add(tablePane, BorderLayout.CENTER);
	}

	private JScrollPane generateRoomTable() {
		
		this.RTable = new JTable(this.search());
		JScrollPane scrollPane = new JScrollPane(this.RTable);
		this.RTable.setFillsViewportHeight(true);
		return scrollPane;
	}

	private TableModel search() {
		ArrayList<Room> data = this.rService.getRoom();
		return new RoomTableModel(data);
	}

	private JPanel createReserveRoomPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		this.roomName = new JComboBox<String>();
		this.roomName.addItem("None");
		for (String s : this.rService.roomNames())
			this.roomName.addItem(s);

		this.year = new JComboBox<String>();
		this.year.addItem("year");
		this.year.addItem("2018");
		this.year.addItem("2019");
		this.Month = new JComboBox<String>();
		this.Month.addItem("Month");
		for(int i =1; i<=12; i++)
			if(i<10){

				this.Month.addItem("0"+i);
			}
			else{

				this.Month.addItem(i+"");
			}
		this.date=new JComboBox<String>();
		this.date.addItem("Date");
		for(int j=1; j<=31; j++){
			if(j<10){
				this.date.addItem("0"+j);
			}
			else{
			this.date.addItem(j+"");
			}
		}
		this.startTime = new JComboBox<String>();
		for(int a =8; a<=23; a++){
			if(a<10){
				this.startTime.addItem("0"+a+":00");
				//this.startTime.addItem("0"+a+":30");
			}
			else{
				this.startTime.addItem(a+":00");
				//this.startTime.addItem(a+":30");
			}
		}
		this.endTime = new JComboBox<String>();
		for(int a =8; a<=23; a++){
			if(a<10){
				this.endTime.addItem("0"+a+":00");
				//this.endTime.addItem("0"+a+":30");
			}
			else{
				this.endTime.addItem(a+":00");
				//this.endTime.addItem(a+":30");
			}
		}

		toReturn.add(new JLabel("UserID:"));
		//toReturn.add(this.userID);
		toReturn.add(new JLabel("Room No.:"));
		toReturn.add(this.roomName);
		toReturn.add(new JLabel("Select Date:"));
		toReturn.add(this.year);
		toReturn.add(this.Month);
		toReturn.add(this.date);
		
		toReturn.add(new JLabel("Start Time:"));
		toReturn.add(this.startTime);
		toReturn.add(new JLabel("End Time:"));
		toReturn.add(this.endTime);
		JButton addButton = new JButton("Reserve Room");
		toReturn.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reserveRoom();
				RTable.setModel(search());
			}

		});
		return toReturn;
	}

	protected boolean reserveRoom() {
		String date = this.year.getSelectedItem()+""+this.Month.getSelectedItem()+""+this.date.getSelectedItem();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		java.util.Date newdate = null;
		SimpleDateFormat timeformatter = new SimpleDateFormat("HH:MM");
		java.util.Date stTime =null;
		java.util.Date endTime =null;
		try{
			newdate = format.parse(date);
			stTime = timeformatter.parse((String) this.startTime.getSelectedItem());
			endTime = timeformatter.parse((String) this.endTime.getSelectedItem());
			
		}catch(ParseException e){
			e.printStackTrace();
		}
		java.sql.Date sqldate = new java.sql.Date(newdate.getTime());
		java.sql.Time sqlstart= new java.sql.Time(stTime.getTime());
		java.sql.Time sqlend = new java.sql.Time(endTime.getTime());
		

		if(this.rService.reserveRoom( (String) this.roomName.getSelectedItem(), sqlstart, sqlend, sqldate)){
			//this.userID.setText("");
			return true;
		}
		return false;
		
	}


}
