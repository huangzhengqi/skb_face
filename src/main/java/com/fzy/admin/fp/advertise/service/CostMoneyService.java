package com.fzy.admin.fp.advertise.service;

import com.fzy.admin.fp.advertise.repository.CostMoneyRepository;
import com.fzy.admin.fp.advertise.domain.CostMoney;
import com.fzy.admin.fp.advertise.repository.CostMoneyRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/7/4 10:36
 * @Description
 */
@Service
public class CostMoneyService implements BaseService<CostMoney> {

    @Resource
    private CostMoneyRepository costMoneyRepository;

    @Override
    public BaseRepository<CostMoney> getRepository() {
        return costMoneyRepository;
    }

    public CostMoney findByOnIdAndTime(String id, String time) {
        return costMoneyRepository.findByOnIdAndDay(id, time);
    }

    //附表当天日期
    public CostMoney saveOne(String onId, String time) {
        CostMoney costMoney = new CostMoney();
        costMoney.setClickNumber(0);
        costMoney.setDay(time);
        costMoney.setOnId(onId);
        costMoney.setRunNumber(0);
        costMoney.setShowNumber(0);
        return costMoneyRepository.save(costMoney);
    }

    //对附表进行的一类操作
    public void updateOneByDay(String id, Integer status) {

        //附表当天计数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        CostMoney costMoney = costMoneyRepository.findByOnIdAndDay(id, time);
        if (costMoney == null) {
            //新建当天表
            costMoney = saveOne(id, time);
        }

        if (status.equals(1)) {
            //纯自动跳转模式
            int runNumber = costMoney.getRunNumber();
            runNumber += 1;
            costMoney.setRunNumber(runNumber);
            costMoneyRepository.save(costMoney);
        } else if (status.equals(2)) {
            //展示计数
            int showNumber = costMoney.getShowNumber();
            showNumber += 1;
            costMoney.setShowNumber(showNumber);
            costMoneyRepository.save(costMoney);
        } else if (status.equals(3)) {
            //手动点击计数
            int clickNumber = costMoney.getClickNumber();
            clickNumber += 1;
            costMoney.setClickNumber(clickNumber);
            costMoneyRepository.save(costMoney);
        }

    }


    //数据分析返回每天的个数情况
    public List<CostMoney> findByOnIdAndCreateTime(String onId, Date time1, Date time2) {

        return costMoneyRepository.findByOnIdAndCreateTimeBetween(onId, time1, time2);
    }


}
