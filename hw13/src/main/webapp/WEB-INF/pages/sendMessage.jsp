<%@ page import="java.util.Date,java.util.Calendar,java.util.Random, java.awt.Color" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<!DOCTYPE html>
<html>
	<%String color = (String) session.getAttribute("pickedBgCol");
		if (color == null) {%>
		<body bgcolor="white">
	<%} else {%>
		<body bgcolor=<%= color %>>
	<%}%>
	
<h3> <%=request.getAttribute("error") %></h3>
<hr>
   <body>
  	 <p><a href="index.jsp"> Home page </a></p>
   </body>
</html>