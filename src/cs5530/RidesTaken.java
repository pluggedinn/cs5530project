package cs5530;

import java.util.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.Statement;

public class RidesTaken {

	public ArrayList<String> findRide(String usr, int time, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.Shifts \r\n" + 
				"natural join 5530db64.UDriver natural join \r\n" + 
				"(5530db64.UCar natural join 5530db64.Registered)\r\n" + 
				"WHERE startShift <= "+time+" AND endShift >= "+time+" \r\n" + 
				"ORDER BY rating DESC";
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			rs = stmt.executeQuery(sql);
			if (!rs.isBeforeFirst() ) {    
				return null;
			} 
			rs.next();
			result.add(rs.getString("username"));
			result.add(rs.getString("rating"));
			result.add(rs.getString("vin"));
			return result;
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
	
	public void recordRide(String usr, int time, String people, String distance, String cost, String vin, Statement stmt) {
		String sql = "INSERT INTO 5530db64.RidesTaken (username, vin, timeTaken, cost, people, distance)\r\n" + 
				"VALUES ('"+usr+"', "+vin+", "+time+", "+cost+", "+people+", "+distance+")";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql);
			System.out.println("Ride requested\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("Car can't be reserved at that time\n");
			} else {
				System.out.println(e);
			}
		}
	}
}
