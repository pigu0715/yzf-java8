package com.tydic.common.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpRespUtil {
	private  int httpStatus=500;
	private  String httpMessage;
	private  String httpResult;
	
	public HttpRespUtil() {}
	
	public HttpRespUtil(int httpStatus ) {
		this.httpStatus=httpStatus;
	}
	
	public HttpRespUtil(int httpStatus,String httpMessage) {
		this.httpStatus=httpStatus;
		this.httpMessage=httpMessage;
	}
	
	public HttpRespUtil(int httpStatus,String httpMessage,String httpResult) {
		this.httpStatus=httpStatus;
		this.httpMessage=httpMessage;
		this.httpResult=httpResult;
	}
}
