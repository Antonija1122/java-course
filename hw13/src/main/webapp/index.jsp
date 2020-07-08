<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<html>
<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null) {
%>

<body bgcolor="white">
	<%
		} else {
	%>

<body bgcolor=<%=color%>>
	<%
		}
	%>
<h3>Hello world! This is your home page.</h3>
<body>
	<p>
		<a href="color.jsp"> Background color chooser </a>
	</p>
	<p>
		<a href="trigonometric?a=0&b=90"> Trigonometric for a=0 & b=90 </a>
	</p>

	<form action="trigonometric" method="GET">
		Početni kut:<br> <input type="number" name="a" min="0" max="360"
			step="1" value="0"><br> Završni kut:<br> <input
			type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>
	
	<p>
		<a href="stories/funny.jsp"> Read a funny story </a>
	</p>
	<p>
		<a href="report.jsp"> Look at the report of OS usage. </a>
	</p>
	<p>
		<a href="powers?a=1&b=100&n=3"> Create powers table for params: a=1, b=100 & n=3. </a>
	</p>
	<p>
		<a href="appinfo.jsp"> How long is his app running? </a>
	</p>
	<p>
		<a href="glasanje"> Vote for your favorite band.</a>
	</p>
</body>
</html>