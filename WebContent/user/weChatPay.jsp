<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta charset="UTF-8" />
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta content="telephone=no" name="format-detection" />
		<title>微信支付</title>
		<link rel="stylesheet" href="../css/cart2.css" />
		
		<link href="../city/css/framework7.ios.min.css" rel="stylesheet">
		<link href="../city/css/framework7.ios.colors.min.css" rel="stylesheet">
		<link href="../city/css/style.css" rel="stylesheet">
		
		<script type="text/javascript" src="../js/jquery.min.js"></script>
		<script type="text/javascript" src="../js/zepto.min.js"></script>
	</head>
	<body>
		<input type="hidden" name="orderId" value="${orderId}">
		<input type="hidden" name="appId" value="${appId}">
	    <input type="hidden" name="nonceStr" value="${nonceStr}">
	    <input type="hidden" name="prepayId" value="${prepayId}">
	    <input type="hidden" name="paySign" value="${paySign}">
	    <input type="hidden" name="timeStamp" value="${timeStamp}">
	    <input type="hidden" name="openId" value="${openId}"/>
	</body>
	<script>
	
	    function onBridgeReady(){
	    	var orderId = $("input[name='orderId']").val();
	        var appId = $("input[name='appId']").val();
	        var nonceStr = $("input[name='nonceStr']").val();
	        var prepayId = $("input[name='prepayId']").val();
	        var paySign = $("input[name='paySign']").val();
	        var timeStamp = $("input[name='timeStamp']").val();
			var openId =$("input[name='openId']").val();
	        WeixinJSBridge.invoke(
	                'getBrandWCPayRequest', {
	                    "appId":appId,
	                    "timeStamp":timeStamp,
	                    "nonceStr":nonceStr,
	                    "package":prepayId,
	                    "signType":"MD5",
	                    "paySign":paySign
	                },
	            function(res){
	                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
	                   location.href="/user/paySuccess?orderId="+orderId+"&openId="+openId;
						return false;
	                }else {//这里支付失败和支付取消统一处理
	                    alert("支付失败");
	                }
	            }
	        );
	    }
	
	    $(document).ready(function () {
	        if (typeof WeixinJSBridge == "undefined"){
	            if (document.addEventListener){
	                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	            }else if (document.attachEvent){
	                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
	                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	            }
	        }else {
	            onBridgeReady();
	        }
	    });
	</script>
</html>