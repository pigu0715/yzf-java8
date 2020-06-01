package com.tydic.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

public class StringCommon
{
  public static boolean isNull(String str)
  {
    if ((str == null) || (str.equals("")) || (str.equalsIgnoreCase("null"))) {
      return true;
    }
    return false;
  }
  
  public static boolean isNull(String... strArray) {
	  
	  for (String str : strArray) {
		  if(isNull(str)) {
			  return true;
		  }	
	  }
	  return false;
  }

  public static String getdecimalWithZero(String value)
  {
    if (value.startsWith("."))
    {
      return "0" + value;
    }

    return value;
  }

  public static String getLastMonth(String thisMonth)
  {
    if (thisMonth.length() == 6) {
      int year = Integer.valueOf(thisMonth.substring(0, 4)).intValue();
      int month = Integer.valueOf(thisMonth.substring(4)).intValue();
      if ((month <= 12) && (month >= 2)) {
        if (month <= 10) {
          return String.valueOf(year) + "0" + String.valueOf(month - 1);
        }
        return String.valueOf(year) + String.valueOf(month - 1);
      }

      return String.valueOf(year - 1) + "12";
    }

    return thisMonth;
  }

  public static String geMonthDay(String thisMonth)
  {
    if (thisMonth.length() == 6) {
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
      Calendar calendar = new GregorianCalendar();

      int day = 30;
      try {
        Date date = format.parse(thisMonth + "01");

        calendar.setTime(date);
        day = calendar.getActualMaximum(5);
      }
      catch (ParseException e) {
        e.printStackTrace();
      }

      return String.valueOf(day);
    }

    return "30";
  }

  public static String trimNull(String str)
  {
    if ((str == null) || (str.equalsIgnoreCase("null"))) {
      return "";
    }
    return str.trim();
  }

  public static boolean isInt(String str)
  {
    if ((str == null) || (str.equals("")) || (str.equalsIgnoreCase("null"))) {
      return false;
    }
    return str.matches("^\\+?\\-?[\\d]+$");
  }

  public static boolean isNumber(String str)
  {
    if ((str == null) || (str.equals("")) || (str.equalsIgnoreCase("null"))) {
      return false;
    }
    return str.matches("(^\\+?\\-?[\\d]+[.]?[\\d]+$)||(^\\+?\\-?[\\d]+$)");
  }

  public static boolean isPercent(String str)
  {
    if ((str == null) || (str.equals("")) || (str.equalsIgnoreCase("null"))) {
      return false;
    }
    return str.matches("^\\+?\\-?\\d+\\.?\\d*\\%?$");
  }

  public static int getChineseNum(String context)
  {
    int lenOfChinese = 0;
    Pattern p = Pattern.compile("[一-龥]");
    Matcher m = p.matcher(context);
    while (m.find()) {
      lenOfChinese++;
    }
    return lenOfChinese;
  }

  public static String getRamCharAndNumr(int length)
  {
    String val = "";
    Random random = new Random();
    for (int i = 0; i < length; i++)
    {
      String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

      if ("char".equalsIgnoreCase(charOrNum))
      {
        int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
        val = val + (char)(choice + random.nextInt(26));
      } else if ("num".equalsIgnoreCase(charOrNum)) {
        val = val + String.valueOf(random.nextInt(10));
      }
    }
    return val;
  }
  
  public static Map<String,Object> getExecReturn() {
		
	  	Map<String,Object> ExceObject= new HashMap<String,Object>();
		ExceObject.put("resultCode", 11007);
		ExceObject.put("resultMsg", "系统繁忙，暂时不能提供服务");
		return ExceObject;
	}
  public static void main(String[] args) {
	
	  System.out.println(getRamCharAndNumr(8));
}
}