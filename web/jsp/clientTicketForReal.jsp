<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>demo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<style>
    .title1 {
        font-size: 3em;
    }

    .title_a {
        color: black;
    }

    .inp_result {
        width: 60%;
        height: 60px;
        font-size: 30px;
    }

    .res_span {
        width: 30%;
        height: 60px;
        font-size: 40px;
    }

    .btn {
        width: 95%;
        height: 70px;
        font-size: 50px;
        background-color: green;
    }

    .div_result {
        margin-top: 20px;
    }

    .share_checkbox {
        size: 50px;
    }
</style>
<body>
<div hidden="true">
    <button id="jsticket">jsticket</button>
    <button> 输入jsticket</button>
    <input type="text" id="jsticketUrl"
           value="5IdsSw8XL3wT4OIMm78fX2ZtfGNH7IHyzxz0mKrGVUqzUvPmlzvK8VsQXq2oWsU="> </input>

    <button id="appid">appid</button>
    <button> 输入appid</button>
    <input type="text" id="appidinput" value="56Bbg4cGzJXuPJVKMYpuug=="> </input>
</div>

<div style="font-size: 3em;padding-left: 20%;" class="panel-body">
    <a href="#step1" class="title_a">基础接口</a><br/>
    <a href="#step2" class="title_a">图像接口</a><br/>
    <a href="#step3" class="title_a">获取地理位置接口</a><br/>
    <a href="#step4" class="title_a">收藏接口</a><br/>
    <a href="#step5" class="title_a">分享接口</a><br/>
    <a href="#step6" class="title_a">扫一扫接口</a><br/>
</div>
<br/>
<div>
    <a name="step1"></a>
    <span class="title1"><b>基础接口</b></span><br/>
    <h3>判断当前客户端是否支持指定JS接口</h3>
    <button id="checkJsApi" class="btn">checkJsApi</button>
    <br/>
    <div class="div_result">
        <span class="res_span">checkJsApiResult:</span>
        <input type="text" id="checkJsApiResult" class="inp_result"> </input><br/>
    </div>
    <a name="step2"></a><br/>
    <span class="title1"><b>图像接口</b></span><br/>
    <h3>选取图片</h3><br/>
    <button id="chooseimage" class="btn">chooseimage</button>
    <br/>
    <div class="div_result">
        <span class="res_span"> 图片id: </span>
        <input type="text" id="chooseimageId" class="inp_result"> </input><br/>
    </div>

    <h3>上传图片</h3>
    <button id="uploadimage" class="btn">uploadimage</button>
    <br/>
    <div class="div_result">
        <span class="res_span"> 图片url: </span>
        <input type="text" id="uploadimageUrl" class="inp_result"> </input>    <br/>

    </div>

    <a name="step3"></a><br/>
    <span class="title1"><b>获取地理位置接口</b></span><br/>
    <button id="getlocation" class="btn">getlocation</button>
    <div class="div_result">
        <span class="res_span"> 经度: </span>
        <input type="text" id="latituede" class="inp_result"> </input>
        <br/>
        <span class="res_span"> 纬度: </span>
        <input type="text" id="longitude" class="inp_result">  </input>
    </div>


    <a name="step4"></a>
    <span class="title1"><b>收藏接口</b></span>
    <button id="favoriteButtonItem" class="btn">favoriteButtonItem</button>
    <div class="div_result">
        <span class="res_span"> 是否提供收藏功能(1:是;0:否):</span>
        <input type="text" id="isAllow" value="1" style="width: 20%;" class="inp_result"> </input><br/>
        <span class="res_span">favorite result:</span>
        <input type="text" id="favoriteButtonItemResult" class="inp_result"> </input>
    </div>
    <a name="step5"></a>
    <span class="title1"><b>分享接口</b></span>
    <button id="shareMenuItemsCustom" class="btn">shareMenuItemsCustom</button>
    <div class="div_result">
 <span class="res_span"> 
