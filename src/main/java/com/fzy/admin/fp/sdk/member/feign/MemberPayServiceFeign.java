package com.fzy.admin.fp.sdk.member.feign;


import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.sdk.member.domain.MemberPayParam;
import com.fzy.admin.fp.sdk.member.domain.MemberRefundParam;
import com.fzy.admin.fp.sdk.member.domain.MemberPayParam;
import com.fzy.admin.fp.sdk.member.domain.MemberRefundParam;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;

/**
 * @author Created by wtl on 2019-05-19 11:22
 * @description
 */
public interface MemberPayServiceFeign {

    /**
     * @author Created by wtl on 2019/5/20 22:23
     * @Description 会员卡支付，计算卡余额及生成会员消费记录，有使用卡券需要核销
     */
    PayRes memberPay(MemberPayParam model);

    /**
     * @author Created by wtl on 2019/6/18 23:08
     * @Description 会员卡退款
     */
    PayRes memberRefund(MemberRefundParam model);

    /**
     * @author Created by wtl on 2019/6/19 18:50
     * @Description 会员小程序充值，回调订单支付成功后添加储值记录
     */
    void memberStoreRecord(OrderVo orderVo);

    /**
     * @author Created by wtl on 2019/6/29 15:00
     * @Description H5会员支付，微信回调生成会员消费记录
     */
    void wxMemberPayRedirect(MemberPayParam model);

    void creditsInfoSave(Member paramMember, int paramInt1, CreditsInfo.Trade paramTrade, int paramInt2, String paramString1, String paramString2);
}
