package com.tydic.common.utils;

import java.util.HashMap;
import java.util.Map;

public class Constant {

	/**返回成功值**/
	public static final String RETURN_CD_SUCCESS = "0";
	/**请求服务内部异常 **/
	public static final String RETURN_CD_EXCEP = "1";
	/**请求接口异常 **/
	public static final String RETURN_CD_INTERFACE = "2";
	/**请求参数错误 **/
	public static final String RETURN_CD_PARAM= "3";
	/**非法请求**/
	public static final String RETURN_CD_ILLEGAL = "9";
	/**报文解析异常 **/
	public static final String RETURN_CD_MSG_PARSE= "4";
	/**报文解析异常 **/
	public static final String RETURN_CD_FLOW= "5";
	/**超时 **/
	public static final String RETURN_CD_TIMEOUT= "6";
	/**节点缺失 **/
	public static final String RETURN_POINT_LACK= "7";
	/**大数据未成功查询到数据 **/
	public static final String RETURN_NONE_DATA= "8";
	/**未匹配到数据 **/
	public static final String RETURN_NO_DATA= "11";
	
	private static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(RETURN_CD_SUCCESS, "成功!");
		map.put(RETURN_CD_EXCEP, "接口调用超时或对端服务异常!");
		map.put(RETURN_CD_INTERFACE, "请求接口异常!");
		map.put(RETURN_CD_ILLEGAL, "非法请求!");
		map.put(RETURN_CD_PARAM, "请求参数错误!");
		map.put(RETURN_CD_MSG_PARSE, "接口数据解析异常!");
		map.put(RETURN_CD_TIMEOUT, "接口调用超时或对端服务异常!");
		map.put(RETURN_POINT_LACK, "大数据节点缺失!");
		map.put(RETURN_CD_FLOW, "工作流异常!");
		map.put(RETURN_NONE_DATA, "调用接口未查询到数据!");
	}
	
	public static String getErrMsg(String key) {
		return String.valueOf(map.get(key));
	}
	private static Map<String, String> retMap = new HashMap<String, String>();
	public static final String RES_CODE= "code";
	public static final String RES_MSG= "msg";
	public static final String RES_OBJ= "obj";
	static {
		retMap.put(RES_CODE, "resultCode");
		retMap.put(RES_MSG, "resultMsg");
		retMap.put(RES_OBJ, "resultObject");
	}
	
	public static String getRetKey(String key) {
		return String.valueOf(retMap.get(key));
	}
}
