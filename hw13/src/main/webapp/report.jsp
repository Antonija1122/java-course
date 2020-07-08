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
	
<h3>OS usage</h3>
<hr>

   <body>
  	 <p>Here are the results of OS usage in survey that we completed.</p>
	 <img src="reportImage">
	 
   </body>
</html>