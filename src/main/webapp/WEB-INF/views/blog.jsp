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
			<c:url value="/images/edit.png" var="editUrl" />
			<!-- Important à savoir, même si on écrit article.id, c'est article.getId() qui sera utilisé ! -->
			<h2>${article.title} (id=${article.id})
				<a href="delete.html?id=${article.id}">
					<img src="${deleteUrl}">
				</a>
				<a href="edit.html?id=${article.id}">
					<img src="${editUrl}">
				</a>
			</h2>			
			<p>
				<!-- L'utilisation de c:out n'est pas obligatoire mais est préféré selon certaines bonnes pratiques.
				Cela dépend des gens avec qui on travaille, étant donné que JSF est plus souvent utilisé les JSTL sont
				un peu obsolètes, mais il faut les connaître. -->
				<c:out value="${article.description}" />
			</p>
		</div>
		<% ++i; %>
	</c:forEach>
</body>
</html>