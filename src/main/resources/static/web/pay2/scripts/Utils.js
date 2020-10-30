var Utils = {}

Utils.openid = "";
Utils.nickName = "";
Utils.headimgurl = "";
//通用方法检查是否获取到openid
Utils.commonfun = function (that, e, fun, obj) {
    var that = that;
    var e = e;
    var setobj = obj
    $.cookie("id", Utils.getParam("id"));
	$.cookie("openid", "");
    if (typeof ($.cookie("openid")) == "undefined" || $.cookie("openid") == "") {
        var curUrl = window.location.href;
        // window.location.href = "/view/index.aspx?backUrl=" + curUrl;
    } else {
        Utils.openid = $.cookie("openid");
        Utils.nickName = $.cookie("nickName");
        Utils.headimgurl = $.cookie("headimgurl");
    }
    fun();
}
//GET请求方法
Utils.webreq = function (url, data, success, fail) {
    $.ajax({
        url: url,
        type: "GET",
        data: data,
        headers: {
            'content-type': 'application/json' // 默认值
        },
        success: function (res) {
            success(res);
        },
        error: function (res) {
            console.log(res)
        }
    });
}
//POST请求方法
Utils.webpost = function (url, data, success, fail) {
    $.ajax({
        url: url,
        type: "POST",
        data: data,
        success: function (res) {            
            success(res)
        },
        error: function (res) {
            console.log(res)
        }
    });
}

//调起微信支付
Utils.chooseWXPay = function(res){
	console.log(res);
	wx.chooseWXPay({
	  timestamp: 0, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
	  nonceStr: '', // 支付签名随机串，不长于 32 位
	  package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
	  signType: '', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
	  paySign: '', // 支付签名
	  success: function (res) {
		// 支付成功后的回调函数
	  }
	});
}

Utils.getParam = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

Utils.share = function (data, success, cancel) {
    var objdata = data
    var url = window.location.href;
    if (data.shareUrl) {
        url = data.shareUrl
    }
    Utils.webreq("http://sqtt.yybc.com/tools/slim_ajax.ashx", { action: "GetShareParam" }, function (data) {        
        var obj = JSON.parse(data);
		console.log(obj);
        if (obj.status == 1) {
            wxJs.Init({
                debug: false,
                appId: obj.data.appId,
                timestamp: obj.data.timestamp,
                nonceStr: obj.data.nonceStr,
                signature: obj.data.signature
            });
            wxJs.Ready(function () {
                var link = url; // 分享链接
                wxJs.showmenu();
                wxJs.onMenuShareAppMessage({
                    title: objdata.shareTitle,
                    link: link,
                    imgUrl: objdata.shareImg,
                    desc: objdata.shareDes,
                    ok: function () {
                        success()
                    },
                    cancel: function () {
                        if (cancel) {
                            cancel()
                        }
                    }
                });

                wxJs.onMenuShareTimeline({
                    title: objdata.shareTitle,
                    link: link,
                    imgUrl: objdata.shareImg,
                    ok: function () {
                        success()
                    },
                    cancel: function () {
                        if (cancel) {
                            cancel()
                        }
                    }
                });

                wxJs.onMenuShareQQ({
                    title: objdata.shareTitle,
                    link: link,
                    imgUrl: objdata.shareImg,
                    desc: objdata.shareDes,
                    ok: function () {
                        success()
                    },
                    cancel: function () {
                        if (cancel) {
                            cancel()
                        }
                    }
                });
            })
        }
    });
}
//时间做比较
Utils.DateDiff = function (sDate, eDate) { //sDate和eDate是yyyy-MM-dd格式    
	var date1 = new Date(sDate);
	var date2 = new Date(eDate);
	var date3 = date2.getTime() - date1.getTime();
	var days = Math.floor(date3 / (24 * 3600 * 1000));
	return days;
}
//时间格式转换
Utils.DateFormat = function (date, fmt) {
	var o = {
		"M+": new Date(date).getMonth() + 1, //月份
		"d+": new Date(date).getDate(), //日
		"H+": new Date(date).getHours(), //小时
		"m+": new Date(date).getMinutes(), //分
		"s+": new Date(date).getSeconds(), //秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (new Date(date).getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
//矫正苹果安卓时差
Utils.DateCompare = function (sDate, eDate) {
    var date1 = new Date(sDate.replace(/\-/g, "/").replace("T", " "));
    var date2 = new Date(eDate.replace(/\-/g, "/").replace("T", " "));
	var datediff = date1.getTime() - date2.getTime();
    if (datediff > 0) {
        return 1;
    }else if(datediff == 0){
        return 0;
    }else{
		return -1
	}
}