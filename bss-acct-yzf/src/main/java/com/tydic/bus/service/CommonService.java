package com.tydic.bus.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.common.utils.ContractRootUtil;
import com.tydic.common.utils.SpringContextUtils;
import com.tydic.common.utils.Tools;

/**
 * @author XC
 *
 */
@Service
public class CommonService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public String commonOper(Map<String, Object> data) {
		String uuid = UUID.randomUUID().toString();
		String result = "";
		String transactionId = "";
		try {
			Map<String, Object> svcContMap = (Map<String, Object>) data.get("SvcCont");
			Map<String, Object> soo = (Map<String, Object>) svcContMap.get("SOO");
			Map<String, Object> pubMap = (Map<String, Object>) soo.get("PUB_REQ");
			Map<String, Object> paraMap = (Map<String, Object>) soo.get("PARAM_REQ");
			String operName = String.valueOf(pubMap.get("OPER_NAME"));

			String message = ContractRootUtil.createRequest(operName, paraMap);
			if (logger.isDebugEnabled()) {
				logger.debug(uuid + "调用服务请求报文:" + message);
			}
			Map<String, Object> reqTcpContMap = ContractRootUtil.getTcpCont(message);
			transactionId = reqTcpContMap.get("transactionId") + "";

			result = this.commonMethod(message);

			if (logger.isDebugEnabled()) {
				logger.debug(uuid + "调用服务返回报文:" + result);
			}
		} catch (Exception e) {
			logger.error(uuid + "commonOper异常" + e.getMessage());
			result = ContractRootUtil.createResponse("2", "调用服务异常" + e.getMessage(), transactionId);
		}
		return result;
	}

	// 公共请求服务
	private String commonMethod(String message) throws Exception {
		Map<String, Object> tcpCont = ContractRootUtil.getTcpCont(message);
		Map<String, Object> svcCont = ContractRootUtil.getSvcCont(message);
		String svcCode = String.valueOf(tcpCont.get("svcCode"));
		String className = "";
		String methodName = "";
		if (svcCode.indexOf(".") > -1) {
			String[] ss = svcCode.split("\\.");
			className = ss[0];
			methodName = ss[1];
		}
		Object bean = SpringContextUtils.getBean(className);
		Class<?> clazz = bean.getClass();
		Method method = clazz.getMethod(methodName, Map.class);
		return String.valueOf(method.invoke(bean, new Object[] { svcCont }));
	}
	
	/**
	 * 应用测试是否存活
	 * @param parmMap
	 * @return
	 */
	public Map<String, Object> autoTest(Map<String, Object> parmMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String autoTest = String.valueOf(parmMap.get("autoTest"));
			if (!Tools.isNull(autoTest)) {
				resultMap.put("code", "0000");
			} else {
				resultMap.put("code", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

}
