<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript"> 

	$("#i_key").val($("#glb_key").val());
	$("#i_token").val($("#glb_token").val());
	$("#i_url").val($("#glb_url").val())
	
	if($("#info-switch").val()=="on") {	
		$("#information").show();
	} else {
		$("#information").hide();
	}

	/* $("span[type=openid]").html($("#i_openid").val()); */
	
	$("#btn_sub").bind('click',function(){
		var i_type=$("input[name=sendType]:checked").val();
		var i_token = $("#i_token").val();
		/* var i_openid = $("#i_openid").val(); */
		var i_key = $("#i_key").val();
		var url = $("#i_url").val();
		var i_content = $("#i_content").val();
		$.ajax({
		   type: "POST",
		   url: "./messageSendServlet",
		   data: "i_token="+encodeURIComponent(i_token) + 
		   		/*  "&i_openid="+encodeURIComponent(i_openid) + */
		   		 "&i_key="+encodeURIComponent(i_key) +
		   		 "&i_url="+encodeURIComponent(url)+
		   		 "&i_content="+encodeURIComponent(i_content, "utf-8")+
		   		 "&i_type="+i_type+
		   		 "&sendAll=true",
		   dataType:"text",
		   success: function(msg){
		     $("#out_body").val(msg);
		   }
		});
	});
	
	/* $("#i_content").bind("focus",function(){
		$("span[type=openid]").html($("#i_openid").val());
	});
	
	$("#i_openid").bind("blur",function(){
		$("span[type=openid]").html($("#i_openid").val());
	}); */
	
	$("#is_to_all").bind("blur",function(){
		$("span[type=is_to_all]").html($("#is_to_all").val());
	});
	$("#groupid").bind("blur",function(){
		$("span[type=groupid]").html($("#groupid").val());
	});
	
	
	$(".msg_type").bind("change",function(){
		showMSG();
	});
	
	function showMSG(){
		//var _openid=$("#i_openid").val();
		var i_type=$("input[name=sendType]:checked").val();
		var msg="{"
		if(i_type=='0'){//按组
			var _toall=$("#is_to_all").val();
			var _groupid=$("#groupid").val();
			msg+="\"filter\":{\"is_to_all\":\""+_toall+"\",\"group_id\":\""+_groupid+"\"},"
			
		}else{
			var _openids=$("#openids").val();
			var _arr=_openids.trim().split(',');
			msg+="\"touser\":[\""+_arr.join("\",\"")+"\"],";
		}
		var _type=$("#msgtype").val();
		msg+="\"msgtype\":\""+_type+"\",\""+_type+"\":{";
		var arr=$(".keyVals_"+_type);
		
	 	if(_type=='news'){
			msg+="\"articles\":[{";
		}
		for(var i=0;i<arr.length;i++){
			var obj=arr[i];
			var name=obj.name;
			var value=obj.value;
			msg+="\""+name+"\":\""+value+"\",";
		}
		msg=msg.substr(0,msg.length-1);
		if(_type=='news'){
			msg+="}]";
			var isAllowSend=$("#isAllowSend").val();
			if(isAllowSend!='-1'){
				msg+=",\"isAllowSend\":\""+isAllowSend+"\"";
			}
		}
		msg+="}}";
		$("#i_content").text(msg);
	}
	
	function changeType(type){
		$(".msg_type").css('display','none');
		$(".type_"+type).css('display','');
		showMSG();
	}
	changeType($("#msgtype").val());
	
</script>
<style type="text/css">
	td input {
		width:500px;
	}
