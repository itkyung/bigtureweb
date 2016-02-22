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
	<style type="text/css">
       body {
			background: url('http://www.bigture.co.kr:8080/bigture/resources/images/email/bg_pattern.png');
			font-family:NanumGothic,"나눔고딕", dotum, "돋움", gulim, "굴림", Arial;
			font-weight:bold;
			color:RGB(117,117,117);
			}

		a:link {text-decoration:none;}
		a:visited {text-decoration:none;}
		a:hover {text-decoration:underline;}
		a:active {text-decoration:underline;}
		a {color:#999999}

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
<body>

	<table style="width:100%" border="0" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td style="line-height:10pt" height="7">&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table style="background-color:#ffffff;" cellspacing="0" cellpadding="0" width="616" align="center">
						<tbody>
							<tr>
								<td width="7" align="left" valign="top" bgcolor=""></td>
								<td style="padding-left:7%;padding-right:7%;">
									<div align="center"><br><img src="http://www.bigture.co.kr:8080/bigture/resources/images/email/bigture_logo.png" border="0" alt="Bigture, Art SNS" align="middle" style="align:middle;border:0;color:#003366;font-size:16pt;margin-left:-13px" width="50%"></div>
									<center>
									<p style="margin-bottom:30px; font-size:14pt; color:#d34b46">인증성공!</p>
									<p style="font-size:12pt">메일 인증이 완료되었습니다.</p>
									<p style="font-size:12pt">이제, 빅쳐 앱으로 돌아가 로그인 해주세요.</p>
									</center>
									<br><br>
									<div style="text-align:center">
									<p><span style="font-size:8pt;color:#999999;font-weight:normal;">Copyright 2014 <a href="http://clockworks.co.kr">The Clockworks Innovate.</a> All rights reserved.</span></p>
									</div>
								</td>
								<td width="7" align="left" valign="top" bgcolor=""></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
			<td style="line-height:10pt" height="7" bgcolor=""> </td>
			</tr>
		</tbody>
	</table>
</body>

</html>
