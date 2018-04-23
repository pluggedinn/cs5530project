<%@ page language="java" import="cs5530.*" %>
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
	
	DBConnector con = new DBConnector();
	Driver d = new Driver();
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
				
				
				<%
					if (d.isRegistered(session.getAttribute("username").toString(), con.stmt)) {
				%>
					<h4>You are already a driver!</h4>
				<%
					} else {
				%>
				
				<h4>Become a driver</h4>
				<form method="get" action="becomeDriver.jsp">
					<div class="form-group">
						<label>Start of your shift</label>
						<input name="shiftstart" type="number" class="form-control" min="0" max="23">
						<small class="form-text text-muted">Please specify the begin of your shift </small>
					</div>
					<div class="form-group">
						<label>End of your shift</label>
						<input name="shiftend" type="number" class="form-control" min="0" max="23">
						<small class="form-text text-muted">Please specify the end of your shift</small>
					</div>
					<div class="form-group">
						<button class="btn btn-warning" type="submit" role="button">BECOME A DRIVER</button>
					</div>
				</form>
				<%
					}
				%>
				<%
					if (request.getParameter("shiftstart") != null) {
						if (!d.registerDriver(session.getAttribute("username").toString(), request.getParameter("shiftstart"), request.getParameter("shiftend"), con.stmt)) {
						%>
							<div class="alert alert-danger" role="alert">
		  						You are already a driver.
							</div>
						<%
							
						} else {
							
						%>
							<div class="alert alert-success" role="alert">
  								You are now a driver!
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
	function logout() { document.navbar.data.value = "logout"; navbar.submit(); }
</script>

<!-- <a href="orders.sql">orders.sql</a><br> -->
<!-- <a href="orders.jsp">orders.jsp</a><br> -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>
