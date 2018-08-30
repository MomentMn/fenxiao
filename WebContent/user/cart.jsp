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
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  <meta content="telephone=no" name="format-detection" />
  <title>提交订单</title>
  <link rel="stylesheet" href="../css/cart1.css" />
  <script type="text/javascript" src="../js/jquery.min.js"></script>
  <script type="text/javascript">
  	var money = ${requestScope.product.money };
  	var pid = ${requestScope.product.id };
  	var number = 1;
  	function updateQuantity(num){
  		var quantity = $("#quantity").val();
  		quantity = parseInt(quantity);
  		num = parseInt(num);
  		if(num==-1){
  			if(quantity==1){
  				return;
  			}
  		}
  		quantity = quantity+num;
  		$("#quantity").val(quantity);
  		$("#userPayAllPrice").html(quantity*money);
  		number = quantity;
  	}
  	function updateQuantityOnblur(){
  		var quantity = $("#quantity").val();
  		quantity = parseInt(quantity);
  		var reg = /^\+?[1-9][0-9]*$/;
  		if(!reg.test(quantity)){
  			$("#quantity").val("1");
  			$("#userPayAllPrice").html(money);
  			number = 1;
  		}else{
  			$("#quantity").val(quantity);
  			$("#userPayAllPrice").html(quantity*money);
  			number = quantity;
  		}
  	}
  	
  	function ordersSave(){
  		window.location.href="ordersSave?pid="+pid+"&num="+number;
  	}
  </script>
  <style>
   #img3 img{
    width: 8%;
    height: 8%;
    margin-left: 17%;
   }
  </style>
 </head>
 <body>
  <section class="ww">
   <div class="loagMask" id="loading" style="-webkit-transform-origin: 0px 0px 0px; opacity: 1; -webkit-transform: scale(1, 1); display: none;">
    <div class="sn-mask-loading fixedLoading"></div>
   </div>
   <div class="sn-nav sn-block">
    <div class="sn-nav-back">
     <a class="sn-iconbtn" href="javascript:history.go(-1);">返回</a>
    </div>
    <div class="sn-nav-title of">
     	提交订单
    </div>
    <div class="sn-nav-right tr pr"><a href="index.jsp">首页</a></div>
   </div>
   <div class="cart-1-6" style="display:none;" id="cartLogin">
    <div class="cart-cont">
     <i></i>
     <p></p>
     <span><a href="javascript:void(0);" class="sn-btn sn-btn-positive" id="cartBtn"></a></span>
    </div>
   </div>
   <div id="cartList" class="mt30">
    <div id="img3" class="cart-edit-success-img"></div>
    <div style="width: 100%;border-top:1px solid white;margin-bottom: 20px;"></div>
    <div class="cart-list" id="snList"> 
     <ul class="cart-item" id="ul">
      <li id="li_0"> 
       <dl> 
        <dt> 
        <c:if test="${requestScope.status==0 }">
        <div class="pr"> 
          <div class="wbox "> 
           <div class="pro-box"> 
            <a href="javascript:void(0);" class="pro-name"> 
            <p class="text-clamp2 fs26">${requestScope.message }</p>
             </a> 
           </div> 
          </div> 
         <div class="cart-active sn-txt-assertive wbox" id="error_message_0"></div> 
        </c:if>
        <c:if test="${requestScope.status==1 }">
         <div class="pr"> 
          <div class="wbox "> 
           <div class="pro-img pr"> 
            <a href="javascript:void(0)"> 
            <img src="../${requestScope.product.picture }"/> </a>
            <div class="pro-mask"></div> 
           </div> 
           <div class="pro-box"> 
            <a href="javascript:void(0)" class="pro-name"> 
            <p class="text-clamp2 fs26">&nbsp;&nbsp;&nbsp;${requestScope.product.title }</p>
             <p class="fs26 sn-txt-positive ofv ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&yen;&nbsp;&nbsp;${product.money }</p>
             </a> 
            <div class="sn-count"> 
             <input type="image" src="../images/-.png" style="width: 20%;" onclick="updateQuantity(-1);"/>
             <input type="text" value="1" class="input-reset count-num sn-txt-positive" maxlength="3" id="quantity" onblur="updateQuantityOnblur()" />
             <input type="image" src="../images/+.png" style="width: 20%;" onclick="updateQuantity(1);"/>
            </div> 
           </div> 
          </div> 
          <div class="cart-list-opra wbox"> 
           <div class="wbox-flex tr">
            <input type="image" style="width: 60%" id="inp1" src="../images/delete.png">
           </div> 
           <em class="cart-edit-ico cart-fav-ico"></em> 
           <em class="cart-edit-ico cart-del-ico"></em> 
          </div> 
         </div> 
         <div class="cart-active sn-txt-assertive wbox" id="error_message_0"></div>
         </c:if> 
        </dt> 
       </dl> </li>
     </ul> 
    </div>
   </div>
  </section>
<div id="hide">
  <div class="copyright tc pdlayout sn-txt-muted">
   <ul >
    <li >商品金额:
     ￥<span id="userPayAllPrice" class="sn-txt-positive">${product.money }</span>
    </li>
   </ul>
  </div>

  <c:if test="${requestScope.status==1 }">
   <div class="copyright tc pdlayout sn-txt-muted">
    <ul >
     <li >
      <input onclick="ordersSave()" type="image" style="width: 60%" src="../images/jiesuan.png">
     </li>
    </ul>
   </div>
  </c:if>
</div>

 </body>
</html>
<script>
 $("#inp1").click(function () {
     $("#ul").hide();
     $("#hide").hide();
     $("#hide").after("<div class='tc'>购物车为空</div>")
 });
</script>