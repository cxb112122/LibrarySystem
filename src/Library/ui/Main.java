package Library.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import Library.services.ArticleService;
import Library.services.BookService;
import Library.services.DatabaseConnectionService;
import Library.services.EncryptionService;
import Library.services.RoomService;
//import Library.services.SodaService;
import Library.services.UserService;

public class Main {
	private static String serverUsername = null;
	private static String serverPassword = null;
	private static DatabaseConnectionService dbService = null;
	private static LoginPrompt lp = null;
	private static WindowCloseListener wcl = null;
	private static EncryptionService es = new EncryptionService();
	private static int checkm;
	private static String userID;
	private static String[] dat;
	//private static JFrame libraryFrame;

	public static void main(String[] args) throws Exception{
		
		Properties props = loadProperties();
		serverUsername = props.getProperty("serverUsername");
		serverPassword = props.getProperty("serverPassword");
		dbService = new DatabaseConnectionService(props.getProperty("serverName"), props.getProperty("databaseName"));
		wcl = new WindowCloseListener(dbService);
		final UserService userService = new UserService(dbService);
		//===========================
//		File file = new File("1.txt");
//		  @SuppressWarnings("resource")
//		  BufferedReader br = new BufferedReader(new FileReader(file));
//		 
//		
//		  String text = "";
//			String line = br.readLine();
//			// Text text is now a long string with every single character contained
//			while(line != null){
//				text += line;
//				//System.out.println(line);
//				line = br.readLine();
//			}
//			// Data data is now an array combined by strings
//			dat = text.split("/");
			
		//==============================
		LoginComplete lc = new LoginComplete() {
			@Override
			public void login(String u, String p) {
				if (userService.login(u, p)) {
					//System.out.println(u+"pp");
					checkm=userService.checkmanager(u);
					//System.out.println(checkm+"d");
					userID=u;
					loginSucceeded();
					
					
					
				} else {
					JOptionPane.showMessageDialog(null, "Login unsuccessful.");
				}
			}

			@Override
			public void register(int ID, String username, String userType, String password, String name) {
				if (userService.register(ID, username, userType, password, name)) {
					loginSucceeded();
					
				} else {
					JOptionPane.showMessageDialog(null, "Registration unsuccessful.");
				}
			}

			
		};

		if (!dbService.connect(serverUsername, serverPassword)) {
			JOptionPane.showMessageDialog(null, "Connection to database could not be made.");
		} else {
			if (userService.useApplicationLogins()) {
				lp = new LoginPrompt(lc);
				lp.addWindowListener(wcl);	
			}
			else {
				loginSucceeded();
			}
		}

	}

	public static void loginSucceeded() {
		if (lp!=null) {
			lp.setVisible(false);
			lp.dispose();
		}
		
		ArticleService aService = new ArticleService(dbService,userID);
		BookService bService=new BookService(dbService,userID);
		
		//-------------------
//		DateFormat format = new SimpleDateFormat("yyyyMMdd");
//		
//		
//		//------------------
//		for(int i=1;i<dat.length;i=i+7){
////			System.out.println(dat[i]);
////			System.out.println(dat[i+1]);
////			System.out.println(dat[i+2]);
////			System.out.println(dat[i+3]);
////			System.out.println(dat[i+4]);
////			System.out.println(dat[i+5]);
////			System.out.println(dat[i+6]);
//			java.util.Date date =null;
//			java.sql.Date sqldate=null;
//			try {
//				date = format.parse(dat[i+6]);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			sqldate = new java.sql.Date(date.getTime());
//			try {
//				aService.addArticle(Integer.parseInt(dat[i]), dat[i+1], dat[i+2], Double.parseDouble(dat[i+3]), dat[i+4], dat[i+5], sqldate);
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		RoomService rService=new RoomService(dbService,userID);
		JFrame libraryFrame = new Frame(aService,bService,rService,checkm);
		
		libraryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		libraryFrame.addWindowListener(wcl);
		
			 
			   
	}

	public static Properties loadProperties() {
		String binDir = System.getProperty("user.dir") + "/bin/";
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(es.getEncryptionPassword());
		FileInputStream fis = null;
		EncryptableProperties props = new EncryptableProperties(encryptor);
		try {
			fis = new FileInputStream(binDir + "sodabaseapp.properties");
			props.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("template.properties file not found");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("template.properties file could not be opened");
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					System.out.println("Input Stream could not be closed.");
					e.printStackTrace();
				}
			}
		}
		return props;
	}

}
