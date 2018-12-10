
package Library.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	private String url = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
	private Connection connection = null;
	
	private String databaseName;
	private String serverName;
	

	public DatabaseConnectionService(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
		this.connection =null;
	}

	public boolean connect(String user, String pass) {
		//String n="jdbc:sqlserver://"+this.serverName +";"+"databaseName="+this.databaseName +";"+"user="+user+";"+"password="+pass;
		String newurl= url.replace("${dbServer}",this.serverName  )
		.replace("${dbName}", this.databaseName )
		.replace("${user}", user)
		.replace("${pass}", pass);
		
		try {  
			
            connection = DriverManager.getConnection(newurl);  

        }  
        catch (Exception e) {  
            e.printStackTrace();  
            return false;
        }  
		
		return true;
	}
	

	public Connection getConnection() {
		
		
		return this.connection;
	}
	
	public void closeConnection() {
		 if (connection != null) {
		        try {
		            connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
	}

	

}
