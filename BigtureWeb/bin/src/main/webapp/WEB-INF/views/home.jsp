<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Bigture :: Coming Soon</title>
	<meta name="author" content="The Clockworks Innovate" />
	<meta name="description" content="Kids Art Social Network Service application">  
	<meta name="keywords" content="빅쳐,빅처,아트,소셜,그림,draw,drawing,paint,art,artwork,sketch,picture,social,friend,network,connect">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<style type="text/css">
    body {
			background: url('http://bigture.co.kr/img/bg_pattern.png');
			font-family:NanumGothic,"나눔고딕", dotum, "돋움", gulim, "굴림", Arial;
			font-weight:bold;
			font-size:14px;
			color:RGB(117,117,117);
			}
		li { list-style:none;	}
		ul {
			margin:0;
			padding:0;
			}
		img { border:0px }

		.cen { 
			width:836px;
			position:relative; 
			left:50%;
			margin-left:-409px;
			}
		.cen .logo_top{
			height:600px;
			background: url('http://bigture.co.kr/img/logobox.png') top center no-repeat;
			margin-bottom:50px;
			}
		.cen .logo_top .soon{
			padding-top:410px;
			position:relative; 
			text-align:center;
			}
		.cen .msg{
			width:836px;
			height:15px;
			position:relative; 
			text-align:center;
			margin-bottom:18px;
			}
		.cen .link_btn {
			width:50%;
			margin:0 auto;
			background-color:red;
			}
		.cen .link_btn ul{
			width:836px;
		}
		.cen .link_btn li{
			float:left;
			margin-right:10px;
			}

	</style>

  <script language="javascript">
  	var uagent = navigator.userAgent.toLowerCase();
  	var iPod = uagent.search("ipod");
  	var iPhone = uagent.search("iphone");
  	var iPad = uagent.search("ipad");
  	var Android = uagent.search("android");
  	var mobile = uagent.search("mobile");
  	
  	function goIos() {
  		if (iPod > -1 || iPhone > -1) {
  			window.open("https://itunes.apple.com/kr/app/bigture-for-iphone/id564116655?mt=8");
  		}
  		else {
  			window.open("https://itunes.apple.com/kr/app/bigture/id550044184?mt=8");
  		}
  	}
  </script>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div class="cen">
		<div class="logo_top"><div class="soon">웹 사이트를 준비중이니 조금만 기다려 주세요.</div></div>
		<div class="msg"><span>모바일로 먼저 즐겨보세요!</span><span><a href="/bigture/admin/home">관리자 페이지</a></span></div>
		<div class="link_btn">
			<ul>
				<li><a href="javascript:goIos();"><img src="http://bigture.co.kr/img/link_appstore.png"/></a></li>
				<li><a href="https://play.google.com/store/apps/details?id=com.clockworks.android.bigture" target="_blank"><img src="http://bigture.co.kr/img/link_googleplay.png"/></a></li>
			</ul>
		</div>
	</div>
</body>

</html>
