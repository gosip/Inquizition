package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DBConnection;

public class QuizEntry extends Quiz {
	private String creator;
	private int times_taken;
	private int best_score;
	private String champion;
	private int champion_id;
	
	public QuizEntry(ResultSet rs) throws SQLException{
		super(rs);
		times_taken = rs.getInt("times_taken");
		setInfo();
		
	}
	
	public QuizEntry(int id){
		super(id);
		if(db == null) db = new DBConnection();
		Statement stat = (Statement) db.getStatement();
		ResultSet rs = null;
		try{
			rs = stat.executeQuery("SELECT * FROM quizzes WHERE id = " + id +";");
			rs.next();
			times_taken = rs.getInt("times_taken");
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		finally{
			try{ stat.close(); } catch(SQLException ignored) {}
			if(rs != null) try{ rs.close(); } catch(SQLException ignored) {}
		}
		setInfo();
	}
	
	private void setInfo(){
		if(db == null) db = new DBConnection();
		Statement stat = (Statement) db.getStatement();
		ResultSet rs = null;
		try{
			//getting creator's name
			rs = stat.executeQuery("SELECT * FROM users WHERE id = " + creator_id +";");
			rs.next();
			creator = rs.getString("name");
			//getting the best score & the champion
			rs = stat.executeQuery("Select * FROM history WHERE id = (SELECT entry_id "
					+ "from best_score WHERE quiz_id = " + id +");");
			if(rs.isBeforeFirst()){
				rs.next();
				best_score = rs.getInt("score");
				champion_id = rs.getInt("user_id");
				rs = stat.executeQuery("Select * FROM users WHERE id = " + champion_id + ";");
				rs.next();
				champion = rs.getString("name");
				//champion_id = rs.getInt("user_id");
			}else{
				best_score = 0;
				champion = "-";
			}
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		finally{
			try{ stat.close(); } catch(SQLException ignored) {}
			if(rs != null) try{ rs.close(); } catch(SQLException ignored) {}
		}
	}
	
	public String getCreator(){
		return creator;
	}
	
	public int getBestScore(){
		return best_score;
	}
	
	public String getChampion(){
		return champion;
	}
	
	public int getChampionID(){
		return champion_id;
	}
	
	public int getTimesTaken(){
		return times_taken;
	}
}
