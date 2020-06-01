package com.tydic.bus.vo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class AcctItemYzfBean {
	
	@EqualsAndHashCode.Exclude
	private Long acctItemYzfId;
	@EqualsAndHashCode.Exclude
	private Long acctItemId;
	@EqualsAndHashCode.Exclude
	private Integer acctItemTypeId;
	@EqualsAndHashCode.Exclude
	private Long amount;
	@EqualsAndHashCode.Exclude
	private Integer statusCd;
	@EqualsAndHashCode.Exclude
	private String statusDate;
	@EqualsAndHashCode.Exclude
	private Integer regionId;
	@EqualsAndHashCode.Exclude
	private Long custId;
	@EqualsAndHashCode.Exclude
	private Long offerInstId;
	@EqualsAndHashCode.Exclude
	private String remark;	
	@EqualsAndHashCode.Exclude
	private Long orderNumber;
	@EqualsAndHashCode.Exclude
	private String accNbr;
	@EqualsAndHashCode.Exclude
	private String latnId;
	@EqualsAndHashCode.Exclude
	private String latnName;
	@EqualsAndHashCode.Exclude
	private String offerName;
	@EqualsAndHashCode.Exclude
	private String areaCode;
	@EqualsAndHashCode.Exclude
	private String acctIdOld;
	
	private Long prodInstId;
	private Integer billingCycleId;
	private Long acctId;
	private Integer offerId;
	
	public static void main(String[] args) {
		List<AcctItemYzfBean> list2 = new CopyOnWriteArrayList<>();
		AcctItemYzfBean yzf1 = new AcctItemYzfBean();
		yzf1.setAcctId(1L);
		yzf1.setBillingCycleId(202004);
		yzf1.setProdInstId(123L);
		yzf1.setOfferId(1);
		yzf1.setAccNbr("123");
		AcctItemYzfBean yzf2 = new AcctItemYzfBean();
		yzf2.setAcctId(1L);
		yzf2.setBillingCycleId(202004);
		yzf2.setProdInstId(123L);
		yzf2.setOfferId(1);
		yzf2.setAccNbr("1234");
		list2.add(yzf1);
		list2.add(yzf2);
		list2.stream()
			 .distinct()
			 .map(JSON::toJSONString)
			 .forEach(System.out::println);
	}
	
//	@Override
//	public boolean equals(Object yzfBeanObj) {
//		if (this == yzfBeanObj) {
//			 return true;
//		}
//		if(yzfBeanObj instanceof AcctItemYzfBean) {
//			AcctItemYzfBean yzfBean = (AcctItemYzfBean) yzfBeanObj;
//			return Objects.equals(acctId, yzfBean.acctId)
//					&& Objects.equals(billingCycleId, yzfBean.billingCycleId)
//					&& Objects.equals(prodInstId, yzfBean.prodInstId)
//					&& Objects.equals(offerId, yzfBean.offerId);
//		}
//		return false;
//	}
//	
//	@Override
//	public int hashCode() {
//		return Objects.hash(acctId, billingCycleId, prodInstId, offerId);
//	}

}
