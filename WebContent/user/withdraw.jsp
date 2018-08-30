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
	<title>提现</title>
	<script type="text/javascript" async="" src="../js/aywmq.js"></script>
	<script async="" src="../js/analytics.js"></script>
	<script type="text/javascript" async="" src="../js/da_opt.js"></script>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script>
		$(function(){
			$.ajax({
				url: "userInfoJson",
				type: "GET",
				dataType: "json",
				async: false,
				success: function(data) {
					$("#restMoney").html((data.commission||0).toFixed(2)+"");
				}
			});
		});
    </script>
</head>
<body>
	<section class="sn-main pr">
		<div style="margin:15px;border-radius: 12px;width: 400;height:300px;background-color: #fff">
			<div style="width: 100%;border-radius: 12px;height:50px;background-color: #FCFCFC;font-size: 20px;color: #000;font-family: 'Microsoft YaHei';"
			><span style="margin-left: 20px;vertical-align: -webkit-baseline-middle;" >提现到微信零钱</span><span style="margin-left: 5px;vertical-align: sub;"><img src="/images/wx.png" style="height: 24px;width: 24px;"/></span>
			</div>
			<div>
				 <div><span style="margin-left: 20px;vertical-align: -webkit-baseline-middle;font-size: 16px;" >提现金额</span></div>
				 <div>
					 <div style="margin-left: 20px;margin-right: 20px;vertical-align: -webkit-baseline-middle;font-size: 32px;border-bottom: solid 1px #ccc;" >￥<input id="money"  style="width: 200px;font-size: 32px;"/></div>
				 </div>
				<div><div style="margin-top:5px;margin-left: 20px;margin-right: 20px;vertical-align: -webkit-baseline-middle;font-size: 16px;color: #AAAAAA" >账户余额￥<span id="restMoney">0.00</span>,<a style="color: #0000ff" href="javascript:withAllDraw()">全部提现</a></div></div>

			</div>
			<div>
				<a href="javascript:withDraw()" name="Wap_reg_person_005" class="first-step sn-btn sn-btn-big sn-btn-block m30 sn-btn-positive" style="background-color: #84b100;border-radius: 16px;">提现</a>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="../js/zepto.min.js"></script>
    <script type="text/javascript">
		function withAllDraw() {
			debugger;
			$("#money").val($("#restMoney").text());
		}
		// 提现方法
		function withDraw() {
			 var val = $("#money").val();
			if(val==0 || !val){
				alertDefaultStyle("mini","提现金额为0");
				return false;
			}
			// 支付
			$.ajax({
				url: "payforCommission",
				type: "POST",
				data: {
					val:val
				},
				dataType: "json",
				async: false,
				success: function(data) {
					if(data.code=="200"){
						alertDefaultStyle("mini", data.msg);
						$("#restMoney").text(data.amount||0.00);
						$("#money").val(0.00);
					}else{
						alertDefaultStyle("mini", data.msg);
					}
				}

		});
		}
	</script>
</body></html>