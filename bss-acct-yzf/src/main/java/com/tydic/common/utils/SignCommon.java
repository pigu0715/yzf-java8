package com.tydic.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class SignCommon {
	public static String getSignValue(Map<String,Object> oldBusinessParams,String signType,String key) throws Exception {
		//将oldBusinessParams排序
		String assembelSignaturingData = assembelSignaturingData(oldBusinessParams);
		String encryptValue = "";
		//读取signTypeInfo中的key,针对不同的加密方式，编写对应加密方法
		switch (signType){
			case "1" :  //MD5-16位大写
				encryptValue = MD5Encrypt.md5(assembelSignaturingData, key).substring(8, 24).toUpperCase();
				break;
				
			case "2"://MD5-16位小写
				encryptValue = MD5Encrypt.md5(assembelSignaturingData, key).substring(8, 24);
				break;
			
			case "3"://MD5-32位大写
				encryptValue = MD5Encrypt.md5(assembelSignaturingData, key).toUpperCase();
				break;
			
			case "4"://MD5-32位小写
				encryptValue = MD5Encrypt.md5(assembelSignaturingData, key);
				break;
			case "5"://HMACSHA256
				encryptValue = HMACSHA256(assembelSignaturingData,signType);
				break;
		}
			 
		return encryptValue;
	}
	
	 /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
    
    //顺序组装请求参数，用于签名/校验
    public static String assembelSignaturingData(Map<String,Object> map) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>(map);
        for (Map.Entry<String, Object> ent : treeMap.entrySet()) {
            String name = ent.getKey();
            Object value= ent.getValue();
            if (value!=null && !"".equals(value.toString()) && !"sign".equals(name)) {
                sb.append(name).append('=').append(ent.getValue()).append('&');
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
		Map<String,Object> a= new HashMap<String,Object>();
		String jsonStr="{ \"contractRoot\": { \"svcCont\": { \"businessParams\": { \"amount\": 100, \"sysUserCode\": \"91919031410\", \"payUserId\": 982095089, \"orgId\": 55124563, \"latnId\": \"919\", \"orderTime\": \"2020-01-21 11:39:39\", \"payUserSerial\": 541802844679572540, \"objId\": \"18091952090\", \"tradeTypeId\": \"1\", \"commodityDesc\": \"前台在线缴费\", \"objType\": \"0\", \"staffId\": \"1100003306\", \"commodityName\": \"前台在线缴费\" }, \"encryptValue\": \"A1D05C50683AFD55\" }, \"tcpCont\": { \"traceId\": \"345a2ca1-0db9-4240-ac20-be2b45c8835f\", \"spanId\": \"payChannel\", \"sampledFlag\": \"0\", \"flowCode\": \"SC_PAYCENTER_QUERY_CASHIER_INIT\", \"debugFlag\": \"0\", \"parentSpanId\": \"\", \"transactionId\": \"57ab29ca-c0a5-483a-8da8-710df8358d1f\" } } }";
		Map<String,Object> svcMap=ContractRootUtil.getSvcCont(jsonStr);
		Map<String,Object> businessParams=(Map<String, Object>) svcMap.get("businessParams");
		try {
			System.out.println(getSignValue(businessParams, "1","SX_IAS"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}