package com.tydic.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
@SuppressWarnings("unchecked")
public class JsonUtils {

	public static String object2String(Object object) {
		String jsonString = JSON.toJSONString(object, new ValueFilter() {
			@Override
			public Object process(Object obj, String s, Object v) {
				if (v == null){
					return "";
				}
				return v;
			}
		});
		return jsonString;
	}

	public static <T> T string2Object(String input, Class<T> type) {
		T object = JSON.parseObject(input, type);
		return object;
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		jsonStr = jsonStr.trim();
		if (jsonStr != null && jsonStr.startsWith("{") && jsonStr.endsWith("}")) {
			Map<String, Object> map = new HashMap<>();
			// 最外层解析
			JSONObject json = JSONObject.parseObject(jsonStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				// 如果内层还是数组的话，继续解析
				if (v instanceof JSONArray) {
					List<Object> list = new ArrayList<>();
					Iterator<Object> it = ((JSONArray) v).iterator();
					while (it.hasNext()) {
						Object obj = it.next();
						if (obj instanceof JSONObject) {
							JSONObject jsonObj = (JSONObject) obj;
							list.add(parseJSON2Map(jsonObj.toString()));
						} else {
							list.add(obj);
						}
					}
					map.put(k.toString(), list);
				} else {
					Map<String, Object> m = parseJSON2Map(v.toString());
					if (m == null)
						map.put(k.toString(), v);
					else
						map.put(k.toString(), m);
				}
			}
			return map;
		} else if (jsonStr != null && jsonStr.startsWith("[") && jsonStr.endsWith("]")){
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> list = new ArrayList<>();
            JSONArray array = JSONArray.parseArray(jsonStr);
            for (Object object : array) {
                list.add(parseJSON2Map(object.toString()));
            }
            map.put("data", list);
            return map;
        } else {
			return null;
		}
	}
	
	public static Map<String, Object> parseJSONListMap(List<?> list) {
		String jsonStr=JSONObject.toJSONString(list);
		 return JsonUtils.parseJSON2Map(jsonStr);
	}
	
	public static Map<String, Object> json2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<>();
        if(jsonStr != null && !"".equals(jsonStr)){
            //最外层解析
            JSONObject json = JSONObject.parseObject(jsonStr);
            for (Object k : json.keySet()) {
                Object v = json.get(k);
                //如果内层还是数组的话，继续解析
                if (v instanceof JSONArray) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Iterator<Object> it =  ((JSONArray) v).iterator();
                    while (it.hasNext()) {
                        JSONObject json2 = (JSONObject) it.next();
                        list.add(json2Map(json2.toString()));
                    }
                    map.put(k.toString(), list);
                } else {
                    map.put(k.toString(), v);
                }
            }
            return map;
        }else{
            return null;
        }
    }

	public static void main(String[] args) {
//		String json = "{\"tcpCont\":{\"resTime\":\"20160202145959245\",\"sign\":\"xxxxxxxxxxxxx\",\"transactionId\":\"1001000101201602021234567890\"},\"svcCont\":{\"aa\":\"11\",\"bb\":\"22\",\"cc\":[1,2,3]}}";
		String json = "[{\"serviceCd\":[],\"factor\":{},\"if_exp\":\"null\",\"class\":\"\",\"seq\":\"2\",\"actionCd\":\"qwer\",\"actionName\":\"测试0609\"},{\"serviceCd\":[\"QryDayFlowInfo\"],\"factor\":{\"offerName\":\"offerName\",\"ifCanOrder\":\"ifCanOrder\",\"lastUseFlow\":\"lastUseFlow\",\"telNo\":\"deviceNumber\"},\"if_exp\":\"null\",\"class\":\"queryServiceOffer\",\"seq\":\"3\",\"actionCd\":\"DAYFLAWNOTICE\",\"actionName\":\"日租包推荐提醒\"},{\"serviceCd\":[\"QryContractRecomendInfo\"],\"factor\":{\"ifHadPromote\":\"ifHadPromote\",\"mainOfferName\":\"mainOfferName\",\"oldPromoteName\":\"oldPromoteName\",\"expDate\":\"expDate\"},\"if_exp\":\"null\",\"class\":\"queryServiceOffer\",\"seq\":\"4\",\"actionCd\":\"PromoteRecomm\",\"actionName\":\"合约到期\"},{\"serviceCd\":[\"SECCARDNOTICE\"],\"factor\":{\"hadSecCardNum\":\"hadSecCardNum\",\"offerName\":\"offerName\",\"capableNum\":\"capableNum\",\"allHadNum\":\"allHadNum\",\"telNo\":\"telNo\"},\"if_exp\":\"null\",\"class\":\"queryServiceOffer\",\"seq\":\"5\",\"actionCd\":\"SECCARDNOTICE\",\"actionName\":\"加装副卡提醒\"},{\"serviceCd\":[\"OWNNOTICE\"],\"factor\":{\"offerName\":\"offerName\",\"ownAmount\":\"ownAmount\",\"telNo\":\"telNo\"},\"if_exp\":\"1\",\"class\":\"queryServiceOffer\",\"seq\":\"6\",\"actionCd\":\"OWNNOTICE\",\"actionName\":\"欠费提醒\"},{\"serviceCd\":[\"SILENCEACTIVE\"],\"factor\":{\"ifSilent\":\"ifSilent\",\"offerName\":\"offerName\",\"teleType\":\"teleType\",\"telNo\":\"telNo\"},\"if_exp\":\"null\",\"class\":\"queryServiceOffer\",\"seq\":\"7\",\"actionCd\":\"SILENCEACTIVE\",\"actionName\":\"全业务融合沉默激活提醒\"},{\"serviceCd\":[\"QrySingleMobileInfo\"],\"factor\":{\"offerName\":\"offerName\",\"ifUnioned\":\"ifUnioned\",\"ifAutoUnion\":\"ifAutoUnion\",\"telNo\":\"telNo\"},\"if_exp\":\"null\",\"class\":\"queryServiceOffer\",\"seq\":\"8\",\"actionCd\":\"CDMATOUNION\",\"actionName\":\"单移动转融合提醒\"},{\"serviceCd\":[\"QrySingleWideInfo\"],\"factor\":{\"offerName\":\"offerName\",\"ifUnioned\":\"ifUnioned\",\"ifAutoUnion\":\"ifAutoUnion\",\"telNo\":\"telNo\"},\"if_exp\":\"null\",\"class\":\"queryServiceOffer\",\"seq\":\"9\",\"actionCd\":\"TOUNION\",\"actionName\":\"单宽转融合提醒\"},{\"serviceCd\":[\"LTECDMA\"],\"factor\":{\"ifOpen4g\":\"ifOpen4g\",\"prodExamp\":\"prodExamp\",\"if4gTerm\":\"if4gTerm\",\"telNo\":\"telNo\"},\"if_exp\":\"null\",\"class\":\"queryServiceOffer\",\"seq\":\"10\",\"actionCd\":\"UP4G\",\"actionName\":\"升级4G\"}]";
		Map<String, Object> m = JsonUtils.parseJSON2Map(json);
		System.out.println(m);
		List<Map<String, Object>> lst = (List<Map<String, Object>>) m.get("data");
		for (Map<String, Object> map : lst) {
		    System.out.println(map.get("serviceCd"));
		    System.out.println(map.get("factor"));
		    System.out.println(map.get("if_exp"));
		    System.out.println(map.get("class"));
		    System.out.println(map.get("seq"));
        }
		System.out.println(JsonUtils.parseJSON2Map(json));
		
		

	}
}
