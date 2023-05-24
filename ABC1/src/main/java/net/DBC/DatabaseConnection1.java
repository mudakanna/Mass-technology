package net.DBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection1 {
	
public static Connection initializeDatabase() throws SQLException, ClassNotFoundException{
		
		String dbDriver = "com.mysql.jdbc.Driver";
		String dbURL = "https://console.firebase.google.com/u/0/project/datadeduplication-61ea7/database/datadeduplication-61ea7-default-rtdb/data/~2F";
		
		//String dbName = "db";
		//String dbUsername = "root";
		//String dbPasssword ="root";
		
		Class.forName(dbDriver);
		Connection con = DriverManager.getConnection(dbURL);
		
		return con;
}

}