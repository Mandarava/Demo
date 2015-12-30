package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	public static Connection getConnection() throws Exception {
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/menu";

		String userName = "root";

		String password = "root";

		conn = DriverManager.getConnection(url, userName, password);
		
		return conn;
	}
	
}
