<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="org.ics.ejb.GymMember"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="../Styles/mystyle.css">

<head>
<meta charset="ISO-8859-1">

</head>
<body>
<%@ include file="../Styles/Header.html" %>

	<div class="navbar">
		<a href="${pageContext.request.contextPath}/Views/Home.jsp">Home</a> <a href="${pageContext.request.contextPath}/Views/About.jsp">About</a><a href="/Views/Gym.jsp">Book</a> <a
		 href="${pageContext.request.contextPath}/Views/Crud.jsp">Rest</a><a class="active" href="${pageContext.request.contextPath}/Views/Test.jsp">Test</a>

	</div>
<div class="row">
<hr><legend>Härkanduväljaenellerfleraavföljandetest:<br></legend>
<form action="${pageContext.request.contextPath}/TestServlet"method="get"name="youPickItForm">
<select name="suite"size="2"multiple>
<option value="ics.junit.ejb.FacadeTest">ics.junit.ejb.FacadeTest</option>
</select>
<input type="submit"value="Run"/>
</form>
</div>
	<%@ include file="../Styles/Footer.html" %>

</body>
</html>