分享到： 好友:<input name="shareMenuItemsValue" class="share_checkbox" type="checkbox" value="appFriend"/>
朋友圈:<input name="shareMenuItemsValue" class="share_checkbox" type="checkbox" value="appMoments"/>
财富吧:<input name="shareMenuItemsValue" class="share_checkbox" type="checkbox" value="appWealthBar"/>
微信好友:<input name="shareMenuItemsValue" class="share_checkbox" type="checkbox" value="weixinFriendApp"/>
微信朋友圈:<input name="shareMenuItemsValue" class="share_checkbox" type="checkbox" value="weixinMomentsApp"/>
微博:<input name="shareMenuItemsValue" class="share_checkbox" type="checkbox" value="WeiboApp"/>
</span><br/>
        <span class="res_span">shareMenu result:</span> <input type="text" id="shareMenuItemsCustomResult"
                                                               class="inp_result"> </input>
    </div>

    <a name="step6"></a>
    <span class="title1"><b>扫一扫接口</b></span>
    <button id="scanCode" class="btn">scanCode</button>
    <div class="div_result">
         <span class="res_span">
        是否融e联进行处理(0:不处理,1:处理): <input type="text" id="needdeal" class="inp_result" style="width:20%;" value="0"> </input>
        </span>
        <span class="res_span">scanCode result:</span>
        <input type="text" id="scanCodeResult" class="inp_result"> </input>

    </div>

    <a name="step6"></a>
    <span class="title1"><b>开始录音接口</b></span>
    <button id="startRecord" class="btn">startRecord</button>
    <div class="div_result">
        <span class="res_span">startRecord result:</span>
        <input type="text" id="startRecordResult" class="inp_result"> </input>

    </div>
    <a name="step6"></a>
    <span class="title1"><b>停止录音接口</b></span>
    <button id="stopRecord" class="btn">stopRecord</button>
    <div class="div_result">
        <span class="res_span">stopRecord localId :</span>
        <input type="text" id="stopRecordResult" class="inp_result"> </input>

    </div>


    <div class="div_result">
        <span class="res_span">得到的录音文件:</span>
        <input type="text" id="VoiceFile" class="inp_result"> </input>

    </div>
    <a name="step6"></a>
    <span class="title1"><b>播放语音接口</b></span>
    <button id="playVoice" class="btn">playVoice</button>
    <div class="div_result">
        <span class="res_span">playVoice result:</span>
        <input type="text" id="playVoiceResult" class="inp_result"> </input>

    </div>

    <a name="step6"></a>
    <span class="title1"><b>暂停播放接口</b></span>
    <button id="pauseVoice" class="btn">pauseVoice</button>
    <div class="div_result">
        <span class="res_span">pauseVoice result:</span>
        <input type="text" id="pauseVoiceResult" class="inp_result"> </input>

    </div>

    <a name="step6"></a>
    <span class="title1"><b>停止播放接口</b></span>
    <button id="stopVoice" class="btn">stopVoice</button>
    <div class="div_result">
        <span class="res_span">stopVoice result:</span>
        <input type="text" id="stopVoiceResult" class="inp_result"> </input>

    </div>

    <a name="step6"></a>
    <span class="title1"><b>上传语音接口</b></span>
    <button id="uploadVoice" class="btn">uploadVoice</button>
    <div class="div_result">
        <span class="res_span">uploadVoice result:</span>
        <input type="text" id="uploadVoiceResult" class="inp_result"> </input>

    </div>

    <a name="step6"></a>
    <span class="title1"><b>获取网络状态接口</b></span>
    <button id="getNetworkType" class="btn">getNetworkType</button>
    <div class="div_result">
        <span class="res_span">getNetworkType result:</span>
        <input type="text" id="getNetworkTypeResult" class="inp_result"> </input>

    </div>

    <a name="step6"></a>
    <span class="title1"><b>关闭当前网页窗口接口</b></span>
    <button id="closeWindow" class="btn">closeWindow</button>
    <!-- <div class="div_result">
     <span class="res_span">closeWindow result:</span>
    <input type="text" id="closeWindowResult" class="inp_result"> </input>

     </div> -->

    <a name="step6"></a>
    <span class="title1"><b>JSPAY支付接口</b></span>
    <button id="chooseRELPay" class="btn">chooseRELPay</button>
    <div class="div_result">
<span class="res_span"> 
appid: <input type="text" id="jspay_appid" class="inp_result" style="width:20%;"
              value="56Bbg4cGzJXuPJVKMYpuug=="> </input>
    jspayticket: <input type="text" id="jspay_ticket" class="inp_result" style="width:20%;"
                        value="96nM3gVF8gnP9Xpy4eGczDTLCVoic81h7l6697dwxLhD4jjivfJBp0GmKyY8fqs="> </input>
    totalFee: <input type="text" id="jspay_totalFee" class="inp_result" style="width:20%;" value="100"> </input>
    orderid: <input type="text" id="jspay_orderid" class="inp_result" style="width:20%;"
                    value="orderidtest001"> </input>
    channelAppNo:<input type="text" id="jspay_channelAppNo" class="inp_result" style="width:20%;" value="re"> </input>
    remark:<input type="text" id="jspay_remark" class="inp_result" style="width:20%;" value="remark"> </input>
</span>
        <span class="res_span">chooseRELPay result:</span>
        <input type="text" id="chooseRELPayResult" class="inp_result"> </input>

    </div>


</div>

</body>
<script defer type="text/javascript" src="../js/jquery.min.js"></script>
<script defer type="text/javascript" src="../js/jsrel1.0.12.js"></script>
<script defer type="text/javascript">
    var targetUrl = location.href.split('#')[0];    /*http://localhost:8080/pages/ticket/clientTicketForReal.html*/
    window.onload = function () {
        alert(targetUrl);
        $.ajax({
            type: "GET",
            url: "../../servlet/signServlet",
            data: "url=" + encodeURIComponent(targetUrl) +
            "&jsticket=" + encodeURIComponent($("#jsticketUrl").val()) +
            "&appid=" + encodeURIComponent($("#appidinput").val()),
            dataType: "json",
            async: false,
            success: function (data, status) {
                var config = data;
                var signature1 = config.signature;
                var appid1 = config.appid;
                var nonceStr1 = config.nonceStr;
                var timestamp1 = config.timestamp;

                alert("begin rel init");
                rel.init({
                    debug: 1,
                    appid: appid1,
                    timestamp: timestamp1,
                    nonceStr: nonceStr1,
                    signature: signature1
                });


            }
        });


    };


</script>
<script defer type="text/javascript" src="../js/demo.js"></script>
</html>