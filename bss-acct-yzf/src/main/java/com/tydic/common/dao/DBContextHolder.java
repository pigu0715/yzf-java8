package com.tydic.common.dao;

public class DBContextHolder {
	
	public static final String DATA_SOURCE_ACCT = "acct";
	public static final String DATA_SOURCE_CUST = "cust";
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDBType(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getDBType() {
		return contextHolder.get();
	}

	public static void clearDBType() {
		contextHolder.remove();
	}
}
