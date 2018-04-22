package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Driver {

	public boolean registerDriver(String usr, String shiftStart, String shiftEnd, Statement stmt) {
//		String sql = "select * from course where cname like '%"+cname+"%' and dname like '%"+dname+"%'";
		String sql = "INSERT INTO 5530db64.UDriver VALUES ('"+usr+"', 0);";
		String sql1 = "INSERT INTO 5530db64.Shifts (username, startShift, endShift)\r\n" + 
				"VALUES ('"+usr+"', "+shiftStart+", "+shiftEnd+")";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql);
			rs = stmt.executeUpdate(sql1);
			System.out.println("Driver successfully registered!\n");
			return true;
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("Driver Already Registered\n");
			} else {
				System.out.println(e);
			}
		}
		return false;
	}
	
	public String displayAllDrivers (String usr, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.UDriver\r\n" + 
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
	
	public boolean isRegistered(String usr, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.UDriver WHERE username = '"+usr+"';";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.getString("username") != null ) {
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
