package com.tydic.common.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;

@SuppressWarnings("all")
public class MapEntry<K, V> extends HashMap<K, V> implements Cloneable, IMapEntry<K, V> {
	public MapEntry() {
		super();
	}

	public MapEntry(int size) {
		super(size);
	}

	
	public File getFile(K key) {
		return getFile(key, null);
	}

	public File getFile(K key, File defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof File) {
			return (File) obj;
		} else {
			return defauleValue;
		}
	}

	public InputStream getInputStream(K key) {
		return getInputStream(key, null);
	}

	public InputStream getInputStream(K key, InputStream defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof InputStream) {
			return (InputStream) obj;
		} else {
			return defauleValue;
		}
	}

	public OutputStream getOutputStream(K key) {
		return getOutputStream(key, null);
	}

	public OutputStream getOutputStream(K key, OutputStream defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof OutputStream) {
			return (OutputStream) obj;
		} else {
			return defauleValue;
		}
	}

	public Collection getCollection(K key) {
		return getCollection(key, null);
	}

	public Collection getCollection(K key, Collection defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof Collection) {
			return (Collection) obj;
		} else {
			return defauleValue;
		}
	}

	public Map getMap(K key) {
		return getMap(key, null);
	}

	public Map getMap(K key, Map defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof Map) {
			return (Map) obj;
		} else {
			return defauleValue;
		}
	}

	public HashMap getHashMap(K key) {
		return getHashMap(key, null);
	}

	public HashMap getHashMap(K key, HashMap defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof HashMap) {
			return (HashMap) obj;
		} else if (obj instanceof Map) {
			MapEntry map = Maps.newMapEntry();
			map.putAll((Map) obj);
			return map;
		} else {
			return defauleValue;
		}
	}

	public TreeMap getTreeMap(K key) {
		return getTreeMap(key, null);
	}

	public TreeMap getTreeMap(K key, TreeMap defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof TreeMap) {
			return (TreeMap) obj;
		} else {
			return defauleValue;
		}
	}


	
	

	public MapEntry getMapEntry(K key) {
		return getMapEntry(key, null);
	}

	public MapEntry getMapEntry(K key, MapEntry defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof MapEntry) {
			return (MapEntry) obj;
		} else if (obj instanceof Map) {
			MapEntry map = Maps.newMapEntry();
			map.putAll((Map) obj);
			return map;
		} else {
			return defauleValue;
		}
	}



	public List getList(K key) {
		return getList(key, null);
	}

	public List getList(K key, List defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof List) {
			return (List) obj;
		} else {
			return defauleValue;
		}
	}

	public ArrayList getArrayList(K key) {
		return getArrayList(key, null);
	}

	public ArrayList getArrayList(K key, ArrayList defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof ArrayList) {
			return (ArrayList) obj;
		} else {
			return defauleValue;
		}
	}

	public LinkedList getLinkedList(K key) {
		return getLinkedList(key, null);
	}

	public LinkedList getLinkedList(K key, LinkedList defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof LinkedList) {
			return (LinkedList) obj;
		} else {
			return defauleValue;
		}
	}

	public Set getSet(K key) {
		return getSet(key, null);
	}

	public Set getSet(K key, Set defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof Set) {
			return (Set) obj;
		} else {
			return defauleValue;
		}
	}

	public HashSet getHashSet(K key) {
		return getHashSet(key, null);
	}

	public HashSet getHashSet(K key, HashSet defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof HashSet) {
			return (HashSet) obj;
		} else {
			return defauleValue;
		}
	}

	public TreeSet getTreeSet(K key) {
		return getTreeSet(key, null);
	}

	public TreeSet getTreeSet(K key, TreeSet defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof TreeSet) {
			return (TreeSet) obj;
		} else {
			return defauleValue;
		}
	}

	public LinkedHashSet getLinkedHashSet(K key) {
		return getLinkedHashSet(key, null);
	}

	public LinkedHashSet getLinkedHashSet(K key, LinkedHashSet defauleValue) {
		Object obj = get(key);
		if (obj == null) {
			return defauleValue;
		} else if (obj instanceof LinkedHashSet) {
			return (LinkedHashSet) obj;
		} else {
			return defauleValue;
		}
	}

	public String getString(K key, String defauleValue) {
		Object object = get(key);
		if (object == null) {
			return defauleValue;
		} else {
			return object.toString();
		}
	}

	public Object getObject(Object key, Object defauleValue) {
		Object object = get(key);
		if (object == null) {
			return defauleValue;
		} else {
			return object;
		}
	}

	public Object getObject(Object key) {
		return getObject(key, null);
	}

	public String getString(K key) {
		Object object = get(key);
		if (object == null) {
			return null;
		} else {
			return object.toString();
		}
	}

	protected Number getNumber(K key) {
		return getNumber(key, 0);
	}

	protected Number getNumber(K key, Number num) {
		Object object = get(key);
		if (object == null) {
			return num;
		} else if (NumberUtils.isNumber(object.toString())) {
			return NumberUtils.createNumber(object.toString());
		} else {
			return num;
		}
	}

	public BigDecimal getBigDecimal(K key) {
		return getBigDecimal(key, 0);
	}

	public BigDecimal getBigDecimal(K key, Number num) {
		return new BigDecimal(getNumber(key, num).toString());
	}

	public BigInteger getBigInteger(K key) {
		return getBigInteger(key, 0);
	}

	public BigInteger getBigInteger(K key, Number num) {
		return new BigInteger(getNumber(key, num).toString());
	}

	public byte[] getByte(K key) {
		return getByte(key, null);
	}

	public byte[] getByte(K key, byte[] num) {
		Object object = get(key);
		if (object == null) {
			return num;
		} else if (object instanceof byte[]) {
			return (byte[]) object;
		} else {
			return num;
		}
	}

	public Short getShort(K key) {
		return getShort(key, 0);
	}

	public Short getShort(K key, Number num) {
		return getNumber(key, num).shortValue();
	}

	public Integer getInteger(K key) {
		return getInteger(key, 0);
	}

	public Integer getInteger(K key, Number num) {
		return getNumber(key, num).intValue();
	}

	public Double getDouble(K key) {
		return getDouble(key, 0);
	}

	public Double getDouble(K key, Number num) {
		return getNumber(key, num).doubleValue();
	}

	public Float getFloat(K key) {
		return getFloat(key, 0);
	}

	public Float getFloat(K key, Number num) {
		return getNumber(key, num).floatValue();
	}

	public Boolean getBoolean(K key) {
		return getBoolean(key, Boolean.FALSE);
	}

	/**
	 * 
	 * Y = true,N = false<br/>
	 * YES = true,NO = false<br/>
	 * 1 >= true , 0 <= false<br/>
	 * 
	 * @param key
	 * @param b
	 * @return
	 */
	public Boolean getBoolean(K key, Boolean b) {
		Object obj = get(key);
		if (obj == null) {
			return b;
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		if (obj instanceof String) {
			if (NumberUtils.isNumber(obj.toString())) {
				if (NumberUtils.createInteger(obj.toString()) >= 1) {
					return true;
				} else {
					return false;
				}
			} else if (((String) obj).equalsIgnoreCase("Y") || ((String) obj).equalsIgnoreCase("TRUE") || ((String) obj).equalsIgnoreCase("YES") || ((String) obj).equals("是")) {
				return true;
			} else if (((String) obj).equalsIgnoreCase("N") || ((String) obj).equalsIgnoreCase("FALSE") || ((String) obj).equalsIgnoreCase("NO") || ((String) obj).equals("否")) {
				return false;
			} else {
				return BooleanUtils.toBooleanObject(obj.toString());
			}
		} else if (obj instanceof Number) {
			if (obj.equals(1) || ((Number) obj).intValue() >= 1) {
				return true;
			} else {
				return false;
			}
		}
		return b;
	}

	@Override
	public String[] getArray(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getArray(K key, String[] defauleValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap getLinkedHashMap(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap getLinkedHashMap(K key, LinkedHashMap defauleValue) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Title: getDate<br/>
	 * 
	 * @param key
	 * @return
	 * @see com.tydic.util.IMapEntry#getDate(java.lang.Object)
	 * @author yzb yangzb@tydic.com,yzhengbin@gmail.com
	 * @date 2015骞�鏈�鏃�涓嬪崍2:56:43
	 */

	/**
	 * Title: getDate<br/>
	 * 
	 * @param key
	 * @param date
	 * @return
	 * @see com.tydic.util.IMapEntry#getDate(java.lang.Object, java.util.Date)
	 * @author yzb yangzb@tydic.com,yzhengbin@gmail.com
	 * @date 2015骞�鏈�鏃�涓嬪崍2:56:43
	 */

	
	


}
