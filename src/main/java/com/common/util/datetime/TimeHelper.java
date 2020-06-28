package com.common.util.datetime;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Class TimeHelper
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2018/12/21 09:26
 * @Copyright Copyright by 智多星
 * @Direction 类说明
 */
public class TimeHelper {


    public static TimeHelper time = new TimeHelper();



    /**
     * 得到100年后的时间，100年即为永恒
     * @return
     */
    public static Date getForeverTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);
        return calendar.getTime();
    }

    /**
     * 获得n小时后的时间
     * @param hour
     * @return
     */
    public static Date getAfterHourTime(int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * @param hour
     * @return
     */
    public static Date getBeforeHourTime(int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -hour);
        return calendar.getTime();
    }

    /**
     * 获得n秒钟后的时间
     * @param second
     * @return
     */
    public static Date getAfterSecondTime(int second){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }
    /**
     * 获得n分钟后的时间
     * @param minute
     * @return
     */
    public static Date getAfterMinuteTime(int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**获得n分钟前的时间
     * @param minute
     * @return
     */
    public static Date getBeforeMinuteTime(int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -minute);
        return calendar.getTime();
    }

    /**
     * 获得n天后的时间
     * @param day
     * @return
     */
    public static Date getAfterDayTime(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**获得n天前的时间
     * @param day
     * @return
     */
    public static Date getBeforeDayTime(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -day);
        return calendar.getTime();
    }

    /**
     *当前时间
     * @return
     */
    public static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获得当前时间的格式化时间戳
     * @return
     */
    public static String getCurrentTimeStr(){
        return format(getCurrentDate() , null );
    }

    /**
     * 比较两个日期的大小,不比较小时、分钟、秒
     * <p>
     * date1大于date2返回1,date1等于date2返回0,date1小于date2返回-1.
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @return 日期的大小
     */
    public static int compareTo(Date date1, Date date2) {
        String strDate1 = getStandardDate(date1);
        String strDate2 = getStandardDate(date2);
        date1 = format(strDate1, "yyyy-MM-dd");
        date2 = format(strDate2, "yyyy-MM-dd");
        return date1.compareTo(date2);
    }
    /**
     * 计算两个日期的间隔天数
     * <p>
     * otherDate在baseDate之后返回N天数,baseDate等于otherDate返回0天,otherDate在baseDate之前返回-N天数.
     *
     * @param baseDate
     *            基准日期
     * @param otherDate
     *            日期2
     * @return 日期的天数
     */
    public static int distance(Date baseDate, Date otherDate) {
        String strDate1 = getStandardDate(baseDate);
        String strDate2 = getStandardDate(otherDate);
        baseDate = format(strDate1, "yyyy-MM-dd");
        otherDate = format(strDate2, "yyyy-MM-dd");
        GregorianCalendar baseCal = new GregorianCalendar();
        GregorianCalendar otherCal = new GregorianCalendar();
        baseCal.setTime(baseDate);
        otherCal.setTime(otherDate);
        long gap = (otherCal.getTimeInMillis()-baseCal.getTimeInMillis())/(1000*3600*24);//从间隔毫秒变成间隔天数</strong>
        return (int) gap;
    }

    /**
     * 获取标准日期（年-月-日）
     *
     * @param date
     *            日期对象
     * @return 标准日期字符串
     */
    public static String getStandardDate(Date date) {
        if (date == null)
            date = new Date();
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = new String(smdf.format(date));
        return strDate;
    }

    /**
     * 获取年份
     * @param date
     * @return
     */
    public static int getYear(Date date){
        if (date == null) date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取月份
     * @param date
     * @return
     */
    public static int getMonth(Date date){
        if (date == null) date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 将指定格式的时间字符串解析成时间对象
     * @param strDate
     *            时间字符串
     * @param format
     *            时间格式,默认格式为"yyyy-MM-dd HH:mm:ss"
     * @return 时间对象
     */
    public static Date format(String strDate, String format) {
        if (StringUtils.isBlank( format )) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将日期转换成字符串
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date,String format){
        if (StringUtils.isBlank( format )) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TimeHelper instance(){
        return time;
    }

}
