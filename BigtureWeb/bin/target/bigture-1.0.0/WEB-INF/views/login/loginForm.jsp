<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function _login() {
		var loginId = $("#loginId").val();
		if(loginId.length == 0){
			alert("아이디를 입력하세요.");
			return;
		}
		var password = $("#pwd").val();
		if(password.length == 0){
			alert("비밀번호를 입력하세요.");
			return;
		}
		$("#loginForm").submit();
	}

	
</script>


	<c:url value="/loginAction" var="loginUrl"/>
	<div class="container">
		<h3>빅쳐 어드민 로그인</h3>
		<form id="loginForm" role="form" action="${loginUrl}" method="POST">
		  <div class="form-group">
		    <label for="loginId">아이디</label>
		    <input type="email" class="form-control" id="loginId" name="loginId" value="admin" placeholder="로그인아이디">
		  </div>
		  <div class="form-group">
		    <label for="pwd">비밀번호</label>
		    <input type="password" class="form-control" id="pwd" name="pwd" placeholder="비밀번호" value="admin1234">
		  </div>
		  <p>
		  	 <a href="javascript:_login();" class="btn btn-primary" role="button">로그인</a>
		  </p>
		</form>
		
	</div>
	