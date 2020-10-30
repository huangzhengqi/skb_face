package com.fzy.admin.fp.distribution.money.service;

import cn.hutool.core.bean.BeanUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.config.AlipayConfig;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import com.fzy.admin.fp.distribution.money.domain.TakeInfo;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.money.repository.TakeInfoRepository;
import com.fzy.admin.fp.distribution.pc.domain.SystemSetup;
import com.fzy.admin.fp.distribution.pc.service.SystemSetupService;
import com.fzy.admin.fp.distribution.utils.DistUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-19 09:39:53
 * @Desp 提现申请
 **/
@Service
@Transactional(rollbackOn = Exception.class)
public class TakeInfoService implements BaseService<TakeInfo> {

    private final String SUCCESS_CODE = "10000";

    @Resource
    private TakeInfoRepository takeInfoRepository;

    @Resource
    private WalletService walletService;

    @Resource
    private AccountDetailService accountDetailService;

    @Resource
    private SystemSetupService systemSetupService;

    @Override
    public TakeInfoRepository getRepository() {
        return takeInfoRepository;
    }

    public String applyTake(String userId, Double money, String account, String name, Integer type, String serviceId) {
        Wallet oldWallet = walletService.getRepository().findByUserId(userId);
        BigDecimal oldTake = oldWallet.getTake();
        BigDecimal oldBalance = oldWallet.getBalance();
        BigDecimal applyMoney = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        if (oldWallet.getBalance().compareTo(applyMoney) == -1) {
            return "余额不足";
        }
        SystemSetup systemSetup = systemSetupService.getRepository().findByServiceProviderId(serviceId);


        Wallet wallet = new Wallet();
        BeanUtil.copyProperties(oldWallet, wallet);
        wallet.setBalance(wallet.getBalance().subtract(applyMoney));
        wallet.setTake(wallet.getTake().add(applyMoney));
        //修改钱包的余额
        while (walletService.getRepository().take(new Date(), wallet.getBalance(), wallet.getBonus(), wallet.getId(), oldBalance, oldTake) == 0) {
            wallet = walletService.getRepository().findByUserId(userId);
            oldTake = wallet.getTake();
            oldBalance = wallet.getBalance();
            wallet.setBalance(wallet.getBalance().subtract(applyMoney));
            wallet.setTake(wallet.getTake().add(applyMoney));
        }
        walletService.getRepository().take(new Date(), wallet.getBalance(), wallet.getBonus(), wallet.getId(), oldBalance, oldTake);
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setSum(applyMoney);
        accountDetail.setBalance(wallet.getBalance());
        accountDetail.setUserId(userId);
        accountDetail.setType(2);
        accountDetail.setServiceProviderId(wallet.getServiceProviderId());
        accountDetail.setName("申请提现");
        accountDetail.setStatus(1);
        accountDetailService.save(accountDetail);

        //提现金额=金额-（金额*服务费）
        applyMoney = applyMoney.subtract(applyMoney.multiply(new BigDecimal(systemSetup.getServiceCharge() * 0.01)));
        TakeInfo takeInfo = new TakeInfo();
        takeInfo.setSum(applyMoney);
        takeInfo.setUserId(userId);
        takeInfo.setAccount(account);
        takeInfo.setTakeType(type);
        takeInfo.setName(name);
        takeInfo.setServiceProviderId(wallet.getServiceProviderId());

        if (type == 1 && systemSetup.getAliStatus() == 0) {
            try {
                String orderNub = DistUtil.orderNub();
                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.APPID,
                        AlipayConfig.RSA_PRIVATE_KEY, "json", "GBK", AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA2");
                AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
                request.setBizContent("{" +
                        "\"out_biz_no\":\"" + orderNub + "\"," +
                        "\"payee_type\":\"ALIPAY_LOGONID\"," +
                        "\"payee_account\":\"" + account + "\"," +
                        "\"amount\":\"" + applyMoney + "\"," +
                        "\"payer_show_name\":\"" + name + "\"," +
                        "\"payee_real_name\":\"" + name + "\"," +
                        "\"remark\":\"申请提现\"" +
                        "  }");
                AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
                //提现成功,本地业务逻辑略
                if (SUCCESS_CODE.equals(response.getCode())) {
                    takeInfo.setStatus(1);
                    takeInfoRepository.save(takeInfo);
                    return "提现成功";
                    // 支付宝提现失败，本地业务逻辑略
                } else {
                    takeInfo.setStatus(1);
                    takeInfoRepository.save(takeInfo);
                    return "提现失败" + response.getMsg();
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        takeInfo.setStatus(0);
        takeInfoRepository.save(takeInfo);
        return "申请成功";
    }

    /**
     * 打款
     *
     * @param takeInfo
     */
    public void remit(TakeInfo takeInfo) {
        Integer type = 1;

        Wallet wallet = walletService.getRepository().findByUserId(takeInfo.getUserId());
        int status = 2;
        //驳回
        if (takeInfo.getStatus() == status) {
            type = 3;
            wallet.setBalance(wallet.getBalance().add(takeInfo.getSum()));
            wallet.setTake(wallet.getBalance().subtract(takeInfo.getSum()));
            BigDecimal oldTake = wallet.getTake();
            BigDecimal oldBalance = wallet.getBalance();
            //修改钱包的余额
            while (walletService.getRepository().take(new Date(), wallet.getBalance(), wallet.getBonus(), wallet.getId(), oldBalance, oldTake) == 0) {
                wallet = walletService.getRepository().findByUserId(takeInfo.getUserId());
                oldTake = wallet.getTake();
                oldBalance = wallet.getBalance();
                wallet.setBalance(wallet.getBalance().add(takeInfo.getSum()));
                wallet.setTake(wallet.getBalance().subtract(takeInfo.getSum()));
            }
        }
        this.update(takeInfo);
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setBalance(wallet.getBalance());
        accountDetail.setSum(takeInfo.getSum());
        accountDetail.setType(type);
        accountDetail.setUserId(takeInfo.getUserId());
        accountDetail.setServiceProviderId(takeInfo.getServiceProviderId());
        accountDetail.setStatus(1);
        //记录账户流水
        accountDetailService.save(accountDetail);

    }


}
