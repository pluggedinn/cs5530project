
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Car {
	public void addCar(String usr, int vin, String category, String carClass, String model, String make, int carYear, Statement stmt) {
		String sql1 = "INSERT INTO 5530db64.UCar\r\n" + 
				"VALUES ("+vin+", '"+category+"', '"+carClass+"', '"+model+"', '"+make+"', "+carYear+");";
		String sql2 = "INSERT INTO 5530db64.Registered\r\n" + 
				"VALUES ("+vin+", '"+usr+"');";
		int rs = 0;
		try {
			rs = stmt.executeUpdate(sql1);
			rs = stmt.executeUpdate(sql2);
			System.out.println("Car added successfully\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("Car can't be added\n");
			} else {
				System.out.println(e);
			}
		}
	}
	
	public ArrayList<Integer> showOwnedCars(String usr, Statement stmt) {
		String sql1 = "SELECT * FROM 5530db64.UCar natural join 5530db64.Registered\r\n" + 
				"WHERE username = '"+usr+"';";

		ResultSet rs = null;
		String output = "";
		ArrayList<Integer> vins = new ArrayList<Integer>();
		try {
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				vins.add(Integer.parseInt(rs.getString("vin")));
				output += rs.getString("vin")+"  "+rs.getString("model")+"  "+rs.getString("make")+"  "+rs.getString("carYear")+"\n"; 
			}
			
			System.out.println("Your current cars:");
			System.out.println("vin  model  make  year");
			System.out.println(output);
			System.out.println("");
			return vins;
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
	
	public void displayMostPopular(String category, String limit, Statement stmt) {
		String sql1 = "SELECT vin, count(vin), make, model, carYear, category as count_vin \r\n" + 
				"FROM 5530db64.RidesTaken natural join 5530db64.UCar\r\n" + 
				"WHERE category = '"+category+"'\r\n" + 
				"group by vin order by count_vin desc\r\n" + 
				"LIMIT "+limit+" ";

		ResultSet rs = null;
		String output = "";
		try {
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				output += rs.getString("vin")+"  "+rs.getString("model")+"  "+rs.getString("make")+"  "+rs.getString("carYear")+"\n"; 
			}
			
			System.out.println("Most popular cars:");
			System.out.println("vin  model  make  year");
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
	
	public void displayMostExpensive(String category, String limit, Statement stmt) {
		String sql1 = "SELECT vin, avg(cost) as avg_cost, make, model, carYear\r\n" + 
				"FROM 5530db64.RidesTaken natural join 5530db64.UCar\r\n" + 
				"WHERE category = '"+category+"'\r\n" + 
				"GROUP BY vin order by avg_cost desc\r\n" + 
				"LIMIT "+limit+" ";

		ResultSet rs = null;
		String output = "";
		try {
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				output += rs.getString("vin")+"  "+rs.getString("model")+"  "+rs.getString("make")+"  "+rs.getString("carYear")+"\n"; 
			}
			
			System.out.println("Most expensive cars:");
			System.out.println("vin  model  make  year");
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
	
	public void updateCar(ArrayList<Integer> vins, String usr, int vin, String category, String carClass, String model, String make, int carYear,  Statement stmt) {
		String sql2 = "UPDATE 5530db64.UCar\r\n" + 
				"SET 5530db64.UCar.vin = "+vin+", 5530db64.UCar.category = '"+category+"', 5530db64.UCar.class = '"+carClass+"', 5530db64.UCar.model = '"+model+"', 5530db64.UCar.make = '"+make+"', 5530db64.UCar.carYear = "+carYear+"\r\n" + 
				"WHERE 5530db64.UCar.vin = 100;";
		try {
			if (!vins.contains(vin)) {
				System.out.println("You don't own this car!\n");
				return;
			}
			
			stmt.executeUpdate(sql2);
			System.out.println("Car updated successfully\n");
		}
		catch(Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				System.out.println("Car can't be updated\n");
			} else {
				System.out.println(e);
			}
		}
	}
	
	public void browseCars(String usr, List<String> options, String category, String address, String model, String order, Statement stmt) {
		String sql = null;
		ResultSet rs = null;
		String output = "";
		
		if (order.equals("1")) {
			sql = "SELECT * FROM (SELECT A.vin, category, class, model, make, carYear, address, avg(score) as avg_score FROM (SELECT c.vin, category, class, model, make, carYear, address FROM 5530db64.UCar c, 5530db64.Registered r, 5530db64.UUser u\r\n" + 
					"WHERE c.vin = r.vin AND r.username = u.username) as A LEFT JOIN 5530db64.Feedbacks f\r\n" + 
					"ON f.vin = A.vin\r\n" + 
					"\r\n" + 
					"GROUP BY A.vin, address\r\n" + 
					"ORDER BY avg_score DESC) as P\r\n";
		} else {
			sql = "SELECT * FROM (SELECT A.vin, category, class, model, make, carYear, address, avg(score) as avg_score FROM (SELECT c.vin, category, class, model, make, carYear, address FROM 5530db64.UCar c, 5530db64.Registered r, 5530db64.UUser u\r\n" + 
					"WHERE c.vin = r.vin AND r.username = u.username) as A LEFT JOIN (SELECT f.username, vin, score FROM 5530db64.Feedbacks f, 5530db64.Trusts t\r\n" + 
					"WHERE f.username = t.username2 AND username1 = '"+usr+"' AND trustLevel = 1 ) as B \r\n" + 
					"ON B.vin = A.vin\r\n" + 
					"\r\n" + 
					"GROUP BY A.vin, address\r\n" + 
					"ORDER BY avg_score DESC) as P\r\n";
		}
		
 		if (options.contains("0") && !options.contains("1") && !options.contains("2")) {
 			sql += "WHERE category = '"+category+"'";
 		} else if (!options.contains("0") && !options.contains("1") && options.contains("2")) {
 			sql += "WHERE model LIKE '%"+model+"%'";
 		} else if (!options.contains("0") && options.contains("1") && !options.contains("2")) {
 			sql += "WHERE address LIKE '%"+address+"%'";
 		} else if (options.contains("0") && options.contains("1") && !options.contains("2")) {
 			sql += "WHERE address LIKE '%"+address+"%' AND category = '"+category+"'";
 		} else if (options.contains("0") && !options.contains("1") && options.contains("2")) {
 			sql += "WHERE model LIKE '%"+model+"%' AND category = '"+category+"'";
 		} else if (!options.contains("0") && options.contains("1") && options.contains("2")) {
 			sql += "WHERE model LIKE '%"+model+"%' AND address LIKE '%"+address+"%'";
 		} else if (options.contains("0") && options.contains("1") && options.contains("2")) {
 			sql += "WHERE model LIKE '%"+model+"%' AND address LIKE '%"+address+"%' AND category = '"+category+"'";
 		} else {
// 			System.out.println(sql);
 			System.out.println("Unrecognized command");
 			return;
 		}
 		sql += "\r\nORDER BY avg_score DESC";
 		
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				output += rs.getString("vin")+"  "+rs.getString("category")+"  "+rs.getString("model")+"  "+rs.getString("make")+"  "+rs.getString("carYear")+"\n"; 
			}
			
			System.out.println("Search result:");
			System.out.println("vin  category  model  make  year");
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

	public void displayMostHighRated(String category, String limit, Statement stmt) {
		String sql1 = "SELECT d.username, avg(score) as avg_score\r\n" + 
				"FROM 5530db64.UDriver d, 5530db64.Feedbacks f, 5530db64.Registered r, 5530db64.UCar c\r\n" + 
				"WHERE r.vin = f.vin AND r.username = d.username AND r.vin = c.vin AND category = '"+category+"'\r\n" + 
				"GROUP BY d.username\r\n" + 
				"ORDER BY avg_score desc\r\n" + 
				"LIMIT "+limit+" ";

		ResultSet rs = null;
		String output = "";
		try {
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				output += rs.getString("d.username")+ "  "+ rs.getString("avg_score") +"\n"; 
			}
			
			System.out.println("Most highly rated drivers:");
			System.out.println("username  avg_score");
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
