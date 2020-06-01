package com.tydic.common.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@SuppressWarnings("all")
public interface IMapEntry<K, V> extends Map<K,V> {

	public File getFile(K key);

	public File getFile(K key, File defauleValue);

	public InputStream getInputStream(K key);

	public InputStream getInputStream(K key, InputStream defauleValue);

	public OutputStream getOutputStream(K key);

	public OutputStream getOutputStream(K key, OutputStream defauleValue);

	public Collection getCollection(K key);

	public Collection getCollection(K key, Collection defauleValue);

	public Map getMap(K key);

	public Map getMap(K key, Map defauleValue);
	
	public String [] getArray(K key);
	
	public String [] getArray(K key,String [] defauleValue);

	public HashMap getHashMap(K key);

	public HashMap getHashMap(K key, HashMap defauleValue);

	public TreeMap getTreeMap(K key);

	public TreeMap getTreeMap(K key, TreeMap defauleValue);

	public LinkedHashMap getLinkedHashMap(K key);

	public LinkedHashMap getLinkedHashMap(K key, LinkedHashMap defauleValue);

	public MapEntry getMapEntry(K key);

	public MapEntry getMapEntry(K key, MapEntry defauleValue);


	public List getList(K key);

	public List getList(K key, List defauleValue);

	public ArrayList getArrayList(K key);

	public ArrayList getArrayList(K key, ArrayList defauleValue);

	public LinkedList getLinkedList(K key);

	public LinkedList getLinkedList(K key, LinkedList defauleValue);

	public Set getSet(K key);

	public Set getSet(K key, Set defauleValue);

	public HashSet getHashSet(K key);

	public HashSet getHashSet(K key, HashSet defauleValue);



	public String getString(K key, String defauleValue);

	public Object getObject(Object key, Object defauleValue);

	public Object getObject(Object key);

	public String getString(K key);



	public byte[] getByte(K key);

	public byte[] getByte(K key, byte[] num);

	public Short getShort(K key);

	public Short getShort(K key, Number num);

	public Integer getInteger(K key);

	public Integer getInteger(K key, Number num);

	public Double getDouble(K key);

	public Double getDouble(K key, Number num);

	public Float getFloat(K key);

	public Float getFloat(K key, Number num);

	public Boolean getBoolean(K key);

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
	public Boolean getBoolean(K key, Boolean b);
}