package com.tydic.common.utils;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description: 常用工具类
 * @Auther: siminx@163.com
 * @Date: 2018/7/25 19:33
 */
public class ToolUtil {
	
	/**
	 * @description: 判断对象是否为空
	 * @param: [o] 判断的对象
	 * @return: boolean 判断的结果
	 * @auther: siminx@163.com
	 * @date: 2018/7/25 19:50
	 */
	public static boolean isEmpty(Object o) {

		if (o == null) {
			return true;
		}
		if (o instanceof String) {
			return StringUtils.isBlank((String) o);
		} else if (o instanceof List) {
			if (((List) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Set) {
			if (((Set) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Object[]) {
			return ArrayUtils.isEmpty((Object[]) o);
		} else if (o instanceof int[]) {
			return ArrayUtils.isEmpty((int[]) o);
		} else if (o instanceof long[]) {
			return ArrayUtils.isEmpty((long[]) o);
		} else if (o instanceof String[]) {
			return ArrayUtils.isEmpty((Object[]) o);
		}
		return false;
	}

	/**
	 * @description: 判断对象是否不为空
	 * @param: [o] 判断的对象
	 * @return: boolean 判断的结果
	 * @auther: siminx@163.com
	 * @date: 2018/7/25 19:50
	 */
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	/**
	 * @description: 判断数组集合中是否有一个空对象
	 * @param: [os] 判断的数组集合
	 * @return: boolean 判断的结果
	 * @auther: siminx@163.com
	 * @date: 2018/7/25 19:50
	 */
	public static boolean isOneEmpty(Object... os) {
		if (ArrayUtils.isEmpty(os)) {
			return false;
		}
		for (Object o : os) {
			if (isEmpty(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description: 判断数组集合中是否全是空对象
	 * @param: [os] 判断的数组集合
	 * @return: boolean 判断的结果
	 * @auther: siminx@163.com
	 * @date: 2018/7/25 19:50
	 */
	public static boolean isAllEmpty(Object... os) {
		if (isEmpty(os)) {
			return true;
		}
		for (Object o : os) {
			if (!isEmpty(o)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 格式化文本
	 * 
	 * @param template
	 *            文本模板，被替换的部分用 {0},{1} 表示 参数1 参数2
	 * @param values
	 *            参数值
	 * @return
	 */
	public static String formatMsg(String template, Object... values) {
		try {
			return MessageFormat.format(template, values);
		} catch (Exception e) {
			return template;
		}
	}
	
	public static boolean equals(Integer a, Integer b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}

	public static boolean equals(Long a, Long b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}

	public static boolean equals(Double a, Double b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}

	public static boolean equals(Float a, Float b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}

	public static boolean equals(String a, String b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}

	public static boolean equals(Boolean a, Boolean b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}
	
	public static String toSimpleName(String name) {
		String str = name.replaceAll("_", "");
		return str.trim();
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
     * 
     * 方法描述  随机获取多位String
     *
     * @Author:zhangxuehua
     * @date: 2019年6月18日 下午2:51:59 
     * @return
     */
    public static String queryStringId(int num) {
    	Random random = new Random();
    	String result="";
    	for (int i=0;i<num;i++){
    		result+=random.nextInt(10);
    	}
		return result;
    }
    
    /**
	 * 
	 * 方法描述 参数校验
	 * @param jsonMap 
	 *
	 * @Author:zhangxuehua
	 * @date: 2019年7月12日 下午4:39:34
	 * @param jsonMap
	 * @return
	 */
    public static Map<String, Object> verify(Map<String,Object> map, JSONObject jsonMap) {
		boolean ifVerify = true;
		String resultMsg = "";
		// 遍历map中的值
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            if (!jsonMap.containsKey(entry.getKey()) || jsonMap.get(entry.getKey())== null || "".equals(jsonMap.get(entry.getKey()).toString())) {
        		ifVerify=false;
        		resultMsg=entry.getKey()+"不能为空";
        		break;
    		}
        }
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("ifVerify", ifVerify);
		returnMap.put("resultMsg", resultMsg);
		return returnMap;
	}
    
    public static Map<String,String> listJson(Object objJson) {
		Map<String,String> returnParam=new HashMap<String,String>();
	    if (objJson instanceof JSONArray) {
	        JSONArray jsonArray =  JSONObject.parseArray(objJson.toString());
	        if (jsonArray.size() > 0) {
	            for (int i = 0; i < jsonArray.size(); i++) {
	            	Object o=jsonArray.get(i);
	                listJson(o);
	            }
	        }
	    } else if (objJson instanceof JSONObject) {
	        JSONObject jsonObject = (JSONObject) objJson;
	        Set<String> keySet = jsonObject.keySet();
	        Iterator<String> iterator = keySet.iterator();
	        while (iterator.hasNext()) {
	            String key = iterator.next();
	            Object value = jsonObject.get(key);
	            if (value instanceof JSONArray) {
	                JSONArray innerArr =  JSONObject.parseArray(value.toString());
	                listJson(innerArr);
	            } else if (value instanceof JSONObject) {
	                listJson(value);
	            } else {
	                //System.out.println(key + "<<===>>" + value.toString());
	            	returnParam.put("key", value.toString());
	            }
	        }
	    }
	    return returnParam;
	}
}
