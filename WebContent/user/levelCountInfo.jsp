<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="telephone=no" name="format-detection">
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	<link rel="stylesheet" type="text/css" href="../css/cart.css">
	<title>下级统计</title>
	<script type="text/javascript" async="" src="../js/aywmq.js"></script>
	<script async="" src="../js/analytics.js"></script>
	<script type="text/javascript" async="" src="../js/da_opt.js"></script>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	
</head>
<body>
    <div class="sn-nav">
		<div class="sn-nav-back"><a href="javascript:history.back(-1)">返回</a></div>
		<div class="sn-nav-title of" id="addAddr">
			<c:if test="${type==0}">
				总共下级人数
			</c:if>
			<c:if test="${type==1}">
				第一级人数
			</c:if>
			<c:if test="${type==2}">
				第二级人数
			</c:if>
			<c:if test="${type==3}">
				第三级人数
			</c:if>
		</div>
	</div>

	<section class="sn-main pr">
		<!--${levelList}-->
		<c:forEach var="entry" items="${levelList}">
			<div style="float:left;width: 18%;margin: 1%;" >
				<div style="width: 100%;text-align: center;">
					<img src="${entry.parentWxImageUrl}" style="width: 100%;">
					<br />
					<font>${entry.parentWxName}</font>
				</div>
			</div>
		</c:forEach>
	</section>
	<script type="text/javascript" src="../js/zepto.min.js"></script>

</body></html>