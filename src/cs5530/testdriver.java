package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class testdriver {

	static String loggedUser = "";
	
	public static void displayBeginMenu()
	{
		 System.out.println("*** Welcome to UUber System ***");
    	 System.out.println("1. Register a new user");
    	 System.out.println("2. Log in");
    	 System.out.println("3. Exit");
    	 System.out.println("Please enter your choice:");
	}
	
	public static void displayUserMenu(boolean admin)
	{
		 System.out.println("*** Welcome to UUber System ***");
		 System.out.println("* Logged In as "+ loggedUser +" *");
    	 System.out.println("1. Reserve a Car");
    	 System.out.println("2. Register/Update a new car");
    	 System.out.println("3. Request a ride");
    	 System.out.println("4. Set a favorite car");
    	 System.out.println("5. Give feedback to car");
    	 System.out.println("6. Rate feeback");
    	 System.out.println("7. Give trust to user");
    	 System.out.println("8. Browse cars by category, model or address");
    	 System.out.println("9. Get most useful feedbacks of a car");
    	 System.out.println("10. Show degree of separation between users");
    	 System.out.println("11. Display most popular car");
    	 System.out.println("12. Display most expensive car");
    	 System.out.println("13. Display highly rated car");
    	 System.out.println("14. Log out");
    	 System.out.println("15. Exit");
    	 if (admin) {
    		 System.out.println("[16.] Most trusted users");
    		 System.out.println("[17.] Most useful users");
    	 }
    	 System.out.println("Please enter your choice:");
	}
	
	public static void main(String[] args) {
		DBConnector con = null;
		String choice;
		String data;
		boolean admin = false;
		int c;
		
		try {
			con = new DBConnector();
            System.out.println ("Database connection established\n");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            while(true) {
            	if (loggedUser == "") {
	            	displayBeginMenu();
	            	while ((choice = in.readLine()) == null && choice.length() == 0);
	           	 	try {
	           	 		c = Integer.parseInt(choice);
	           	 	}
	           	 	catch (Exception e) { continue; }
	           	 	
	           	 	if (c == 1) {
	           	 		System.out.println("- Please enter a username:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String username = data;
	           	 		
		           	 	System.out.println("- Please enter your full name:");
		       	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String fullName = data;
	           	 		
		           	 	System.out.println("- Please enter your password:");
		       	 		while ((data = in.readLine()) == null && data.length() == 0);
		       	 		String password = data;
		       	 		
			       	 	System.out.println("- Please enter your address:");
		       	 		while ((data = in.readLine()) == null && data.length() == 0);
		       	 		String address = data;
		       	 		
			       	 	System.out.println("- Please enter your phone number:");
		       	 		while ((data = in.readLine()) == null && data.length() == 0);
		       	 		String phone = data;
				       	
				       	User user = new User();
				       	user.registerUser(username, fullName, password, address, phone, 0, con.stmt);
	           	 		
	           	 	} else if (c == 2) {
	           	 		System.out.println("- Please enter a username:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String username = data;
	           	 		
		           	 	System.out.println("- Please enter your password:");
		       	 		while ((data = in.readLine()) == null && data.length() == 0);
		       	 		String password = data;
		       	 		
				       	User user = new User();
				       	if (user.login(username, password, con.stmt)) {
				       		loggedUser = username;
				       		admin = user.isAdmin(loggedUser, con.stmt);
				       		System.out.println("Successfully Logged In\n");
				       	} else {
				       		System.out.println("Incorrect username and password\n");
				       	}
	           	 		
	           	 	} else {
	           	 		con.stmt.close();
	           	 		break;
	           	 	}
            	} else {
            		displayUserMenu(admin);
	            	while ((choice = in.readLine()) == null && choice.length() == 0);
	           	 	try {
	           	 		c = Integer.parseInt(choice);
	           	 	}
	           	 	catch (Exception e) { continue; }
	           	 	
	           	 	if (c == 1) {
	           	 		System.out.println("- Please specify a preferred time of the day in hours:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String time = data;
	           	 		
	           	 		RidesReserved r = new RidesReserved();
	           	 		r.displayCarsAtTime(Integer.parseInt(time), con.stmt);
	           	 		
	           	 		System.out.println("- Please type the vin of the car to reserve:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String vin = data;
	           	 		
	           	 		System.out.println("- Are you sure you want to reserve this car at "+time+" (yes or no):");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String response = data;
	           	 		
	           	 		if (response.toLowerCase().equals("yes")) {
	           	 			r.reserveCar(loggedUser, Integer.parseInt(time), Integer.parseInt(vin), con.stmt);
	           	 		}
	           	 		
	           	 		r.displaySuggestedCars(loggedUser, vin, con.stmt);
	           	 		
	           	 	} else if (c == 2) {
	           	 		System.out.println("- Do you want to add or update a car (type add or update):");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String response = data;
	           	 		Car car = new Car();
	           	 		
	           	 		if (response.toLowerCase().equals("add")) {
		           	 		System.out.println("- Type your car's vin, category, class, model, make, year separated by a comma:");
		           	 		System.out.println("for example '100,Comfort,Sedan,Legacy,Subaru,2008'");
		           	 		while ((data = in.readLine()) == null && data.length() == 0);
		           	 		String res = data;
		           	 		String[] elements = res.split(",");
		           	 		car.addCar(loggedUser, Integer.parseInt(elements[0]), elements[1], elements[2], elements[3], elements[4], Integer.parseInt(elements[5]), con.stmt);
	           	 			
	           	 		} else if (response.toLowerCase().equals("update")) {
	           	 			String cars = car.showOwnedCars(loggedUser, con.stmt);
	           	 			
		           	 		System.out.println("- Type your car's vin, category, class, model, make, year separated by a comma:");
		           	 		System.out.println("for example '100,Comfort,Sedan,Legacy,Subaru,2008'");
			           	 	while ((data = in.readLine()) == null && data.length() == 0);
		           	 		String res = data;
		           	 		String[] elements = res.split(",");
		           	 		
		           	 		car.updateCar(loggedUser, Integer.parseInt(elements[0]), elements[1], elements[2], elements[3], elements[4], Integer.parseInt(elements[5]), con.stmt);
		           	 		
	           	 		} else {
	           	 			System.out.println("Command can't be recognized\n");
	           	 		}
	           	 	} else if (c == 3) {
	           	 		Calendar rightNow = Calendar.getInstance();
	           	 		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
//	           	 		hour = 6;
	           	 		
	           	 		RidesTaken r = new RidesTaken();
	           	 		
	           	 		System.out.println("- Insert how many people, the distance of the ride and the cost you are willing to pay separated by a comma:");
	           	 		System.out.println("for example '1,4,15'");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String[] elements = data.split(",");
	           	 		
	           	 		ArrayList<String> rideData = r.findRide(loggedUser, hour, con.stmt);
	           	 		if (rideData == null) {
	           	 			System.out.println("Could not find a ride with those specifications\n");
	           	 			continue;
	           	 		}
	           	 		System.out.println("- "+rideData.get(0)+", with rating "+rideData.get(1)+" can pick you up. Do you accept it? (yes or no)");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String res1 = data;
	           	 		
	           	 		if (res1.toLowerCase().equals("yes")) {
	           	 			r.recordRide(loggedUser, hour, elements[0], elements[1], elements[2], rideData.get(2), con.stmt);
	           	 		}
	           	 	} else if (c == 4) {
	           	 		Favorites fav = new Favorites();
	           	 		fav.displayAllCars(con.stmt);
	           	 		
		           	 	System.out.println("- Type the vin of the car you want to favorite");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String vin = data;
	           	 		
	           	 		if (!fav.getSelectedCar(vin, con.stmt)) {
	           	 			System.out.println("That car doesn't exists!\n");
	           	 			continue;
	           	 		}
	           	 		
	           	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	           	        Date date = new Date();
	           	 		fav.setFavoriteCar(loggedUser, vin, dateFormat.format(date), con.stmt);
	           	 	} else if (c == 5) {
	           	 		Favorites fav = new Favorites();
	           	 		Feedbacks feed = new Feedbacks();
	           	 		fav.displayAllCars(con.stmt);
	           	 		
		           	 	System.out.println("- Type the vin of the car you want to give a feedback:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String vin = data;
	           	 		
	           	 		if (!fav.getSelectedCar(vin, con.stmt)) {
	           	 			System.out.println("That car doesn't exists!\n");
	           	 			continue;
	           	 		}
	           	 		
		           	 	System.out.println("- Type the score (from 1 to 10) and the comment to give to this car separated by a comma:");
		           	 	System.out.println("for example '6, The driver was very nice'");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String res = data;
	           	 		String[] elements = res.split(",");
	           	 		
	           	 		if (Integer.parseInt(elements[0]) < 0 || Integer.parseInt(elements[0]) > 10) {
	           	 			System.out.println("Rating must be between 0 and 10\n");
	           	 			continue;
	           	 		}
	           	 		
	           	 		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	           	 		Date date = new Date();
	           	 		feed.setFeedback(loggedUser, vin, elements[0], elements[1], dateFormat.format(date), con.stmt);
	           	 	} else if (c == 6) {
	           	 		Feedbacks feed = new Feedbacks();
	           	 		Usefulness use = new Usefulness();
	           	 		feed.displayAllFeedbacks(loggedUser, con.stmt);
	           	 		
		           	 	System.out.println("- Type the fid of the feedback you want to rate:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String fid = data;
	           	 		
	           	 		if (!feed.getSelectedFeedback(fid, con.stmt)) {
	           	 			System.out.println("That feedback doesn't exists!\n");
	           	 			continue;
	           	 		}
	           	 		
	           	 		System.out.println("- Type how useful the feedback where 0, 1 or 2 (useless, useful or very useful):");
		           	 	while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String rating = data;
	           	 		
	           	 		if (!rating.equals("0") && !rating.equals("1") && !rating.equals("2")) {
	           	 			System.out.println("Rating can only be 0, 1 or 2\n");
	           	 			continue;
	           	 		}
	           	 		
	           	 		use.setUsefullness(loggedUser, fid, rating, con.stmt);
	           	 	} else if (c == 7) {
	           	 		User u = new User();
	           	 		Trusts trusts = new Trusts();
	           	 		
	           	 		u.displayAllUsers(loggedUser, con.stmt);
	           	 		
		           	 	System.out.println("- Type the other username to trust:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String username = data;
	           	 		
	           	 		if (!trusts.getSelectedUser(username, con.stmt)) {
	           	 			System.out.println("That username doesn't exists!\n");
	           	 			continue;
	           	 		}
	           	 		
		           	 	System.out.println("- Type the trust level to the user (-1 for not trusted, 1 for trusted):");
		           	 	while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String rating = data;
	           	 		
	           	 		if (!rating.equals("-1") && !rating.equals("1")) {
	           	 			System.out.println("Rating can only be -1 or 1\n");
	           	 			continue;
	           	 		}
	           	 		
	           	 		trusts.setTrustedUser(loggedUser, username, rating, con.stmt);
	           	 	} else if (c == 8) {
	           	 		Car car = new Car();
	           	 		String category = null;
	           	 		String address = null;
	           	 		String model = null;
	           	 		String order = null;
	           	 		
		           	 	System.out.println("- Type what filter you want to apply to the search where 0 is category, 1 is address and 2 is model:");
		           	 	System.out.println("for example '0,1' searches for cars filtered by category and address");
		           	 	System.out.println("for example '2' searches for cars filtered only by model");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String res = data;
	           	 		List<String> elements = Arrays.asList(res.split(","));
	           	 		
	           	 		System.out.println("- Type 1 to order by feedback scores, type 2 to order by feedback scores of trusted users:");
	           	 		while ((data = in.readLine()) == null && data.length() == 0);
           	 			order = data;
           	 			
	           	 		if (!order.equals("1") && !order.equals("2")) {
	           	 			System.out.println("Order can only be 1 or 2\n");
	           	 			continue;
	           	 		} 
	           	 		
	           	 		if (elements.contains("0")) {
			           	 	System.out.println("- Type the category you are looking for:");
			           	 	while ((data = in.readLine()) == null && data.length() == 0);
		           	 		category = data;
	           	 		}
	           	 		if (elements.contains("1")) {
			           	 	System.out.println("- Type the address you are looking for:");
			           	 	while ((data = in.readLine()) == null && data.length() == 0);
		           	 		address = data;
	           	 		}
	           	 		if (elements.contains("2")) {
			           	 	System.out.println("- Type the model you are looking for:");
			           	 	while ((data = in.readLine()) == null && data.length() == 0);
		           	 		model = data;
	           	 		}
	           	 		
	           	 		if (!elements.contains("0") && !elements.contains("1") && !elements.contains("2")) {
	           	 			System.out.println("Unrecognized command");
	           	 			continue;
	           	 		}
	           	 		
	           	 		car.browseCars(loggedUser, elements, category, address, model, order, con.stmt);
	           	 	} else if (c == 9) {
	           	 		Driver d = new Driver();
	           	 		Usefulness u = new Usefulness();
	           	 		d.displayAllDrivers(loggedUser, con.stmt);
	           	 		
		           	 	System.out.println("- Type the driver name and how many feedbacks you want to see separated by a comma:");
		           	 	System.out.println("for example 'Samir,2'");
		           	 	while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String res = data;
	           	 		String[] elements = res.split(",");
	           	 		
	           	 		u.getMostUsefulFeedbacks(elements[0], elements[1], con.stmt);
	           	 	} else if (c == 10) {
	           	 		User u = new User();
	           	 		
		           	 	System.out.println("- Type the username of the two users to check if they is a 1st degree separation separated by a comma:");
		           	 	System.out.println("for example 'Samir,Ricc'");
		           	 	while ((data = in.readLine()) == null && data.length() == 0);
	           	 		String res = data;
	           	 		String[] elements = res.split(",");
	           	 		
	           	 		if (u.areFirstDegree(elements[0], elements[1], con.stmt)) {
	           	 			System.out.println("They are 1 degree away\n");
	           	 			continue;
	           	 		} else {
	           	 			if (u.areSecondDegree(elements[0], elements[1], con.stmt)) {
	           	 				System.out.println("They are 2 degrees away\n");
	           	 			} else {
	           	 				System.out.println("They are not related\n");
	           	 			}
	           	 			continue;
	           	 		}
	           	 	} else if (c == 11) {
	           	 		Car car = new Car();
	           	 		
		           	 	System.out.println("- Type the category of the car and how many cars to show separated by a comma:");
		           	 	System.out.println("for example 'Luxury,5'");
			           	while ((data = in.readLine()) == null && data.length() == 0);
		        	 	String res = data;
		        	 	String[] elements = res.split(",");
		        	 	
		        	 	car.displayMostPopular(elements[0], elements[1], con.stmt);
	           	 	} else if (c == 12) {
	           	 		Car car = new Car();
	           	 		
		           	 	System.out.println("- Type the category of the car and how many cars to show separated by a comma:");
		           	 	System.out.println("for example 'Luxury,5'");
			           	while ((data = in.readLine()) == null && data.length() == 0);
		        	 	String res = data;
		        	 	String[] elements = res.split(",");
		        	 	
		        	 	car.displayMostExpensive(elements[0], elements[1], con.stmt);
	           	 	} else if (c == 13) {
	           	 		Car car = new Car();
	           	 		
		           	 	System.out.println("- Type the category of the car and how many cars to show separated by a comma:");
		           	 	System.out.println("for example 'Luxury,5'");
			           	while ((data = in.readLine()) == null && data.length() == 0);
		        	 	String res = data;
		        	 	String[] elements = res.split(",");
		        	 	
		        	 	car.displayMostHighRated(elements[0], elements[1], con.stmt);
		        	 	
	           	 	} else if (c == 14) {
	           	 		// logout
	           	 		loggedUser = "";
	           	 		admin = false;
	           	 		System.out.println("Logging out...\n");
	           	 		continue;
	           	 	} else if (c == 15) {
	           	 		// exit
	           	 		System.out.println("Exiting...\n");
	           	 		con.stmt.close();
	           	 		break;
	           	 	} else if (c == 16) {
	           	 		if (!admin) {
	           	 			System.out.println("Unrecognized number");
	           	 			continue;
	           	 		}
	           	 		Trusts t = new Trusts();
		           	 	System.out.println("- Type how many trusted users to display:");
		           	 	
			           	while ((data = in.readLine()) == null && data.length() == 0);
		        	 	String res = data;
	           	 		t.displayMostTrusted(res, con.stmt);
	           	 	} else if (c == 17) {
	           	 		if (!admin) {
	           	 			System.out.println("Unrecognized number");
	           	 			continue;
	           	 		}
	           	 		Trusts t = new Trusts();
	           	 		System.out.println("- Type how many useful users to display:");
		           	 	
			           	while ((data = in.readLine()) == null && data.length() == 0);
		        	 	String res = data;
	           	 		t.displayMostUseful(res, con.stmt);
	           	 	}
	           	 	else {
           	 			System.out.println("Unrecognized number");
           	 			continue;
	           	 	}
            	}
            }
		}
		
		catch (Exception e) {
			e.printStackTrace();
			System.err.println ("Either connection error or query execution error!");
        }
		
		finally {
			if (con != null) {
				try {
					con.closeConnection();
					System.out.println ("Database connection terminated");
				}
				catch (Exception e) { /* ignore close errors */ }
			}	 
        }
	}

}
