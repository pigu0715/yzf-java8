package com.tydic.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
    public static SimpleDateFormat dfm = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    private static String firstDay;// 第一天
    private static String lastDay;//  最后一天

    public static String getNowTime() {
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /***
     * @return "yyyy-MM-dd %H:%i:%s"
     */
    public static String getNowDteailTime() {
        return df1.format(new Date());// new Date()为获取当前系统时间
    }

    /***
     * @return "yyyy年MM月dd日%H:%i:%s"
     */
    public static String getNowDteailTime2() {
        return df2.format(new Date());// new Date()为获取当前系统时间
    }

    /***
     * @return "yyyy年MM月dd日%H:%i:%s"
     * @throws Exception
     */
    public static String getNowDteailTime2(String time) throws Exception {
        Date date = null;
        try {
            date = df2.parse(time);
        } catch (ParseException e) {
            logger.error("日期格式转换错误：" + time);
            e.printStackTrace();
            throw new Exception("期格式转换错误：" + time);
        }
        return df2.format(date);// new Date()为获取当前系统时间
    }

    /**
     *
     * @param date     yyyyMMddHHmmss
     * @return         yyyy-MM-dd HH:mm:ss
     * @throws Exception
     */
    public static String getSpecDate(String date) throws Exception {
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            logger.error("日期格式转换错误：" + date);
            e.printStackTrace();
            throw new Exception("期格式转换错误：" + date);
        }
        return df1.format(d);
    }

    public static String getNowTimeMs() {
        return dfm.format(new Date());// new Date()为获取当前系统时间
    }

    public static long getTimestamp(String date) {
        return (new SimpleDateFormat("yyyyMMddHHmmss")).parse(date, new ParsePosition(0)).getTime() / 1000;// new Date()为获取当前系统时间
    }

    /**
     * @Title: paramDateFormatCheck @Description: 校验时间格式 @param: @param
     * params @param: @param fieldArr @param: @return @return:
     * Map<String,Object> @throws
     */
    public static Map<String, Object> paramDateFormatCheck(Map<String, Object> params, String fieldArr, int t) {
        Map<String, Object> returnArr = new HashMap<String, Object>();
        int ReturnCode = 0;
        String ReturnMsg = "success";
        String regex;
        switch (t) {
            case 1:
                regex = "^[0-9]{1,4}-(1(?![3456789])|0(?!0))[0-9]-[0-9]{2} ([01]|2(?![56789]))[0-9]([:][0-5][0-9]){2}$";
                break;
            case 2:
                regex = "^[1-9][0-9]{3}(0[1-9]|1[0-2])(0[0-9]|[1-2][0-9]|3[0-1])((0|1)[0-9]|2[0-3])([0-5][0-9]){2}$";
                break;
            default:
                regex = "^[0-9]{1,4}-(1(?![3456789])|0(?!0))[0-9]-[0-9]{2} ([01]|2(?![56789]))[0-9]([:][0-5][0-9]){2}$";
        }
        String[] fieldStr = fieldArr.split(",");
        for (int i = 0; i < fieldStr.length; i++) {
            if (params.containsKey(fieldStr[i] + "")) {
                String temp = (String) params.get(fieldStr[i]);
                if (!Tools.isNull(temp)) {
                    boolean bool = Pattern.matches(regex, temp);
                    //截取传入的字符串，分为年月日
                    if (bool) {
                        int year = DataTransUtil.strToInt(temp.substring(0, 4));
                        int month = DataTransUtil.strToInt(temp.substring(4, 6));
                        int day = DataTransUtil.strToInt(temp.substring(6, 8));
                        int hour = DataTransUtil.strToInt(temp.substring(8, 10));
                        int minute = DataTransUtil.strToInt(temp.substring(10, 12));
                        int second = DataTransUtil.strToInt(temp.substring(12, 14));
                        //毫秒
                        //int msec  =DataTransUtil.strToInt(temp.substring(14, 17)) ;
                        bool = dateCheck(year, month, day);
                    }
                    if (!bool) {
                        ReturnCode = 2007; // 2007:日期格式不正确
                        ReturnMsg = "日期格式不正确 :" + fieldStr[i];
                    }
                }
            }
        }
        returnArr.put("ReturnCode", ReturnCode);
        returnArr.put("ReturnMsg", ReturnMsg);
        return returnArr;
    }

    /**
     * 时间格式校验2
     *
     * @param t（长度大于14才行）
     * @return
     */
    public static boolean paramDateFormatCheck(String t) {
        if (t.length() < 14) {
            return false;
        }
        try {
            int year = DataTransUtil.strToInt(t.substring(0, 4));
            int month = DataTransUtil.strToInt(t.substring(4, 6));
            int day = DataTransUtil.strToInt(t.substring(6, 8));
            int hour = DataTransUtil.strToInt(t.substring(8, 10));
            int minute = DataTransUtil.strToInt(t.substring(10, 12));
            int second = DataTransUtil.strToInt(t.substring(12, 14));
            return dateCheck(year, month, day, hour, minute, second);
        } catch (Exception e) {
            return false;
        }
        //毫秒
        //int msec  =DataTransUtil.strToInt(temp.substring(14, 17)) ;
    }

    /**
     * 校验传入的年份、月份、日期、小时、分钟、秒取值合法性
     *
     * @param year（年）
     * @param month（月）
     * @param day（日）
     * @param hour（时）
     * @param minute（分）
     * @param second（秒）
     * @return
     */
    private static boolean dateCheck(int year, int month, int day, int hour, int minute, int second) {
        if (dateCheck(year, month, day)) {
            if (hour > 24 || hour < 0) {
                return false;
            }
            if (minute > 60 || minute < 0) {
                return false;
            }
            if (second > 60 || second < 0) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 校验传入的年份、月份、日期取值合法性
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static boolean dateCheck(int year, int month, int day) {
        logger.info("year: " + year + ", month: " + month + ", day: " + day);
        if (year <= 0) {
            return false;
        }

        if (month < 1 || month > 12) {
            return false;
        }

        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) {
            if (day > 0 && day <= 31) {
                return true;
            } else {
                return false;
            }
        } else if ((month == 4 || month == 6 || month == 9 || month == 11)) {
            if (day > 0 && day <= 30) {
                return true;
            } else {
                return false;
            }
        } else {
            if (((year % 4) == 0 && (year % 100) != 0) || year % 400 == 0) {
                if (day > 0 && day <= 29) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (day > 0 && day <= 28) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * @throws
     * @Title: getLastMonth
     * @Description: 获取上个月时间
     * @param: @return
     * @return: String
     */
    public static String getLastMonth() {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyyMM");
        return formatters.format(today);
    }

    /**
     * @throws
     * @Title: getLastMonth
     * @Description: 获取当前月份
     * @param: @return
     * @return: String
     */
    public static String getNowMonth() {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(0);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyyMM");
        return formatters.format(today);
    }

    public static Date getNextMonth() {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(-1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyyMM");

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = today.atStartOfDay(zoneId);

        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /***
     * 获取任意指定月的上个月
     * @param nDate (长整型-yyyyMM)
     * @return (长整型 - yyyyMM)
     */
    public static long getAnyLastMonth(long nDate) {
        Long lastDate = nDate;
        if (lastDate % 100 == 1) {
            lastDate = nDate - 89;
        } else {
            lastDate = nDate - 1;
        }
        return lastDate;
    }

    /***
     * 获取任意指定月的下个月
     * @param nDate (长整型-yyyyMM)
     * @return (长整型 - yyyyMM)
     */
    public static long getAnyNextMonth(long nDate) {
        Long lastDate = nDate + 1;
        if (lastDate % 100 == 13) {
            lastDate += 88;
        }
        return lastDate;
    }

    /**
     * 获取时间段内所有月份,例（201803~201807:传出LIST:[201803,201804,201805,201806,201807]）
     *
     * @param Begin
     * @param End
     * @return
     */
    public static List<String> findDates(String Begin, String End) throws ParseException {
        Date dBegin = format.parse(Begin);
        Date dEnd = format.parse(End);
        List<String> lDate = new ArrayList();
        lDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            lDate.add(sdf.format(calBegin.getTime()));
        }
        lDate.remove(lDate.size() - 1);
        return lDate;
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static String getNowMonthFistDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return first;
    }

    /**
     * 获取当前月最后一天
     *
     * @return
     */
    public static String getNowMonthLastDay() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }

    public static String getNowMsCN(String dateStr) {
        Date date = new Date();//获取此时的系统时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
        return sdf.format(date);
    }

    /**
     * 获取当前月的前六个月时间
     *
     * @return
     */
    public static String getNowMonthSixMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -5);
        Date m = c.getTime();
        return format.format(m);
    }

    /**
     * 获取当前时间两年前的时间
     *
     * @return
     */
    public static String getNowTwoYearAgo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -2);
        Date m = c.getTime();
        return format.format(m);
    }

    /***
     *  秒转 小时：分钟：秒
     * @param seconds
     * @return
     */
    public static String transSeconds(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days) {
            return days + "天，" + hours + ":" + minutes + ":" + second;
        } else {
            return hours + ":" + minutes + ":" + second;
        }
    }


    /**
     * 获取指定月第一天
     *
     * @return
     */
    public static String getMonthFistDay(String billingCycleId) {
        Calendar c = Calendar.getInstance();
        try {
            Date date = yyyyMMdd.parse(billingCycleId+"01");
            c.setTime(date);
        } catch (ParseException e) {
            logger.error("getMonthFistDay error!",e);
        }
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = yyyyMMdd.format(c.getTime());
        return first;
    }

    /**
     * 获取指定月最后一天
     *
     * @return
     */
    public static String getMonthLastDay(String billingCycleId) {
        Calendar c = Calendar.getInstance();
        try {
            Date date = yyyyMMdd.parse(billingCycleId+"01");
            c.setTime(date);
        } catch (ParseException e) {
            logger.error("getMonthLastDay error!",e);
        }
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = yyyyMMdd.format(c.getTime());
        return last;
    }

    public static String getCurrentDateTime(){
		Calendar nowtime = new GregorianCalendar();
		String strDateTime=String.format("%04d", nowtime.get(Calendar.YEAR))+
				String.format("%02d", nowtime.get(Calendar.MONTH)+1)+
				String.format("%02d", nowtime.get(Calendar.DATE))+
				String.format("%02d", nowtime.get(Calendar.HOUR_OF_DAY))+
				String.format("%02d", nowtime.get(Calendar.MINUTE))+
				String.format("%02d", nowtime.get(Calendar.SECOND));
		System.out.println(strDateTime);
		return strDateTime;			
	}
    
    public static String getTimeFormatyyyyMMddHHmmss(String dateStr){
		String dateFormat = "";
		//接收String类型时间的格式
		SimpleDateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//输出Date类型时间的字符串格式
		SimpleDateFormat dateToStringFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			Date today = stringToDateFormat.parse(dateStr);
			dateFormat = dateToStringFormat.format(today);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return dateFormat;
	}
    
    /**
	   * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	   * 
	   * @param dateDate
	   * @return
	   */
	public static String dateToStrLong(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}
}