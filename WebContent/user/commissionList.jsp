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
	<title>佣金明细</title>
	<script type="text/javascript" async="" src="../js/aywmq.js"></script>
	<script async="" src="../js/analytics.js"></script>
	<script type="text/javascript" async="" src="../js/da_opt.js"></script>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script>
    $(function(){
		$.ajax({
		    url: "userCommission",
		    type: "GET",
		    dataType: "json",
		    async: false,
		    success: function(data) {
		        $("#accountBalance").html(data.commission);
		    }
		});
	});
    </script>
</head>
<body>
    <div class="sn-nav">
		<div class="sn-nav-back"><a href="javascript:history.back(-1)">返回</a></div>
		<div class="sn-nav-title of" id="addAddr">佣金明细</div>
		
	</div>

	<section class="sn-main pr">
		<div class="input-a sn-block wbox mt30 pr">
			<span>账户余额:</span>
			<div class="wbox-flex ml30 pr" id="accountBalance">
				0
			</div>
		</div>
		<div class="sn-block">
			<a href="withdraw.jsp" class="input-a  wbox mt30 pr">
				<span>提现:</span>
				<div class="wbox-flex ml30 pr" >
				</div>
				<div>
					<img style="height: 50%;margin-top: 20px ;" src='../images/in_small.png' />
				</div>
			</a>	
		</div>
		<div class="sn-block">
			<a href="commissionBill.jsp" class="input-a  wbox mt30 pr">
				<span>账单:</span>
				<div class="wbox-flex ml30 pr" >
				</div>
				<div>
					<img style="height: 50%;margin-top: 20px ;" src='../images/in_small.png'/>
				</div>
			</a>	
		</div>			
	</section>
	<script type="text/javascript" src="../js/zepto.min.js"></script>

</body></html>