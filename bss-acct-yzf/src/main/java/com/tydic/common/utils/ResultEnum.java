package com.tydic.common.utils;

public enum ResultEnum {
	SUCCESS(0,"处理成功"),
	SQL_ERROR(1,"数据库错误"),
    ERROR(-1,"系统超时"),
	REQ_NULL(-2,"入参为空");
	private Integer code;
	
	private String msg;
	
	private ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
