<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta content="telephone=no" name="format-detection" />
		<title>支付成功</title>
		<link rel="stylesheet" type="text/css" href="../css/base.css">
		<link rel="stylesheet" type="text/css" href="../css/member.rem.css">
		<script type="text/javascript" src="../js/jquery.min.js"></script>
	</head>
	<body>
		<!-- 公用头部导航 -->
		<div class="sn-nav sn-block">
			<div class="sn-nav-back"><a class="sn-iconbtn" href="../index.jsp">返回</a></div>
			<div class="sn-nav-title of">支付成功</div>
			<div style="text-align:center;"><img src="${qrCodeUrl}"></div>
		</div>
	</body>
</html>