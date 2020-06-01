package com.tydic.bus.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.tydic.bus.vo.AcctItemYzfBean;
import com.tydic.common.config.DcaClientTemplate;
import com.tydic.common.dao.CommonDaos;
import com.tydic.common.dao.DBContextHolder;
import com.tydic.common.utils.Tools;

/**
 * 翼支付返还定时任务实现类
 * @author zhangtw
 *
 */
@Service
public class YzfMainTask {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DcaClientTemplate redisClient; 
	
	@Autowired
	CommonDaos commonDaos;
	
	private static final String NAME_SPACE = "web.TaskMapper.";
	
	@Resource(name="myAsyncTaskExecutor")
	ThreadPoolTaskExecutor executor;
	
	@Value("${yzf.execute.area}")
	private String areaStr;
	
	//offerId信息
	public Map<Integer,String> offerIdMap = new HashMap<>();
	
	//regionId信息
	public Map<Integer,String> commonRegionMap = new HashMap<>();
	
	
	/**
	 * 定时任务主函数
	 * @param latnId
	 */
	public void executeMainTask(String latnId) {
		
		logger.info("****************本地网:" +latnId+ ",片区：" +areaStr+ "翼支付返还定时任务开始****************");
		
		String[] areas = areaStr.trim().split(",");
		String latnName = getLatnMap().get(latnId);
		List<Map<String,Object>> billingCycleIdList = new ArrayList<>();
		try {
			//初始化yzf返还销售品集合
			this.setOfferIdMap();
			//初始化commonRegionMap
			this.setCommonRegionMap();
			Map<String,Object> latnMap = new HashMap<>();
			latnMap.put("latnId", latnId);
			//查出可能的账期
			billingCycleIdList = commonDaos.selectList(NAME_SPACE + "queryGroupBillingCycleId", latnMap, DBContextHolder.DATA_SOURCE_ACCT);
		} catch (Exception e1) {
			logger.error("executeMainTask查询账期信息异常：" + e1.getLocalizedMessage());
		}
		CountDownLatch campCountDownLatch = new CountDownLatch(areas.length * billingCycleIdList.size());
		//循环片区
		for (String area : areas) {
			//循环账期
			Optional.ofNullable(billingCycleIdList)
					.orElse(new ArrayList<>())
					.stream()
					.filter(Objects::nonNull)
					.forEach(billingCycleIdMap -> {
						Map<String,Object> reqParam = new HashMap<>();
						reqParam.put("billingCycleId", billingCycleIdMap.get("billingCycleId"));
						reqParam.put("latnId", latnId);
						reqParam.put("latnName", latnName);
						reqParam.put("area", area);
						//开启多线程处理任务 
						//一个片区、一个账期 -> 启一个线程
						executor.execute(() -> this.dealTask(reqParam, campCountDownLatch));
					});
		}
		try {
			campCountDownLatch.await(); // 保证之前的所有的线程都执行完成，才会走下面的；
		} catch (Exception e) {
			logger.error("executeTask线程阻塞异常:" + e.getLocalizedMessage());
		}
		
		logger.info("****************本地网:" +latnId+ ",片区：" +areaStr+ "翼支付返还定时任务结束****************");
	}

