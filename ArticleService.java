package sodabase.services;

import java.beans.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ArticleService {

	private DatabaseConnectionService dbService = null;
	
	public ArticleService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addResturant(String restName, String addr, String contact)  {
		//TODO: Task 5
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		int rel = 0;
		try {
			cs = con.prepareCall("{?=call AddRestaurant(?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, restName);
			cs.setString(3, addr);
			cs.setString(4, contact);
			rel = cs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(rel){
		case 0: return true;
		case 1: JOptionPane.showMessageDialog(null, "Invalid Restaurant Name");
		case 2: JOptionPane.showMessageDialog(null, "Invalid Address");
		case 3: JOptionPane.showMessageDialog(null, "Invalid Contact");
		case 4: JOptionPane.showMessageDialog(null, "Existing Restaurant Name");
		}
		return false;
	}
	

	public ArrayList<String> getArticles() {	

		ArrayList<String> articles = new ArrayList<String>();
		try {
			String query = "select title "+ "from " + "dbo.Article";
			Connection con = this.dbService.getConnection();
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs =stmt.executeQuery(query);
			while(rs.next()){
				articles.add(rs.getString("title"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

	

	public boolean addArticle(Double iD, String title, String publisher, 
			Double libraryID, String author, String permissionType,
			String availability, java.sql.Date Date) throws SQLException {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		cs = con.prepareCall("{call addArticle(?,?,?,?,?,?,?,?)}");
		cs.setDouble(1, iD);
		cs.setString(2, title);
		cs.setString(3, publisher);
		cs.setDouble(4, libraryID);
		cs.setString(5, availability);
		cs.setString(6, author);
		cs.setString(7, permissionType);
		cs.setDate(8, Date);
		Boolean output = cs.execute();
		
		return output;
	}
}
