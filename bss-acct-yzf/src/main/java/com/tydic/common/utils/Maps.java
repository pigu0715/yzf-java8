package com.tydic.common.utils;


import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("all")
public final class Maps {
	private static MapEntry entry = new MapEntry(0);

	public static TreeMap newTreeMap() {
		return new TreeMap();
	}

	public static EnumMap newEnumMap(Map map) {
		return new EnumMap(map);
	}

	public static WeakHashMap newWeakHashMap() {
		return new WeakHashMap(20);
	}

	public static Hashtable newHashtable() {
		return new Hashtable(20);
	}

	public static IdentityHashMap newIdentityHashMap() {
		return new IdentityHashMap(20);
	}

	public static ConcurrentHashMap newConcurrentHashMap() {
		return new ConcurrentHashMap(20);
	}

	/**
	 * 鍒犻櫎鎸囧畾Map涓殑鎸囧畾Key
	 * 
	 * @param map
	 * @param removeKeys
	 * @return
	 */
	public static <T extends Map> T removeKeys(T map, Object... removeKeys) {
		for (Object removeKey : removeKeys) {
			map.remove(removeKey);
		}
		return map;
	}

	/**
	 * 鍒涘缓涓�釜鏂扮殑Map, 鍖呭惈鎸囧畾Map涓殑鎸囧畾Key
	 * 
	 * @param map
	 * @param removeKeys
	 * @return
	 */
	public static <T extends Map> Map newMapHasKeys(T map, Object... keys) {
		Map newMap = null;
		if (map instanceof HashMap) {
			newMap = (HashMap) ((HashMap) map).clone();
			newMap.clear();
		} else {
			newMap = new HashMap(20);
		}
		for (Object key : keys) {
			newMap.put(key, map.get(key));
		}
		return newMap;
	}

	/**
	 * 鎸囧畾鐨刱ey蹇呴』鍦ㄦ寚瀹氱殑Map涓瓨鍦紝涓旀寚瀹氱殑key鐨勫�涓嶈兘涓虹┖鎴栫┖瀛楃
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isBlankKey(Map map, String key) {
		return !map.containsKey(key) || map.get(key) == null || StringUtils.isBlank(map.get(key).toString());
	}

	/**
	 * 鎸囧畾鐨刱ey蹇呴』鍦ㄦ寚瀹氱殑Map涓瓨鍦紝涓旀寚瀹氱殑key鐨勫�涓嶈兘涓虹┖鎴栫┖瀛楃
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isNotBlankKey(Map map, String key) {
		return map.containsKey(key) && map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString());
	}

	/**
	 * 鎸囧畾鐨刱ey蹇呴』鍦ㄦ寚瀹氱殑Map涓瓨鍦紝涓旀寚瀹氱殑key鐨勫�涓嶈兘涓虹┖鎴栫┖瀛楃
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	public static boolean isBlankKeys(Map map, String... keys) {
		for (String key : keys) {
			if (isBlankKey(map, key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 鎸囧畾鐨刱ey蹇呴』鍦ㄦ寚瀹氱殑Map涓瓨鍦紝涓旀寚瀹氱殑key鐨勫�涓嶈兘涓虹┖鎴栫┖瀛楃
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	public static boolean isNotBlankKeys(Map map, String... keys) {
		for (String key : keys) {
			if (isNotBlankKey(map, key)) {
				return true;
			}
		}
		return false;
	}


	public static MapEntry newMapEntry() {
		return (MapEntry) entry.clone();
	}

	
	
}
