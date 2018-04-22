<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
	Favorites f = new Favorites();
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
				<h4>Add a favorite car</h4>
				<%
					String output = f.displayAllCars(con.stmt);
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
				<form name="favoriteadd" method="get" action="favorite.jsp">
					<input type="hidden" name="confirm" value="0">
					<div class="form-group">
						<label>Vin</label>
						<input name="favoritevin" type="text" class="form-control" placeholder="Example: 489">
						<small class="form-text text-muted">Please specify the vin of the vehicle to favorite</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit" role="button">Favorite car</button>
					</div>
				</form>
				<%
					if (request.getParameter("favoritevin") != null) {
 						if (!f.getSelectedCar(request.getParameter("favoritevin"), con.stmt)) {
				%>
					<div class="alert alert-danger" role="alert">
  						This car doesn't exist
					</div>
				<%
						} else {
							DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
							Date date = new Date();
							f.setFavoriteCar(session.getAttribute("username").toString(), request.getParameter("favoritevin"), d.format(date), con.stmt);
				%>
					<div class="alert alert-success" role="alert">
  						Car successfully favorited!
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
