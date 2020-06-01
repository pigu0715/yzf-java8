package com.tydic.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings(value={"unchecked","rawtypes","unused"})
public class Tools {

	/**
	 * @param objs
	 * @return boolean 是否为空
	 */
	public static boolean isNull(Object[] objs) {
		if (null == objs || objs.length <= 0) {
			return true;
		}
		/* String[] */
		if (objs instanceof String[]) {
			return checkDate((String[]) objs);
		}
		/* Object[] */
		if (objs instanceof Object[]) {
			return checkObjects((Object[]) objs);
		}
		return false;
	}

	/**
	 * @param obj
	 * @return boolean 是否为空
	 */
	public static boolean isNull(Object obj) {
		/* 为空 */
		if (obj == null)
			return true;
		/* String */
		if (obj instanceof String) {
			return checkString((String) obj);
		}
//		/* Integer */
//		if (obj instanceof Integer) {
//			return checkInteger((Integer) obj);
//		}
//		/* Long */
//		if (obj instanceof Long) {
//			return checkLong((Long) obj);
//		}
//		/* Double */
//		if (obj instanceof Double) {
//			return checkDouble((Double) obj);
//		}
		/* Date */
		if (obj instanceof Date) {
			return checkDate((Date) obj);
		}
		/* List */
		if (obj instanceof List) {
			return checkList((List) obj);
		}
		/* Set */
		if (obj instanceof Set) {
			return checkSet((Set) obj);
		}
		/* Map */
		if (obj instanceof Map) {
			return checkMap((Map) obj);
		}
//		if (obj instanceof BigDecimal) {
//			return BigDecimal((BigDecimal) obj);
//		}

		return false;
	}