	/**
	 * 定时任务处理逻辑
	 * @param param
	 * @param campCountDownLatch
	 */
	private void dealTask(Map<String, Object> param, CountDownLatch campCountDownLatch) {
		try {
			
			String area = String.valueOf(param.get("area"));
			String latnId = String.valueOf(param.get("latnId"));
			String billingCycleId = String.valueOf(param.get("billingCycleId"));
			String areaCode = "0" + latnId;
			
			List<AcctItemYzfBean> areaDatas = commonDaos.selectList(NAME_SPACE + "queryYzfInfo", param, DBContextHolder.DATA_SOURCE_ACCT);
			
			Integer dataSize = Optional.ofNullable(areaDatas).orElse(new ArrayList<>()).size();
			logger.info("acctdb_" +area+ ",分片 本地网:" +latnId+ ",账期为:" +billingCycleId+ "上的数据量:" +dataSize);
			
			//初始化需要返还的记录集合
			List<List<AcctItemYzfBean>> groupYzfAddList = new CopyOnWriteArrayList<>();
			//循环过滤、增强翼支付数据
			Optional.ofNullable(areaDatas)//集合判空
					.orElse(new ArrayList<>())
					.stream()//转化为流 便于下面过滤和增强数据
					.filter(Objects::nonNull)//元素判空
					.distinct()//去重 重写AcctItemYzfBean的equals方法和hashCode方法
					.filter(yzfBean -> this.judgeIfOfferId(yzfBean))//判断销售品ID是否相同
					.filter(yzfBean -> this.enhanceYzfBean(yzfBean))//增强过滤accNbr和acctId
					.filter(yzfBean -> this.judgeIfArrears(yzfBean))//判断是否不欠费
					.filter(yzfBean -> this.judgeIfCancel(yzfBean))//判断是否销账金额大于0
					.filter(yzfBean -> this.judgeIfReturn(yzfBean))//判断是否上月未返还
					.forEach(yzfBean -> {
						//增强latnName
						yzfBean.setLatnName(commonRegionMap.get(yzfBean.getRegionId()));
						//增强areaCode
						yzfBean.setAreaCode(areaCode);
						//数据封装
						this.groupAddYzfList(yzfBean, groupYzfAddList);
					});
			
			//循环groupYzfAddList 一次插入最多2000条
			Optional.ofNullable(groupYzfAddList)
					.orElse(new CopyOnWriteArrayList<>())
					.stream()
					.forEach(addData -> {
						//开启多线程插入
						executor.execute(() -> {
							try {
								Map<String,Object> params= new HashMap<>();
								Integer size = Optional.ofNullable(addData).orElse(new ArrayList<>()).size();
								params.put("addData", addData);
								logger.info("本地网:"  +latnId+ ",acctdb_" +area+ "分片上的数据,插入数据:" + size);
								//插入数据
								commonDaos.add(NAME_SPACE + "addYzfPackResult", params, DBContextHolder.DATA_SOURCE_ACCT);
								//更新原表
								this.updateYzfStatusCd(addData);
							} catch (Exception e) {
								logger.error("addYzfPackResult批量插入数据异常！msg:" + e.getLocalizedMessage());
							}
						});
					});
			
		} catch (Exception e) {
			logger.error("executeTask多线程处理异常:" + e.getLocalizedMessage());
		}finally{
			campCountDownLatch.countDown();
		}
	}
	
	/**
	 * 逐条更新acct_item_yzf_${latnId} 状态为已返还
	 * @param addData
	 */
	private void updateYzfStatusCd(List<AcctItemYzfBean> addData) {
		Optional.ofNullable(addData)
				.orElse(new ArrayList<>())
				.stream()
				.filter(Objects::nonNull)//元素判空
				.forEach(yzfBean -> {
					try {
						commonDaos.update(NAME_SPACE + "updateYzfStatusCd", yzfBean, DBContextHolder.DATA_SOURCE_ACCT);
					} catch (Exception e) {
						logger.error("updateYzfStatusCd更新数据异常！msg:" + e.getLocalizedMessage() + "yzfBean:" + yzfBean);
					}
				});
	}

	
	/**
	 * 封装入表翼支付List 2000个实体一条记录
	 * @param yzfBean
	 * @param groupYzfAddList
	 */
	private void groupAddYzfList(AcctItemYzfBean yzfBean, List<List<AcctItemYzfBean>> groupYzfAddList) {
		if(Tools.isNull(groupYzfAddList)) {
			groupYzfAddList.add(new ArrayList<>());
		}
		List<AcctItemYzfBean> yzfList = groupYzfAddList.get(groupYzfAddList.size() -1);
		if(yzfList.size() < 2000) {
			yzfList.add(yzfBean);
		}else {
			List<AcctItemYzfBean> yzfListNew = new ArrayList<>();
			yzfListNew.add(yzfBean);
			groupYzfAddList.add(yzfListNew);
		}
	}
	
	/**
	 * 判断销售品ID是否相同
	 * @param yzfBean
	 * @return
	 */
	private boolean judgeIfOfferId(AcctItemYzfBean yzfBean) {
		String offer = offerIdMap.get(yzfBean.getOfferId());
		return Tools.isNull(offer) ? false : true;
	}
	
