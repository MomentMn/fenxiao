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
  <title>结算信息</title>
  <link rel="stylesheet" href="../css/cart2.css" />
  <link href="../city/css/framework7.ios.min.css" rel="stylesheet">
  <link href="../city/css/framework7.ios.colors.min.css" rel="stylesheet">
  <link href="../city/css/style.css" rel="stylesheet">
  <script type="text/javascript" src="../js/zepto.min.js"></script>
   <style>
   #img3 img{
    width: 8%;
    height: 8%;
    margin-left: 17%;
   }
  </style>
 </head>
 <body>
 <form id="form1" method="post">
  <section class="ww">
   <div class="loagMask" id="loading" style="-webkit-transform-origin: 0px 0px 0px; opacity: 1; -webkit-transform: scale(1, 1); display: none;">
    <div class="sn-mask-loading fixedLoading"></div>
   </div>
   <section class="cart-main">
    <div class="sn-nav sn-block">
     <div id="back" class="sn-nav-back">
      <a class="sn-iconbtn" href="javascript:history.go(-1);">返回</a>
     </div>
     <div class="sn-nav-title of">
      订单编辑
     </div>
     <div class="sn-nav-right tr pr"><a href="index.jsp">...</a></div>
    </div>
    <input type="hidden" name="pid" value="${product.id}">
    <input type="hidden" name="num" value="${num}">
    <input type="hidden" name="code" id="inp" value="">
    <div id="img3" style="background-color:#f2f2f2;background: url(../images/editLight.png) no-repeat center;height:45px;background-size: 100%;width:100%;"></div>
    <div class="sn-block ">
     <ul class="sn-list-input sn-txt-muted">
      <li><label style="margin-right: 2.6rem;">收货人</label>
         	<input type="text" placeholder="请输入收货人姓名" name="name" value="" id="name" style="border: none;outline: none;"><span id="span1" style="color: red"></span>
       </li>
      <div class=" wbox of"></div>
     </ul>
    </div>
    <div class="sn-block">
     <ul class="sn-list-input sn-txt-muted">
      <li><label style="margin-right: 2.1rem;">手机号码</label>
       <input type="text" placeholder="请输入收货人手机号码" name="tel" id="tel" style="border: none;outline: none;"><span id="span2" style="color: red"></span>
       </li>
      <div class=" wbox of"></div>
     </ul>
    </div>

    <div class="sn-block">
     <ul class="sn-list-input sn-txt-muted">
      <li><label style="margin-right: 1.1rem;">确认手机号码</label>
       <input type="text" placeholder="请再次输入手机号码" name="oktel" id="oktel" style="border: none;outline: none;"><span id="span3" style="color: red"></span>
      </li>
      <div class=" wbox of"></div>
     </ul>
    </div>

    <div class="sn-block">
     <ul class="sn-list-input sn-txt-muted">
      <li><label>所在区域</label>
       <div class="wbox-flex tr ">
       <input placeholder="请选择城市地址>" style="border: none;outline: none;" readonly class="sn-txt-muted" id="location" name="location"><span id="span4" style="color: red"></span>
       </div>
      </li>
      <div class="wbox of"></div>
     </ul>
    </div>

    <div class="sn-block">
     <ul class="sn-list-input sn-txt-muted">
      <li><label>详细地址</label>
        <br>
        <textarea placeholder="请输入收货人的详细地址(请勿重复填写省市区)" name="content" id="content" style="border: none;outline: none;width: 550px;font-family: Arial,Helvetica,STHeiTi,sans-serif;margin-top: 10px"></textarea><span id="span5" style="color: red"></span>
      </li>
     </ul>
    </div>

    <div id="snProdListDivId">
     <div class="cart-list mt30">

      <div class="cart-title wbox of"> 
       <div class="sn-txt-muted">
        	订单商品
       </div> 
       <div class="wbox-flex tr ">
         	 订单修改
       </div> 
      </div>

      <div class="cart-more-list pr "> 
       <ul class="sn-list-common sn-block"> 
        <li>

         <div class="pro-img" style="width: 2.4rem;height: 2.4rem;margin-top: 10px">
          <a href="javascript:void(0)">
           <img src="../${product.picture }"/> </a>
         </div>

         <div class="wbox-flex tr">
          <a class="pro-list">
           <div class="wbox ">
            &nbsp;&nbsp;&nbsp;${product.title }
            <div class="pro-info wbox-flex">

             <div class="snPrice">
              	￥${product.money }
             </div>
             <div style="padding: 25px"></div>
             <div  class="snPrice">
             <span class="cart-number">x ${num}</span>
             </div>
            </div> 
           </div> </a> 
         </div> </li> 
       </ul> 
      </div>

      <div class="sn-block">
       <ul class="sn-list-input sn-txt-muted">
        <li><label>商品金额:</label>
         <div class="wbox-flex tr ">
          ￥${product.money*num }
         </div>
        </li>
       </ul>
      </div>

      <div class="cart-list mt30">
       <div class="sn-block">
        <ul class="sn-list-input sn-txt-muted">
         <li><label>运费金额:</label>
          <div class="wbox-flex tr ">
           ￥ 0
          </div>
         </li>
        </ul>
       </div>
      </div>

     </div>
    </div>

    <div class="cart-list mt30">
     <div class="sn-block">
      <ul class="sn-list-input sn-txt-muted">
       <li><label>应付总额:</label>
        <div class="wbox-flex tr ">
         ￥${product.money*num }
        </div>
       </li>
      </ul>
     </div>
    </div>

    <div class="cart-list mt30">
     <div class="sn-block">
      <ul class="sn-list-input sn-txt-muted">
       <li><label>小妃提醒:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;港澳台及海外不能发货,公司随机发货不能指定快递,
        少数偏远地区以及快递不能到的镇.村.组的快件需自提,
        公司不承担额外的快递费用,快递物流时效为一个月,
        请在发货后7天内及时查询物流信息,新老包装随机发货,
        不能指定包装,如有问题请联系客服.</label>
       </li>
      </ul>
     </div>
    </div>


    <div class="cart-list mt30">
     <div class="sn-block">
     <ul class="sn-list-input sn-txt-muted">
      <li><label>备注:</label>
       <div>
        <textarea style="border: none;outline: none;width: 600px" name="summary" id="summary"></textarea>
       </div>
      </li>
     </ul>
    </div>
    </div>

   </section>
  </section>

  <div class="copyright tc pdlayout sn-txt-muted">
   <ul >
    <li >
     <input id="commitimg" style="width: 100%" type="image" src="../images/commit.png">
    </li>
   </ul>
  </div>
 </form>
 </body>
</html>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script src="../city/js/framework7.min.js"></script>
<script src="../city/js/cityPicker.js"></script>

