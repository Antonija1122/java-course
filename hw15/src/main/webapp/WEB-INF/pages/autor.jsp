<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
	<head>
		<title>Author ${author.nick}</title>
	</head>
	<body>
	
	<c:choose>
    <c:when test="${sessionScope.currentid==null}">
      not logged in
    </c:when>
    <c:otherwise>
   <h3> Welcome! ${sessionScope.currentfn} ${sessionScope.currentln} <a href="../logout"> logout</a></h3>
    </c:otherwise>
    </c:choose>
	
	<h3>List of blogs for this author</h3>
	
	<c:choose>
    <c:when test="${blogs.isEmpty()}">
       <p><i>This author doesn't have any blogs yet.</i></p>
    </c:when>
    <c:otherwise>
  	<c:forEach var="e" items="${blogs}">
   	<ul>	 
		 <li><a href="${author.nick}/${e.id}">${e.title}</a></li>
	</ul>
	</c:forEach>
    </c:otherwise>
    </c:choose>
	

	<c:if test="${sessionScope.currentid==author.id}">
		<p><a href="${author.nick}/new"> Add new blog </a></p>
	</c:if>
	
	
	<p><a href="../main"> Go to home page </a></p>

	</body>
</html>
