<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
   <h3>  Welcome! ${sessionScope.currentfn} ${sessionScope.currentln}<a href="../../logout"> logout</a></h3>
    </c:otherwise>
    </c:choose>

  <c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>  
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>
    </c:otherwise>
  </c:choose>
 
	
		<form 
		
		<% if(request.getAttribute("error")==null){
			%> 
			action="../../newMessage?id=${blogEntry.id}"
			<%
		} else {
			%> 
			action="newMessage?id=${blogEntry.id}"
			<%
		}; %> method="post">
		
		<div>
		 <div>
		 <br>
		  <span class="formLabel">Comment</span><textarea rows="4" cols="50" name="comment"> <%if(request.getAttribute("comment")!=null){
			 out.println(request.getAttribute("comment"));
		  }%></textarea>
		 </div>
		</div>

	<c:if test="${sessionScope.currentid==null}">
		<div>
		 <div>
		  <span class="formLabel">Email</span><input type="text" name="email" value='<c:out value=""/>' size="20">
		 </div>

	
		 <span class="greska"> 
		 <%if(request.getAttribute("error")!=null){
			 out.println(request.getAttribute("error"));
		 }
		 %> 
		 </span>

		 <br>
		</div>
	</c:if>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>
	
	<c:if test="${sessionScope.currentnick==blogEntry.user.nick}">
		<p><a href="edit?id=${blogEntry.id}"> Edit blog </a></p>
	</c:if>
	
	
	<p><a href="../../main"> Go to home page </a></p>

  </body>
</html>
