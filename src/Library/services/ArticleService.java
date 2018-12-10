package Library.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Library.ui.Article;
import Library.ui.Borrow;



public class ArticleService {
	private String u;
	private String[] dat;
	private DatabaseConnectionService dbService = null;

	public ArticleService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	public ArticleService(DatabaseConnectionService dbService,String u)  {
		
		this.dbService = dbService;
		this.u=u;
		try {
			this.populate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean addArticle(int iD, String title, String publisher, 
			Double libraryID, String author, String permissionType,
			 Date Date) throws SQLException {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		cs = con.prepareCall("{call addArticle(?,?,?,?,?,?,?)}");
		cs.setDouble(1, iD);
		cs.setString(2, title);
		cs.setString(3, publisher);
		cs.setDouble(4, libraryID);
		cs.setString(5, author);
		cs.setString(6, permissionType);	
		cs.setDate(7, Date);
		Boolean output = cs.execute();
		JOptionPane.showMessageDialog(null, "add successful.");
		return output;
	}
	public boolean BorrowArticle(int AID, int idm) throws SQLException{
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		cs = con.prepareCall("{call Borrow1(?,?,?)}");
		cs.setString(1, u);
		cs.setInt(2, AID);
		
		

		
		cs.setInt(3, idm);
		Boolean output = cs.execute();
		JOptionPane.showMessageDialog(null, "Borrow successful.");
		return output;
	}
	
	
	public void populate() throws IOException{
		File file = new File("1.txt");
		
		  @SuppressWarnings("resource")
		  BufferedReader br = new BufferedReader(new FileReader(file));
		 
		
		  String text = "";
			String line = br.readLine();
			// Text text is now a long string with every single character contained
			while(line != null){
				text += line;
				//System.out.println(line);
				line = br.readLine();
			}
			// Data data is now an array combined by strings
			dat = text.split("/");
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			
			
			//------------------
			for(int i=1;i<dat.length;i=i+7){
				System.out.println(dat[i]);
				System.out.println(dat[i+1]);
				System.out.println(dat[i+2]);
				System.out.println(dat[i+3]);
				System.out.println(dat[i+4]);
				System.out.println(dat[i+5]);
				System.out.println(dat[i+6]);
				java.util.Date date =null;
				java.sql.Date sqldate=null;
				try {
					date = format.parse(dat[i+6]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sqldate = new java.sql.Date(date.getTime());
				try {
					this.addArticle(Integer.parseInt(dat[i]), dat[i+1], dat[i+2], Double.parseDouble(dat[i+3]), dat[i+4], dat[i+5], sqldate);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileWriter fw = new FileWriter(file);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.append('A');
	        bw.flush();
	        bw.close();
	}
	public boolean returnArticle(int ID,int idm) throws SQLException{
//		Connection con = this.dbService.getConnection();
//		CallableStatement cs = null;
//		cs = con.prepareCall("{call Return1(?,?,?)}");
//		cs.setInt(1, ID);
//		cs.setString(2, u);
//		cs.setInt(3, idm);
//		
//		Boolean output = cs.execute();
//		JOptionPane.showMessageDialog(null, "return successful.");
//		return output;
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		
		cs = con.prepareCall("{?=call Return1(?,?,?)}");
		cs.registerOutParameter(1, Types.INTEGER);
		//System.out.println(arg0);
		cs.setInt(2, ID);
		cs.setString(3, u);
		cs.setInt(4, idm);
		Boolean output = cs.execute();
		int re1 = cs.getInt(1);
		
		if(re1!=0)
			JOptionPane.showMessageDialog(null, "Return unsuccessful.");
		else
			JOptionPane.showMessageDialog(null, "Return successful.");
		return output;
	}
	public ArrayList<Borrow> getBorrow(){
		PreparedStatement ps=null;
		Connection con = this.dbService.getConnection();
		String query = "SELECT id FROM AllBorrowInfo WHERE ReturnDate IS NULL";
		try {
			ps = con.prepareStatement(query);
			ResultSet rs;
			
			rs = ps.executeQuery();
			return parseBorrow(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new ArrayList<Borrow>();
		}
		
	
	}
	public ArrayList<Article> getArticle(Double ID) {
		PreparedStatement ps=null;
		Connection con = this.dbService.getConnection();
		String query = "SELECT Title, ID, Author,availability FROM AllBnA";
		try {
			
			if(ID!=0){
				query+=" WHERE ID =?";
				ps = con.prepareStatement(query);
				ps.setDouble(1, ID);
			}
			else{	
				ps = con.prepareStatement(query);
			
			}
			ResultSet rs;
			rs = ps.executeQuery();
			return parseResults(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new ArrayList<Article>();
		}

		
		}
	
	public boolean deleteArticle(int iD) throws SQLException {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		cs = con.prepareCall("{call deleteArticle(?)}");
		cs.setInt(1, iD);
		Boolean output = cs.execute();
		JOptionPane.showMessageDialog(null, "Delete successful.");
		return output;
	}

	public boolean editArticle(Double iD) {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		try {
			cs = con.prepareCall("{call dupdateAva(?)}");
			cs.setDouble(1, iD);
			Boolean output = cs.execute();

			return output;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
		
	
	/**
	 * Creates a string containing ? in the correct places in the SQL statement based on the filter information provided.
	 * 
	 * @param rest - The restaurant to match
	 * @param soda - The soda to match
	 * @param price - The price to compare to
	 * @param useGreaterThanEqual - If true, the prices returned should be greater than or equal to what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed 
	 */
//	private String buildParameterizedSqlStatementString(String rest, String soda, Double price, boolean useGreaterThanEqual) {
//		String sqlStatement = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n";
//		ArrayList<String> wheresToAdd = new ArrayList<String>();
//
//		if (rest != null) {
//			wheresToAdd.add("Restaurant = ?");
//		}
//		if (soda != null) {
//			wheresToAdd.add("Soda = ?");
//		}
//		if (price != null) {
//			if (useGreaterThanEqual) {
//				wheresToAdd.add("Price >= ?");
//			} else {
//				wheresToAdd.add("Price <= ?");
//			}
//		}
//		boolean isFirst = true;
//		while (wheresToAdd.size() > 0) {
//			if (isFirst) {
//				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
//				isFirst = false;
//			} else {
//				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
//			}
//		}
//		return sqlStatement;
//	}

	private ArrayList<Article> parseResults(ResultSet rs) {
		try {
			ArrayList<Article> articles = new ArrayList<Article>();
			int TitleIndex = rs.findColumn("Title");
			int AuthorIndex = rs.findColumn("Author");
			int IDIndex = rs.findColumn("ID");
			int AvaIndex = rs.findColumn("availability");
			
			while (rs.next()) {
				articles.add(new Article(rs.getString(TitleIndex), rs.getInt(IDIndex),
						rs.getString(AuthorIndex), rs.getString(AvaIndex)));
			}
			return articles;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving articles. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Article>();
		}

	}
	
	///////////////
	private ArrayList<Borrow> parseBorrow(ResultSet rs) {
		try {
			ArrayList<Borrow> borrow = new ArrayList<Borrow>();
			int ID = rs.findColumn("id");
			
			while (rs.next()) {
				borrow.add(new Borrow( rs.getInt(ID)));
			}
			return borrow;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas borrows. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Borrow>();
		}

	}
	///////////////
	public ArrayList<String> getArticlesTitle() {	

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


	public String getUserid(){
		return this.u;
	}

	public boolean editAuthor(Double iD, String author) {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		try {
			cs = con.prepareCall("{call updateAuthor(?, ?)}");
			cs.setDouble(1, iD);
			cs.setString(2, author);
			Boolean output = cs.execute();

			return output;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


}
