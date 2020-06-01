package com.tydic.bus.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignTypeBean implements Serializable{
	
	private static final long serialVersionUID = 8772717192088614261L;
	
	private int signTypeId;
	private String signTypeName;
	private String statusCd;
	private long createStaff;
	private long updateStaff;
	private String createDate;
	private String statusDate;
	private String updateDate;
	
	private String pageFlag="false";
//	private int pageIndex;
//	private int PageSize=10;
//	private int start=0;
//	private int end=10;


}
