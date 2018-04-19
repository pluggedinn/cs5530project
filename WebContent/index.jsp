<%@ page language="java" import="cs5530.*" %>
<!DOCTYPE HTML>
<html>
<head>
<title>UUber - login</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="custom.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<body>
<%
	DBConnector con = new DBConnector();
	User user = new User();
%> 

<div class="container">
	<div class="row">
		<div class="col-3"></div>
		<div class="col-6">
			<div class="login-title text-center">
				<h1>UUber</h1>
				<h6>Login</h1>
			</div>
		
			<form action="index.jsp">
				<div class="form-group">
					<label for="username">Username</label>
			        <input type="text" id="username" name="user" class="form-control" placeholder="Username" required="" autofocus="">
			    </div>
			    <div class="form-group">
			        <label for="password">Password</label>
			        <input type="password" id="password" name="psw" class="form-control" placeholder="Password" required="">
			    </div>
			    <button class="btn btn-md btn-primary btn-block" type="submit">Sign in</button>
			    <div style="margin-top: 15px;">
			    <%
					String username = request.getParameter("user");
					String psw = request.getParameter("psw");
					if (user.login(username, psw, con.stmt)) {
				%>
					<div class="alert alert-success" role="alert">
  						Successfully logged in as <%=username %>
					</div>
				<% } else {
					if (request.getParameterMap().containsKey("user")) {%>
					<div class="alert alert-danger" role="alert">
  						Wrong username and/or password
					</div>
				<%	}
				}
					con.stmt.close();
				%>
				</div>
			    <div class="form-group">
		        	<a href="/phase3/register.jsp">Create account</a>
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
