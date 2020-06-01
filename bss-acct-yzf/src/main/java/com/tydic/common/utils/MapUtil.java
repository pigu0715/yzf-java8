package com.tydic.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class MapUtil {
	/**
	 * 按key从map中查找值
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getValueFromMap(Map<String, Object> map,String key) {
		if(map==null){
			return "";
		}
		if (map.containsKey(key)){
			 if(map.get(key)==null){
				 return "";
			 }
			 return map.get(key).toString();
		 }else {
			 for (String sKey : map.keySet()) {
				 if(map.get(sKey) instanceof Map){
					 String childRet=getValueFromMap((Map)map.get(sKey),key);
					 if(childRet!=""){
		        			return childRet;
		        	}
				 }else if(map.get(sKey) instanceof List){
					 List list =(List)map.get(sKey) ;
		        		for(Object object:list){
		        			if(object instanceof Map){
		        				String childRet= getValueFromMap((Map)object,key);
		                		if(childRet!=""){
		                			return childRet;
		                		}
		        			}  
		        		}
				 } 
			 }
		  
		  }
		 return "";
	}
	
	/**
	 * 按key从map中查找值,并且类型是map的
	 * @param map
	 * @param key
	 * @return
	 */
	public static Map getMapValueFromMap(Map<String, Object> map,String key) {
		if(map==null || map.size() == 0){
			return null;
		}
		if (map.containsKey(key)){
			 if(map.get(key)==null){
				 return null;
			 }
			 if (map.get(key) instanceof Map) {
				return (Map)map.get(key);
			}else {
				return null;
			}
		 }else {
			 for (String sKey : map.keySet()) {
				 if(map.get(sKey) instanceof Map){
					 Map childRet=getMapValueFromMap((Map)map.get(sKey),key);
					 if(childRet!=null){
		        			return childRet;
		        	 }
				 }else if(map.get(sKey) instanceof List){
					 List list =(List)map.get(sKey) ;
		        		for(Object object:list){
		        			if(object instanceof Map){
		        				Map childRet= getMapValueFromMap((Map)object,key);
		                		if(childRet!=null){
		                			return childRet;
		                		}
		        			}  
		        		}
				 } 
			 }
		  }
		 return null;
	}
	
	
	
	public static HashMap<String, Object> JsonToMap(JSONObject jsonObject){
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue());
        }		
		return resultMap;
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
		Map<String,Object> returnArr = new HashMap<String,Object>();
		
		int ReturnCode = 0;
		String ReturnMsg = "success";
		
		String[] fieldStr = fieldArr.split(",");
		for(int i=0;i<fieldStr.length;i++) {
			if(!params.containsKey(fieldStr[i].trim())) {
				ReturnCode = 2002; //2002:参数缺失
				ReturnMsg = "missing paramter :" + fieldStr[i];
				break;
			}else {
				if(null == params.get(fieldStr[i].trim()) || "".equals(params.get(fieldStr[i].trim())) ) {
					ReturnCode = 2003; //2003:参数不能为空
					ReturnMsg = "paramter :" + fieldStr[i] + " can not be null";
					break;
				}
			}
		}
		returnArr.put("ReturnCode", ReturnCode);
		returnArr.put("ReturnMsg", ReturnMsg);
		return returnArr;
	}
	
	public static Object deepCopy(Object src) throws IOException, ClassNotFoundException {  
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
		ObjectOutputStream out = new ObjectOutputStream(byteOut);  
		out.writeObject(src);  
		  
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
		ObjectInputStream in = new ObjectInputStream(byteIn);  
		@SuppressWarnings("unchecked")  
		Object dest =  in.readObject();  
		return dest;  
	}  
	   
}
