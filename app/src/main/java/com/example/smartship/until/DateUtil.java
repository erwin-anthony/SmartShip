package com.example.smartship.until;


import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    //获取当前日期 yyyy-MM-dd
    public static String getTodayData() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }

    //获取当前日期 yyyy-MM-dd HH:mm
    public static String getTodayData_2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }

    //获取当前日期 yyyy-MM-dd HH:mm:ss
    public static String getTodayData_3() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }

    //获取当前时间，例如：10月26日 19:29
    public static String getCurTime(Context context) {
        String time_refresh = DateUtils.formatDateTime(
                context,
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL
        );
        return time_refresh;
    }

    //获取下一个月
    public static String getNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }

    /*时间戳转换为距离现在的时间*/
    public static String getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    //获得每月的天数
    public static int getDay(int year, int month) {
        int day = 0;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                if (flag) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    //将秒值转换为时分秒 00:00:01
    public static String ssToHMS(long totalSecond) {
        if (totalSecond < 60) {
            return "00:00:" + getFullTime(totalSecond);
        } else {
            long second = totalSecond % 60;
            long totalMinute = totalSecond / 60;
            if (totalMinute < 60) {
                return "00:" + getFullTime(totalMinute) + ":" + getFullTime(second);
            } else {
                long minute = totalMinute % 60;
                long totalHour = totalMinute / 60;
                return getFullTime(totalHour) + ":" + getFullTime(minute) + ":" + getFullTime(second);
            }
        }

    }

    //将秒值转换为时分秒 00:01、01:01、01:01:01
    public static String ssToHMS2(long totalSecond) {
        if (totalSecond < 60) {
            return "00:" + getFullTime(totalSecond);
        } else {
            long second = totalSecond % 60;
            long totalMinute = totalSecond / 60;
            if (totalMinute < 60) {
                return getFullTime(totalMinute) + ":" + getFullTime(second);
            } else {
                long minute = totalMinute % 60;
                long totalHour = totalMinute / 60;
                return getFullTime(totalHour) + ":" + getFullTime(minute) + ":" + getFullTime(second);
            }
        }

    }

    /*
     * 将时分秒转为秒数
     *
     *  10:00:01  -> xxx 秒
     * */
    public static long formatTurnSecond(String time) {
        String s = time;
        int index1 = s.indexOf(":");
        int index2 = s.indexOf(":", index1 + 1);
        int hh = Integer.parseInt(s.substring(0, index1));
        int mi = Integer.parseInt(s.substring(index1 + 1, index2));
        int ss = Integer.parseInt(s.substring(index2 + 1));
        return hh * 60 * 60 + mi * 60 + ss;
    }


    //得到两位的数字，例如：5，转为05； 45还是45
    public static String getFullTime(long time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return time + "";
        }
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static String type_1 = "yyyy-MM-dd";
    public static String type_2 = "yyyy-MM-dd HH:mm:ss";
    public static String type_3 = "yyyy/MM/dd HH:mm";
    public static String type_4 = "yyyy年MM月dd日";

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    //比较指定时间与当前时间。当前的时间- 指定时间 = 差
    public static long dateTNow(String strTime1, String formatType) {
        return (new Date().getTime() - stringToDate(strTime1, formatType).getTime());
    }
}


