<%@ page import="java.util.Date,java.util.Calendar,java.util.Random, java.awt.Color" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->
<%! 
//ovdje metode
private String getColor() throws java.io.IOException {
    String[] colors={"red", "blue", "purple", "green", "yellow", "pink", "brown", "black", "grey"};
    Random r=new Random();
    int i=r.nextInt(colors.length);
	return colors[i];
}
	
%>


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
<h3>Hello world! Here is a funny story.</h3>
<hr>

   <body><font color=<%=getColor() %>>
  	 <p>This story is a funny story that is not to long.</p>
	 <p>Actually it's not that funny but look at the colors :)</p>
 	 <p>Teacher : Now, Sam, tell me frankly do you say prayers before eating?</p>
 	 <p>Sam : No sir, I don't have to, my mom is a good cook.</p></font>
 	 
 	 <p>
	<b><a href="../index.jsp">Go back to
			home page</a></b>
	</p>

   </body>
</html>