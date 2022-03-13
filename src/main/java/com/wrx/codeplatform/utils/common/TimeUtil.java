package com.wrx.codeplatform.utils.common;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Luckily_Baby
 * @date 2018/5/3 20:15
 */
public class TimeUtil {

    /**
     * 计算和当日的天数差距
     * @param oldTime 当前日期
     * @param newTime 之前日期
     * @return 相差天数
     */
    public static int daysBetween(String oldTime, String newTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(oldTime));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(newTime));
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * 计算和现在的分钟差距
     * @param oldTime 以前时间
     * @param newTime 现在时间
     * @return 分钟数
     * @throws ParseException 转换错误
     */
    public static double minuteBetween(String oldTime,String newTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nTime =df.parse(newTime).getTime();
        long oTime = df.parse(oldTime).getTime();
        long diff=(nTime-oTime)/1000/60;
        if(diff < 1){
            return 0;
        }
        DecimalFormat dfs = new DecimalFormat("#.00");
        return Double.parseDouble(dfs.format(diff));
    }

    /**
     * 计算两个日期相差的秒数
     *
     * @param startDate 开始世家
     * @param endDate 介绍时间
     * @return 秒
     */
    public static int calLastedTime(Date startDate, Date endDate) {
        long endDateTime = endDate.getTime();
        long startDateTime = startDate.getTime();
        return (int) ((endDateTime - startDateTime) / 1000);
    }


    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    /**
     * 判断是否为同一天
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 是否为同一天
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

}
