<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Bigture</title>
	<meta name="author" content="The Clockworks Innovate" />
	<meta name="description" content="Kids Art Social Network Service application">  
	<meta name="keywords" content="빅쳐,빅처,아트,소셜,그림,draw,drawing,paint,art,artwork,sketch,picture,social,friend,network,connect">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	
</head>
<body>
	<h3>어드민용 임시 사용자 활성화 페이지</h3>
	<div style="margin:0 auto;">
	<c:url var="actionUrl"	value="/admin/doActivateUser"/>
	<form method="POST" action="${actionUrl}">
		LoginId : <input type="text" name="loginId" id="loginId"/>
		<br>
		<input type=submit value="활성화하기"/>
	</form>
	</div>
</body>
</html>