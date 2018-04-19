package cs5530;

import java.sql.*;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class User {

	public void displayAllUsers (String usr, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.UUser\r\n" + 
				"WHERE username != '"+usr+"'";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("username")+"\n"; 
			}
			System.out.println("username");
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
	
	public void registerUser(String usr, String name, String psw, String addr, String phone, int admin, Statement stmt) {
//		String sql = "select * from course where cname like '%"+cname+"%' and dname like '%"+dname+"%'";
		String sql = "INSERT INTO 5530db64.UUser VALUES ('"+usr+"', '"+name+"', '"+psw+"', '"+addr+"', '"+phone+"', "+admin+");";
		int rs = 0;
		System.out.println("...executing " + sql);
		try {
			rs = stmt.executeUpdate(sql);
			System.out.println("User successfully registered!\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("User Already Registered\n");
			} else {
				System.out.println(e);
			}
		}
	}
	
	public boolean login(String usr, String psw, Statement stmt) {
		String sql = "SELECT count(1) FROM 5530db64.UUser WHERE 5530db64.UUser.username = '"+usr+"' AND 5530db64.UUser.password = '"+psw+"';";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			int result = Integer.parseInt(rs.getString("count(1)"));
			if (result == 1) {
				return true;
			}
			return false;
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
	
	public boolean isAdmin(String usr, Statement stmt) {
		String sql = "SELECT isAdmin FROM 5530db64.UUser\r\n" + 
				"WHERE username = '"+usr+"'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			int result = Integer.parseInt(rs.getString("isAdmin"));
			if (result == 1) {
				return true;
			}
			return false;
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
	
	public boolean areFirstDegree(String user1, String user2, Statement stmt) {
		String sql = "select count(*) as cc\r\n" + 
				"from\r\n" + 
				"(SELECT f1.username u1, f2.username u2\r\n" + 
				"FROM 5530db64.Favorites f1 join 5530db64.Favorites f2\r\n" + 
				"on f1.vin = f2.vin\r\n" + 
				"where f1.username != f2.username) as A\r\n" + 
				"where u1 = '"+user1+"' and u2 = '"+user2+"'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			int result = Integer.parseInt(rs.getString("cc"));
			if (result > 0) {
				return true;
			}
			return false;
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
	
	public boolean areSecondDegree(String user1, String user2, Statement stmt) {
		String sql = "select count(*) as cc\r\n" + 
				"from\r\n" + 
				"(SELECT f1.username u1, f2.username u2\r\n" + 
				"FROM 5530db64.Favorites f1 join 5530db64.Favorites f2\r\n" + 
				"on f1.vin = f2.vin\r\n" + 
				"where f1.username != f2.username) as A1\r\n" + 
				"join\r\n" + 
				"(SELECT f1.username u1, f2.username u2\r\n" + 
				"FROM 5530db64.Favorites f1 join 5530db64.Favorites f2\r\n" + 
				"on f1.vin = f2.vin\r\n" + 
				"where f1.username != f2.username) as A2\r\n" + 
				"on A2.u1 = A1.u2\r\n" + 
				"where A1.u1 = '"+user1+"' and A2.u2 = '"+user2+"'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			int result = Integer.parseInt(rs.getString("cc"));
			if (result > 0) {
				return true;
			}
			return false;
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
}
