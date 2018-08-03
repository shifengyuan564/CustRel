function showMessage(res){
  alert("xiaolei v5=" +res);	
}
function setLatitude(latituede){
	$("#latituede").val(latituede);
}
function setLongitude(longitude){
	$("#longitude").val(longitude);
}
function parseToObject(result){
	return eval('(' +result +')');
}
function doCheckjsAPI(result){
	alert("doCheckjsAPI" +result);
	var res=JSON.stringify(result);
	alert("res=" + res);
	return $("#checkJsApiResult").val(res);
}
function doUploadImageAfter(url){
	alert("必须把服务器的图片，自己下载到自己的服务器本地" +url);
}


function showRecording(isRecording){
	if(isRecording){
		document.title="demo</br>正在录音……";
	}else{
		document.title="demo";
	}
	
}

function loadJSPAY(){
	 var appid=$("#jspay_appid").val();
	 var ticket=$("#jspay_ticket").val();
	 var totalFee=$("#jspay_totalFee").val();
	 var orderid=$("#jspay_orderid").val();
	 var channelAppNo=$("#jspay_channelAppNo").val();
	 var remark=$("#jspay_remark").val();
	 var payobj={};
	 payobj.appid=appid;
	 payobj.totalFee=totalFee;
	 payobj.orderid=orderid;
	 payobj.channelAppNo=channelAppNo;
	 payobj.remark=remark;
	 $.ajax({
			type:"GET",
			url:"../../servlet/PayTicketServlet",
			data: "url="+encodeURIComponent(targetUrl) +
				"&jspayticket=" +encodeURIComponent(ticket)+
				"&appid=" +encodeURIComponent(appid)+
				"&totalFee="+encodeURIComponent(totalFee)+
				"&orderid="+encodeURIComponent(orderid),
			dataType:"json",
			async:false,
			success:function(data,status){
				var config =data;
				payobj.signature= config.signature;
				payobj.nonceStr= config.nonceStr;
				payobj.timestamp= config.timestamp;
				
			}
		});
	 
	 return payobj;
}