	/**
	 * 判断accNbr不为空,并且取最新的acctId 之后增强yzfBean
	 * @param yzfBean
	 */
	private boolean enhanceYzfBean(AcctItemYzfBean yzfBean) {
		try {
			List<String> prodInst = redisClient.hvals("BILLING.HASHPRDID:" + yzfBean.getProdInstId());
			if(!Tools.isNull(prodInst)) {
				String[] listProdInst = prodInst.get(0).split("\\^", -1);
				String statusCd = listProdInst[4];
				if("100000".equals(statusCd) || "120000".equals(statusCd)) {
					Long acctId = Long.valueOf(listProdInst[10]);
					String accNbr = listProdInst[3];
					yzfBean.setAccNbr(accNbr);
					yzfBean.setAcctId(acctId);
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("判断accNbr不为空,并且取最新的acctId 之后增强yzfBean异常：" +e.getLocalizedMessage());
			return false;
		}
		return false;
	}
	
	/**
	 * 判断是否不欠费
	 * @param yzfBean
	 * @return
	 */
	private boolean judgeIfArrears(AcctItemYzfBean yzfBean) {
		Map<String,Object> resultMap = null;
		try {
			boolean acctFlag = this.queryOwe("queryOweFeeByAcctId", yzfBean);
			if(!acctFlag) return false;
			boolean prodInstFlag = this.queryOwe("queryOweFeeByProdInstId", yzfBean);
			//账户和用户都不欠费 ->返还
			if(acctFlag && prodInstFlag) {
				return true;
			}
		} catch (Exception e) {
			logger.error(yzfBean + "判断是否不欠费查询异常:" + e.getLocalizedMessage());
			return false;
		}
		return false;
	}

	private boolean queryOwe(String key, AcctItemYzfBean yzfBean)  throws Exception{
		Map<String,Object> resultMap = null;
		Object selectObjAcct = commonDaos.selectOne(NAME_SPACE + key, yzfBean, DBContextHolder.DATA_SOURCE_ACCT);
		resultMap = selectObjAcct == null ? null : (Map<String, Object>)selectObjAcct;
		if(Tools.isNull(resultMap) || Tools.isNull(resultMap.get("oweFee"))) {
			return true;
		}else {
			Long oweAcct = Long.valueOf(String.valueOf(resultMap.get("oweFee")));
			if(oweAcct <= 0) return true;
		}
        return false;
	}

	/**
	 * 判断是否销账金额大于0
	 * @param yzfBean
	 * @return
	 */
	private boolean judgeIfCancel(AcctItemYzfBean yzfBean) {
		List<Map<String,Object>> resultList = null;
		try {
			resultList= commonDaos.selectList(NAME_SPACE + "queryAmount", yzfBean, DBContextHolder.DATA_SOURCE_ACCT);
			return Tools.isNull(resultList) ? false : true;
		} catch (Exception e) {
			logger.error(yzfBean + "判断是否销账金额大于0异常:" + e.getLocalizedMessage());
			return false;
		}
	}
	
	/**
	 * 判断是否上月未返还
	 * @param yzfBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean judgeIfReturn(AcctItemYzfBean yzfBean) {
		Map<String,Object> resultMap = null;
		try {
			Object selectObj = commonDaos.selectOne(NAME_SPACE + "queryReturn", yzfBean, DBContextHolder.DATA_SOURCE_ACCT);
			resultMap = selectObj == null ? null : (Map<String, Object>)selectObj;
			if(Tools.isNull(resultMap) || Tools.isNull(resultMap.get("record"))) {
				return false;
			}
			Long record = Long.valueOf(String.valueOf(resultMap.get("record")));
			if(record <= 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error(yzfBean + "判断是否上月未返还异常:" + e.getLocalizedMessage());
			return false;
		}
		return false;
	}
	
	/**
	 * 初始化offerIdMap
	 * @throws Exception
	 */
	private void setOfferIdMap() {
		List<Map<String, Integer>> offerIdList = null;
		try {
			offerIdList = commonDaos.selectList(NAME_SPACE + "queryOfferIdList", null, DBContextHolder.DATA_SOURCE_CUST);
			Map<Integer, String> offerIdMap = new HashMap<>();
			Optional.ofNullable(offerIdList)
					.orElse(new ArrayList<>())
					.stream()
					.filter(Objects::nonNull)
					.forEach(offerIdObj -> offerIdMap.put(offerIdObj.get("offerId"), String.valueOf(offerIdObj.get("attrId"))));
			this.offerIdMap = offerIdMap;
			if(Tools.isNull(offerIdMap)) {
				logger.error("queryOfferIdList查询数据为空！");
			}
		} catch (Exception e) {
			logger.error("queryOfferIdList初始化offerIdMap异常:" +e.getLocalizedMessage());
		}
		
	}
	
	/**
	 * 获取本地网Map 
	 * key:latnId 
	 * value:latnName
	 * @return
	 */
	private Map<String,String> getLatnMap(){
		List<Map<String,String>> latnList = null;
		Map<String,String> latnMap = new HashMap<>();
		try {
			latnList = commonDaos.selectList(NAME_SPACE + "getLatn", null, DBContextHolder.DATA_SOURCE_ACCT);
			Optional.ofNullable(latnList)
					.orElse(new ArrayList<>())
					.stream()
					.filter(Objects::nonNull)
					.forEach(latnObj -> latnMap.put(latnObj.get("latnId"), latnObj.get("latnName")));
		} catch (Exception e) {
			logger.error("查询本地网异常！");
		}
		return latnMap;
	}
	
	/**
	 * List分割 
	 * @param list
	 * @return
	 */
	public List<List<AcctItemYzfBean>> groupList(List<AcctItemYzfBean> list) {
		List<List<AcctItemYzfBean>> listGroup = new CopyOnWriteArrayList<List<AcctItemYzfBean>>();
	    int listSize = list.size();
	    // 子集合的长度
	    int toIndex = 2000;
	    for (int i = 0; i < list.size(); i += 2000) {
	    	if (i + 2000 > listSize) {
	    		toIndex = listSize - i;
	        }
	        List<AcctItemYzfBean> newList = new CopyOnWriteArrayList<>(list.subList(i, i + toIndex));
	        listGroup.add(newList);
	    }
	    return listGroup;
	}
	
	/**
	 * DCA查询accNbr
	 * @param prodInstId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAccNbr(Long prodInstId) {
		String accNbr = "";
		try {
			List<String> accNumList = redisClient.hvals("BILLING.HASHPRDID:" + prodInstId);
			if(!Tools.isNull(accNumList)) {
				for (String accNumstr : accNumList) {
					String[] accNbrArr = accNumstr.split("\\^");
					if(!"".equals(accNbrArr[3])) {
						accNbr = accNbrArr[3];
						break;
					}
				}
			}
//			if(Tools.isNull(accNbr)) {
//				Map<String,Object> param = new HashMap<>();
//				param.put("prodInstId", prodInstId);
//				Map<String,String> resultMap = null;
//				Object selectObj = commonDaos.selectOne(NAME_SPACE + "queryAccNbr", param, DBContextHolder.DATA_SOURCE_CUST);
//				resultMap = selectObj == null ? new HashMap<>() : (Map<String, String>)selectObj;
//				return resultMap.get("accNbr");
//			}
		} catch (Exception e) {
			logger.error("查询accNbr异常：" + e.getLocalizedMessage());
			return null;
		}
		return accNbr;
	
	}
	
	private void setCommonRegionMap() {
		List<Map<String, Object>> regionList = null;
		try {
			regionList = commonDaos.selectList(NAME_SPACE + "queryCommonRegion", null, DBContextHolder.DATA_SOURCE_ACCT);
			Map<Integer, String> commonRegionMap = new HashMap<>();
			Optional.ofNullable(regionList)
					.orElse(new ArrayList<>())
					.stream()
					.filter(Objects::nonNull)
					.forEach(commonRegionObj -> commonRegionMap.put(Integer.valueOf(String.valueOf(commonRegionObj.get("regionId"))), String.valueOf(commonRegionObj.get("regionName"))));
			this.commonRegionMap = commonRegionMap;
			if(Tools.isNull(commonRegionMap)) {
				logger.error("queryCommonRegion查询数据为空！");
			}
		} catch (Exception e) {
			logger.error("queryCommonRegion初始化commonRegionMap异常:" +e.getLocalizedMessage());
		}
		
	}


	public static void main(String[] args) {
		List<String> list = null;
		//list.stream().forEach(System.out::println);//NullPointerException
		Optional.ofNullable(list)
				.orElse(new ArrayList<>())
				.forEach(System.out::println);//ok
		
	}
	

}
