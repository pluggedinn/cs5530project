package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Favorites {

	public String displayAllCars (Statement stmt) {
		String sql = "SELECT * FROM 5530db64.UCar";
		String output = "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("vin")+"  "+rs.getString("model")+"  "+rs.getString("make")+"  "+rs.getString("carYear")+"\n"; 
			}
			System.out.println("vin  model  make  year");
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
	
	public boolean getSelectedCar (String vin, Statement stmt) {
		String sql = "SELECT * FROM 5530db64.UCar\r\n" + 
				"WHERE vin = "+vin+"";
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
	
	public void setFavoriteCar(String usr, String vin, String date, Statement stmt) {
		String sql = "INSERT INTO 5530db64.Favorites\r\n" + 
				"VALUES ("+vin+", '"+usr+"', '"+date+"')";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql);
			System.out.println("Favorited car\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("Car can't be favorited\n");
			} else {
				System.out.println(e);
			}
		}
	}
	
}
