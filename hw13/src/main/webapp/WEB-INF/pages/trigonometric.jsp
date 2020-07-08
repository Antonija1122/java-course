<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->
<!DOCTYPE html>
<html>
	<%
		String color = (String) session.getAttribute("pickedBgCol");
		if (color == null) {%>
		<body bgcolor="white">
	<%} else {%>
		<body bgcolor=<%= color %>>
	<%}
	%>
	<head>
		<style> 
			TD {border: 1px solid black;}
			TABLE {border: 1px solid black;}
		</style>
	</head>
   <body>
   	<p>
		<b><a href="index.jsp">Go back to home page</a></b>
	</p>
   	<table style="TABLE">
   	 <tr>
  <th>X</th>
  <th>sin(X)</th>
  <th>cos(X)</th>
 </tr>
   	<% for(int i=(int)request.getAttribute("varA"); i<=(int)request.getAttribute("varB"); i++ ){
   		%><tr><td><%=i%></td><td><%out.print(request.getAttribute("sin"+i)); %></td><td><%out.print(request.getAttribute("cos"+i)); %></td></tr><%
   	}%>
   	</table>  
   </body>
</html>