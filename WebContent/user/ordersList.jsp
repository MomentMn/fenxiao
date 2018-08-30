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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  <meta content="telephone=no" name="format-detection" />
  <meta name="wap-font-scale" content="no" />
  <title>订单列表</title>
  <link rel="stylesheet" href="../css/base.css" />
  <link rel="stylesheet" type="text/css" href="../css/cart3.css" />
  <script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/zepto.min.js"></script>
	<script>
	var page = 1;
	var type = 2;
	</script>
 </head>
 <body>
  <section class="ww order-wrap">
   <div class="order-tab" id="b2cOrder" style="-webkit-transform-origin: 0px 0px 0px; opacity: 1; -webkit-transform: scale(1, 1);">
    <ul class="order-nav wwbox" id="orderTab">
     <li class="cur" id="orderTab1"><a href="javascript:void(0);" onclick="javascript:change(2);">全部订单</a></li>
     <li class="" id="orderTab2"><a href="javascript:void(0);" onclick="javascript:change(0);">待支付</a></li>
     <li class="" id="orderTab3"><a href="javascript:void(0);" onclick="javascript:change(1);">已支付</a></li>
    </ul>
    <div class="order-tab-count" id="orderList">

    </div>
    <script>
    $(function(){
		list();
	});
	function change(t){
		if(t==2){
			$("#orderTab1").removeClass().addClass("cur");
			$("#orderTab2").removeClass();
			$("#orderTab3").removeClass();
		}else if(t==0){
			$("#orderTab1").removeClass();
			$("#orderTab2").removeClass().addClass("cur");
			$("#orderTab3").removeClass();
		}else{
			$("#orderTab1").removeClass();
			$("#orderTab2").removeClass();
			$("#orderTab3").removeClass().addClass("cur");
		}
		page=1;
		type=t;
		$("#orderList").empty();
		list();
	}
	// 模块格式化
    function fs(str) {

        for ( var i = 0; i < arguments.length - 1; i++) {
//             全局替换忽略大小写
            var reg=new RegExp("\\{"+ i+"\\}",'gi');
            str = str.replace(reg,arguments[i + 1]);
        }
        return  str;
    }
	function list(){
		$.ajax({
		    url: "ordersList?type="+type+"&p="+page,
		    type: "GET",
		    dataType: "json",
		    async: false,
		    success: function(data) {
		        if(data.status==1){
		        	var list = data.list;
		        	var status = "";
                    var htmlContent ="";
                    var template=$("#template").html();

		        	for(var i=0;i<list.length;i++){
		        	    var display="inline";
		        		if(list[i].status=="0"){
		        			status = "等待支付";
		        		}else{
		        			status = "支付成功";
                            display="none"
		        		}
		        		var product = list[i];
		        		var d = new Date(list[i].createDate);
                        htmlContent =htmlContent +fs(template,status,
                                        product.productName,
                                        product.productMoney,
                                        product.productNum,
                                        product.money,
                                        display,
                                        product.phone,
                                        product.address,
                                        product.productUrl,
                                        product.id
                                );
		        	}
                    $("#orderList").append(htmlContent);
		        	$("#nextPage").html("点击加载更多");
		        	$("#nextPage").attr("style","display:");
		        	$("#noLoadNextPage").attr("style","display:none");
		        	if(data.isNextPage==0){
		        		$("#nextPage").html("已全部加载完");
		        		$("#nextPage").attr("style","display:none");
		        		$("#noLoadNextPage").attr("style","display:");
		        	}
		        	page ++;
		        }else{
		        	alertDefaultStyle("mini", "暂无数据");
		        }
		    }
		});
	}
    /**
     * 删除订单
     */
    function deleteOrder(id) {
        $.ajax({
            url: "deleteOrder?id="+id,
            type: "GET",
            dataType: "json",
            async: false,
            success: function(data) {
                //删除订单成功
                if(data.code==200) {
                    $("#item" + id).hide();
                    alertDefaultStyle("mini","删除成功！")
                }
                else{
                    alertDefaultStyle("mini","删除失败！")
                }
            }});

    }
    function payFor(id) {
        setTimeout("window.location.href='unifiedOrder?orderId="+id+"';",100);
    }
    </script>
   </div>
   </div>
   <div class="mb30 sn-txt-muted tc" id="noLoadNextPage" style="display:none">
    <span>Duang~到底了</span>
   </div>
   <div id="loadNextPage">
    <div class="mb30 tc">
     <a href="javascript:void(0)" id="nextPage" name="Wap_reg_person_005" onclick="list();return false;" class="sn-txt-muted">点击加载更多</a>
    </div>
   </div>
  </section>
 <script id="template" type="text/html">
     <div style='height:260px;background:#fff;margin-top:10px;display:block' id="item{9}">
         <div style="text-align: right">
             <span style='font-size:16px;color:#1aad19;margin-right:15px;margin-top: 5px;'>{0}</span>
         </div>
         <div style="background-color: #f2f2f2; display:table;width: 100%; ">
             <div style='display:table-cell;vertical-align: text-bottom;margin:10px;width: 165px;'>
                 <img src='<%=basePath%>/{8}' style='width:150px;height: 150px;'/>
             </div>
             <div style='display:table-cell; margin:10px;vertical-align: top;padding-top: 5px;'>
                 <div><span style="font-size:16px;">{1}</span><span style="font-size:16px;float: right;margin-right: 15px;">￥{2}</span></div>
                 <div style="color: #9C9C9C;font-size: 14px;margin-top: 5px;"><span>颜色分类:白色</span><span style="font-size:16px;float: right;margin-right: 15px;">x{3}</span></div>
                 <div style="color: #9C9C9C;font-size: 14px;margin-top: 5px;"><span>联系方式:{6}</span></div>
                 <div style="color: #9C9C9C;font-size: 14px;margin-top: 5px;"><span >收货地址:{7}</span></div>
             </div>
         </div>
         <div style="text-align: right;padding: 5px 0px;">
             <span style='color:#000;margin-right:15px;'><span style="font-size: 12px;">共计{3}件商品 合计:￥</span><span style="font-size: 20px;">{4}</span> <span style="font-size: 12px;">(含运费￥0.00)</span> </span>
         </div>
         <div style="text-align: right;border-top: solid #F2F2F2 1px;font-size: 20px;">
             <span style='font-size:16px;cursor:pointer;vertical-align: -webkit-baseline-middle;color:#000;margin-right:11px;border: solid #F2F2F2 2px;border-radius: 4px;padding: 4px 6px;' onclick="deleteOrder('{9}')">删除订单</span>
             <span style='font-size:16px;cursor:pointer;vertical-align: -webkit-baseline-middle;color:#000;margin-right:11px;border: solid #F2F2F2 2px;border-radius: 4px;padding: 4px 6px;display:{5}' onclick="payFor('{9}')">付款</span>
         </div>
     </div>
 </script>
 </body>
</html>