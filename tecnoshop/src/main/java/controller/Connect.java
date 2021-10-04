package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	
	private String db="tecnoshop";
	private String url="jdbc:mysql://localhost:3306/"+db;
	private String user="root";
	private String pass="";
	Connection con=null;
	
	public Connection Conecta() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,user,pass);
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(null, "Error al conectar DB: "+e);
		}
		return con;
	}
	
}

