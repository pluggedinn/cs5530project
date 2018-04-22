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
	Feedbacks fb = new Feedbacks();
	Usefulness u = new Usefulness();
	Driver dr = new Driver();
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
				<form name="feedbackadd" method="get" action="feedback.jsp">
					<input type="hidden" name="confirm" value="0">
					<div class="form-group">
						<label>Vin</label>
						<input name="feedbackvin" type="text" class="form-control" placeholder="Example: 489">
						<small class="form-text text-muted">Please specify the vin of the vehicle to give feedback to</small>
					</div>
					<div class="form-group">
						<label>Score</label>
						<input name="feedbackscore" type="number" class="form-control" min="1" max="10">
						<small class="form-text text-muted">Please specify the score between 1 and 10</small>
					</div>
					<div class="form-group">
						<label>Comment</label>
						<textarea name="feedbackcomment" class="form-control"rows="3" placeholder="Place a comment here. Be nice."></textarea>
						<small class="form-text text-muted">Please write a comment</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit" role="button">Give feedback</button>
					</div>
				</form>
				<hr>
				<%
					if (request.getParameter("feedbackvin") != null) {
 						if (!f.getSelectedCar(request.getParameter("feedbackvin"), con.stmt)) {
				%>
					<div class="alert alert-danger" role="alert">
  						This car doesn't exist
					</div>
				<%
						} else {
							DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
							Date date = new Date();
							fb.setFeedback(session.getAttribute("username").toString(), request.getParameter("feedbackvin"), request.getParameter("feedbackscore"), request.getParameter("feedbackcomment"), d.format(date), con.stmt);
				%>
					<div class="alert alert-success" role="alert">
  						Successfully added feedback!
					</div>
				<%
						}
					}
				%>
				<h4>Rate a feedback</h4>
				<%
					output = fb.displayAllFeedbacks(session.getAttribute("username").toString(), con.stmt);
				%>
				<p>feedbackId  score  comment</p>
				<%
					s = new Scanner(output);
					while(s.hasNextLine()) {
						String a = s.nextLine();
				%>
					<p><%=a %></p>
				<%
					}
				%>
				<form name="feedbackrate" method="get" action="feedback.jsp">
					<input type="hidden" name="confirm" value="0">
					<div class="form-group">
						<label>Feedback id</label>
						<input name="rateid" type="text" class="form-control" placeholder="Example: 489">
						<small class="form-text text-muted">Please specify the feedback id</small>
					</div>
					<div class="form-group">
						<label>Score</label>
						<input name="raterating" type="number" class="form-control" min="0" max="2">
						<small class="form-text text-muted">Please specify the score between 0, 1, or 2</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit" role="button">Rate feedback</button>
					</div>
				</form>
				<hr>
				<%
					if (request.getParameter("rateid") != null) {
 						if (!fb.getSelectedFeedback(request.getParameter("rateid"), con.stmt)) {
				%>
					<div class="alert alert-danger" role="alert">
  						This feedback doesn't exist.
					</div>
				<%
						} else {
							u.setUsefullness(session.getAttribute("username").toString(), request.getParameter("rateid"), request.getParameter("raterating"), con.stmt);
				%>
					<div class="alert alert-success" role="alert">
  						Successfully rated feedback!
					</div>
				<%
						}
					}
				%>
				<h4>Display feedbacks</h4>
				<%
					output = dr.displayAllDrivers(session.getAttribute("username").toString(), con.stmt);
				%>
				<p>username</p>
				<%
					s = new Scanner(output);
					while(s.hasNextLine()) {
						String a = s.nextLine();
				%>
					<p><%=a %></p>
				<%
					}
				%>
				<form name="findfeedback" method="get" action="usefulFeedback.jsp">
					<div class="form-group">
						<label>Driver name</label>
						<input name="drivername" type="text" class="form-control" placeholder="Example: ricc">
						<small class="form-text text-muted">Please specify the driver name</small>
					</div>
					<div class="form-group">
						<label>How many feedbacks</label>
						<input name="drivercount" type="number" class="form-control" min="1" max="50">
						<small class="form-text text-muted">Please specify how many feedbacks to show</small>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit" role="button">Rate feedback</button>
					</div>
				</form>
				<%
					if (request.getParameter("drivername") != null) {
						u.getMostUsefulFeedbacks(request.getParameter("drivername"), request.getParameter("drivercount"), con.stmt);
					}
				%>
			</div>
		</div>
	</div>
				<%
					con.stmt.close();
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
