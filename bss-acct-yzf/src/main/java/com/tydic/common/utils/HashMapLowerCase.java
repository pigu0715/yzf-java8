package com.tydic.common.utils;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class HashMapLowerCase<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1L;

	@Override
	public V put(K key, V value) {
		if(key instanceof String){
			key = (K) ((String) key).toLowerCase();
		}
		return super.put(key, value);
	}

	@Override
	public V remove(Object key) {
		if(key instanceof String){
			key = (K) ((String) key).toLowerCase();
		}
		return super.remove(key);
	}

}
