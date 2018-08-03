<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
    if ($("#info-switch").val() == "on") {
        $("#information").show();
    } else {
        $("#information").hide();
    }

    $("#i_ras_priv_key").val($("#tps_priv_key").val());
    $("#tps_pubkey_p").text($("#tps_pub_key").val());


    $("#btn_getkey").bind('click', function () {
        var appid = $("#i_appid").val();
        var timestamp = $("#i_timestamp").val();
        var key = $("#i_ras_priv_key").val();
        $.ajax({
            type: "GET",
            url: "/CustRel/getSignatureServlet",
            data: "i_appid=" + encodeURIComponent(appid, "utf-8") +
            "&i_timestamp=" + timestamp +
            "&i_key=" + key,
            dataType: "text",
            success: function (msg) {
                $("#i_signature").val(msg);
            }
        });
    });

    $("#btn_gettimestamp").bind('click', function () {
        var key = "gettimestamp";
        $.ajax({
            type: "GET",
            url: "/CustRel/getSignatureServlet",
            data: "&i_key=" + key,
            dataType: "text",
            success: function (msg) {
                $("#i_timestamp").val(msg);
            }
        });
    });


    $("#btn_sub").bind('click', function () {
        var appid = $("#i_appid").val();
        var timestamp = $("#i_timestamp").val();
        var signature = $("#i_signature").val();
        var url = $("#i_url").val();

        $.ajax({
            type: "GET",
            url: "/CustRel/getTokenServlet",
            data: "i_appid=" + encodeURIComponent(appid, "utf-8") +
            "&i_timestamp=" + timestamp +
            "&i_signature=" + signature +
            "&i_url=" + url,
            dataType: "text",
            success: function (msg) {
                var token = msg.split("|")[0];
                var key = msg.split("|")[1];

                $("#out_token").val(token);
                $("#glb_token").val(token);
                $("#out_key").val(key);
                $("#glb_key").val(key);
                $("#glb_url").val(url);

                $("#temptable").show();
            }
        });
    });

    // 获取jsAPI ticket
    $("#btn_ticket").bind('click', function () {
        var token = $("#out_token").val();  // access_tocken
        var type = "jsapi";
        var url = $("#i_url").val();        // https://imapi.icbc.com.cn:443
        var key = $("#out_key").val();      // session_key
        $.ajax({
            type: "GET",
            url: "/CustRel/getTicketServlet",
            data: "access_token=" + token +
            "&type=" + type +
            "&i_key=" + key +
            "&i_url=" + url,
            dataType: "text",
            success: function (msg) {
                /*   var token = msg.split("|")[0];
                  var key = msg.split("|")[1]; */

                $("#out_ticket").val(msg); // jsapi ticket
                /*  $("#out_ticket").val(token);
                 $("#out_key").val(key);
                 $("#glb_key").val(key);
                 $("#glb_url").val(url); */

                $("#temptable").show();
            }
        });
    });

</script>
<style type="text/css">
    td input {
        width: 500px;
    }

    td textarea {
        width: 500px;
    }
</style>
<div id="information">
    <div>操作说明：</div>
    <div>1、正确填写表单（开放平台URL，APPID，时间戳）</div>
    <div>2、点击生成按钮，等待生成签名</div>
    <div>3、点击获取token按钮，等待回显accesstoken和sessionkey</div>
    <div>备注：</div>
    <div>1、如果accesstoken和sessionkey为null，请联系开发人员</div>
    <div>2、accesstoken和sessionkey生成成功后，进入其他页面时，会保留token和key，请不要随意刷新页面，一旦刷新页面就需要手动去数据库查询token和key</div>
    <div>3、appid获取方式：(页面中默认值是90000000的appid)</div>
    <div>1)进入服务平台-开发者中心</div>
    <div>2)填写url：http://本机ip:8090/OpenServerAPITestProj/IcbcServlet</div>
    <div>3)上传密钥文件，<span id="tps_pubkey_p"></span></div>
    <br/>
    <hr/>
</div>
<table>
    <input type="hidden" value=${redirectParam} >
    <tr>
        <td>开放平台URL</td>
        <td>
            <select id="i_url">
                <option value="https://imapi.icbc.com.cn:443">生产</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>APPID</td>
        <select id="i_appid">
            <option value="DLgiOs2NNEoLNWWKUqXa2Q==">DLgiOs2NNEoLNWWKUqXa2Q==</option>
        </select>
    </tr>
    <tr>
        <td>时间戳</td>
        <td><input type="text" id="i_timestamp" value=""></input></td>
        <td>
            <button id="btn_gettimestamp">获取系统时间戳</button>
        </td>
    </tr>
    <tr>
        <td>第三方私钥</td>
        <td><input type="text" id="i_ras_priv_key" value=""></input>
        </td>
    </tr>
    <tr>
        <td>签名</td>
        <td><input type="text" id="i_signature"></input></td>
        <td>
            <button id="btn_getkey">生成</button>
        </td>
    </tr>
</table>
<button id="btn_sub">获取token</button>

<hr/>

<table>
    <tr>
        <td>access token</td>
        <td><input type="text" id="out_token"></input></td>
    </tr>
    <tr>
        <td>session key</td>
        <td><input type="text" id="out_key"></input></td>
    </tr>
</table>
<hr/>
<button id="btn_ticket">获取ticket(jsapi专用,不用jsapi不要点击)</button>
<table>
    <tr>
        <td>ticket</td>
        <td><input type="text" id="out_ticket"></input></td> <!-- jsapi ticket -->
    </tr>
</table>
<hr/>

