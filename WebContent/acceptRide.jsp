<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.ArrayList" %>
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
			<h3>Confirm ride</h3>
			<%
				DBConnector con = new DBConnector();
				int hour = 0;
				ArrayList<String> data = new ArrayList<String>();
				if (request.getParameter("requestpeople") != null && 
					request.getParameter("requestdistance") != null &&
					request.getParameter("requestcost") != null &&
					request.getParameter("confirm").equals("0")) {
					Calendar now = Calendar.getInstance();
					hour = now.get(Calendar.HOUR_OF_DAY);
					
					String people = request.getParameter("requestpeople");
					String distance = request.getParameter("requestdistance");
					String cost = request.getParameter("requestcost");
					RidesTaken r = new RidesTaken();
					data = r.findRide(session.getAttribute("username").toString(), hour, con.stmt);
					if (data == null) {
					%>
						<div class="alert alert-danger" role="alert">
						  There was a problem reserving the ride.
						</div>
					<%
					} else {
					%>
						<p><b><%=data.get(0) %></b>, with rating <b><%=data.get(1) %></b> can pick you up.
						</p>
						<button type="button" class="btn btn-success" onclick="confirmRide()">Yes</button>
						<button type="button" class="btn btn-light">No</button>
					<%
					}
				} else if (request.getParameter("confirm").equals("1")) {
					RidesTaken r = new RidesTaken();
					if (r.recordRide(session.getAttribute("username").toString(), Integer.parseInt(request.getParameter("hour")), request.getParameter("requestpeople"), request.getParameter("requestdistance"), request.getParameter("requestcost"), request.getParameter("vin"), con.stmt)) {
					%>
						<div class="alert alert-success" role="alert">
							Ride taken!
						</div>
					<%
					} else {
					%>
						<div class="alert alert-danger" role="alert">
							There was a problem setting the ride up.
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
	function confirmRide() {
		<% if (!data.isEmpty()) {
			String vin = data.get(2);%>
		window.location.href = "acceptRide.jsp?requestpeople=<%=request.getParameter("requestpeople") %>&requestdistance=<%=request.getParameter("requestdistance") %>&requestcost=<%=request.getParameter("requestcost") %>&hour=<%=hour %>&vin=<%=vin %>&confirm=1";
		<% } else {} %>
	}
</script>

<!-- <a href="orders.sql">orders.sql</a><br> -->
<!-- <a href="orders.jsp">orders.jsp</a><br> -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>
