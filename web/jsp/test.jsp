<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD Xhtml 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>TEST</title>
<link href="${pageContext.request.contextPath}/css/zzsc.css" type="text/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap.css" type="text/css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		
		$.ajax({
			type:"GET",
			url:"/CustRel/getPropertiesServlet",
			dataType:"json",
			success:function(data,status){
				$("#tps_priv_key").val(data.thirdparty_priv_key);
				$("#pltf_pub_key").val(data.platform_pub_key);
				$("#tps_pub_key").val(data.thirdparty_pub_key);
			}
		});
			
		
		$(".subNav").click(
				function() {
					$(this).toggleClass("currentDd").siblings(".subNav")
							.removeClass("currentDd")
					$(this).toggleClass("currentDt").siblings(".subNav")
							.removeClass("currentDt")

					// 修改数字控制速度， slideUp(500)控制卷起速度
					$(this).next(".navContent").slideToggle(500).siblings(
							".navContent").slideUp(500);
				});

		$("a").bind("click", function() {
			var url = this.getAttribute("url");
			$("#subcontent").load(url, function(r, s, x) {
				$("#subcontent").html(r);
			})
		});

	})
	
	$("#info-switch").bind("change",function(){
		alert($("#info-switch"),val());
		if($("#info-switch").val()=="on") {	
			$("#information").show();
		} else {
			$("#information").hide();
		}
	});
</script>
</head>
<body>
	<input type="hidden" value="${jsapi}" id="ticket" name="ticket">

	<!-- 代码 开始 -->
	<table style="display:none">
		<tr>
			<td>glb_token</td>
			<td><input type="text" id="glb_token"></input></td>
		</tr>
		<tr>
			<td>glb_key</td>
			<td><input type="text" id="glb_key"></input></td>
		</tr>
		<tr>
			<td>glb_url</td>
			<td><input type="text" id="glb_url"></input></td>
		</tr>
		<tr>
			<td>tps_priv_key</td>
			<td><input type="text" id="tps_priv_key"></input></td>
		</tr>
		<tr>
			<td>tps_pub_key</td>
			<td><input type="text" id="tps_pub_key"></input></td>
		</tr>
		<tr>
			<td>pltf_pub_key</td>
			<td><input type="text" id="pltf_pub_key"></input></td>
		</tr>
	</table>
	<div style="float: right;margin-top: 100px;padding-right: 50px;">
		<label>提示信息开关</label>
		<select id = "info-switch">
			<option value = "off">关</option>
		 	<option value = "on">开</option>
		</select>
	</div>
	<h1 style="text-align:center">第三方模拟器1.0</h1>
	<h4  style="color:red;text-align:center">请认真阅读接口文档及本项目的readme.txt</h4>
	<div class="navContainer">
		<div class="row">
			<div class="col-xs-3 subNavBox">
				<div class="subNav currentDd currentDt">新手接入</div>
				<ul class="navContent " style="display: block">
					<li><a url="${pageContext.request.contextPath}/test/token">获取ACCESS TOKEN</a></li>
				</ul>
				<div class="subNav">用户管理</div>
				<ul class="navContent">
					<li><a url="pages/user/getFan.jsp">获取关注列表</a></li>
					<li><a url="pages/user/getInfo.jsp">获取用户信息</a></li>
					<!-- <li><a url="pages/user/getInfoForICBC.jsp">获取用户行内信息</a></li> -->
					<li><a url="pages/user/batchGetInfo.jsp">批量获取用户信息</a></li>
					<li><a url="pages/user/oauth2.jsp">网页授权</a></li><!--  -->
					<li><a url="pages/user/updateremark.jsp">设置用户备注名</a></li>
				</ul>
				
				<div class="subNav">消息发送</div>
				<ul class="navContent">
					<li><a url="${pageContext.request.contextPath}/test/msgsend4openid">消息发送（openid）</a></li>
					<li><a url="pages/message/msgsendall.jsp">消息群发（openid）</a></li>
				</ul>
				<div class="subNav">自定义菜单</div>
				<ul class="navContent">
					<li><a url="pages/menu/base35menu.jsp">自定义菜单基本操作</a></li>
				</ul>
				<div class="subNav">jsapi ticket</div>
				<ul class="navContent">
					<li><a url="pages/ticket/clientTicketForReal.html">demo(请在手机内单独访问该页面http://82.200.61.11:8088/ThirdPartySimulatorProj/pages/ticket/clientTicketForReal.html，并修改页面的appid和ticket)</a></li>
				</ul>
				<div class="subNav">群组相关</div>
				<ul class="navContent">
					<li><a url="pages/groups/groupInfo.jsp">群组功能</a></li>
				
				</ul>
				<div class="subNav">商户订单管理</div>
				<ul class="navContent">
					<li><a url="pages/order/orderInfo.jsp">商户订单管理</a></li>
				<div class="subNav">事件二维码相关</div>
				<ul class="navContent">
					<li><a url="pages/qrcode/QRcodeInfo.jsp">二维码功能</a></li>
				</ul>
				
			</div>
			<div id="subcontent" class='col-xs-9 content'>
			</div>
		</div>
	</div>
	<!-- 代码 结束 -->
</body>
</html>