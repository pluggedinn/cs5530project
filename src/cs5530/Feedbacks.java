package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Feedbacks {

	public String displayAllFeedbacks (String usr, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.Feedbacks\r\n" + 
				"WHERE username != '"+usr+"'";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("fid")+"  "+rs.getString("score")+"  "+rs.getString("userComment")+"\n"; 
			}
			System.out.println("fid  score  comment");
			System.out.println(output);
			return output;
		}
		catch(Exception e) {
			System.out.println(e);
		}
	 	finally
	 	{
	 		try {
		 		if (rs!=null && !rs.isClosed())
		 			rs.close();
		 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return null;
	}
	
	public boolean getSelectedFeedback (String fid, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.Feedbacks\r\n" + 
				"WHERE fid = "+fid+"";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (!rs.isBeforeFirst() ) {    
				return false;
			} 
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
		}
	 	finally
	 	{
	 		try {
		 		if (rs!=null && !rs.isClosed())
		 			rs.close();
		 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return false;
	}
	
	public void setFeedback(String usr, String vin, String score, String comment, String date, Statement stmt) {
		String sql = "INSERT INTO 5530db64.Feedbacks (username, vin, score, userComment, fDate)\r\n" + 
				"VALUES ('"+usr+"', "+vin+", "+score+", '"+comment+"', '"+date+"')";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql);
			System.out.println("Feedback given\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("You already gave a feedback to this car\n");
			} else {
				System.out.println(e);
			}
		}
	}
}
