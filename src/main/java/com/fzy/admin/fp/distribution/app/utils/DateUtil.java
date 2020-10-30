package com.fzy.admin.fp.distribution.app.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hzq
 * @Description:  时间工具类
 */
@Slf4j
public class DateUtil {

    /**
     * 计算时间类型
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static int hourMinuteBetween(long nowTime, long startTime, long endTime){

        if(nowTime >= startTime && nowTime <= endTime){
            return 2;
        }else if(nowTime >= endTime) {
            return 3;
        }
        return 1;
    }


    /**
     * 获取当天最大时间  23：59：59
     * new LocalTime(23, 59, 59, 999_999_999)
     * 不设置为最大的值，因为设置为最大的值后，mysql（有些版本的）会对插入的时间的毫秒值大于500的进位操作，所以在此地设置毫秒值为0.
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.of(23,59,59,0));
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }


    public static boolean isSameDate(Date date1, Date date2) {
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                    .get(Calendar.YEAR);
            boolean isSameMonth = isSameYear
                    && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            return isSameMonth;
        } catch (Exception e) {
            log.error("[RatingEngine] error occurred: ERROR ", e);
        }
        return false;
    }
}
