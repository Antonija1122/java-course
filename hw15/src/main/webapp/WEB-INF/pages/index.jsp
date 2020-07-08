<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
	<head>
		<title>Main Blog page</title>
		
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
	</head>

	<body>
	
	<c:choose>
    <c:when test="${sessionScope.currentid==null}">
      not logged in
    </c:when>
    <c:otherwise>
   <h3> Welcome! ${sessionScope.currentfn} ${sessionScope.currentln}<a href="logout"> logout</a></h3>
    </c:otherwise>
  </c:choose>
	
		<h1>Login</h1>

		<form action="login" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${login.nick}"/>' size="20">
		 </div>
		 <c:if test="${login.imaPogresku('nick')}">
		 <div class="greska"><c:out value="${login.dohvatiPogresku('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value='<c:out value=""/>' size="50">
		 </div>
		 <c:if test="${login.imaPogresku('password')}">
		 <div class="greska"><c:out value="${login.dohvatiPogresku('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Login">
		  <input type="submit" name="metoda" value="Clear">
		</div>
		
		</form>
		
		
	<p><a href="register"> Register a new account </a></p>
	
	<h3>List of currently registrated users</h3>
	
	<c:forEach var="e" items="${users}">
   	<ul>	 
		 <li><a href="author/${e.nick}">${e.nick}</a></li>
	</ul>
	</c:forEach>

	</body>
</html>
