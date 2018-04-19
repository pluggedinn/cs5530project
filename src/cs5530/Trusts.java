package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Trusts {
	
	public boolean getSelectedUser (String user, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.UUser\r\n" + 
				"WHERE username = '"+user+"'";
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
	
	public void setTrustedUser(String usr, String otherUsername, String trust, Statement stmt) {
		String sql = "INSERT INTO 5530db64.Trusts\r\n" + 
				"VALUES ('"+usr+"', '"+otherUsername+"', "+trust+")";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql);
			System.out.println("Gave trust level to user\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("You already gave trust to this user!\n");
			} else {
				System.out.println(e);
			}
		}
	}

	public void displayMostTrusted(String limit, Statement stmt) {
		String sql = "SELECT username2, sum(trustLevel) as sumTrust FROM 5530db64.UUser u, 5530db64.Trusts t\r\n" + 
				"WHERE u.username = t.username2\r\n" + 
				"GROUP BY username2\r\n" + 
				"ORDER BY sumTrust desc\r\n" + 
				"LIMIT "+limit+" ";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("username2")+"\n"; 
			}
			System.out.println("Most trusted users:");
			System.out.println(output);
			System.out.println("");
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

	public void displayMostUseful(String limit, Statement stmt) {
		String sql = "SELECT f.username, avg(u.score) as avg_score FROM 5530db64.Usefulness u, 5530db64.Feedbacks f\r\n" + 
				"WHERE u.fid = f.fid\r\n" + 
				"GROUP BY f.username\r\n" + 
				"ORDER by avg_score desc\r\n" + 
				"Limit "+limit+" ";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("username")+"\n"; 
			}
			System.out.println("Most useful users:");
			System.out.println(output);
			System.out.println("");
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
