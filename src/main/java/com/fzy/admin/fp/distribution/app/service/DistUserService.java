package com.fzy.admin.fp.distribution.app.service;

import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.repository.DistUserRepository;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.money.service.WalletService;
import com.fzy.assist.wraps.DateWrap;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
@Slf4j
@Service
public class DistUserService implements BaseService<DistUser> {
    @Resource
    private DistUserRepository distUserRepository;

    @Resource
    private WalletService walletService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public DistUserRepository getRepository() {
        return distUserRepository;
    }

    public int updateUserActivate(DistUser agentUser,Integer oldTeamActivate){
        log.info("agentUser.getStatus() ==== > " + agentUser.getStatus() + "  agentUser.getActivate() ==== >"  + agentUser.getActivate() + "  agentUser.getTeamActivate() ====> "+ agentUser.getTeamActivate() + "  agentUser.getId() ==> " + agentUser.getId() +  "  oldTeamActivate == >" + oldTeamActivate );
        log.info("agentUser ==== > " + agentUser.toString() + "oldTeamActivate ====> " + oldTeamActivate);
        int i = distUserRepository.updateActivate(agentUser.getStatus(), agentUser.getActivate(), agentUser.getTeamActivate(), agentUser.getId(), oldTeamActivate);
        log.info("更新完成的数字 ===============>" + i);
        return i;
    }

    public List<Map> getListById(Integer type, String userId, String sort,String param, PageVo pageVo) {
        int startNum = (pageVo.getPageNumber() - 1) * pageVo.getPageSize();
        Date dayByNum = DateUtils.getDayByNum(new Date(), -1);
        String format = DateWrap.format(dayByNum, "yyyy-MM-dd 00:00:00");//得到昨天的时间

        Date date = new Date();
        String nowMonthDate = DateWrap.format(date, "yyyy-MM-01 00:00:00");//得到当月的时间
        String level=null;

        //查询是否是运营中心
        DistUser distUser = distUserRepository.findOne(userId);
        if(distUser.getGrade().equals(Integer.valueOf(2))){
            if(type==0){
                level="u.one_level_id=:userId";
            }else if(type==1){
                level="u.zero_level_id=:userId";
            }
        }else {
            if(type==0){
                level="u.one_level_id=:userId";
            }else if(type==1){
                level="u.two_level_id=:userId";
            }else if(type==2){
                level="u.three_level_id=:userId";
            }

        }
        StringBuilder sb = new StringBuilder();
        sb.append("select a.team_activate teamActivate,a.buy_num as buyNum,a.merchant_num as merchantNum,a.head_img as headImg,a.id,a.name,a.user_name,a.grade,a.activate,a.create_time as createTime,a.direct_num as directNum,b.yesterdayCommission,a.totalCommission,c.monthCommission from (select u.*,sum(d.commission_total) as totalCommission from lysj_dist_user u LEFT JOIN order_commission_day d on u.id=d.company_id where ");
        if(StringUtil.isNotEmpty(param)){
            sb.append("(u.name like :param or u.user_name like :param or u.invite_num like :param) and ");
        }
        sb.append(level);
        sb.append(" GROUP BY d.company_id,u.id ) a LEFT JOIN (select u.id,d.commission_total as yesterdayCommission  from lysj_dist_user u LEFT JOIN order_commission_day d on u.id=d.company_id and d.type=1 and d.create_time=:format) b on a.id=b.id ");
        sb.append(" LEFT JOIN (select u.id,sum(d.commission_total) as monthCommission from lysj_dist_user u LEFT JOIN order_commission_day d on u.id=d.company_id where ");
        sb.append(level);
        sb.append(" and d.create_time BETWEEN :nowMonthDate and :nowDate GROUP BY d.company_id,u.id ) c on a.id=c.id ORDER BY ");
        sb.append(sort);
        sb.append(" ,a.create_time desc");
        sb.append(" limit :startNum,:pageSize");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("userId", userId);
        if(StringUtil.isNotEmpty(param)){
            query.setParameter("param", param+"%");
        }
        query.setParameter("format", format);
        query.setParameter("nowMonthDate", nowMonthDate);
        query.setParameter("nowDate", new Date());
        query.setParameter("startNum", startNum);
        query.setParameter("pageSize", pageVo.getPageSize());
        List<Map> list = query.getResultList();
        return list;
    }

    public List<Map> getCommission(List<String> ids){
        StringBuilder sb = new StringBuilder();
        sb.append("select SUM(direct_commission) as directCommission,SUM(operation_commission) as operationCommission,SUM(one_level_commission) as oneLevelCommission,SUM(two_level_commission) as twoLevelCommission,SUM(three_level_commission) as threeLevelCommission,direct_id as directId,merchant_id as merchantId,one_level_id as oneLevelId,two_level_id as twoLevelId,three_level_id as threeLevelId from order_commission where merchant_id in (:ids) AND order_status = 2 GROUP BY merchant_id,one_level_id,two_level_id,three_level_id,direct_id");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("ids", ids);
        List<Map> list = query.getResultList();
        return list;
    }

    /**
     * 根据商户ID查询有效的交易金额
     * @return
     */
    public String getOrderTotalPrice(String merchantId){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum( act_pay_price ) AS totalPrice FROM lysj_order_order WHERE `status` IN ( '2', '6' )  AND merchant_id = :merchantId");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchantId);
        List<Map> list = query.getResultList();
        String totalPrice="";
        for (Object obj : list) {
            Map row = (Map) obj;
            totalPrice=row.get("totalPrice").toString();
        }
        return totalPrice;
    }


    @Transactional
    public DistUser saveUser(DistUser distUser) {
        DistUser user = save(distUser);
        Wallet wallet=new Wallet();
        wallet.setUserId(user.getId());
        wallet.setServiceProviderId(distUser.getServiceProviderId());
        wallet.setBalance(new BigDecimal("0"));
        wallet.setBonus(new BigDecimal("0"));
        wallet.setTake(new BigDecimal("0"));
        walletService.save(wallet);
        
        //----------- 邀请码里面的数字不能带4 --------------------
//        int inviteLen = 4;
//        String id=user.getId();
//        String newInviteNum = id.substring(id.length() - inviteLen, id.length());
        //----------- 邀请码里面的数字不能带4 --------------------
        String newInviteNum=String.valueOf(getInviteNum());
        while (this.getRepository().findByInviteNumAndServiceProviderId(newInviteNum,distUser.getServiceProviderId()) != null) {
//            ++inviteLen;
//            newInviteNum = id.substring(id.length() - inviteLen, id.length());
            newInviteNum=String.valueOf(getInviteNum());
        }
        user.setInviteNum(newInviteNum);
        update(user);
        return user;
    }

    public int getInviteNum(){
        int v = (int)(Math.random()*9999);
        while (v < 1000 || String.valueOf(v).contains("4")) {
            v = (int)(Math.random()*9999);
        }
       return v;
    }
}
