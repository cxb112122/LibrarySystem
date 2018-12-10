package Library.ui;

import java.sql.Time;

public class Room {
	private int RoomID;
	private Time StartTime;
	private Time EndTime;
	private java.sql.Date Date;
	
	
	public Room(int roomid,Time s,Time e,java.sql.Date d){
	this.RoomID=roomid;
	this.StartTime=s;
	this.EndTime=e;
	this.Date=d;
	}
	
	//TODO: refactor this
	public String getValue(String propertyName) {
		switch (propertyName) {
			case "RoomID":
				return this.RoomID+"";
			case "StartTime":
				return this.StartTime.toString();
			case "EndTime":
				return this.EndTime.toString();
			case "Date":
				return this.Date.toString();
		}
		return null;
	}
	
	
	public String gets() {
		return this.StartTime.toString();
	}
	public double getID() {
		return this.RoomID;
	}
	public String gete() {
		return this.EndTime.toString();
	}
			
	public String getD() {
		return this.Date.toString();
	}
}
