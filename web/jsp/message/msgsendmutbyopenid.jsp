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

	$("span[type=openid]").html($("#i_openid").val());
	
	$("#btn_sub").bind('click',function(){
		var i_token = $("#i_token").val();
		var i_openid = $("#i_openid").val();
		var i_key = $("#i_key").val();
		var url = $("#i_url").val();
		var i_content = $("#i_content").val();
		$.ajax({
		   type: "GET",
		   url: "./messageSendMutServlet",
		   data: "i_token="+encodeURIComponent(i_token) + 
		   		 "&i_openid="+encodeURIComponent(i_openid) +
		   		 "&i_key="+encodeURIComponent(i_key) +
		   		 "&i_url="+encodeURIComponent(url)+
		   		 "&i_content="+encodeURIComponent(i_content, "utf-8"),
		   dataType:"text",
		   success: function(msg){
		     $("#out_body").val(msg);
		   }
		});
	});
	
	$("#i_content").bind("focus",function(){
		$("span[type=openid]").html($("#i_openid").val());
	});
	
	$("#i_openid").bind("blur",function(){
		$("span[type=openid]").html($("#i_openid").val());
	});
	
	
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
		<br/>
		<hr/>
		</div>
		<table>
		<tr>
			<td>开放平台URL</td>
			<td>
				<jsp:include page="../sub/servers.jsp"></jsp:include>
			</td>
		</tr>
		<tr>
			<td>openID</td>
			<td><jsp:include page="../sub/openids.jsp"></jsp:include></input></td>
		</tr>
		<tr>
			<td>access token</td>
			<td><input type="text" id="i_token" value="oX9Ie4SHV5D5Ifp8ZwCJ853li4cwf06nBB5r1rxl70XBD5Xn4pO7CHVJrqgdm6UpEikdpWHb9GdrYNS771236aks2g9G28c7N0piu+rNyyJ3OcHFz+Z6J8J/oYN0g3o="></input></td>
		</tr>
		<tr>
			<td>session key</td>
			<td><input type="text" id="i_key" value="KrwfHErWSdxuqEq/Kt9kDhZDWFK2iipP"></input></td>
		</tr>
		
		<tr>
			<td>消息报文</td>
			<td><textarea id="i_content" rows="5" cols="90"></textarea></td>
		</tr>
		</table>
		<button id="btn_sub">发送信息</button>
	
	<hr/>
	<div>群发文本：
	 [   
    	{
    	"touser":"KP44huX0rMdsrFHWfP1yqHDSvkQWLxka",    
        "text":{
        	"title":"good",      
        	"content":"hello world1"   
        	 },    
        "msgtype":"text",
        "msgid":"001"
    	},
    	{
    	"touser":"KP44huX0rMdsrFHWfP1yqLL9iMbTcYbo",    
        "text":{  
        	"title":"good2",    
        	"content":"hello world2"   
        	 },    
        "msgtype":"text",
        "msgid":"002"
    	}
    ]
	</div>
	<br/>
	文本-图文混发：
	[
{
    	"touser":"KP44huX0rMdsrFHWfP1yqHDSvkQWLxka",    
        "text":{ 
        	"title":"good",     
        	"content":"hello world1"   
        	 },    
        "msgtype":"text",
        "msgid":"001"
    	},
{'touser':'KP44huX0rMdsrFHWfP1yqLL9iMbTcYbo','msgid':'102','news':{'articles':[{'title':'title3','description':'des3','url':'URL3','picurl':'PIC_URL3'},{'title':'title4','description':'des4','url':'URL4','picurl':'PIC_URL4'}]},'msgtype':'news','isAllowSend':'0'},{'touser':'KP44huX0rMdKzClFAyif9IaC/Gjs2t3o','msgid':'103','news':{'articles':[{'title':'title3','description':'des3','url':'URL3','picurl':'PIC_URL3'},{'title':'title4','description':'des4','url':'URL4','picurl':'PIC_URL4'}]},'msgtype':'news','isAllowSend':'0'}]
	<br/>
	<div>群发图文：[
{
'touser':'KP44huX0rMdsrFHWfP1yqHDSvkQWLxka',
'msgid':'101',
'news':
{'articles':
[{'title':'title1','description':'des1','url':'URL1','picurl':'PIC_URL1'},{'title':'title2','description':'des2','url':'URL2','picurl':'PIC_URL2'}]},'msgtype':'news','isAllowSend':'0'},
{'touser':'KP44huX0rMdsrFHWfP1yqLL9iMbTcYbo','msgid':'102','news':{'articles':[{'title':'title3','description':'des3','url':'URL3','picurl':'PIC_URL3'},{'title':'title4','description':'des4','url':'URL4','picurl':'PIC_URL4'}]},'msgtype':'news','isAllowSend':'0'},{'touser':'KP44huX0rMdKzClFAyif9IaC/Gjs2t3o','msgid':'103','news':{'articles':[{'title':'title3','description':'des3','url':'URL3','picurl':'PIC_URL3'},{'title':'title4','description':'des4','url':'URL4','picurl':'PIC_URL4'}]},'msgtype':'news','isAllowSend':'0'}]</div>
	<hr/>
	<table>
	<tr>
		<td>处理结果</td>
		<td><input type="text" id="out_body"></text></td>
	</tr>
	</table>