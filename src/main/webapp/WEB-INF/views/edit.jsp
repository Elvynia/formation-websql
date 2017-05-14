<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ma deuxième JSP..</title>
</head>
<body>
	<form action="#" method="post">
		<h2>Modifier l'article (id=${article.id})</h2>
		Titre : <input type="text" name="title" value="${article.title}">
		Description : <textarea name="description">${article.description}</textarea>
		<button>Valider</button>
	</form>
</body>
</html>