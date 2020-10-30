package com.fzy.admin.fp.auth.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DateUtil.offsetDay;

/**
 * 日期工具类
 * yy
 */

public class DateUtils {

    /**
     * 得到当月1号的日期
     */
    public static Date initDateByMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     * 返回上个月日期
     * yy
     *
     * @param time
     * @return
     */
    public static Date getLastMonth(Date time) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(time);
        c1.add(Calendar.MONTH, -1);
        return c1.getTime();
    }

    /**
     * 返回上一天日期
     *
     * @return
     */
    public static Date getYesterday(Date time) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(time);
        c1.add(Calendar.DATE, -1);
        return c1.getTime();
    }

    /**
     * 返回上一个时间段时间
     *
     * @return
     */
    public static Date getLastday(Date time, int num) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(time);
        c1.add(Calendar.DATE, -num);
        return c1.getTime();
    }

    /**
     * 得到两个日期相差的天数
     * yy
     *
     * @param start
     * @return
     */
    public static long getDay(Date start, Date end) {
        long days = (start.getTime() - end.getTime()) / 1000 / 3600 / 24;
        return days;
    }

    /**
     * 得到date前num天的日期
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDayByNum(Date date, int num) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }

    //得到日期表
    public static List<String> dateRangeList(Date startDate, Date endDate, DateField dateField, String format) {
        List<DateTime> range = DateUtil.rangeToList(startDate, endDate, dateField);
        List<String> xData = range.stream().map(dateTime -> DateUtil.format(dateTime, format)).collect(Collectors.toList());
        return xData;
    }

    //得到cron时间表达式
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return fmtDateToStr(date, dateFormat);
    }

    //转化时间
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //转化时间

    /**
     * yy 2020-3-2 16:57:58
     *
     * @param date
     * @param dtFormat
     * @return
     * @throws ParseException
     */
    public static Date fmtStrToDate(String date, String dtFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dtFormat);
        return simpleDateFormat.parse(date);
    }

    /**
     * 获取指定日期最后一天
     */
    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        // cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.MONTH, month); //设置当前月的上一个月
        // 获取某月最大天数
        //int lastDay = cal.getActualMaximum(Calendar.DATE);
        int lastDay = cal.getMinimum(Calendar.DATE); //获取月份中的最小值，即第一天
        // 设置日历中月份的最大天数
        //cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    /**
     * 获得某天最小时间 2020-02-17 00:00:00
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天最大时间 2020-02-19 23:59:59
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }


}
