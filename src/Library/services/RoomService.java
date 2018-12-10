//package Library.services;
//
//public class RoomService {
//
//}
package Library.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Library.ui.Article;
import Library.ui.Room;

public class RoomService {
private DatabaseConnectionService dbService = null;
private String userID;
public RoomService(DatabaseConnectionService dbService){
this.dbService= dbService;
Connection con = this.dbService.getConnection();
CallableStatement cs = null;
}

public RoomService(DatabaseConnectionService dbService,String u){
this.dbService= dbService;
this.userID=u;
Connection con = this.dbService.getConnection();
CallableStatement cs = null;
}
public ArrayList<String> roomNames() {
PreparedStatement ps = null;
Connection con = this.dbService.getConnection();
String query ="SELECT RoomNo FROM StudyRoom";
ResultSet rs;
ArrayList<String> roomNo = new ArrayList<String>();
try {
ps=con.prepareStatement(query);
rs= ps.executeQuery();
while(rs.next()){
roomNo.add(rs.getString(rs.findColumn("RoomNo")));
}
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
// TODO Auto-generated method stub
return roomNo;
}



public ArrayList<Room> getRoom() {
	PreparedStatement ps=null;
	Connection con = this.dbService.getConnection();
	String query = "SELECT RoomID,StartTime,EndTime,Date FROM Reserved";
	try {
		
		
			ps = con.prepareStatement(query);
		
		
		ResultSet rs;
		rs = ps.executeQuery();
		return parseResults(rs);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		return new ArrayList<Room>();
	}

	
	}
private ArrayList<Room> parseResults(ResultSet rs) {
	try {
		ArrayList<Room> articles = new ArrayList<Room>();
		int ID = rs.findColumn("RoomID");
		int S = rs.findColumn("StartTime");
		int E = rs.findColumn("EndTime");
		int D = rs.findColumn("Date");
		
		while (rs.next()) {
			articles.add(new Room(rs.getInt(ID), rs.getTime(S),
					rs.getTime(E), rs.getDate(D)));
		}
		return articles;
	} catch (SQLException ex) {
		JOptionPane.showMessageDialog(null,
				"An error ocurred while retrieving room. See printed stack trace.");
		ex.printStackTrace();
		return new ArrayList<Room>();
	}
}

public boolean reserveRoom (String RoomNo, Time startTime, Time endTime, Date Date){
Connection con= this.dbService.getConnection();
CallableStatement cs = null;
int re1=0;
try {
cs = con.prepareCall("{?=call ReserveRoom(?,?,?,?,?)}");
cs.registerOutParameter(1, Types.INTEGER);
cs.setString(3, this.userID);
cs.setString(2, RoomNo);
cs.setTime(4, startTime);
cs.setTime(5, endTime);
cs.setDate(6, Date);
System.out.println(cs.toString());
cs.executeUpdate();
re1=cs.getInt(1);
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
if(re1==0){
return true;
}
if(re1==1){
JOptionPane.showMessageDialog(null, "RoomID need to be chosen");
}if(re1==2){
JOptionPane.showMessageDialog(null, "UserID need to be entered");
}if(re1==3){
JOptionPane.showMessageDialog(null, "Reservation cannot exceed two hours");
}if(re1==4){
JOptionPane.showMessageDialog(null, "Please select valid ending time");
}
return false;
}
public String getUserid(){
	return this.userID;
}
}

