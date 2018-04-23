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
	if (session == null || session.getAttribute("username") == null) {
		System.out.println("redirect");
		response.sendRedirect("index.jsp");
		return;
	}
	
	DBConnector con = new DBConnector();
	User u = new User();
	Trusts t = new Trusts();
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
				<h4>Give feedback to a car</h4>
				<%
					String output = u.displayAllUsers(session.getAttribute("username").toString(), con.stmt);
				%>
				<p>username</p>
				<%
					Scanner s = new Scanner(output);
					while(s.hasNextLine()) {
						String a = s.nextLine();
				%>
					<p><%=a %></p>
				<%
					}
				%>
				<form method="get" action="trusts.jsp">
					<div class="form-group">
						<label>Name</label>
						<input name="trustname" type="text" class="form-control" placeholder="Example: ricc">
						<small class="form-text text-muted">Please specify who you want to trust</small>
					</div>
					<div class="form-group">
						<label>Rating</label>
						    <select class="form-control" name="trustrating">
						      <option>-1</option>
						      <option>1</option>
						    </select>
						<small class="form-text text-muted">Please specify -1 for not-trusted and 1 for trusted</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit" role="button">Give feedback</button>
					</div>
				</form>
				<hr>
				<%
					if (request.getParameter("trustname") != null) {
 						if (!t.getSelectedUser(request.getParameter("trustname"), con.stmt)) {
				%>
					<div class="alert alert-danger" role="alert">
  						This user doesn't exists
					</div>
				<%
						} else {
							t.setTrustedUser(session.getAttribute("username").toString(), request.getParameter("trustname"), request.getParameter("trustrating"), con.stmt);
				%>
					<div class="alert alert-success" role="alert">
  						Successfully trusted the user!
					</div>
				<%
						}
					}
				%>
			</div>
		</div>
	</div>
				<%
					con.stmt.close();
					con.closeConnection();
				%>

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
