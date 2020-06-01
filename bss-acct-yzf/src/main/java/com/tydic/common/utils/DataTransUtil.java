package com.tydic.common.utils;

public class DataTransUtil {
	//String 转 int 
	public static int strToInt(Object str) {
		if(null == str){
			return 0;
		}
		String str2 = str.toString();
		if(str2.isEmpty()){
			return 0;
		}
		return Integer.parseInt(str2);
	}
	
	//String 转 long
	public static long strToLong(Object str) {
		if(null == str){
			return 0;
		}
		String str2 = str.toString();
		if(str2.isEmpty()){
			return 0;
		}
		return Long.parseLong(str2);
	}
	
	/**
	 * 流量单位转化
	 * @param bytes
	 * @return
	 */
	private static double  kb = 1024.0;
	public static String transFlow(long bytes) {
		double g = bytes / kb / kb;
		double m = 0;
		if(g > 1) {
			return String.format("%.2f", g)+"G";
		}else {
			bytes = bytes % ((int)kb * (int)kb );
			m = bytes / kb;
		}
		if(m > 1) {
			return String.format("%.2f", m)+"M";
		}else {
			return bytes + "K";
		}
	}
}
