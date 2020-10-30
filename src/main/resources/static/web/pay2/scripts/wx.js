var wxJs = {
	Init: function (param) {
		wx.config({
			debug: param.debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId: param.appId, // 必填，公众号的唯一标识
			timestamp: param.timestamp, // 必填，生成签名的时间戳
			nonceStr: param.nonceStr, // 必填，生成签名的随机串
			signature: param.signature, // 必填，签名，见附录1
			jsApiList: [
				"onMenuShareTimeline",
				"onMenuShareAppMessage",
				"onMenuShareQQ",
				"onMenuShareWeibo",
				"startRecord",
				"stopRecord",
				"onVoiceRecordEnd",
				"playVoice",
				"pauseVoice",
				"stopVoice",
				"onVoicePlayEnd",
				"uploadVoice",
				"downloadVoice",
				"chooseImage",
				"previewImage",
				"uploadImage",
				"downloadImage",
				"translateVoice",
				"getNetworkType",
				"openLocation",
				"getLocation",
				"hideOptionMenu",
				"showOptionMenu",
				"hideMenuItems",
				"showMenuItems",
				"hideAllNonBaseMenuItem",
				"showAllNonBaseMenuItem",
				"closeWindow",
				"scanQRCode",
				"chooseWXPay",
				"openProductSpecificView",
				"addCard",
				"chooseCard",
				"openCard",
				"getLocalImgData"
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
	},
	Ready: function (callback) {
	    wx.ready(function () {
	        callback();
	    });
	},
	payment: function(data,success, fial){
		wx.chooseWXPay({//此方法应放在调用后台统一下单接口成功后回调里面，接口返回  timeStamp，nonceStr，package，paySign等参数
			timestamp: data.timeStamp,
			nonceStr: data.nonceStr,
			package: data.pkg,
			signType: data.signType,
			paySign: data.paySign,
			appId: data.appId,//此参数可不用
			success,
			fial
				// alert('success',r);
				// // 支付成功后的回调函数
				// if (r.errMsg == "chooseWXPay:ok") {
				// //支付成功
				// 	setTimeout(function(){
				// 		$('.payment').hide();
				// 		$('.complete').fadeIn();
				// 	},500);
				// 	$('.btn').css({'border': '#07c160', 'color': '#07c160'});
				// 	$('.orderTime').text(formatUnixtimestamp(data.timeStamp));
				// 	$('.orderNum').text(data.orderNumber);
				// 	$('.orderMoney').text(data.price);
				// } else {
				// 	location.reload();//支付失败 刷新界面
				// };
		// 　　},
		// 　　cancel: function(r) {
		// 		alert('err',r);
		// 	}
		});
	}
	
}