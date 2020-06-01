package com.tydic.common.utils;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContractRootUtil {
    private static String  CONTRACT_ROOT = "contractRoot";
    private static String  TCP_CONT = "tcpCont";
    private static String  SVC_CONT = "svcCont";
    
    public static Map<String,Object> toHeaderMap(Object code,Object reason,Object message,Object referenceError) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("code", code);
        headerMap.put("reason", reason);
        headerMap.put("message", message);
        headerMap.put("referenceError", referenceError);
        return headerMap;
    }
    
    public static String toJson(Map<String, Object> tcpCont, Map<String, Object> svcCont) {
        Map<String, Object> root = new HashMap<>();
        Map<String, Object> contractRoot = new HashMap<>();
        contractRoot.put(TCP_CONT, tcpCont);
        contractRoot.put(SVC_CONT, svcCont);
        root.put(CONTRACT_ROOT, contractRoot);
        return JsonUtils.object2String(root);
    }

	@SuppressWarnings("unchecked")
    public static Map<String, Object> getTcpCont(String json) {
		Map<String, Object> contractRoot = getContractRoot(json);
		if (!Tools.isNull(contractRoot.get(TCP_CONT))) {
            return (Map<String, Object>) contractRoot.get(TCP_CONT);
        }else {
            return Collections.EMPTY_MAP;
        }
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getContractRoot(String json) {
        Map<String, Object> contractRootMap = JsonUtils.parseJSON2Map(json);
		Map<String, Object> contractRoot = (Map<String, Object>) contractRootMap.get(CONTRACT_ROOT);
        return contractRoot;
    }

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSvcCont(String json) {
	    Map<String, Object> contractRoot = getContractRoot(json);
	    if (!Tools.isNull(contractRoot.get(SVC_CONT))) {
            return (Map<String, Object>) contractRoot.get(SVC_CONT);
        }else {
            return Collections.EMPTY_MAP;
        }
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getTcpCont(Map<String, Object> json) {
        Map<String, Object> contractRoot = (Map<String, Object>) json.get(CONTRACT_ROOT);
        if (!Tools.isNull(contractRoot.get(TCP_CONT))) {
            return (Map<String, Object>) contractRoot.get(TCP_CONT);
        }else {
            return Collections.EMPTY_MAP;
        }

    }
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSvcCont(Map<String, Object> vo) {
        Map<String, Object> contractRoot = (Map<String, Object>) vo.get(CONTRACT_ROOT);
        if (!Tools.isNull(contractRoot.get(SVC_CONT))) {
            return (Map<String, Object>) contractRoot.get(SVC_CONT);
        }else {
            return Collections.EMPTY_MAP;
        }
    }

	public static String createRequest(String svcCode, Map<String, Object> svcCont) {
        Map<String, Object> tcpCont = new HashMap<>();
        if(!Tools.isNull(svcCode)) {
        	tcpCont.put("svcCode", svcCode);
        }
        tcpCont.put("reqTime", Tools.addDate("yyyy-MM-dd HH:mm:ss", Calendar.DATE, 0));
        tcpCont.put("transactionId", Tools.addDate("yyyyMMddHHmmssSSS", Calendar.DATE, 0)+Tools.getRandom(4));
        tcpCont.put("version", "1.0");
        return ContractRootUtil.toJson(tcpCont, svcCont);
	}
	
	public static String createResponseToken(String resultCode, String resultMsg) {
		return createResponseToken(resultCode, resultMsg, null);
	}
	
	public static String createResponseToken(String resultCode, String resultMsg, Object object) {
        Map<String, Object> tcpCont = new HashMap<>();
        tcpCont.put("responTime", Tools.addDate("yyyy-MM-dd HH:mm:ss", Calendar.DATE, 0));
        tcpCont.put("transactionId", Tools.addDate("yyyyMMddHHmmssSSS", Calendar.DATE, 0)+Tools.getRandom(4));
        Map<String, Object> svcCont = new HashMap<String, Object>();
        svcCont.put("resultCode", resultCode);
        svcCont.put("resultMsg", resultMsg);
        svcCont.put("resultObject", object);
        return ContractRootUtil.toJson(tcpCont, svcCont);
	}
	
	public static String createResponse(String resultCode, String resultMsg,String transactionId) {
		return createResponse(resultCode, resultMsg, null,transactionId);
	}
	
	public static String createResponse(String resultCode, String resultMsg, Object object,String transactionId) {
        Map<String, Object> tcpCont = new HashMap<>();
        tcpCont.put("responTime", Tools.addDate("yyyy-MM-dd HH:mm:ss", Calendar.DATE, 0));
        if(Tools.isNull(transactionId)){
        	tcpCont.put("transactionId", Tools.addDate("yyyyMMddHHmmssSSS", Calendar.DATE, 0)+Tools.getRandom(4));
        }else{
        	tcpCont.put("transactionId", transactionId);
        }
        Map<String, Object> svcCont = new HashMap<String, Object>();
        svcCont.put("resultCode", resultCode);
        svcCont.put("resultMsg", resultMsg);
        svcCont.put("resultObject", object);
        return ContractRootUtil.toJson(tcpCont, svcCont);
	}
	
	public static String createResponse(ResultEnum resultEnum,String transactionId) {
        return createResponse(resultEnum, null,transactionId);
    }
	
	public static String createResponse(ResultEnum resultEnum, Object object) {
        Map<String, Object> tcpCont = new HashMap<>();
        tcpCont.put("responTime", Tools.addDate("yyyy-MM-dd HH:mm:ss", Calendar.DATE, 0));
        tcpCont.put("transactionId", System.currentTimeMillis() + "");
        Map<String, Object> svcCont = new HashMap<String, Object>();
        svcCont.put("resultCode", resultEnum.getCode()+"");
        svcCont.put("resultMsg", resultEnum.getMsg());
        svcCont.put("resultObject", object);
        return ContractRootUtil.toJson(tcpCont, svcCont);
    }
	
	public static String createResponse(ResultEnum resultEnum, Object object,String transactionId) {
        Map<String, Object> tcpCont = new HashMap<>();
        tcpCont.put("responTime", Tools.addDate("yyyy-MM-dd HH:mm:ss", Calendar.DATE, 0));
        if(Tools.isNull(transactionId)){
        	tcpCont.put("transactionId", Tools.addDate("yyyyMMddHHmmssSSS", Calendar.DATE, 0)+Tools.getRandom(4));
        }else{
        	tcpCont.put("transactionId", transactionId);
        }
        Map<String, Object> svcCont = new HashMap<String, Object>();
        svcCont.put("resultCode", resultEnum.getCode()+"");
        svcCont.put("resultMsg", resultEnum.getMsg());
        svcCont.put("resultObject", object);
        return ContractRootUtil.toJson(tcpCont, svcCont);
    }

    
}
