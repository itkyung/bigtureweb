<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>Bigture</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/admin/user/listUser.js"></script>
</head>
<body>
	<div class="container">
		<h3 class="page-header">회원 관리</h3>
		
		<form id="searchForm" class="form-horizontal" role="form"  method="post">
			<div class="tab-content">
				<div class="tab-pane active opengarden-tab">
					<div class="form-group border-bottom">
						<label class="col-lg-2 control-label">인증여부</label>
						<div class="col-lg-10 og-form-input">
							<div class="col-lg-10">
								 <label class="radio-inline">
								    <input type="radio" name="verified" id="verified" value="true"> 인증회원
								 </label>
								  <label class="radio-inline">
								    <input type="radio" name="verified" id="verified" value="false"> 미인증회원
								 </label>
							</div>
						</div>
					</div>
					<div class="form-group border-bottom">
						<label class="col-lg-2 control-label">회원종류</label>
						<div class="col-lg-10 og-form-input">
							<div class="col-lg-10">
								 <label class="radio-inline">
								    <input type="radio" name="userSearchRole" id="userSearchRole" value="NORMAL"> 일반회원
								 </label>
								  <label class="radio-inline">
								    <input type="radio" name="userSearchRole" id="userSearchRole" value="EXPERT"> 전문가
								 </label>
							</div>
						</div>
					</div>
					<div class="form-group border-bottom">
						<label class="col-lg-2 control-label">검색어</label>
						<div class="col-lg-10 og-form-input">
							<select class="form-control" id="userSearchType" name="userSearchType">
						     		<option value="EMAIL">이메일</option>
						     		<option value="NICKNAME">닉네임</option>
						     </select>
						     	
							<input type="text" class="span2 form-control input-sm" id="searchKeyword" name="keyword" placeholder="키워드입력"/>
						 </div>
					</div>
					
				</div>
			</div>
			<div class="info-section">
				<div class="container align-center">
					<button type="button" id="btnSearch" class="btn og-btn-primary">검 색</button>
				</div>
			</div>
		</form>
		<br/>
		
		<form id="resultTable">
			<div class="search-result-title">
				<div class="pull-left">
					검색결과 : 총 <code id="totalCount"> 0</code> 개.
				</div>
				<div class="pull-right">
					<button  type="button" class="btn og-btn-warning" onclick="addUser();">새로 추가</button>
				</div>
			</div>
			<table id="searchResult" class="display table-bordered" cellspacing="0" width="100%">
				<thead>
		            <tr>
		                <th></th>
		                <th>이메일</th>
		                <th>닉네임</th>
		                <th>국가</th>
		                <th>가입일</th>
		                <th>최근 로그인</th>
		                <th>OS</th>
		                <th>앱버전</th>
		                <th>성별</th>
		                <th>생일</th>
		            </tr>
		        </thead>
		        
			</table>
			
		</form>
	</div>
</body>
</html>