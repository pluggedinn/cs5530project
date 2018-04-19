package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class Driver {

	public void displayAllDrivers (String usr, Statement stmt) {
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
