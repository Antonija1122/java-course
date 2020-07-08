<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Registration</title>
		
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
   <h3> Welcome! ${sessionScope.currentfn}${sessionScope.currentln} <a href="logout"> logout</a></h3>
    </c:otherwise>
  </c:choose>

		<form action="save" method="post">
		

		<div>
		 <div>
		  <span class="formLabel">First name</span><input type="text" name="ime" value='<c:out value="${zapis.ime}"/>' size="20">
		 </div>
		 <c:if test="${zapis.imaPogresku('ime')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('ime')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last name</span><input type="text" name="prezime" value='<c:out value="${zapis.prezime}"/>' size="20">
		 </div>
		 <c:if test="${zapis.imaPogresku('prezime')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('prezime')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">email</span><input type="text" name="email" value='<c:out value="${zapis.email}"/>' size="50">
		 </div>
		 <c:if test="${zapis.imaPogresku('email')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('email')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">nick</span><input type="text" name="nick" value='<c:out value="${zapis.nick}"/>' size="50">
		 </div>
		 <c:if test="${zapis.imaPogresku('nick')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('nick')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">password</span><input type="password" name="password" value='<c:out value=""/>' size="50">
		 </div>
		 <c:if test="${zapis.imaPogresku('password')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>

	</body>
</html>