rel.ready(function(){
	alert("ready");
	 document.querySelector("#checkJsApi").onclick = function () {
		 alert("checkJsApi onclick");
		 rel.checkJsApi({
			 jsApiList:"checkJsApi,getLocation,chooseImage,uploadImage,user/get",
			 success:function(result){
				 var res = result.retMsg.checkResult;   
				 doCheckjsAPI(res);
			}
		 });
	  };
	document.querySelector("#getlocation").onclick=function(){
			  alert("demo.js getlocation  begin10.18");
			 rel.getLocation({
				 success:function(result){
					 showMessage(result);
				     showMessage(result.retMsg);
				     showMessage(result.retMsg.latitude);
					 var latitude = result.retMsg.latitude;   
					 var longitude = result.retMsg.longitude;
					 var locationName=result.retMsg.locationName;
					 setLatitude(latitude);
					 setLongitude(longitude);
					 $("#label").val(locationName);
				     showMessage(latitude);
				     showMessage(longitude);
					}
				}); 
	 };
	 
	 document.querySelector("#chooseimage").onclick=function(){
		 rel.chooseImage({
				success:function(res){
					 var localIds = res.retMsg.localIds;
					 $("#chooseimageId").val(localIds);
				},
				failed:function(res){
					 $("#chooseimageId").val(res.errMsg);
				},
				complete:function(res){
					alert("chooseimage complete " + res.errMsg);
				},
				cancel:function(res){
					alert("chooseimage  cancel " + res.errMsg);
				}
			}); 
 };
 
	 document.querySelector("#uploadimage").onclick=function(){
		 if ($("#chooseimageId").val() == '') {
		      alert('请先使用 chooseimageId 接口选择一张图片');
		      return;
		    }
		 rel.uploadImage({
			  localIds:  $("#chooseimageId").val(), // 需要上传的图片的本地ID，由chooseImage接口获得    
				success:function(res){
					alert("正在上传uploadimage ,请稍后");
				},
				failed:function(res){
					alert("uploadimage  failed  " + res.errMsg);
				},
				complete:function(res){
					var url= res.retMsg.url;
					alert("uploadimage 图片上送 返回 url=" + url);
					if(url==""){
						alert("uploadimage complete failed");
						return;
					}
					$("#uploadimageUrl").val(url);
					$("#uploadimageUrlA").attr("href",url);
					
					
					doUploadImageAfter(url);
					alert("uploadimage complete " + res.errMsg);
				},
				cancel:function(res){
					alert("uploadimage  cancel " + res.errMsg);
				}
			}); 
	}; 
	 
	
	document.querySelector("#favoriteButtonItem").onclick=function(){
		var isAllow=$("#isAllow").val();
		rel.favoriteButtonItem({
			isAllow:isAllow,
			success:function(res){
				$("#favoriteButtonItemResult").val(res.errMsg);
				alert("favorite success status="+res.status);
			},
			failed:function(res){
				alert("favorite  failed " + res.errMsg);
			},
			complete:function(res){
				alert("favorite  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("favorite  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	
	document.querySelector("#startRecord").onclick=function(){
		rel.startRecord({
			success:function(res){
					$("#startRecordResult").val(res.errMsg);
					showRecording(true);
					alert("startRecord success status="+res.status);
			},
			failed:function(res){
				alert("startRecord  failed " + res.errMsg);
			},
			complete:function(res){
				showRecording(false);
				$("#VoiceFile").val(res.retMsg.localId);
				alert("startRecord  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("startRecord  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	document.querySelector("#stopRecord").onclick=function(){
		rel.stopRecord({
			success:function(res){
				alert("stopRecord success status="+res.status);
			},
			failed:function(res){
				alert("stopRecord  failed " + res.errMsg);
			},
			complete:function(res){
				showRecording(false);
				$("#stopRecordResult").val(res.retMsg.localId);
				$("#VoiceFile").val(res.retMsg.localId);
				alert("stopRecord  complete " + res.errMsg);
			},
			cancel:function(res){
				alert("stopRecord  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
		/*rel.onVoiceRecordEnd({
			success:function(res){
				alert("onVoiceRecordEnd success status="+res.status);
			},
			failed:function(res){
				alert("onVoiceRecordEnd  failed " + res.errMsg);
			},
			complete:function(res){
				$("#onVoiceRecordEndResult").val(res.retMsg.localId);
				$("#VoiceFile").val(res.retMsg.localId);
			},
			cancel:function(res){
				alert("onVoiceRecordEnd  cancel " + res.errMsg);
			}
			
		});*/
		
		
	
	document.querySelector("#playVoice").onclick=function(){
		alert("playVoice begin");
		var localId=$("#VoiceFile").val();
		rel.playVoice({
			localId:localId,
			success:function(res){
				$("#playVoiceResult").val(res.errMsg);
				alert("playVoice success status="+res.status);
			},
			failed:function(res){
				alert("playVoice  failed " + res.errMsg);
			},
			complete:function(res){
				alert("playVoice  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("playVoice  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	document.querySelector("#pauseVoice").onclick=function(){
		alert("pauseVoice begin");
		var localId=$("#VoiceFile").val();
		rel.pauseVoice({
			localId:localId,
			success:function(res){
				$("#pauseVoiceResult").val(res.errMsg);
				alert("pauseVoice success status="+res.status);
			},
			failed:function(res){
				alert("pauseVoice  failed " + res.errMsg);
			},
			complete:function(res){
				alert("pauseVoice  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("pauseVoice  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	document.querySelector("#stopVoice").onclick=function(){
		alert("stopVoice begin");
		var localId=$("#VoiceFile").val();
		rel.stopVoice({
			localId:localId,
			success:function(res){
				$("#stopVoiceResult").val(res.errMsg);
				alert("stopVoice success status="+res.status);
			},
			failed:function(res){
				alert("stopVoice  failed " + res.errMsg);
			},
			complete:function(res){
				alert("stopVoice  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("stopVoice  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	
	
	/*document.querySelector("#onVoicePlayEnd").onclick=function(){
		alert("onVoicePlayEnd begin");
		var localId=$("#VoiceFile").val();
		rel.onVoicePlayEnd({
			localId:localId,
			success:function(res){
				$("#onVoicePlayEndResult").val(res.errMsg);
				alert("onVoicePlayEnd success status="+res.status);
			},
			failed:function(res){
				alert("onVoicePlayEnd  failed " + res.errMsg);
			},
			complete:function(res){
				alert("onVoicePlayEnd  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("onVoicePlayEnd  cancel " + res.errMsg);
			}
			
		});
		
		
	};*/
	
	document.querySelector("#uploadVoice").onclick=function(){
		alert("uploadVoice begin");
		var localId=$("#VoiceFile").val();
		rel.uploadVoice({
			localId:localId,
			success:function(res){
				var url= res.retMsg.url;
				alert(url);
				$("#uploadVoiceResult").val(url);
				alert("uploadVoice success status="+res.status);
			},
			failed:function(res){
				alert("uploadVoice  failed " + res.errMsg);
			},
			complete:function(res){
				
				alert("uploadVoice  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("uploadVoice  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	document.querySelector("#getNetworkType").onclick=function(){
		rel.getNetworkType({
			success:function(res){
				$("#getNetworkTypeResult").val(res.retMsg.networkType);
				alert("getNetworkType success status="+res.status);
			},
			failed:function(res){
				alert("getNetworkType  failed " + res.errMsg);
			},
			complete:function(res){
				alert("getNetworkType  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("getNetworkType  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	document.querySelector("#closeWindow").onclick=function(){
		rel.closeWindow({
			success:function(res){
				alert("closeWindow success status="+res.status);
			},
			failed:function(res){
				alert("closeWindow  failed " + res.errMsg);
			},
			complete:function(res){
				alert("closeWindow  complete " + res.errMsg)
			},
			cancel:function(res){
				alert("closeWindow  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
	
	/*document.querySelector("#shareAppOnlyItems").onclick=function(){
		rel.shareAppOnlyItems({
			success:function(res){
				$("#shareAppOnlyItemsResult").val(res.errMsg);
				alert("shareapponly success  status="+res.status);
			},
			failed:function(res){
				alert("shareapponly  failed  " + res.errMsg);
			},
			complete:function(res){
				alert("shareapponly complete " + res.errMsg)
			},
			cancel:function(res){
				alert("shareapponly  cancel " + res.errMsg);
			}
			
		});
		
	};
	document.querySelector("#shareMenuItems").onclick=function(){
		rel.shareMenuItems({
			success:function(res){
				$("#shareMenuItemsResult").val(res.errMsg);
				alert("sharemenuitem success  status="+res.status);
			},
			failed:function(res){
				alert("sharemenuitem  failed  " + res.errMsg);
			},
			complete:function(res){
				alert("sharemenuitem complete " + res.errMsg)
			},
			cancel:function(res){
				alert("sharemenuitem  cancel " + res.errMsg);
			}
			
		});
	};
	*/
	
	document.querySelector("#shareMenuItemsCustom").onclick=function(){
		var shareObj=$("input[name='shareMenuItemsValue']:checked");
		var index=shareObj.length;
		var shareStr="";
		for(var i=0;i<index;i++){
			shareStr+=shareObj[i].value+",";
		}
		shareStr=shareStr.substring(0, shareStr.length-1);
		
		rel.shareMenuItemsCustom({
			showMenuList:shareStr,
			success:function(res){
				$("#shareMenuItemsCustomResult").val(res.errMsg);
				alert("shareMenuItemsCustom success  status="+res.status);
			},
			failed:function(res){
				alert("shareMenuItemsCustom  failed  " + res.errMsg);
			},
			complete:function(res){
				alert("shareMenuItemsCustom complete " + res.errMsg)
			},
			cancel:function(res){
				alert("shareMenuItemsCustom cancel " + res.errMsg);
			}
			
		});
	};
	
	document.querySelector("#scanCode").onclick=function(){
		var needdeal=$("#needdeal").val();
		
		
		rel.scanCode({
			Needdeal:needdeal,
			success:function(res){
				$("#scanCodeResult").val(res.retMsg.result);
				alert("scanCode success  status="+res.status+" retMsg="+res.retMsg);
			},
			failed:function(res){
				alert("scanCode  failed  " + res.errMsg);
			},
			complete:function(res){
				alert("scanCode complete " + res.errMsg)
			},
			cancel:function(res){
				alert("scanCode  cancel " + res.errMsg);
			}
			
		});
	};
	
	document.querySelector("#chooseRELPay").onclick=function(){
		var payobj=loadJSPAY();
		rel.chooseRELPay({
			appid:payobj.appid,
			timestamp:payobj.timestamp,
			nonceStr:payobj.nonceStr,
			orderid:payobj.orderid,
			totalFee:payobj.totalFee,
			channelAppNo:payobj.channelAppNo,
			remark:payobj.remark,
			signature:payobj.signature,
			success:function(res){
				$("#chooseRELPayResult").val(res.errMsg);
				alert("chooseRELPay success status="+res.status);
			},
			failed:function(res){
				$("#chooseRELPayResult").val(res.errMsg);
				alert("chooseRELPay  failed " + res.errMsg);
			},
			complete:function(res){
				$("#chooseRELPayResult").val(res.errMsg);
				alert("chooseRELPay  complete " + res.errMsg)
			},
			cancel:function(res){
				$("#chooseRELPayResult").val(res.errMsg);
				alert("chooseRELPay  cancel " + res.errMsg);
			}
			
		});
		
		
	};
	
  
	 
	}); 


rel.error(function (res) {
  alert(""+res.errMsg +":"+res.status);
});

