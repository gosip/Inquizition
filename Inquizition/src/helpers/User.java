package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import db.DBConnection;

public class User {
	private int id;
	private String name;
	private boolean admin;
	private static DBConnection db;
	
	public static void setDB(DBConnection connection){
		db = connection;
	}
	
	
	//determines whether a user exists
	public static int exists(String name){
		if(db == null) 
			db = new DBConnection();
		int id = 0;
		try{
		Statement stat = (Statement) db.getStatement();
		ResultSet rs =  stat.executeQuery("SELECT * FROM users WHERE name = \"" + name + "\";");
		if(!rs.isBeforeFirst())
			return id;
		rs.next();
		id = rs.getInt("id");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return id;
	}
	
	// retrieving user info from database
	public User(String username) {
		this.name = username;
		Statement stat = (Statement) db.getStatement();
		selectFromDB(stat);
		if(stat != null) try{ stat.close(); } catch(Exception e) { };
	}
	
	// creating new user and adding to database
	public User(String username, String password) {
		this.name = username;
		username = "\""+ name + "\"";
		password = "\""+ password + "\"";
		Statement stat = (Statement) db.getStatement();
		
		try {
			// adding user to database
			stat.executeUpdate("INSERT INTO users(name, password) VALUES(" + username + ", " + password + ")");
			// retrieving information
			selectFromDB(stat);
		} catch (SQLException e) { }
		
		finally{
			if(stat != null) try{ stat.close(); } catch(Exception e) { };
		}
	}
	
	// creating new user having full information
	// no connection needed
	public User(int id, String username, boolean admin) {
		this.name = username;
		this.id = id;
		this.admin = admin;
	}
	
	private void selectFromDB(Statement stat) {
		String username = "\""+ name + "\"";
		ResultSet rs = null;
		
		try {
			rs = stat.executeQuery("SELECT * from users where name = " + username);
			if(rs.next()){
				this.id = rs.getInt("id");
				this.admin = rs.getBoolean("admin");
			}
		} catch (NumberFormatException | SQLException e) { e.printStackTrace(); }
	}
	
	public String getUsername() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isAdmin() {
		return admin;
	}
		
}
