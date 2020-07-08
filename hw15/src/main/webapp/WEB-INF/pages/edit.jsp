<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
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
   <h3>  Welcome! ${sessionScope.currentfn} ${sessionScope.currentln} <a href="../../logout"> logout</a></h3>
    </c:otherwise>
  </c:choose>

		<form action="../../editBlog?id=${blogEntry.id}" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Title</span><input type=text name="title" value='<c:out value="${blogEntry.title}"/>' size="50">
		 </div>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Text</span><textarea rows="20" cols="100" name="text"><%if(request.getAttribute("blogEntry")!=null){
			 out.println(((BlogEntry)request.getAttribute("blogEntry")).getText());
		  }%></textarea>
		  
		  
		 </div>
		</div>


		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>
		
		<p><a href="../../main"> Go to home page </a></p>

	</body>
</html>
