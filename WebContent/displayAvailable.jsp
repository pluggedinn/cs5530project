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
		response.sendRedirect("index.jsp");
		return;
	}
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
			<h3>Available cars</h3>
			<p>vin  model  make  year  rating</p>
			<%
				DBConnector con = new DBConnector();
				int parsedTime = 0;
				if (request.getParameter("reservetime") != null) {
					String time = request.getParameter("reservetime");
					if (time.matches("[0-2][0-3]|[0-9]")) {
						parsedTime = Integer.parseInt(time);
						if (parsedTime >= 0 && parsedTime <= 23) {
							RidesReserved r = new RidesReserved();
							String result = r.displayCarsAtTime(parsedTime, con.stmt);
							Scanner s = new Scanner(result);
							while(s.hasNextLine()) {
								String a = s.nextLine();
							%>
								<p><%=a %></p>
							<%
							}
						}
					}
				}
				
				
			%>
				<form name="reserve" method="get" action="displayAvailable.jsp">
					<div class="form-group">
						<label>Vin</label>
						<input type="hidden" name="reservetime">
						<input name="vin" type="text" class="form-control" placeholder="Example: 10">
						<small class="form-text text-muted">Please specify a vin number to reserve</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" onclick="showConfirmation()" role="button">Reserve</button>
					</div>
				</form>
				<%
					if (request.getParameter("vin") != null) {
						String vin = request.getParameter("vin");
						RidesReserved r = new RidesReserved();
						if (r.reserveCar(session.getAttribute("username").toString(), parsedTime, Integer.parseInt(vin), con.stmt)) {
				%>
					<div class="alert alert-success" role="alert">
					  Ride successfully reserved! :)
					</div>
				<%
						} else {
				%>
					<div class="alert alert-danger" role="alert">
					  There was a problem reserving the ride.
					</div>
				<%
							
						}
					}
					con.stmt.close();
					con.closeConnection();
				%>
			</div>
		</div>
	</div>

<script>
	function getParameterByName(name, url) {
	    if (!url) url = window.location.href;
	    name = name.replace(/[\[\]]/g, "\\$&");
	    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, " "));
	}
	function logout() { document.navbar.data.value = "logout"; navbar.submit(); }
	function showConfirmation() {
		var r = confirm("Are you sure you want to reserve this car?");
		if (r == true) {
			reserve.reservetime.value = getParameterByName("reservetime");
			reserve.submit();
			console.log("submitted");
		} else {
			reserve.reset();
			console.log("ciao");
		}
	}
</script>

<!-- <a href="orders.sql">orders.sql</a><br> -->
<!-- <a href="orders.jsp">orders.jsp</a><br> -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>