	public static boolean isEmpty(Object... objs) {
		for (Object object : objs) {
			if(isNull(object)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNumber(String str) {  
		if(isNull(str)) {
			return false;
		}
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}
	
	
	/**
	 * 时间跨度
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param type
	 * @return
	 */
	public static long countDay(String startTime, String endTime, String format,int type) {
		 
		//按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数long diff;try {
		
		//获得两个时间的毫秒时间差异
		long spanDate  = 0;
		try {
			long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			switch(type){
				case Calendar.DATE : 
					spanDate = diff/nd;
					break;
				case Calendar.HOUR :
					spanDate = diff/nh;
					break;
				case Calendar.MINUTE :
					spanDate = diff/nm;
					break;
				case Calendar.SECOND :
					spanDate = diff/ns;
					break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return spanDate;
	}

	private static boolean BigDecimal(BigDecimal bigDecimal) {
		if (null == bigDecimal)
			return true;
		return false;
	}

	private static boolean checkList(List list) {
		if (list.size() <= 0)
			return true;
		return false;
	}

	private static boolean checkObjects(Object[] objs) {
		if (objs.length <= 0)
			return true;
		return false;
	}

	private static boolean checkSet(Set set) {
		if (set.isEmpty() || set.size() <= 0)
			return true;
		return false;
	}

	private static boolean checkMap(Map set) {
		if (set.isEmpty() || set.size() <= 0)
			return true;
		return false;
	}

	private static boolean checkDate(String[] strings) {
		if (strings.length <= 0)
			return true;
		return false;
	}

	private static boolean checkDate(Date date) {
		if (date == null) {
			return true;
		}
		return false;
	}

	private static boolean checkDouble(Double double1) {
		if (double1.doubleValue() == 0) {
			return true;
		}
		return false;
	}

	private static boolean checkLong(Long long1) {
		if (long1.longValue() == 0 || long1.longValue() == -1L) {
			return true;
		}
		return false;
	}

	private static boolean checkInteger(Integer integer) {
		if (integer.intValue() == -1) {
			return true;
		}
		return false;
	}

	private static boolean checkString(String string) {
		if (string.trim().length() <= 0 || "null".equalsIgnoreCase(string)) {
			return true;
		}
		return false;
	}

	public static Class gatAttrClassType(Object obj) {

		/* String */
		if (obj instanceof String) {
			return String.class;
		}
		/* Integer */
		if (obj instanceof Integer) {
			return Integer.class;
		}
		/* Long */
		if (obj instanceof Long) {
			return Long.class;
		}
		/* Double */
		if (obj instanceof Double) {
			return Double.class;
		}
		/* Date */
		if (obj instanceof Date) {
			return Date.class;
		}
		/* Date */
		if (obj instanceof java.sql.Date) {
			return java.sql.Date.class;
		}
		/* Timestamp */
		if (obj instanceof java.sql.Timestamp) {
			return java.sql.Timestamp.class;
		}
		/* List */
		if (obj instanceof List) {
			return List.class;
		}
		/* Set */
		if (obj instanceof Set) {
			return Set.class;
		}
		/* Map */
		if (obj instanceof Map) {
			return Map.class;
		}
		if (obj instanceof BigDecimal) {
			return BigDecimal.class;
		}

		if (obj instanceof Boolean) {
			return Boolean.class;
		}

		return Object.class;
	}

	public final static java.sql.Timestamp string2Time(String dateString,
			String forMat) {
		DateFormat dateFormat;

		dateFormat = new SimpleDateFormat(forMat, Locale.ENGLISH);// 设定格式
		dateFormat.setLenient(false);
		java.util.Date timeDate = null;
		try {
			timeDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());// Timestamp类型,timeDate.getTime()返回一个long型

		return dateTime;
	}

	/**
	 * 计算当天加减 获取 根据指定格式 计算指定类型
	 * 
	 * @param format
	 *            格式：如"yyyy-MM-dd"
	 * @param type
	 *            类型 如:Calendar.YEAR,Calendar.MONTH,Calendar.DATE
	 * @param add
	 *            具体家的值
	 * @return String
	 */
	public static String addDate(String format, int type, int add) {
		Calendar cal = Calendar.getInstance();
		int newYear = cal.get(Calendar.YEAR);
		int newMonth = cal.get(Calendar.MONTH);
		int newDay = cal.get(Calendar.DATE);
		int newHour = cal.get(Calendar.HOUR_OF_DAY);
		int newMinute = cal.get(Calendar.MINUTE);
		int newSecond = cal.get(Calendar.SECOND);
		switch (type) {
		case Calendar.YEAR:
			newYear += add;
			break;
		case Calendar.MONTH:
			newMonth += add;
			break;
		case Calendar.DATE:
			newDay += add;
			break;
		case Calendar.HOUR_OF_DAY:
			newHour += add;
			break;
		case Calendar.MINUTE:
			newMinute += add;
			break;
		case Calendar.SECOND:
			newSecond += add;
			break;
		}
		cal.set(newYear, newMonth, newDay, newHour, newMinute, newSecond);
		SimpleDateFormat simpDate = new SimpleDateFormat(format);
		return simpDate.format(cal.getTime());
	}

	/**
	 * 指定日期 指定格式 指定类型 计算时间
	 * 
	 * @param inDate
	 *            按照format格式指定的时间，如：2010-02-02
	 * @param format
	 *            格式：如"yyyy-MM-dd"
	 * @param type
	 *            类型 如:Calendar.YEAR,Calendar.MONTH,Calendar.DATE
	 * @param add
	 *            具体增加的值
	 * @return string
	 */
	public static String addDateByTime(String inDate, String format, int type,
			int add) {
		SimpleDateFormat simpDate = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(simpDate.parse(inDate));
			int newYear = cal.get(Calendar.YEAR);
			int newMonth = cal.get(Calendar.MONTH);
			int newDay = cal.get(Calendar.DATE);
			int newHour = cal.get(Calendar.HOUR_OF_DAY);
			int newMinute = cal.get(Calendar.MINUTE);
			int newSecond = cal.get(Calendar.SECOND);
			switch (type) {
			case Calendar.YEAR:
				newYear += add;
				break;
			case Calendar.MONTH:
				newMonth += add;
				break;
			case Calendar.DATE:
				newDay += add;
				break;
			case Calendar.HOUR_OF_DAY:
				newHour += add;
				break;
			case Calendar.MINUTE:
				newMinute += add;
				break;
			case Calendar.SECOND:
				newSecond += add;
				break;
			}
			cal.set(newYear, newMonth, newDay, newHour, newMinute, newSecond);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return simpDate.format(cal.getTime());
	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String InputStream2String(InputStream in, String encoding)
			throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = in.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "ISO-8859-1");
	}

	/**
	 * 根据制定类型转换业务类型
	 * 
	 * @param value
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public static Object setPropertyType(Object value, Class type)
			throws ParseException {

		/* Char */
		if (type == char.class) {
			value = Double.valueOf(value.toString());
		}
		/* String */
		if (type == String.class) {
			value = value.toString();
		}
		/* Short */
		if (type == Short.class || type == short.class) {
			value = Double.valueOf(value.toString());
		}
		/* Integer */
		if (type == Integer.class || type == int.class) {
			value = Integer.valueOf(value.toString());
		}
		/* Long */
		if (type == Long.class || type == long.class) {
			value = Long.valueOf(value.toString());
		}
		/* Float */
		if (type == Float.class || type == float.class) {
			value = Double.valueOf(value.toString());
		}
		/* Double */
		if (type == Double.class || type == double.class) {
			value = Double.valueOf(value.toString());
		}
		/* Boolean */
		if (type == Boolean.class || type == boolean.class) {
			value = Boolean.valueOf(value.toString());
		}
		/* Date */
		if (type == Date.class) {
			SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd",
					Locale.ENGLISH);
			simpDate.setLenient(false);
			java.util.Date timeDate = simpDate.parse(value.toString());// util类型
			value = timeDate;
		}
		if (type == BigDecimal.class) {
			value = BigDecimal.valueOf(Long.valueOf(value.toString()));
		}

		return value;
	}

	/**
	 * 返回集合一部分
	 * 
	 * @param allSet
	 *            集合
	 * @param beginIdx
	 *            开始标记位
	 * @param endCount
	 *            结束idx+1的标记位
	 * @return
	 */
	public static List getSubList(List allSet, int beginIdx, int endCount) {
		List subSet = new ArrayList();
		for (int i = beginIdx; i < endCount; i++) {
			subSet.add(allSet.get(i));
		}
		return subSet;
	}

	// 将手字母大写
	public static String toFirstLetterUpperCase(String strName) {
		if (strName == null || strName.length() < 2) {
			return strName;
		}
		String firstLetter = strName.substring(0, 1).toUpperCase();
		return firstLetter + strName.substring(1, strName.length());
	}

	
	public static Map<String,Object> createSoo(String operName,
			String  nameSpace,
			List<Map<String,Object>> parmArry,
			Object pageIndex,Object pageSize,Object totalSize){
			if(Tools.isNull(parmArry)){
			parmArry = new LinkedList<Map<String,Object>>();
			}
			
			Map<String,Object> resultMap = new HashMap<String,Object>();
			Map<String,Object> pubReqMap = new HashMap<String,Object>();
			pubReqMap.put("OPER_NAME"	, operName);
			pubReqMap.put("NAME_SPACE"	, nameSpace);
			pubReqMap.put("PAGE_INDEX"	, pageIndex);
			pubReqMap.put("PAGE_SIZE"	, pageSize);
			pubReqMap.put("TOTAL_SIZE"	, totalSize);
			
			resultMap.put("PUB_REQ"	, pubReqMap);
			resultMap.put("PARAM_REQ", parmArry);
			
			return resultMap;
	}

	public static Map<String,Object> createReqData(List<Map<String,Object>> sooArray,String isTranSync){
			if(Tools.isNull(isTranSync)){
			isTranSync = "Y";;
			}
			String now = Tools.addDate("yyyy-MM-dd hh:mm:ss", Calendar.YEAR, 0);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			Map<String,Object> SvcCont = new HashMap<String,Object>();
			Map<String,Object> TcpCont = new HashMap<String,Object>();
			SvcCont.put("SOO", sooArray);
			SvcCont.put("TRAN_SYNC", isTranSync);
			TcpCont.put("REQ_DATE", now);
			resultMap.put("SvcCont", SvcCont);
			resultMap.put("TcpCont", TcpCont);
			
			return resultMap;

	}
	//随机生成随机数字字母
	public static String getRandomWord(int length) {
        StringBuffer sb =new StringBuffer();  
        Random random = new Random();  
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                sb.append((char)(random.nextInt(26) + temp));  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
            	sb.append(String.valueOf(random.nextInt(10)));  
            }  
        }  
        return sb.toString(); 
	}
	
