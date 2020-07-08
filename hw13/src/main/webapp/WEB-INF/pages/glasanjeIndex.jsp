<%@ page import="java.util.Date,java.util.Calendar, java.util.List"
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
 <body>
 <h1>Glasanje za omiljeni bend:</h1>
 <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
glasali!</p>
 <ol>
 
 <% List<String> bands =(List<String>) session.getAttribute("ids");
 	for(String id : bands){
 		%>	 <li><a href="glasanje-glasaj?id=<%=id%>"><%=session.getAttribute(id+"name")%></a></li>	<%	
 	}
 	%>
 </ol>
 </body>
</html>