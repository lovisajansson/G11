<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="org.ics.ejb.GymMember"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
<script src="../Scripts/Javascript.js"></script>
<link rel="stylesheet" type="text/css" href="../Styles/mystyle.css">

<head>
<meta charset="ISO-8859-1">

</head>
<body>
<%@ include file="../Styles/Header.html" %>

	<div class="navbar">
		<a href="/Views/Home.jsp">Home</a> <a href="/Views/About.jsp">About</a><a href="/Views/Gym.jsp">Book</a> <a
		 href="/Views/Crud.jsp">Rest</a><a class="active" href="/Views/Test.jsp">Test</a>

	</div>
	<%@ include file="../Styles/Footer.html" %>
	
</body>
</html>