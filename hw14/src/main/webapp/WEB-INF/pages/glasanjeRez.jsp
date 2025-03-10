<%@page import="hr.fer.zemris.java.p12.model.PollOptionsModel"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.Date,java.util.Calendar, java.util.List"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->


<html>		
<head>
 <style type="text/css">
 table.rez td {text-align: center;}
 </style>
</head>
	
 <body>

 <h1>Rezultati glasanja</h1>
 <p>Ovo su rezultati glasanja.</p>
 <table border="1" cellspacing="0" class="rez">
 <thead><tr><th>Kandidat</th><th>Broj glasova</th></tr></thead>
 <tbody>
  <c:forEach var="e" items="${options}">	 
		 <tr><td>${e.optionTitle}</td><td>${e.votesCount}</td></tr>
</c:forEach>
 </tbody>
 </table>

 <h2>Grafički prikaz rezultata</h2>
 <img alt="Pie-chart" src="glasanje-grafika?pollID=${poll.id}" width="400" height="400" />

 <h2>Rezultati u XLS formatu</h2>
 <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${poll.id}">ovdje</a></p>

 <h2>Razno</h2>
 <p>Primjeri internetskih(pjesme, stranice i sl.) stranica pobjedničkih kandidata:</p>
 
  	<c:forEach var="e" items="${winners}">
   	<ul>	 
		 <li><a href="${e.optionLi}" target="_blank"></td><td>${e.optionTitle}</a></li>
	</ul>
	</c:forEach>

 </body>
</html>