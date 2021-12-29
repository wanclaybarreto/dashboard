package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJdbc {
	
	private static ConnectionJdbc connectionJdbc;
	
	private ConnectionJdbc() {}
	
	public static ConnectionJdbc getInstance() {
		if (connectionJdbc == null) {
			connectionJdbc = new ConnectionJdbc();
		}
		
		return connectionJdbc;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/db_empresa?useTimezone=true&serverTimezone=UTC",
										   "dashboard",
										   "d123456");
	}
	
}
