
<%@ page import="java.util.Date,java.util.Calendar"
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
	<h3>Choose your background color.</h3>
<body>
	<p>
		<a href="setcolor?color=white"> WHITE </a>
	</p>
	<p>
		<a href="setcolor?color=red"> RED </a>
	</p>
	<p>
		<a href="setcolor?color=green"> GREEN </a>
	</p>
	<p>
		<a href="setcolor?color=cyan"> CYAN </a>
	</p>
		<p>
		<a href="index.jsp"> Home page </a>
	</p>
</body>
</html>