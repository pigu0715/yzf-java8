package com.tydic.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 传入list，按条件分割成 多个 list
 * @author lss
 *
 */
public class ListUtils {
	
	public final static int splitLen = 50; 
	
	public final static int splitLen100 = 100; 
	
	public final static int splitLen500 = 500; 
	
	/**
	 * 参数：待分割list，每个长度len
	 * @param list
	 * @param len
	 * @return
	 */
	public static List<List<?>> splitList(List<?> list, int len){
		if (list == null || list.size() == 0 || len < 1) {  
			return null;  
		}
		List<List<?>> result = new ArrayList<List<?>>();  
		  
		int size = list.size();  
		int count = (size + len - 1) / len;  
	  
		for (int i = 0; i < count; i++) {
			List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));  
			result.add(subList);  
		} 
		return result;
	}

}
