<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<link rel="stylesheet" type="text/css" href="../css/style.css" media="all">
	<title>二维码</title>
	<script type="text/javascript" async="" src="../js/aywmq.js"></script>
	<script async="" src="../js/analytics.js"></script>
	<script type="text/javascript" async="" src="../js/da_opt.js"></script>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
</head>
<body>
    <%--<div class="sn-nav">--%>
		<%--<div class="sn-nav-back">--%>
		<%--<a class="sn-iconbtn" href="javascript:history.go(-1)">返回</a></div>--%>
		<%--<div class="sn-nav-title of">二维码</div>--%>
		<%--<div class="sn-nav-right tr pr"><a href="/index.jsp">首页</a></div>--%>
	<%--</div>--%>
	<section class="sn-main pr" id="QR" style="display: none">
		<div  class="input-a sn-block wbox mt30 pr" >
		 <img src="${sessionScope.loginUser.wxQrUrl }" style="margin: 0 auto;width: 300px;height: 300px;"/>
		</div>
	</section>
	<script type="text/javascript" src="../js/zepto.min.js"></script>
	<script>
		$(function () {
			$("#QR").hide();
			loadQR();
		})
		function  loadQR() {
			$.ajax({
				url: "promoteValid",
				type: "GET",
				dataType: "json",
				success: function(data) {
					if(data.data==0){
						alertDefaultStyle('onceConfirm','你还未消费体验产品,不能使用二维码');
					}
					else if(data.data>0){
						$("#QR").show();
					}
				}
			});
		}


	</script>
</body>
</html>