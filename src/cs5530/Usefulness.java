package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Usefulness {
	
	public void setUsefullness(String usr, String fid, String rating, Statement stmt) {
		String sql = "INSERT INTO 5530db64.Usefulness\r\n" + 
				"VALUES ("+fid+", '"+usr+"', "+rating+")";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql);
			System.out.println("Feedback rated\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("You already rated this feedback!\n");
			} else {
				System.out.println(e);
			}
		}
	}
	
	public void getMostUsefulFeedbacks(String driver, String limit, Statement stmt) {
		String sql = "select fid, username, avg(score) as average, userComment as comment from\r\n" + 
				"(select F.fid, B.username, U.score, F.score as rating, userComment\r\n" + 
				"from 5530db64.Usefulness U join 5530db64.Feedbacks F\r\n" + 
				"on U.fid = F.fid join 5530db64.Registered B on F.vin = B.vin) as final\r\n" + 
				"WHERE username = '"+driver+"'\r\n" + 
				"group by fid, username, userComment\r\n" + 
				"order by average DESC\r\n" + 
				"limit "+limit+"";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("fid")+"  "+rs.getString("username")+"  "+rs.getString("average")+"  "+rs.getString("comment")+"\n"; 
			}
			System.out.println("fid  username  average  comment");
			System.out.println(output);
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
	}

}
