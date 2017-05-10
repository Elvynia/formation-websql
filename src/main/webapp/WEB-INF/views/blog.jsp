<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ma première JSP</title>
</head>
<body>
	<h1><%=request.getAttribute("page_title")%></h1>
	<form action="#" method="post">
		<h2>Créer un nouvel article</h2>
		Titre : <input type="text" name="title">
		Description : <input type="text" name="description">
		<button>Valider</button>
	</form>
	<hr />
	<% int i = 1; %>
	<c:forEach items="${articles}" var="article">
		<div>
			<c:url value="/images/delete.png" var="deleteUrl" />
			<h2>Article n°<%= i %> <a href="delete.html?index=<%= i - 1 %>"><img src="${deleteUrl}"></a></h2>			
			<p>
				<c:out value="${article}" />
			</p>
		</div>
		<% ++i; %>
	</c:forEach>
</body>
</html>