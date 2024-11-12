package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import com.mysql.cj.jdbc.*;
public class database {
	public static Connection connectDB() {
		try {
			
			String url="jdbc:mysql://127.0.0.1:3306/coffeestore";
			String username="root";
			String password="Workhardtobedev247";
			Connection connection= DriverManager.getConnection(url, username, password);
			return connection;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; //if connection fail
	}
	
}
