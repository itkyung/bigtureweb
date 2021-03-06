<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.context.*" %>
<%@ page import="org.springframework.web.context.support.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Bigture</title>
	<script>
		
		var _requestPath = "${pageContext.request.contextPath}";
		var _imageServerPath = "${_imageServerPath}";
		
		
	</script>	

	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery/smoothness/jquery-ui-1.9.1.custom.min.css"/>	
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>	
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/plug-ins/e9421181788/integration/bootstrap/3/dataTables.bootstrap.css"/>	
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/script/jquery/fancybox/jquery.fancybox.css"/>	
	
	<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap/datepicker.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css"/>	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/front.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bigture.css"/>
	
	
	<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/jquery-ui-1.9.1.custom.min.js"></script>
	<!-- script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/fancybox/jquery.fancybox.pack.js"></script -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/jquery.masonry.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/jquery.imagesloaded.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/jquery.number.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery/jqGrid-4.4.1/js/i18n/grid.locale-kr.js"></script>
	<script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="//cdn.datatables.net/plug-ins/e9421181788/integration/bootstrap/3/dataTables.bootstrap.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/bootstrap/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/bootstrap/bootstrap-datepicker.kr.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/bootstrap/bootbox.min.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/common.js"></script>
	
	
	
	<decorator:head />
</head>


<c:url value="/admin/listUser" var="listUserUrl"/>
<c:url value="/myGarden/mydesk/myInfo" var="myInfoUrl"/>
<c:url value="/logout" var="logoutUrl"/>
<c:url value="/login/loginForm" var="loginUrl"/>
<c:url value="/myGarden/sharing/orderList" var="myPageUrl"/>
<body>

	<div id="main-container">
		<div id="main-header">
			<div class="container main-header-inner">
				
				<a href="${pageContext.request.contextPath}/admin/home" class="header-logo">Bigture 로고</a>
				
				
				<div class="logout-area">
					<img src="${pageContext.request.contextPath}/resources/images/1.5/icon_menu_1.png" class="textmiddle"/><span class="span-margin-left"><a href="${logoutUrl}">로그아웃</a></span>
				</div>
				
				
			
			</div>
		</div>
		<div id="main-body-wrapper">
			<div class="menu-bg">
				<nav class="navbar navbar-default container opengarden-nav" role="navigation">
				  <div class="container-fluid">
				    <!-- Brand and toggle get grouped for better mobile display -->
				    <div class="navbar-header">
				      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				        <span class="sr-only">Toggle navigation</span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				      </button>
				     
				    </div>
				
				    <!-- Collect the nav links, forms, and other content for toggling -->
				    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				      <ul class="nav navbar-nav">
				      	
			      		<li class="dropdown">
				        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">회원 관리</a>
				        	<ul class="dropdown-menu">
					            <li><a href="${listUserUrl}">회원 리스트</a></li>
					        </ul>
				        </li>
				      	<li class="dropdown">
				        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">그림 관리</a>
				        	<ul class="dropdown-menu">
					            <li><a href="#">신고된 그림</a></li>
					            <li><a href="#">그림 리스트</a></li>
					         </ul>
				        </li>
				        <li class="dropdown">
				        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">스토리 관리</a>
				        	<ul class="dropdown-menu">
				        		<li><a href="#">스토리 만들기</a></li>
					            <li><a href="#">스토리 리스트</a></li>
					         </ul>
				        </li>
				        <li class="dropdown">
				        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">공모전 관리</a>
				        	<ul class="dropdown-menu">
					            <li><a href="#">공모전 등록</a></li>
					            <li><a href="#">공모전 리스트</a></li>
					         </ul>
				        </li>
				        <li class="dropdown">
				        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">추가기능</a>
				        	<ul class="dropdown-menu">
					            <li><a href="#">공지사항</a></li>
					            <li><a href="#">단체메일</a></li>
					            <li><a href="#">푸쉬전송</a></li>
					         </ul>
				        </li>
				      </ul>
				     
				    </div><!-- /.navbar-collapse -->
				  </div><!-- /.container-fluid -->
				</nav>
			</div>
				<decorator:body />
			
			
		</div>
		<div id="main-footer">
			
				<%@ include file="/WEB-INF/views/decorators/footer.jspf" %>
			
		</div>
		
		<div id="totaMask"></div>
		
	</div>
</body>

</html>