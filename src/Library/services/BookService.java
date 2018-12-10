package Library.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import Library.ui.Book;



public class BookService {
	private String u;
	private String dat[];
	private DatabaseConnectionService dbService = null;

	public BookService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	public BookService(DatabaseConnectionService dbService,String u) {
		this.dbService = dbService;
		this.u=u;
		try {
			this.populate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean addBook(int ID, String title, String publisher, 
			Double libraryID, Double edition, Double ISBN, String author,
			String permissionType) throws SQLException {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		cs = con.prepareCall("{call addBook(?,?,?,?,?,?,?,?)}");
		cs.setDouble(1, ID);
		cs.setString(2, title);
		cs.setString(3, publisher);
		cs.setDouble(4, libraryID);
		cs.setDouble(5, edition);
		cs.setDouble(6, ISBN);
		cs.setString(7, author);
		cs.setString(8, permissionType);
		
		Boolean output = cs.execute();
		JOptionPane.showMessageDialog(null, "add successful.");
		return output;
	}

	public ArrayList<Book> getBook(int ID, String title, String author, Double ISBN) {
		PreparedStatement ps=null;
		Connection con = this.dbService.getConnection();
		String query = "SELECT Title, ID, Author, availability FROM Book";
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
			return new ArrayList<Book>();
		}

		
		}
	public void populate() throws IOException{
		File file = new File("2.txt");
		
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
			for(int i=1;i<dat.length;i=i+8){
				System.out.println(dat[i]);
				System.out.println(dat[i+1]);
				System.out.println(dat[i+2]);
				System.out.println(dat[i+3]);
				System.out.println(dat[i+4]);
				System.out.println(dat[i+5]);
				System.out.println(dat[i+6]);
				System.out.println(dat[i+7]);
					try {
						this.addBook(Integer.parseInt(dat[i]), dat[i+1], dat[i+2], (Double)Double.parseDouble(dat[i+3]),(Double)Double.parseDouble (dat[i+4]), (Double)Double.parseDouble(dat[i+5]),dat[i+6],dat[i+7]);
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
	public boolean returnBook(int ID, int idm) throws SQLException{
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		System.out.println(u+"!!!");
		cs = con.prepareCall("{?=call Return1(?,?,?)}");
		cs.registerOutParameter(1, Types.INTEGER);
		//System.out.println(arg0);
		cs.setInt(2, ID);
		cs.setString(3, u);
		cs.setInt(4, idm);
		Boolean output = cs.execute();
		int re1 = cs.getInt(1);
		if(re1==5)
			JOptionPane.showMessageDialog(null, "Return unsuccessful.");
		else
			JOptionPane.showMessageDialog(null, "Return successful.");
		return output;
	}
	
	public boolean BorrowBook( int BID, int idm) throws SQLException{
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		cs = con.prepareCall("{call Borrow1(?,?,?)}");
		cs.setString(1, u);
		cs.setInt(2, BID);
		
		

		
		cs.setInt(3, idm);
		Boolean output = cs.execute();
		JOptionPane.showMessageDialog(null, "Borrow successful.");
		return output;
	}
	
	public boolean deleteBook(int iD) throws SQLException {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		cs = con.prepareCall("{call deleteBook(?)}");
		cs.setInt(1, iD);
		Boolean output = cs.execute();
		JOptionPane.showMessageDialog(null, "Delete successful.");
		return output;
	}

//	public boolean returnBook(Double iD) {
//		Connection con = this.dbService.getConnection();
//		CallableStatement cs = null;
//		try {
//			cs = con.prepareCall("{call returnBook(?)}");
//			cs.setDouble(1, iD);
//			Boolean output = cs.execute();
//
//			return output;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//		
//	}
		
	
	/**
	 * Creates a string containing ? in the correct places in the SQL statement based on the filter information provided.
	 * 
	 * @param rest - The restaurant to match
	 * @param soda - The soda to match
	 * @param price - The price to compare to
	 * @param useGreaterThanEqual - If true, the prices returned should be greater than or equal to what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed 
	 */
	private String buildParameterizedSqlStatementString(String title, String author, Double ISBN) {
		String sqlStatement = "SELECT Title, Author, ID, availability, ArticleOrBook \nFROM AllBooksAndArticles\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();
		if (title != null) {
			wheresToAdd.add("title = ?");
		}
		if (author != null) {
			wheresToAdd.add("author = ?");
		}
		if (ISBN != null) {
			wheresToAdd.add("ISBN = ?");
		}
		if (ISBN != null) {
			wheresToAdd.add("ISBN = ?");
		}

		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}

	private ArrayList<Book> parseResults(ResultSet rs) {
		try {
			ArrayList<Book> books = new ArrayList<Book>();
			int TitleIndex = rs.findColumn("Title");
			int AuthorIndex = rs.findColumn("Author");
			int IDIndex = rs.findColumn("ID");
			int AvaIndex = rs.findColumn("availability");
			
			while (rs.next()) {
				books.add(new Book(rs.getString(TitleIndex), rs.getDouble(IDIndex),
						rs.getString(AuthorIndex), rs.getString(AvaIndex)));
			}
			return books;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving Articles. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Book>();
		}

	}
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

	public String getUserid(){
		return this.u;
	}
}
