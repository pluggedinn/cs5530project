<%@ page language="java" import="cs5530.*" %>
<!DOCTYPE HTML>
<html>
<head>
<title>UUber - register</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="custom.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<body>
<div class="container">
	<div class="row">
		<div class="col-3"></div>
		<div class="col-6">
			<div class="login-title text-center">
				<h1>UUber</h1>
				<h6>Register</h1>
			</div>
		
			<form action="register.jsp">
				<div class="form-group">
					<label for="username">Username</label>
			        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required="" autofocus="">
			    </div>
			    <div class="form-group">
			        <label for="fullName">Full Name</label>
			        <input type="text" id="fullName" name="fullName" class="form-control" placeholder="John Doe" required="">
			    </div>
			    <div class="form-group">
			        <label for="password">Password</label>
			        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="">
			    </div>
			    <div class="form-group">
			        <label for="address">Full address</label>
			        <input type="text" id="address" name="address" class="form-control" placeholder="123 McKevin UT Salt Lake City 84102" required="">
			    </div>
			    <div class="form-group">
			        <label for="phone">Phone number</label>
			        <input type="tel" id="phone" name="phone" class="form-control" placeholder="1234445555" required="">
			    </div>
			    <div class="form-group">
			    	<button class="btn btn-sm btn-primary btn-block" type="submit">Create account</button>
			    </div>
			    <div style="margin-top: 15px;">
			<%
				DBConnector con = new DBConnector();
				User user = new User();
				if (request.getParameterMap().containsKey("username")) {
					String username = request.getParameter("username");
					String fullName = request.getParameter("fullName");
					String password = request.getParameter("password");
					String address = request.getParameter("address");
					String phone = request.getParameter("phone");
					if (!user.registerUser(username, fullName, password, address, phone, 0, con.stmt)) {
			%>
					<div class="alert alert-danger" role="alert">
  						There was a problem registering the user
					</div>
			<%
					}
				}
				con.stmt.close();
				con.closeConnection();
			%>
			    </div>
			</form>
		</div>
		<div class="col-3"></div>
	</div>
</div>

<!-- <a href="orders.sql">orders.sql</a><br> -->
<!-- <a href="orders.jsp">orders.jsp</a><br> -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>
