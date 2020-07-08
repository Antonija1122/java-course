
<%@ page import="java.util.Date,java.util.Calendar, java.util.concurrent.TimeUnit"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<html>
	<%
		String color = (String) session.getAttribute("pickedBgCol");
		if (color == null) {%>
		<body bgcolor="white">
	<%} else {%>
		<body bgcolor=<%= color %>>
	<%}
	%>
	<h3>Time elapsed</h3>
<body>
	<% Long time=System.currentTimeMillis()-(long) getServletContext().getAttribute("timeStart");
	long days = TimeUnit.MILLISECONDS.toDays(time);
	long hours = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(time));
	long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
	long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
	long ms = TimeUnit.MILLISECONDS.toMillis(time) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(time));
	%>This webapp is currently running for : <%=days %> days 
		<%=hours %> hours <%=minutes %> minutes <%=seconds %> seconds and <%=ms %> milliseconds
		
	<p>
		<b><a href="index.jsp">Go back to home page</a></b>
	</p>
</body>
</html>