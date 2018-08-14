<%@ page contentType="text/html; charset=UTF-8" %>

<script  type="text/javascript"> 
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
		   url: "./messageMessengerServlet",
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
	
	
	$("#i_openid").bind("blur",function(){
		$("span[type=openid]").html($("#i_openid").val());
	});
	
	function displaytypeChange(){
		//alert("displaytype change");
		 var displaytype=$("#displaytype").val();
		if(displaytype=="title"){
			$("#displaytypeText").css('display','none');
			$("#displaytypeTitle").css('display','block');
			$("#displaytypeAccount").css('display','none');
		}else if(displaytype=="text"){
			$("#displaytypeText").css('display','block');
			$("#displaytypeTitle").css('display','none');
			$("#displaytypeAccount").css('display','none');
		}else if(displaytype=="account"){
			$("#displaytypeText").css('display','none');
			$("#displaytypeTitle").css('display','none');
			$("#displaytypeAccount").css('display','block');
		} 
		
	};  
	function crateMessage(){
		var message={};
		message.msgtype=$("#msgtype").val();
		message.touser=$("#i_openid").val();
		message.busstype=$("#busstype").val();
		message.busssubtype=$("#busssubtype").val();
		message.title=$("#title").val();
		message.subtitle=$("#subtitle").val();
		message.icon=$("#icon").val();	
		message.displaytype=$("#displaytype").val();
		message.isAllowSend=$("#isAllowSend").val();
		message.remark=$("#remark").val();
		//alert("xxxx="+message);
		var json=JSON.stringify(message);
		//alert("yyyy="+json);
		
		if(message.displaytype=="text"){
			message.content={};
			//message.content=$("#displaytypeText :input").val();
			message.content.msgcontent=$("#text_msgcontent").val();
		}else if(message.displaytype=="title"){
			message.content={};
			//message.content=$("#displaytypeTitle :input").val();
			//message.content.msgcontent=$("#displaytypeTitle :input").val();
			message.content.msgcontent=$("#title_msgcontent").val();
			message.content.title=$("#title_title").val();
		}else if(message.displaytype=="account"){
			var accountJson={};
			accountJson.amount=$("#account_amount").val();
			accountJson.msgcontent=$("#account_msgcontent").val();
			var contents=new Array();
			for(var i=1;i<=3;i++){
				//var temp="content"+i;
				var temp={};
				//alert("temp="+temp);
				var no="no"+i;
				var key="key"+i;
				var value="value"+i;
				temp.no=$("#"+no).val();
				temp.key=$("#"+key).val();
				temp.value=$("#"+value).val();
				var tempjson=JSON.stringify(temp);
				//alert("tempjson="+tempjson);				
				contents[i-1]=temp;
			}
			//alert("xxxx2="+contents);
			accountJson.details=contents;
			message.content=accountJson;
			//alert("bbbbb="+JSON.stringify(message));
		}
		//buttons 处理
		var buttons=new Array();
		for(var i=0;i<4;i++){
			//var temp="content"+i;
			var temp={};
			//alert("temp="+temp);
			var desc="desc"+i;
			var href="href"+i;
			var isFunc="isFunc"+i;
			var buttonNo="buttonNo"+i;
			temp.desc=$("#"+desc).val();
			temp.href=$("#"+href).val();
			temp.isFunc=$("#"+isFunc).val();
			temp.buttonNo=$("#"+buttonNo).val();
			var tempjson=JSON.stringify(temp);
			//alert("tempjson="+tempjson);				
			buttons[i]=temp;
		}
		//alert("xxxx2="+buttons);
		message.buttons=buttons;
		//alert("bbbbb="+JSON.stringify(message));
		var messageTemp=JSON.stringify(message);
		//alert("yyyy="+messageTemp);
		$("#i_content").text(messageTemp);
	};
	
	
	
	
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
			<td>消息类型</td>
			<td><select id="msgtype" onchange="changeType(this.value);" >
				<option value="generalinfo" selected="selected">通用信使类消息</option>
			</select> </td>
		</tr>
		
		<tr class="msg_type type_generalinfo">	
			<td>信使大类</td>
			<td><input type="text" class="keyVals_generalinfo" id="busstype" value="accumulationFund">公积金: accumulationFund</input></td>
		</tr>
		<tr class="msg_type type_generalinfo">
			<td>信使小类</td>
			<td><input type="text" class="keyVals_generalinfo" id="busssubtype" value="inAccountNotice">入账提醒: inAccountNotice</input></td>
		</tr>
		
		
		<tr class="msg_type type_generalinfo">
			<td>是否允许转发(isAllowSend)</td>
			<td><select id="isAllowSend" >
				<option value="0" >不允许转发</option>
				<option value="1" >不控制</option>
			</select> </td>
		</tr>
		<tr class="msg_type type_generalinfo">
			<td>标题（title）</td>
			<td><input type="text"  class="keyVals_generalinfo" id="title" value="中国工商银行公积金"></input></td>
		</tr>
		<tr class="msg_type type_generalinfo">
			<td>子标题(subtitle)</td>
			<td><input type="text" class="keyVals_generalinfo"  id="subtitle" value="公积金详情"></input></td>
		</tr>
		<tr class="msg_type type_generalinfo">
			<td>图标(icon)</td>
			<td><input type="text" class="keyVals_generalinfo"  id="icon" value="https://"></input></td>
		</tr>
		<tr class="msg_type type_generalinfo">
			<td>备注(remark)</td>
			<td><input type="text" class="keyVals_generalinfo"  id="remark" value=""></input></td>
		</tr>
		<tr class="msg_type type_generalinfo">
				<td>模板(displaytype)</td>
				<td><select id="displaytype" onChange="displaytypeChange()">
						<option value="title" >标题</option>
						<option value="text" >文本</option>
						<option value="account" >金额</option>
					</select> 
				</td>
		</tr>
		</table>
		</br>
		</hr>
				<!--  displaytype 为title -->
				<div style="display:block" id="displaytypeTitle" >
					<table>
					<tr >
						<td>模板标题内容标题</td> 
						<td><input type="text" id="title_title" value="我是模板标题内容标题:您有一条新信息"></input></td>
					</tr>
					<tr >
						<td>模板标题内容正文</td> 
						<td><input type="text" id="title_msgcontent" value="我是模板标题内容正文:您尾号3220卡11月30日10:46ATM支出(ATM转帐)600元，手续费6元，余额84,642.71元，可用余额84,692.71元。"></input></td>
					</tr>
					</table>
				</div>
				<!--  displaytype 为text -->
				<div style="display:none" id="displaytypeText" >
					<table>
					<tr > 
						<td>模板文本内容</td>
						<td><input type="text" id="text_msgcontent" value="我是模板文本内容"></input></td>
					</tr>
					
					</table>
				</div>
				<!--  displaytype 为account -->
				<div id="displaytypeAccount"   style="display:none" >
				 	<table>
				 	<tr >
						<td>amount:</td>
						<td><input type="text"  id="account_amount" value="10000元" ></input></td>
						</tr>
					<tr>
					<tr >
						<td>msgcontent:</td>
						<td><input type="text"  id="account_msgcontent" value="您尾号3220卡11月30日10:46ATM收入600元，余额84,642.71元，可用余额84,692.71元。" ></input></td>
						</tr>
					<tr>
					
					<tr >
						<td>no1:</td>
						<td><input type="text"  id="no1" value="1" ></input></td>
						</tr>
					<tr>	
						<td>key1:</td>
						<td><input type="text" id="key1"  value="key1" ></input></td>
						</tr>
					<tr>
						<td>value1:</td>
						<td><input type="text" id="value1"  value="value1" ></input></td>
						</tr> 
					
					
					<tr>
						<td>no2:</td>
						<td><input type="text"  id="no2" value="2"></input></td>
						</tr>
					<tr>
						<td>key2:</td>
						<td><input type="text" id="key2"  value="key2"></input></td>
						</tr>
					<tr>
						<td>value2:</td>
						<td><input type="text" id="value2"  value="value2"></input></td>
						</tr>
					
					<tr>
						<td>no3:</td>
						<td><input type="text"  id="no3" value="3"></input></td>
						</tr>
					<tr>
						<td>key3:</td>
						<td><input type="text" id="key3"  value="key3"></input></td>
						</tr>
					<tr>
						<td>value3:</td>
						<td><input type="text" id="value3"  value="value3"></input></td>
					</tr>
					</table>
				</div>
	 
		 	<div>
		 		<table>
		 			<tr>
						<td>按钮(buttons),最对可以配置4个</td>
					</tr> 
					<tr >
						<td>desc0</td>
						<td><input type="text"  id="desc0" value="desc0" ></input></td>
						</tr>
					<tr>	
						<td>href0:</td>
						<td><input type="text" id="href0"  value="28" ></input></td>
						</tr>
					<tr>
						<td>isFunc0:(1：原子功能，0：普通链接)</td>
						<td><input type="text" id="isFunc0"  value="1" ></input></td>
						</tr> 
					<tr>
						<td>buttonNo0:</td>
						<td><input type="text" id="buttonNo0"  value="1" ></input></td>
						</tr> 
					
					<tr >
						<td>desc1</td>
						<td><input type="text"  id="desc1" value="desc1" ></input></td>
						</tr>
					<tr>	
						<td>href1:</td>
						<td><input type="text" id="href1"  value="https://www.icbc.com.cn" ></input></td>
						</tr>
					<tr>
						<td>isFunc1:(1：原子功能，0：普通链接)</td>
						<td><input type="text" id="isFunc1"  value="0" ></input></td>
						</tr> 
					<tr>
						<td>buttonNo1:</td>
						<td><input type="text" id="buttonNo1"  value="2" ></input></td>
						</tr> 	
						
					<tr >
						<td>desc2</td>
						<td><input type="text"  id="desc2" value="desc2" ></input></td>
						</tr>
					<tr>	
						<td>href2:</td>
						<td><input type="text" id="href2"  value="26" ></input></td>
						</tr>
					<tr>
						<td>isFunc2:(1：原子功能，0：普通链接)</td>
						<td><input type="text" id="isFunc2"  value="1" ></input></td>
						</tr>
					<tr>
						<td>buttonNo2:</td>
						<td><input type="text" id="buttonNo2"  value="3" ></input></td>
						</tr>  
					<tr >
						<td>desc3</td>
						<td><input type="text"  id="desc3" value="desc3" ></input></td>
						</tr>
					<tr>	
						<td>href3:</td>
						<td><input type="text" id="href3"  value="https://www.icbc.com.cn" ></input></td>
						</tr>
					<tr>
						<td>isFunc3:(1：原子功能，0：普通链接)</td>
						<td><input type="text" id="isFunc3"  value="0" ></input></td>
						</tr> 
					<tr>
						<td>buttonNo3:</td>
						<td><input type="text" id="buttonNo3"  value="4" ></input></td>
						</tr> 
				</table>
			</div>
			</hr>
			<button onclick="crateMessage()">生成报文 </button>
		<table>
		<tr>
			<td>消息报文</td>
			<td><textarea id="i_content" rows="5" cols="90"></textarea></td>
		</tr>
		</table>
		<button id="btn_sub">发送信息</button>
	
	<hr/>
	<div><span >{"touser":"</span><span type="openid"/><span>","msgtype":"generalinfo","generalinfo":{"articles": [{"title":"title1","description":"des1","url":"URL1","picurl":"PIC_URL1"},{"title":"title2","description":"des2","url":"URL2","picurl":"PIC_URL2"}]}}</span></div>
	<hr/>
	<table>
	<tr>
		<td>处理结果</td>
		<td><input type="text" id="out_body"></text></td>
	</tr>
	</table>
	