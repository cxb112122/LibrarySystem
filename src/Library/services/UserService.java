package Library.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	public int checkmanager(String username){
		if(username.substring(0,1).equals("5")){
			//System.out.println(username);
			//System.out.println(chec);
			return 1;
		}
		return 0;
	}
	public boolean login(String username, String password) {
		byte[] Usalt =null;
		String UP=null;
		
		Connection con=this.dbService .getConnection();
		PreparedStatement state = null;
		String query="Select PasswordSalt,PasswordHash From [User] Where UserID=? ";
		try{
			state=con.prepareStatement(query);
			System.out.println(state);
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			while(rs.next()){
				Usalt=rs.getBytes("PasswordSalt");
				UP=rs.getString("PasswordHash");
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		String Checkpass = hashPassword(Usalt, password);
		String che=hashPassword(Usalt, UP);
		System.out.println(Checkpass.length());
		
		//System.out.println(che);
		System.out.println(UP.length());
		
		
		if(Checkpass  .equals(UP.substring(0, Checkpass.length()))){
			return true;
		}
		else{
			JOptionPane.showMessageDialog(null, "Fail to login");
		
		}
		return false;
	}
		
	public boolean register(int ID, String username, String userType, String password, String name) {
		//TODO: Task 6
		CallableStatement cs = null;
		Connection con=this.dbService .getConnection();
		byte[] salt =getNewSalt();
		String re=this.hashPassword(salt, password);
		int re1=0;
		if(username.substring(0,1).equals("5")){
			JOptionPane.showMessageDialog(null, "Username cannot start with 5");
			return false;
		}
		if(userType=="None"){
			JOptionPane.showMessageDialog(null, "Select UserType.");
			return false;
		}
		try {
			cs = con.prepareCall("{?=call Register(?,?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, userType);
			cs.setBytes(4,salt);
			cs.setString(5, re);
			cs.setString(6, name);
			cs.setInt(7, 0);
			cs.executeUpdate();
			re1 = cs.getInt(1);
			System.out.println(re1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		switch(userType){
		
		case "Student":
			try {
				cs = con.prepareCall("{?=call addStudent(?,?)}");
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setDouble(2, ID);
				cs.setString(3, username);
				cs.executeUpdate();
				re1 = cs.getInt(1);
				System.out.println(re1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "Professor":
			try {
				cs = con.prepareCall("{?=call addProfessor(?,?)}");
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setDouble(2, ID);
				cs.setString(3, username);
				cs.executeUpdate();
				re1 = cs.getInt(1);
				System.out.println(re1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
	}
		
		if (re1== 0) {
			return true;
		}
		if (re1 == 1) {
			JOptionPane.showMessageDialog(null, "Username cannot be empty.");
		}
		if (re1 == 2) {
			JOptionPane.showMessageDialog(null, "Select UserType.");
		}
			if (re1 == 3) {
			JOptionPane.showMessageDialog(null, "PasswordSalt cannot be empty.");
		} 
			if (re1 == 4) {
			JOptionPane.showMessageDialog(null, "PasswordHash cannot be empty.");
		} 
		 	if (re1== 5) {
			JOptionPane.showMessageDialog(null, "ERROR: Name cannot be empty.");
		} 
			if (re1== 6) {
			JOptionPane.showMessageDialog(null, "ERROR: Username already exists.");
		}	
		return false;

	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}

