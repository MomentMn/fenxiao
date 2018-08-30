<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" />
	<title>会员中心</title>
	<link rel="stylesheet" type="text/css" href="../css/base.css">
	<link rel="stylesheet" type="text/css" href="../css/member.rem.css">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
</head>

<body>

	<!-- 公用头部导航 -->
	<div class="sn-nav sn-block">
		<div class="sn-nav-back"><a class="sn-iconbtn" href="../index.jsp">返回</a></div>
		<div class="sn-nav-title of">会员中心</div>
	<!-- 头像区域 -->
	<div class="meb-banner" style="background:url(../images/banner.jpg) no-repeat center top;background-size:cover;">
		<div class="meb-person-data">
			<div class="meb-head">
				<img src="${sessionScope.loginUser.wxImageUrl }" onerror="this.src='../images/avatar.png'">
			</div>
			<div class="meb-name">
			${sessionScope.loginUser.wxName }
			</div>
		</div>
	</div>
	<div class="sn-block meb-list">
			<ul class="sn-list-input">
				<li>
					<a href="promote.jsp" class="block wbox" >
						<label class="meb-list-ico" style="background:url('../images/ico-myTicket.png') no-repeat left center;background-size:1rem 1rem;">我的二维码</label>
						<div class="wbox-flex tr sn-txt-muted arrow">
								<span id="toExpiredCouponsNum"></span>
						</div>
					</a>
				</li>
				<li>
				<a href="ordersList.jsp" class="block wbox" id="mts-allorder">
					<label class="meb-list-ico" style="background:url('../images/or.png') no-repeat left center;background-size:1rem 1rem;">订单管理</label>
					<div class="wbox-flex tr sn-txt-muted arrow">查看所有订单</div>
				</a>
				</li>
				<li>
					<a href="levelCount.jsp" class="block wbox" >
						<label class="meb-list-ico" style="background:url('../images/ico-addr.png') no-repeat left center;background-size:1rem 1rem;">下级统计</label>
						<div class="wbox-flex tr sn-txt-muted arrow">
						</div>
					</a>
				</li>
				<li>
					<a href="commissionList.jsp" class="block wbox" >
						<label class="meb-list-ico" style="background:url('../images/ico-mylc.png ') no-repeat left center;background-size:1rem 1rem;">佣金明细</label>
						<div class="wbox-flex tr sn-txt-muted arrow">
						</div>
					</a>
				</li>
			</ul>

	</div>

 


<script type="text/javascript">
var pageNumber = 1;
var totalPages = 0;
var pageSize = 10;
var resRoot = '/RES';
var base = '';
//$(function(){
//	getToExpiredCouponListNum()
//})

</script>

</body>
</html>