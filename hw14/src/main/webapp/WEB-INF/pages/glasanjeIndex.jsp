<%@ page import="java.util.Date,java.util.Calendar, java.util.List"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<html>

 <body>
  <h1>${poll.title}</h1>
 <p>${poll.message}</p>
 <ol>
 <c:forEach var="e" items="${pollOptions}">
	 <li>
		<a href="glasanje-glasaj?id=${e.id}"> ${e.optionTitle} </a>
	</li>
</c:forEach>
 </ol>
 </body>
</html>