	//随机生成随机数字
	public static String getRandom(int length) {
		  StringBuffer sb =new StringBuffer();  
        Random random = new Random();  
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
        	sb.append(String.valueOf(random.nextInt(10)));  
        }  
        return sb.toString(); 
	}
	
	/**
	 * 获取客户端ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getRequestParam(HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((ServletInputStream) request.getInputStream(),"utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		return sb.toString();
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	/**
	 * AES 128 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 解密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public static String getMapFirst(Map<String, Object> map) {
    	Object obj = null;
        for (Entry<String, Object> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return String.valueOf(obj);
    }
    
    public static boolean regularCheck(String regularStr,String str){
		Pattern pattern = Pattern.compile(regularStr);
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
    }
    /**
     * 校验时间格式
     * @param str
     * @return
     */
    public static boolean isValidDate(String dataFormat, String str) {
        boolean convertSuccess=true;
         SimpleDateFormat format = new SimpleDateFormat(dataFormat);
         try {
            format.setLenient(false);
            format.parse(str);
         } catch (ParseException e) {
            // e.printStackTrace();
        	 	// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
             convertSuccess=false;
         } 
         return convertSuccess;
  }
    
    public  static String clobToString(Clob clob) throws SQLException, IOException { 
        Reader is = clob.getCharacterStream();// 得到流 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {// 执行循环将字符串全部取出付值给 StringBuffer由StringBuffer转成STRING 
    		sb.append(s); 
    		s = br.readLine(); 
        } 
        return sb.toString(); 
      }
	public static String ClobToString(Object obj) throws SQLException, IOException {
		if(null==obj || "".equals(obj)){
			return "";
		}
		Clob clob = (Clob) obj;
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
 
	
    public static boolean validPsdRegex(String password) {
    	if (password.length() < 8) {
			return false;
		}
		String regex1 = "[A-Z]+";
		String regex2 = "[a-z]+";
		String regex3 = "[0-9]+";
		String regex4 = "[^A-Za-z0-9]";
		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(password);
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(password);
		Pattern p3 = Pattern.compile(regex3);
		Matcher m3 = p3.matcher(password);
		Pattern p4 = Pattern.compile(regex4);
		Matcher m4 = p4.matcher(password);
		int i = 0;
		if (m.find()) {
			i++;
		}
		if (m2.find()) {
			i++;
		}
		if (m3.find()) {
			i++;
		}
		if (m4.find()) {
			i++;
		}
		if (i < 3) {
			return false;
		}
		return true;
    }
	public static Map<String,String> urlSplit(String urlparam){
	   Map<String,String> map = new HashMap<String,String>();
	   String[] param =  urlparam.split("&");
	   for(String keyvalue:param){
	      String[] pair = keyvalue.split("=");
	      if(pair.length==2){
	         map.put(pair[0], pair[1]);
	      }
	   }
	   return map;
	}
	
	/**
	 * 获取所有请求参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParams(HttpServletRequest request) {
		Map<String, Object> reqParamMap = new HashMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length > 0) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					reqParamMap.put(paramName, paramValue);
				}
			}
		}
		return reqParamMap;
	}
    public  static Map<String,Object> parseObject2Map(Object obj) {

        return (Map<String, Object>) obj;
    }
    
	 /**
     * 
     * 方法描述  获取随机数作为主键
     *
     * @Author:zhangxuehua
     * @date: 2019年6月18日 下午2:51:59 
     * @return
     */
    public static long queryId(int num) {
    	Random random = new Random();
    	String result="";
    	for (int i=0;i<num;i++){
    		result+=random.nextInt(10);
    	}
 		return Long.parseLong(result);
    }
	
    /**
	 * 1.判断Map中是否存在key 
	 * 2.判断key对应的value是否为空
	 * 只针对单层数据结构,复杂map无法适应,未做递归
	 * @param params
	 * @param fieldArr
	 * @return
	 */
	public static Map<String,Object> paramValiCheck(Map<String,Object> params, String fieldArr) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String  returnCode = "0";
		String returnMsg = "success";
		
		String[] fieldStr = fieldArr.split(",");
		for(int i=0;i<fieldStr.length;i++) {
			if(!params.containsKey(fieldStr[i].trim())) {
				returnCode = "1001-1003"; 
				returnMsg = "必填字段缺失";
				break;
			}else {
				if(null == params.get(fieldStr[i].trim()) || "".equals(params.get(fieldStr[i].trim())) ) {
					returnCode = "1001-1003"; 
					returnMsg = "必填字段缺失";
					break;
				}
			}
		}
		returnMap.put("resultCode", String.valueOf(returnCode));
		returnMap.put("resultMsg", returnMsg);
		return returnMap;
	}
    
	 /**
		 * 1.判断Map中是否存在key 
		 * 2.判断key对应的value是否为空
		 * 只针对单层数据结构,复杂map无法适应,未做递归
		 * @param params
		 * @param fieldArr
		 * @return
		 */
		public static Map<String,String> paramNumberCheck(Map<String,Object> params, String fieldArr) {
			Map<String,String> returnMap = new HashMap<String,String>();
			
			int returnCode = 0;
			String returnMsg = "success";
			
			String[] fieldStr = fieldArr.split(",");
			for(int i=0;i<fieldStr.length;i++) {
				Object valObj=params.get(fieldStr[i].trim());
				if(!Tools.isNull(valObj) && !Tools.isNumber(valObj.toString())) {
					returnCode = 2003; //2003:参数不能为空
					returnMsg = "paramter :" + fieldStr[i] + " is not numeric ";
					break;
				}
			}
			returnMap.put("returnCode", String.valueOf(returnCode));
			returnMap.put("returnMsg", returnMsg);
			return returnMap;
		}
		
		
	public static Object isDateValue(Object dateObject) {
		if(Tools.isNull(dateObject)) {
			return "";
		}
		String dateStr = String.valueOf(dateObject);
		String date = DateUtil.getTimeFormatyyyyMMddHHmmss(dateStr);
		return date;
	}

	public static Long isNumberValue(Object obj) {
		Long number = Tools.isNumber(String.valueOf(obj)) ? Long.valueOf(String.valueOf(obj)) : null;
		return number;
	}
	
	public static BigInteger isBigNumberValue(Object obj) {
		BigInteger number = Tools.isNumber(String.valueOf(obj)) ? new BigInteger(String.valueOf(obj)) : null;
		return number;
	}
	
	
	public static void main(String[] args) {
//		int a=0,b=0;
//		String str="11.22";
//		int c=str.length();
//		for(int i = c;--i>=0;) {
//			if (Character.isDigit(str.charAt(i))){
//				a++;
// 	        }else if (".".equals(String.valueOf(str.charAt(i)))){
// 	        	b++;
// 	        }
//		}
//		System.out.println(a+":"+b+":"+c);
//		if(b<=1 && (a+b==c)) {
//			System.out.println(Double.valueOf(str));
//		} else {
//			System.out.println(0.0);
//		}
		
		Map<String,Object> reqParam=Maps.newMapEntry();
		reqParam.put("aa", "a122");
		reqParam.put("bb", "12313");
		String numFieldArr="bb,cc,dd";
		Map<String,String> nunmberFiledsMap=Tools.paramNumberCheck(reqParam, numFieldArr);
		System.out.println(nunmberFiledsMap);
		
	}
	
	
	 public static Map<String,Object> getNullReturn() {
			
		  	Map<String,Object> ExceObject= new HashMap<String,Object>();
			ExceObject.put("resultCode", "1001-1003");
			ExceObject.put("resultMsg", "必填字段缺失");
			return ExceObject;
		}
}
