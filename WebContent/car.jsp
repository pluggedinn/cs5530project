<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.Scanner" %>
<!DOCTYPE HTML>
<html>
<head>
<title>UUber - Home</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="custom.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<%

	if (request.getParameter("data") != null) {
	 	if (request.getParameter("data").equals("logout")) {
	 		System.out.println("logout");
			session.setAttribute("username", null);
		}
	}
	if (session.getAttribute("username") == null) {
		System.out.println("redirect");
		response.sendRedirect("/phase3/index.jsp");
		return;
	}
	
	DBConnector con = new DBConnector();
	Car c = new Car();
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand" href="#">UUber - <i><%=session.getAttribute("username") %></i></a>
	<form name="navbar" method="post">
		<input type="hidden" name="data">
		<div class="navbar-nav">
			<a class="nav-item nav-link" href="rides.jsp">Rides</a>
			<a class="nav-item nav-link" href="car.jsp">Register/Update Car</a>
			<a class="nav-item nav-link" href="favorite.jsp">Favorites</a>
			<a class="nav-item nav-link" href="feedback.jsp">Feedback</a>
			<a class="nav-item nav-link" href="trusts.jsp">Trusts</a>
			<a class="nav-item nav-link" href="search.jsp">Search</a>
			<a class="nav-item nav-link" href="becomeDriver.jsp"><b>BECOME A DRIVER</b></a>
			<a class="nav-item nav-link logout" onclick="logout()" href="#">Logout</a>
		</div>
	</form>
</nav>
	<div class=container>
		<div class="row">
			<div class="col-12">
				<h4>Add a new car</h4>
				<form name="caradd" method="get" action="car.jsp">
					<input type="hidden" name="confirm" value="0">
					<div class="form-group">
						<label>Vin</label>
						<input name="addcarvin" type="text" class="form-control" placeholder="Example: 489">
						<small class="form-text text-muted">Please specify the vin of the vehicle</small>
					</div>
					<div class="form-group">
						<label>Category</label>
						<input name="addcarcategory" type="text" class="form-control" placeholder="Example: Comfort">
						<small class="form-text text-muted">Please specify the category of the vechile</small>
					</div>
					<div class="form-group">
						<label>Class</label>
						<input name="addcarclass" type="text" class="form-control" placeholder="Example: Sedan">
						<small class="form-text text-muted">Please specify the class of the vechile</small>
					</div>
					<div class="form-group">
						<label>Model</label>
						<input name="addcarmodel" type="text" class="form-control" placeholder="Example: Legacy">
						<small class="form-text text-muted">Please specify the model of the vechile</small>
					</div>
					<div class="form-group">
						<label>Make</label>
						<input name="addcarmake" type="text" class="form-control" placeholder="Example: Subaru">
						<small class="form-text text-muted">Please specify the make of the vechile</small>
					</div>
					<div class="form-group">
						<label>Make</label>
						<input name="addcaryear" type="text" class="form-control" placeholder="Example: 2008">
						<small class="form-text text-muted">Please specify the year of the vechile</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit" role="button">Add a new car</button>
					</div>
				</form>
				<%
					if (request.getParameter("addcarvin") != null) {
 						if (c.addCar(session.getAttribute("username").toString(),
								Integer.parseInt(request.getParameter("addcarvin")),
								request.getParameter("addcarcategory"),
								request.getParameter("addcarclass"), 
								request.getParameter("addcarmodel"),
								request.getParameter("addcarmake"),
								Integer.parseInt(request.getParameter("addcaryear")), con.stmt)) {
				%>
					<div class="alert alert-success" role="alert">
  						Car successfully registered
					</div>
				<%
						} else {
				%>
					<div class="alert alert-danger" role="alert">
  						There was a problem registering the car
					</div>
				<%
						}
					}
				%>
				
				<hr>
				<h4>Update an existing car</h4>
				<%
					String output = c.showOwnedCars(session.getAttribute("username").toString(), con.stmt);
				%>
				<p>vin  model  make  year</p>
				<%
					Scanner s = new Scanner(output);
					while(s.hasNextLine()) {
						String a = s.nextLine();
				%>
					<p><%=a %></p>
				<%
					}
				%>
				<form name="carupdate" method="get" action="car.jsp">
					<input type="hidden" name="confirm" value="0">
					<div class="form-group">
						<label>Vin</label>
						<input name="carvin" type="text" class="form-control" placeholder="Example: 489">
						<small class="form-text text-muted">Please specify the vin of the vehicle</small>
					</div>
					<div class="form-group">
						<label>Category</label>
						<input name="carcategory" type="text" class="form-control" placeholder="Example: Comfort">
						<small class="form-text text-muted">Please specify the category of the vechile</small>
					</div>
					<div class="form-group">
						<label>Class</label>
						<input name="carclass" type="text" class="form-control" placeholder="Example: Sedan">
						<small class="form-text text-muted">Please specify the class of the vechile</small>
					</div>
					<div class="form-group">
						<label>Model</label>
						<input name="carmodel" type="text" class="form-control" placeholder="Example: Legacy">
						<small class="form-text text-muted">Please specify the model of the vechile</small>
					</div>
					<div class="form-group">
						<label>Make</label>
						<input name="carmake" type="text" class="form-control" placeholder="Example: Subaru">
						<small class="form-text text-muted">Please specify the make of the vechile</small>
					</div>
					<div class="form-group">
						<label>Make</label>
						<input name="caryear" type="text" class="form-control" placeholder="Example: 2008">
						<small class="form-text text-muted">Please specify the year of the vechile</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit" role="button">Update the car</button>
					</div>
				</form>
				<%
					if (request.getParameter("carvin") != null) {
 						if (c.updateCar(session.getAttribute("username").toString(),
								Integer.parseInt(request.getParameter("carvin")),
								request.getParameter("carcategory"),
								request.getParameter("carclass"), 
								request.getParameter("carmodel"),
								request.getParameter("carmake"),
								Integer.parseInt(request.getParameter("caryear")), con.stmt)) {
				%>
					<div class="alert alert-success" role="alert">
  						Car successfully updated
					</div>
				<%
						} else {
				%>
					<div class="alert alert-danger" role="alert">
  						There was a problem updating the car
					</div>
				<%
						}
					}
				
					con.stmt.close();
				%>
			</div>
		</div>
	</div>

<script>
	function logout() { document.navbar.data.value = "logout"; navbar.submit(); }
</script>

<!-- <a href="orders.sql">orders.sql</a><br> -->
<!-- <a href="orders.jsp">orders.jsp</a><br> -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>
