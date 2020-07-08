<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.Date,java.util.Calendar, java.util.List"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->
<%!
	private List<String> sort(List<String> bands, HttpSession session){
	int n = bands.size();
	String temp;
	for(int i=0; i < n; i++){
		for(int j=1; j < (n-i); j++){
			int num1=Integer.parseInt((String)session.getAttribute(bands.get(j-1)+"votes"));
			int num2=Integer.parseInt((String)session.getAttribute(bands.get(j)+"votes"));
			if(num1 < num2){
				//swap the elements!
				temp = bands.get(j-1);
				bands.set(j-1, bands.get(j));
				bands.set(j, temp);
			}
		}
	}
	return bands;
	}
%>

<html>
<% String color = (String) session.getAttribute("pickedBgCol");
	if (color == null) {%>
	<body bgcolor="white"><%
		} else {%>
	<body bgcolor=<%=color%>><%
		}%>
		
<head>
 <style type="text/css">
 table.rez td {text-align: center;}
 </style>
</head>
	
 <body>

 <h1>Rezultati glasanja</h1>
 <p>Ovo su rezultati glasanja.</p>
 <table border="1" cellspacing="0" class="rez">
 <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
 <tbody>
  <% List<String> bands =(List<String>) session.getAttribute("ids");
  	bands=sort(bands, session);
  	 int max=0;
 	for(String id : bands){
 		%>	  <tr><td><%=session.getAttribute(id+"name")%></td><td><%=session.getAttribute(id+"votes")%></td></tr>	<%	
 		if(Integer.parseInt((String)session.getAttribute(id+"votes"))>max){
 			max=Integer.parseInt((String)session.getAttribute(id+"votes"));
 		}
 	}%>
 </tbody>
 </table>

 <h2>Grafički prikaz rezultata</h2>
 <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

 <h2>Rezultati u XLS formatu</h2>
 <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

 <h2>Razno</h2>
 <p>Primjeri pjesama pobjedničkih bendova:</p>
 
 <ul>
 <%	for(String id : bands){
	 if(Integer.parseInt((String)session.getAttribute(id+"votes"))==max){%>
		 <li><a href=<%=session.getAttribute(id+"urlSong")%> target="_blank"><%=session.getAttribute(id+"name")%></a></li>
	 <%}
 	}%>
 </ul>
 
 </body>
</html>