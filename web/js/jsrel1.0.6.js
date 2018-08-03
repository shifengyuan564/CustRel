var rel = {
    ame: function (d) {
        if (this.B.debug == 1) {
            alert(d);
        }
    }, init: function (f) {
        var e = f.debug, d, a;
        e != 1 || e == undefined ? this.B.debug = 0 : this.B.debug = 1;
        d = inner.v();
        if (d != null && d > 200) {
            a = {key: "config", DataString: this.pa(f), ReturnFlag: "1", callBack: "rel.ca"};
            inner.Cl(a);
        } else {
            rel.ca('{"status":"-1","errMsg":"requires greater client version ","retMsg":""}');
        }
    }, pa: function (d) {
        return '{"appid":"' + d.appid + '","signature":"' + d.signature + '","timestamp":"' + d.timestamp + '","noncestr":"' + d.nonceStr + '"}';
    }, ca: function (t, x) {
        var r = eval("(" + t + ")"), a;
        switch (r.status) {
            case"1":
                this.B.state = 1;
                a = this.A._completes;
                for (b = 0, c = a.length; b < c; b++) {
                    if (a[b] != undefined) {
                        a[b]();
                    }
                }
                this.A._completes = [];
                break;
            default:
                this.B.state = -1;
                if (this.A._failed != undefined) {
                    this.A._failed(r);
                }
                break;
        }
    }, A: {_completes: []}, B: {state: 0, dubug: 1, cb: "", res: {}}, ready: function (a) {
        this.B.state == 1 && a != undefined ? a() : this.A._completes.push(a);
    }, error: function (a) {
        this.B.state == -1 && a != undefined ? a() : this.A._failed = a;
    }, checkJsApi: function (d) {
        var e = d.jsApiList, f, g;
        f = '"jsApiList":"' + encodeURI(e) + '",';
        g = {key: "checkJsApi", DataString: "{" + f + this.pac(d) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        inner.Cl(g);
    }, chooseImage: function (d) {
        var e = {key: "chooseImage", DataString: "{" + this.pac(d) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        inner.Cl(e);
    }, uploadImage: function (e) {
        var d = e.localIds, g, f, h;
        g = e.isShowProgressTips;
        f = '"localIds":"' + encodeURI(d) + '","isShowProgressTips":"' + encodeURI(g) + '",';
        h = {key: "uploadImage", DataString: "{" + f + this.pac(e) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        inner.Cl(h);
    }, getLocation: function (d) {
        this.ame("jsrel.js getLocation begin");
        var e = {key: "getLocation", DataString: "{" + this.pac(d) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        inner.Cl(e);
    }, favoriteButtonItem: function (e) {
        this.ame("jsrel.js favoriteButtonItem begin");
        var d = e.isAllow, f;
        if (d != "0") {
            d = "1";
        }
        f = {
            key: "favoriteButtonItem",
            DataString: '{"isAllow":"' + d + '",' + this.pac(e) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        inner.Cl(f);
    }, shareAppOnlyItems: function (d) {
        this.ame("jsrel.js shareAppOnlyItems begin");
        var e = '"showMenuList":"appFriend",', f = {
            key: "showMenuItems",
            DataString: "{" + e + this.pac(d) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        inner.Cl(f);
    }, shareMenuItems: function (d) {
        this.ame("jsrel.js shareMenuItems begin");
        var e = '"showMenuList":"appFriend,appMoments,appWealthBar,weixinFriendApp,weixinMomentsApp,WeiboApp",', f = {
            key: "showMenuItems",
            DataString: "{" + e + this.pac(d) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        inner.Cl(f);
    }, shareMenuItemsCustom: function (d) {
        this.ame("jsrel.js shareMenuItemsCustom begin");
        var e = '"showMenuList":"' + d.showMenuList + '",', f = {
            key: "showMenuItems",
            DataString: "{" + e + this.pac(d) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        inner.Cl(f);
    }, scanCode: function (e) {
        this.ame("jsrel.js scanCode begin");
        var d = e.Needdeal, f;
        if (d != "1") {
            d = "0";
        }
        f = {
            key: "scanCode",
            DataString: '{"Needdeal":"' + d + '",' + this.pac(e) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        this.ame(f);
        inner.Cl(f);
    }, startRecord: function (d) {
        this.ame("jsrel.js startRecord begin");
        var e = {key: "startRecord", DataString: "{" + this.pac(d) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        this.ame(e);
        inner.Cl(e);
    }, stopRecord: function (d) {
        this.ame("jsrel.js stopRecord begin");
        var e = {key: "stopRecord", DataString: "{" + this.pac(d) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        this.ame(e);
        inner.Cl(e);
    }, playVoice: function (e) {
        this.ame("jsrel.js playVoice begin");
        var d = e.localId, f = {
            key: "playVoice",
            DataString: '{"localId":"' + d + '",' + this.pac(e) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        this.ame(f);
        inner.Cl(f);
    }, pauseVoice: function (e) {
        this.ame("jsrel.js pauseVoice begin");
        var d = e.localId, f = {
            key: "pauseVoice",
            DataString: '{"localId":"' + d + '",' + this.pac(e) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        this.ame(f);
        inner.Cl(f);
    }, stopVoice: function (e) {
        this.ame("jsrel.js stopVoice begin");
        var d = e.localId, f = {
            key: "stopVoice",
            DataString: '{"localId":"' + d + '",' + this.pac(e) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        this.ame(f);
        inner.Cl(f);
    }, uploadVoice: function (e) {
        this.ame("jsrel.js uploadVoice begin");
        var d = e.localId, f = {
            key: "uploadVoice",
            DataString: '{"localId":"' + d + '",' + this.pac(e) + "}",
            ReturnFlag: "1",
            callBack: "rel.callback"
        };
        this.ame(f);
        inner.Cl(f);
    }, getNetworkType: function (d) {
        this.ame("jsrel.js getNetworkType begin");
        var e = {key: "getNetworkType", DataString: "{" + this.pac(d) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        this.ame(e);
        inner.Cl(e);
    }, closeWindow: function (d) {
        this.ame("jsrel.js closeWindow begin");
        var e = {key: "closeWindow", DataString: "{" + this.pac(d) + "}", ReturnFlag: "1", callBack: "rel.callback"};
        this.ame(e);
        inner.Cl(e);
    }, chooseRELPay: function (d) {
        this.ame("jsrel.js chooseRELPay begin");
        var e = '"appid":"' + d.appid + '","timestamp":"' + d.timestamp + '","nonceStr":"' + d.nonceStr + '","orderid":"' + d.orderid + '","signature":"' + d.signature + '","totalFee":"' + d.totalFee + '","channelAppNo":"' + d.channelAppNo + '","remark":"' + d.remark + '",',
            f = {
                key: "chooseRELPay",
                DataString: "{" + e + this.pac(d) + "}",
                ReturnFlag: "1",
                callBack: "rel.callback"
            };
        this.ame(f);
        inner.Cl(f);
    }, pac: function (a) {
        this.ame("jsrel.js parseCallback begin p=" + a);
        var d = "";
        this.B.cb = a;
        a.success != undefined ? d += '"success":"' + encodeURI(a.success.toString()) + '"' : d;
        a.failed != undefined ? d += ',"failed":"' + encodeURI(a.failed.toString()) + '"' : d;
        a.complete != undefined ? d += ',"complete":"' + encodeURI(a.complete.toString()) + '"' : d;
        a.cancel != undefined ? d += ',"cancel":"' + encodeURI(a.cancel.toString()) + '"' : d;
        this.ame("jsrel.js pac=" + d);
        return d;
    }, callback: function (p, f) {
        this.ame("jsrel.js callback begin p=" + p);
        var b = this.B.cb;
        this.ame("jsrel.js callback begin b=" + b);
        var a = eval("(" + p + ")"), m = this.getCallBackName(a);
        if (b[m] != undefined) {
            eval("(0," + decodeURI(b[m]) + ")")(a);
        } else {
            this.ame("jsrel.js   methordName=" + methodName + "not define");
        }
    }, getCallBackName: function (d) {
        switch (d.status) {
            case"1":
                return "success";
            case"-1":
                return "failed";
            case"2":
                return "complete";
            case"-2":
                return "cancel";
            default:
                return "failed";
        }
    }
};
var inner = {
    p: function () {
        var a = navigator.userAgent.toLowerCase();
        if (a.indexOf("icbciphonebs") > -1 || a.indexOf("iphone") > -1 || a.indexOf("ipad") > -1) {
            return true;
        }
        return false;
    }, a: function () {
        var a = navigator.userAgent.toLowerCase();
        if (a.indexOf("icbcandroidbs") > -1 || a.indexOf("android") > -1) {
            return true;
        }
        return false;
    }, v: function () {
        var f = navigator.userAgent;
        f = f.split("fullversion");
        f = f[1];
        try {
            var d = f.match(new RegExp("\\d.\\d.\\d"));
            if (d != undefined) {
                var a = parseInt(d[0].replace(/\./g, ""));
                return a;
            }
        } catch (g) {
        }
        return null;
    }, Cl: function (d) {
        try {
            if (d.key == undefined || d.DataString == undefined || d.callBack == undefined || d.ReturnFlag == undefined) {
                return;
            }
            d.key = encodeURIComponent(d.key);
            d.DataString = encodeURIComponent(d.DataString);
            if ("1" == d.ReturnFlag) {
                if (this.p()) {
                    this.iEx("Native://ClientJSAPIService=1&key=" + d.key + "&DataString=" + d.DataString + "&callBack=" + d.callBack);
                } else {
                    if (this.a()) {
                        prompt("callNativeMethod", "{obj:Native,func:ClientJSAPIService,args:['" + d.key + "','" + d.DataString + "','" + d.callBack + "']}");
                    }
                }
            } else {
                if (this.p()) {
                    this.iEx("Native://ClientJSAPIService=1&key=" + d.key + "&DataString=" + d.DataString);
                } else {
                    if (this.a()) {
                        prompt("callNativeMethod", "{obj:Native,func:ClientJSAPIService,args:['" + d.key + "','" + d.DataString + "']}");
                    }
                }
            }
        } catch (a) {
            console.log("Error: " + a);
        }
    }, iEx: function (d) {
        var a;
        a = document.createElement("iframe");
        a.setAttribute("src", d);
        a.setAttribute("style", "display:none");
        a.setAttribute("height", "0px");
        a.setAttribute("width", "0px");
        a.setAttribute("frameborder", "0");
        document.body.appendChild(a);
        a.parentNode.removeChild(a);
        a = null;
    }
};