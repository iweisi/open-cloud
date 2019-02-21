package com.github.lyd.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 日期工具类,
 * 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author Liuyadu
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {


    private static final long ONE_MILLIS = 1000;
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;


    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM"};

    /**
     * 日期型字符串转化为日期 格式
     * {
     * "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
     * "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM"}
     */
    public static Date parseDate(String str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String formatDate() {
        return formatDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (pattern != null) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }


    /**
     * 获取当前时间戳（yyyyMMddHHmmss）
     *
     * @return nowTimeStamp
     */
    public static long getTimestamp() {
        long nowTimeStamp = Long.parseLong(getTimestampStr());
        return nowTimeStamp;
    }

    /**
     * 获取当前时间戳（yyyyMMddHHmmss）
     *
     * @return
     */
    public static String getTimestampStr() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取Unix时间戳
     *
     * @return
     */
    public static long getUnixTimestamp() {
        long nowTimeStamp = System.currentTimeMillis() / 1000;
        return nowTimeStamp;
    }

    /**
     * 获取Unix时间戳
     *
     * @return
     */
    public static String getUnixTimestampStr() {
        return String.valueOf(getUnixTimestamp());
    }

    /**
     * 转换Unix时间戳
     *
     * @return nowTimeStamp
     */
    public static long parseUnixTimeStamp(long time) {
        return time / ONE_MILLIS;
    }

    /**
     * 获取上周
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getLastWeek(Date date, String pattern) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        //一周
        cl.add(Calendar.WEEK_OF_YEAR, -1);
        Date dateFrom = cl.getTime();
        return formatDate(dateFrom, pattern);
    }

    /**
     * 获取昨天
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getLastDay(Date date, String pattern) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        //一天
        cl.add(Calendar.DAY_OF_YEAR, -1);
        Date dateFrom = cl.getTime();
        return formatDate(dateFrom, pattern);
    }

    /**
     * 获取上个月
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getLastMouth(Date date, String pattern) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        //一个月
        cl.add(Calendar.MONTH, -1);
        Date dateFrom = cl.getTime();
        return formatDate(dateFrom, pattern);
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long getAgoDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (ONE_DAY * ONE_MILLIS);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long getAgoHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (ONE_HOUR * ONE_MILLIS);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long getAgoMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (ONE_MINUTE * ONE_MILLIS);
    }

    /**
     * 获取过去的秒
     *
     * @param date
     * @return
     */
    public static long getAgoSecond(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / ONE_MILLIS;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (ONE_MILLIS * ONE_DAY);
    }

    /**
     * 距离今天多久
     *
     * @param date
     * @return
     */
    public static String formatFromToday(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            long time = date.getTime() / ONE_MILLIS;
            long now = System.currentTimeMillis() / ONE_MILLIS;
            long ago = now - time;
            if (ago <= ONE_HOUR) {
                return ago / ONE_MINUTE + "分钟前";
            } else if (ago <= ONE_DAY) {
                return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE)
                        + "分钟前";
            } else if (ago <= ONE_DAY * 2) {
                return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else if (ago <= ONE_DAY * 3) {
                return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else if (ago <= ONE_MONTH) {
                long day = ago / ONE_DAY;
                return day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else if (ago <= ONE_YEAR) {
                long month = ago / ONE_MONTH;
                long day = ago % ONE_MONTH / ONE_DAY;
                return month + "个月" + day + "天前"
                        + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else {
                long year = ago / ONE_YEAR;
                // JANUARY which is 0 so month+1
                int month = calendar.get(Calendar.MONTH) + 1;
                return year + "年前" + month + "月" + calendar.get(Calendar.DATE)
                        + "日";
            }

        } else {
            return "";
        }

    }

    /**
     * 距离今天多久
     *
     * @param createAt
     * @return
     */
    public static String formatFromTodayCn(Date createAt) {
        // 定义最终返回的结果字符串。
        String interval = null;
        if (createAt == null) {
            return "";
        }
        long millisecond = System.currentTimeMillis() - createAt.getTime();

        long second = millisecond / ONE_MILLIS;

        if (second <= 0) {
            second = 0;
        }
        //*--------------微博体（标准）
        if (second == 0) {
            interval = "刚刚";
        } else if (second < ONE_MINUTE/2) {
            interval = second + "秒以前";
        } else if (second >= ONE_MINUTE/2 && second < ONE_MINUTE) {
            interval = "半分钟前";
        } else if (second >= ONE_MINUTE && second < ONE_MINUTE * ONE_MINUTE) {
            //大于1分钟 小于1小时
            long minute = second / ONE_MINUTE;
            interval = minute + "分钟前";
        } else if (second >= ONE_HOUR && second < ONE_DAY) {
            //大于1小时 小于24小时
            long hour = (second / ONE_MINUTE) / ONE_MINUTE;
            interval = hour + "小时前";
        } else if (second >= ONE_DAY && second <= ONE_DAY * 2) {
            //大于1D 小于2D
            interval = "昨天" + formatDate(createAt, "HH:mm");
        } else if (second >= ONE_DAY * 2 && second <= ONE_DAY * 7) {
            //大于2D小时 小于 7天
            long day = ((second / ONE_MINUTE) / ONE_MINUTE) / 24;
            interval = day + "天前";
        } else if (second <= ONE_DAY * 365 && second >= ONE_DAY * 7) {
            //大于7天小于365天
            interval = formatDate(createAt, "MM-dd HH:mm");
        } else if (second >= ONE_DAY * 365) {
            //大于365天
            interval = formatDate(createAt, "yyyy-MM-dd HH:mm");
        } else {
            interval = "0";
        }
        return interval;
    }


    /**
     * 距离截止日期还有多长时间
     *
     * @param date
     * @return
     */
    public static String formatFromDeadline(Date date) {
        long deadline = date.getTime() / ONE_MILLIS;
        long now = (System.currentTimeMillis()) / ONE_MILLIS;
        long remain = deadline - now;
        if (remain <= ONE_HOUR) {
            return "只剩下" + remain / ONE_MINUTE + "分钟";
        } else if (remain <= ONE_DAY) {
            return "只剩下" + remain / ONE_HOUR + "小时"
                    + (remain % ONE_HOUR / ONE_MINUTE) + "分钟";
        } else {
            long day = remain / ONE_DAY;
            long hour = remain % ONE_DAY / ONE_HOUR;
            long minute = remain % ONE_DAY % ONE_HOUR / ONE_MINUTE;
            return "只剩下" + day + "天" + hour + "小时" + minute + "分钟";
        }

    }


    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (ONE_DAY * ONE_MILLIS);
        long hour = (timeMillis / (ONE_HOUR * ONE_MILLIS) - day * 24);
        long min = ((timeMillis / (ONE_MINUTE * ONE_MILLIS)) - day * 24 * ONE_MINUTE - hour * ONE_MINUTE);
        long s = (timeMillis / ONE_MILLIS - day * 24 * ONE_MINUTE * ONE_MINUTE - hour * ONE_MINUTE * ONE_MINUTE - min * ONE_MINUTE);
        long sss = (timeMillis - day * 24 * ONE_MINUTE * ONE_MINUTE * ONE_MILLIS - hour * ONE_MINUTE * ONE_MINUTE * ONE_MILLIS - min * ONE_MINUTE * ONE_MILLIS - s * ONE_MILLIS);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }


    /**
     * Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String unixTimeStamp2Date(String timestampString, String formats) {
        if (StringUtils.isBlank(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * ONE_MINUTE;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 日期格式字符串转换成Unix时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2UnixTimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / ONE_MINUTE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 毫秒转化时分秒毫秒 10000 - 10秒
     *
     * @param ms
     * @return
     */
    public static String formatTime(Long ms) {
        long ss = ONE_MINUTE;
        long mi = ss * ONE_MINUTE;
        long hh = mi * ONE_MINUTE;
        long dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分钟");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println(getLastDay(new Date(), "yyyy-MM-dd"));
            System.out.println(getLastWeek(new Date(), "yyyy-MM-dd"));
            System.out.println(getLastMouth(new Date(), "yyyy-MM-dd"));
            System.out.println(formatTime(10000L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
