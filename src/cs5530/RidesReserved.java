package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class RidesReserved {
	
	public String displayCarsAtTime (int time, Statement stmt) {
		String sql = "SELECT vin, model, make, carYear, rating FROM (SELECT * FROM \r\n" + 
				"(5530db64.Shifts natural join 5530db64.UDriver) \r\n" + 
				"natural join (5530db64.UCar natural join 5530db64.Registered)) as a\r\n" + 
				"WHERE startShift <= "+time+" and endShift >= "+time+";";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("vin")+"  "+rs.getString("model")+"  "+rs.getString("make")+"  "+rs.getString("carYear")+"  "+rs.getString("rating")+"\n"; 
			}
			System.out.println("vin  model  make  year  rating");
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
	
	public boolean reserveCar(String usr, int time, int vin, Statement stmt) {
		String sql = "INSERT INTO 5530db64.RidesReserved (username, resTime, vin)\r\n" + 
				"VALUES ('"+usr+"', "+time+", "+vin+");";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql);
			System.out.println("Ride reserved\n");
			return true;
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("Car can't be reserved at that time\n");
			} else {
				System.out.println(e);
			}
		}
		return false;
	}

	public void displaySuggestedCars(String usr, String vin, Statement stmt) {
		String sql = "SELECT vin, count(vin) as count_vin FROM (SELECT * FROM \r\n" + 
				"(SELECT username FROM (5530db64.RidesTaken) WHERE vin = "+vin+" AND username != '"+usr+"') as A\r\n" + 
				"natural join 5530db64.RidesTaken WHERE vin != "+vin+") as T\r\n" + 
				"GROUP BY vin\r\n" + 
				"ORDER BY count_vin desc";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("vin")+"\n"; 
			}
			System.out.println("Suggested cars");
			System.out.println("vin");
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