</style>
		<div id="information">
		<div>操作说明：</div>
		<div>1、正确填写表单（开放平台URL，OPENID）</div>
		<div>2、填写消息体（页面中有模板，复制粘贴到文本框，修改字段即可）</div>
		<div>3、点击发送按钮，等待提示显示{"errcode"："0","errmsg":"success"}为成功</div>
		<div>备注：</div>
		<div>1、accesstoken和sessionkey为接入成功后，自动填充</div>
		<div>2、openid可以在左侧“其他工具-openid生成与解析”页面中生成，需要填写mpid和目标客户的userid</div>
		<div>3、openid文本框填写完后，下方消息模板会自动根据所填写openid变动，仅需修改消息体内容</div>
		<div>4、图文消息最多包含10条图文，超出10条的话只取前10条</div>
		<br/>5、普通文本的特殊字符，请自行处理
		<hr/>
		</div>
		<table>
		<tr>
			<td>开放平台URL</td>
			<td>
				<jsp:include page="../sub/servers.jsp"></jsp:include>
			</td>
		</tr>
		<%-- <tr>
			<td>openID</td>
			<td><jsp:include page="../sub/openids.jsp"></jsp:include></input></td>
		</tr> --%>
		<tr>
			<td>access token</td>
			<td><input type="text" id="i_token" value="oX9Ie4SHV5D5Ifp8ZwCJ853li4cwf06nBB5r1rxl70XBD5Xn4pO7CHVJrqgdm6UpEikdpWHb9GdrYNS771236aks2g9G28c7N0piu+rNyyJ3OcHFz+Z6J8J/oYN0g3o="></input></td>
		</tr>
		<tr>
			<td>session key</td>
			<td><input type="text" id="i_key" value="KrwfHErWSdxuqEq/Kt9kDhZDWFK2iipP"></input></td>
		</tr>
		<tr>
			<td>按组/按openid发送</td>
			<td>按组：<input type="radio" id="sendType" style="width: 100px;" checked="checked" onclick="javascript:document.getElementById('tr_sendtoall').style.display='';document.getElementById('tr_groupid').style.display='';document.getElementById('div_bygroupid').style.display='';document.getElementById('div_byopenid').style.display='none';document.getElementById('tr_openid').style.display='none';showMSG();" name="sendType" value="0"></input>
				按openid：<input type="radio" id="sendType"  style="width: 100px;" onclick="javascript:document.getElementById('tr_sendtoall').style.display='none';document.getElementById('tr_groupid').style.display='none';document.getElementById('div_bygroupid').style.display='none';document.getElementById('div_byopenid').style.display='';document.getElementById('tr_openid').style.display='';showMSG();" name="sendType" value="1" width="100px"></input>
			</td>
		</tr>
		<tr id="tr_sendtoall">
			<td>is_to_all(true/false)</td>
			<td><input type="text" id="is_to_all" value="true" onchange="if(this.value=='true'){document.getElementById('groupid').value='';};showMSG();" ></input></td>
		</tr>
		<tr id="tr_groupid">
			<td>groupid</td>
			<td><input type="text" id="groupid" value="" onchange="if(this.value!=''){document.getElementById('is_to_all').value='false';};showMSG();" ></input></td>
		</tr>
		
		<tr id="tr_openid" style="display:none">
			<td>openids(openid1,openid2,openid3格式)</td>
			<td><input type="text" id="openids" value="" onchange="showMSG();" ></input></td>
		</tr>
		
		<tr>
			<td>消息类型</td>
			<td><select id="msgtype" onchange="changeType(this.value);" >
				<option value="text" selected="selected">文本消息</option>
				<option value="news">图文消息</option>
				<option value="notice">通知消息</option>
			</select> </td>
		</tr>
		
		<tr class="msg_type type_text">	
			<td>文本消息标题(可为空)</td>
			<td><input type="text" class="keyVals_text" name="title"  value=""></input></td>
		</tr>
		<tr class="msg_type type_text">
			<td>文本消息内容</td>
			<td><input type="text" class="keyVals_text" name="content" value=""></input></td>
		</tr>
		
		<tr class="msg_type type_news">
			<td>是否允许转发(isAllowSend)</td>
			<td><select id="isAllowSend" onchange="showMSG();" >
				<option value="-1" >请选择</option>
				<option value="0" >不允许转发</option>
				<option value="1" >不控制</option>
				<option value="2">只允许行内员工转发</option>
			</select> </td>
		</tr>
		<tr class="msg_type type_news">
			<td>图文消息标题（title）</td>
			<td><input type="text"  class="keyVals_news" name="title" value=""></input></td>
		</tr>
		<tr class="msg_type type_news">
			<td>图文消息描述（description）</td>
			<td><input type="text" class="keyVals_news"  name="description" value=""></input></td>
		</tr>
		<tr class="msg_type type_news">
			<td>图文消息链接（url）</td>
			<td><input type="text" class="keyVals_news"  name="url" value=""></input></td>
		</tr>
		<tr class="msg_type type_news">
			<td>图文消息图片链接(picurl)</td>
			<td><input type="text" class="keyVals_news"  name="picurl" value=""></input></td>
		</tr>
		<tr class="msg_type type_notice">
			<td>通知消息标题(title)</td>
			<td><input type="text" class="keyVals_notice"  name="title" value=""></input></td>
		</tr>
		<tr class="msg_type type_notice">
			<td>通知消息来源(source)</td>
			<td><input type="text"  class="keyVals_notice" name="source" value=""></input></td>
		</tr>
		<tr class="msg_type type_notice">
			<td>通知消息备注</td>
			<td><input type="text"  class="keyVals_notice" name="remark" value=""></input></td>
		</tr>
		<tr>
			<td>消息报文</td>
			<td><textarea id="i_content" rows="5" cols="90"></textarea></td>
		</tr>
		</table>
		<button id="btn_sub">发送信息</button>
	
	<hr/>
	
	<div id="div_bygroupid">{   "filter":{      "is_to_all":"<span type="is_to_all"/><span>",      "group_id":"<span type="groupid"></span>"   },   "text":{"content":"Hello World"},    "msgtype":"text"
}
	 </div>
	 
	 <div id="div_byopenid" style="display:none;">{   "touser":[   "YWW4ans6MLBsrFHWfP1yqF4ckvOPynGq","YWW4ans6MLBMkAKH6ihvNUzOLCgC51pU","KP44huX0rMdtndxsHZol/1GtcIqUZhtX"
],   "text":{"content":"Hello World"},    "msgtype":"text"}
	 </div>
	
<hr/>
	<table>
	<tr>
		<td>处理结果</td>
		<td><input type="text" id="out_body"></text></td>
	</tr>
	</table>