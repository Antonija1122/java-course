<%@page import="hr.fer.zemris.java.p12.model.PollModel"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.p12.dao.DAOProvider"%>
<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<html>

<h2>Hello world! This is your voting home page.</h2>
<body>
<h3>Choose a voting link.</h3>
<ol>
<c:forEach var="e" items="${pollList}">
	<p>
		<a href="glasanje?pollID=${e.id}"> ${e.title} </a>
	</p>
</c:forEach>
</ol>

</body>
</html>