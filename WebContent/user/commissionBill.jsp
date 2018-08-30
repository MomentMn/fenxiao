<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="../css/style.css?v=7" />
<link href="../css/mobiscroll.css" rel="stylesheet" />
<link href="../css/mobiscroll_date.css" rel="stylesheet" />
<title>账单</title>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/mobiscroll_date.js" ></script> 
<script type="text/javascript" src="../js/mobiscroll.js"></script> 
<script>
		var page = 1;
		var date = "";
		var type = 0;
</script>
<script>
	function formatDateTime(date){  
	    da = new Date(date);
	    var month = da.getMonth()+1+'月';
	    var date = da.getDate()+'日';
	    var hour = da.getHours()+':';
	    var min = da.getMinutes();
	    return [month,date,hour,min].join('');
	};  
	 function formatNewDate(){  
		var da = new Date();
		var year = da.getFullYear()+'年';
	    var month = da.getMonth()+1+'月';
	    
	    return [year,month].join('');
	}; 
	/* 手机时间空间 */
	$(function()  {
		var currYear = (new Date()).getFullYear();	
		var opt={};
		opt.date = {preset : 'date'};
		opt.datetime = {preset : 'datetime'};
		opt.time = {preset : 'time'};
		opt.default = {
			theme: 'android-ics light', //皮肤样式
			display: 'modal', //显示方式 
			mode: 'scroller', //日期选择模式
			dateFormat: 'yyyy年mm月',
			lang: 'zh',
			showNow: true,
			nowText: "今天",
			startYear: currYear - 50, //开始年份
			endYear: currYear + 10 //结束年份
		};
		$("#USER_AGE").mobiscroll($.extend(opt['date'], opt['default']));
	}); 
	$(function(){
		$("#USER_AGE").val(formatNewDate());
		list(0);
	});
	function getdate(){
		date=$("#USER_AGE").val();	
		var aa = date.replace('年','-');
		date = aa.replace('月','');
		page=1;
		list(1);
	}
	function list(type) {
		$.ajax({
			url : "commissionList?p="+page+"&date="+date,
			type : "GET",
			dataType : "json",
			async : false,
			success : function(data) {
				if(data.status==1){
					$("#allPayMoney").html(data.commissionListDto.allPayMoney);
					$("#incomeMoney").html(data.commissionListDto.incomeMoney);
					/* $("#USER_AGE").html(formatNewDate()); */
					$("#USER_AGE").val(formatNewDate());
					var item="";
					if(type==1){
						$('.content-center').empty();
					}
					$.each(data.commissionListDto.commissionList,function(i,result){
					item="<div class='content-centerOne'><div class='content-centerTwo'><img alt='' src='../images/house.png' style='float: left;width:1.5rem;'><div class='three' style='margin-left: 0.5rem;'><span>"
					+result['typeStr']+"</span><br><span>"+result['remark']+"</span></div><div class='content-float' style='margin-right: 10px;'>";	
					if(result['type']==1){/* 入款 */
						item += "<span style='color:#EE6A50;font-weight: bold;'>+"+result['money']+"</span>";
					}else{
						item += "<span style='font-weight: bold;'>-"+result['money']+"</span>";
					}
					item += "</div></div><div class='content-bottom' style='margin-left: 2.6rem;'><span>"
					+formatDateTime(result['createDate'])+"</span><hr></div></div>"; 
					$('.content-center').append(item);
					});
					if(data.isNextPage==0){
		        		$("#nextPage").html("已全部加载完");
		        		$("#nextPage").attr("disabled","disabled");
		        	}
		        	page ++;
				}else{
		        	alertDefaultStyle("mini", "已全部加载完");
		        }
			}
		});
	};
</script>
</head>
<body>
	<div class="sn-nav" >
		<div class="sn-nav-back">
			<a href="javascript:history.back(-1)">返回</a>
		</div>
		<div class="sn-nav-title of" id="addAddr">账单</div>
	</div>
	<input name="USER_AGE" id="USER_AGE" onchange="getdate()" style="font-size: 0.7rem;width:25%;height: 1rem;background-color: transparent;margin-left: 0.6rem;"/>
	<img src="../images/sool.png" style="width:0.5rem;"/>
	<!-- 本月 -->
	<div class="content-sin">
		<div class="content-left" style="color: #BDBDBD;font-size: 0.4rem;height: 1rem;">
			<span style="font-size: 0.5rem;height: 1rem">支出：<span id="allPayMoney"></span>&nbsp&nbsp&nbsp&nbsp收入：<span id="incomeMoney"></span></span>
		</div>
	</div>
	<!-- 明细 -->
	<div class="content-center"></div> 	
	<!-- 加载更多 -->
	<div class="loadmore">
		<a href="javascript:void(0)" id="nextPage" name="Wap_reg_person_005" onclick="list();return false;" class="first-step sn-btn sn-btn-big sn-btn-block m30">加载更多</a>
	</div>
	<!-- 加载完成提示 -->
	<div class="result-loaded-tip hide" id="end_load" style="display: none;"><span>Duang～到底了</span></div>
</body>
<style>
.selectBox {
	background-color: #ffffff;
}

.select {
	border: 0;
}

.select option {
	border: 0;
}

.content-sin {
	display: flex;
	align-items: center;
	padding: 0 .6rem;
}
.content-center {
	background-color: #ffffff;
}

.content-centerTwo {
	width: 100%;
	float: left;
	display: flex;
	align-items: center;
	padding: 0 .6rem;
	margin-top: 0.5rem;
}

.content-centerOne {	
	width: 100%;
}
.three{
	width: 100%;
}
.dw.dwbg{
	height: 13.5rem;
}
.dwwr{
	important;
	width:100%;
}
</style>
</html>