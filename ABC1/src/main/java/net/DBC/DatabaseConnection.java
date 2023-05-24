package net.DBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
public static Connection initializeDatabase() throws SQLException, ClassNotFoundException{
		
		String dbDriver = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/db";
		
		//String dbName = "db";
		String dbUsername = "root";
		String dbPasssword ="root";
		
		Class.forName(dbDriver);
		Connection con = DriverManager.getConnection(dbURL, dbUsername, dbPasssword);
		
		return con;
}

}
