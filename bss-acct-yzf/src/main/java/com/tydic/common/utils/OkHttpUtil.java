package com.tydic.common.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressWarnings({"unchecked","rawtypes","unused"})
public class OkHttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

	/**
	 * get
	 * 
	 * @param url
	 *            请求的url
	 * @param queries
	 *            请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return
	 */
	/**
	 * get
	 * 
	 * @param url
	 *            请求的url
	 * @param queries
	 *            请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return
	 */
	public static String get(String url, Map<String, String> queries,int timeWait) {
		String uuid=UUID.randomUUID().toString();
		String responseBody = "";
		StringBuffer sb = new StringBuffer(url);
		if (queries != null && queries.keySet().size() > 0) {
			boolean firstFlag = true;
			
			Iterator iterator = queries.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry<String, String>) iterator.next();
				if (firstFlag) {
					sb.append("?" + entry.getKey() + "=" + entry.getValue());
					firstFlag = false;
				} else {
					sb.append("&" + entry.getKey() + "=" + entry.getValue());
				}
			}
		}
		Request request = new Request.Builder().url(sb.toString()).build();
		Response response = null;
		try {
			OkHttpClient okHttpClient = SpringContextUtils.getApplicationContext().getBean(OkHttpClient.class);
			if(!Tools.isNull(timeWait) && Tools.isNumber(timeWait+"") && timeWait>0) {
				okHttpClient.newBuilder().connectTimeout(timeWait, TimeUnit.SECONDS)
				.readTimeout(timeWait, TimeUnit.SECONDS)
				.writeTimeout(timeWait,TimeUnit.SECONDS).build();
			}
			response = okHttpClient.newCall(request).execute();
			int status = response.code();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
		 	logger.error(uuid+"##okhttp3 get error##url##"+url);
		 	logger.error(uuid+"##okhttp3 get error##param##"+queries);
		 	logger.error(uuid+"##okhttp3 get error##"+e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}

	/**
	 * patch
	 * @param url
	 *            请求的url
	 * @param params
	 *            patch form 提交的参数
	 * @return
	 */
	public static String patch(String url, Map<String, String> params,int timeWait) {
		String uuid=UUID.randomUUID().toString();
		String responseBody = "";
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JsonUtils.object2String(params));
		Request request = new Request.Builder().url(url).patch(requestBody).build();
		Response response = null;
		try {
			OkHttpClient okHttpClient = SpringContextUtils.getApplicationContext().getBean(OkHttpClient.class);
			if(!Tools.isNull(timeWait) && Tools.isNumber(timeWait+"") && timeWait>0) {
				okHttpClient.newBuilder().connectTimeout(timeWait, TimeUnit.SECONDS)
				.readTimeout(timeWait, TimeUnit.SECONDS)
				.writeTimeout(timeWait,TimeUnit.SECONDS).build();
			}
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			logger.error(uuid+"##okhttp3 patch error##url##"+url);
		 	logger.error(uuid+"##okhttp3 patch error##param##"+params);
		 	logger.error(uuid+"##okhttp3 patch error##"+e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}
	
	/**
	 * put
	 * @param url
	 *            请求的url
	 * @param params
	 *            put form 提交的参数
	 * @return
	 */
	public static String put(String url, Map<String, String> params,int timeWait) {
		String uuid=UUID.randomUUID().toString();
		String responseBody = "";
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JsonUtils.object2String(params));
		Request request = new Request.Builder().url(url).put(requestBody).build();
		Response response = null;
		try {
			OkHttpClient okHttpClient =SpringContextUtils.getApplicationContext().getBean(OkHttpClient.class);
			if(!Tools.isNull(timeWait) && Tools.isNumber(timeWait+"") && timeWait>0) {
				okHttpClient.newBuilder().connectTimeout(timeWait, TimeUnit.SECONDS)
				.readTimeout(timeWait, TimeUnit.SECONDS)
				.writeTimeout(timeWait,TimeUnit.SECONDS).build();
			}
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			logger.error(uuid+"##okhttp3 put error##url##"+url);
		 	logger.error(uuid+"##okhttp3 put error##param##"+params);
		 	logger.error(uuid+"##okhttp3 put error##"+e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}
	/**
	 * get
	 * 
	 * @param url
	 *            请求的url
	 * @param queries
	 *            请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return
	 */
	public static String getForHeader(String url, Map<String, String> queries,int timeWait ) {
		String uuid=UUID.randomUUID().toString();
		String responseBody = "";
		StringBuffer sb = new StringBuffer(url);
		if (queries != null && queries.keySet().size() > 0) {
			boolean firstFlag = true;
			Iterator iterator = queries.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry<String, String>) iterator.next();
				if (firstFlag) {
					sb.append("?" + entry.getKey() + "=" + entry.getValue());
					firstFlag = false;
				} else {
					sb.append("&" + entry.getKey() + "=" + entry.getValue());
				}
			}
		}
		Request request = new Request.Builder().addHeader("key", "value").url(sb.toString()).build();
		Response response = null;
		try {
			OkHttpClient okHttpClient = SpringContextUtils.getApplicationContext().getBean(OkHttpClient.class);
			if(!Tools.isNull(timeWait) && Tools.isNumber(timeWait+"") && timeWait>0) {
				okHttpClient.newBuilder().connectTimeout(timeWait, TimeUnit.SECONDS)
				.readTimeout(timeWait, TimeUnit.SECONDS)
				.writeTimeout(timeWait,TimeUnit.SECONDS).build();
			}
			response = okHttpClient.newCall(request).execute();
			int status = response.code();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			logger.error(uuid+"##okhttp3 getForHeader error##url##"+url);
		 	logger.error(uuid+"##okhttp3 getForHeader error##param##"+queries);
		 	logger.error(uuid+"##okhttp3 getForHeader error##"+e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}

	/**
	 * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"} 参数一：请求Url
	 * 参数二：请求的JSON 参数三：请求回调
	 */
	public static String postJsonParams(String url, String jsonParams,int timeWait) {
		String uuid=UUID.randomUUID().toString();
		String responseBody = "";
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
		Request request = new Request.Builder().url(url).post(requestBody).build();
		Response response = null;
		try {
			OkHttpClient okHttpClient =SpringContextUtils.getApplicationContext().getBean(OkHttpClient.class);
			if(!Tools.isNull(timeWait) && Tools.isNumber(timeWait+"") && timeWait>0) {
				okHttpClient.newBuilder().connectTimeout(timeWait, TimeUnit.SECONDS)
				.readTimeout(timeWait, TimeUnit.SECONDS)
				.writeTimeout(timeWait,TimeUnit.SECONDS).build();
			}
			response = okHttpClient.newCall(request).execute();
			int status = response.code();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			logger.error(uuid+"##okhttp3 postJsonParams error##url##"+url);
		 	logger.error(uuid+"##okhttp3 postJsonParams error##param##"+jsonParams);
		 	logger.error(uuid+"##okhttp3 postJsonParams error##"+e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}
	
	/**
	 * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"} 
	 * 参数一：请求Url
	 * 参数二：请求的JSON
	 * 参数三：请求header
	 */
	public static String postJsonParams(String url, String jsonParams, Map<String, String> header,int timeWait) {
		String uuid=UUID.randomUUID().toString();
		String responseBody = "";
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
		Request.Builder post = new Request.Builder();
		if(!Tools.isNull(header)) {
			for(Entry<String,String> entry : header.entrySet()) {
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
		Request request = post.url(url).post(requestBody).build();
		Response response = null;
		try {
			OkHttpClient okHttpClient = SpringContextUtils.getApplicationContext().getBean(OkHttpClient.class);
			if(!Tools.isNull(timeWait) && Tools.isNumber(timeWait+"") && timeWait>0 ) {
				okHttpClient.newBuilder().connectTimeout(timeWait, TimeUnit.SECONDS)
				.readTimeout(timeWait, TimeUnit.SECONDS)
				.writeTimeout(timeWait,TimeUnit.SECONDS).build();
			}
			response = okHttpClient.newCall(request).execute();
			int status = response.code();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			logger.error(uuid+"##okhttp3 postJsonParams error##url##"+url);
			logger.error(uuid+"##okhttp3 postJsonParams error##header##"+header);
		 	logger.error(uuid+"##okhttp3 postJsonParams error##param##"+jsonParams);
		 	logger.error(uuid+"##okhttp3 postJsonParams error##"+e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}

	/**
	 * Post请求发送xml数据.... 参数一：请求Url 参数二：请求的xmlString 参数三：请求回调
	 */
	public static String postXmlParams(String url, String xml) {
		String uuid=UUID.randomUUID().toString();
		String responseBody = "";
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
		Request request = new Request.Builder().url(url).post(requestBody).build();
		Response response = null;
		try {
			OkHttpClient okHttpClient = SpringContextUtils.getApplicationContext().getBean(OkHttpClient.class);
			response = okHttpClient.newCall(request).execute();
			int status = response.code();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			logger.error(uuid+"##okhttp3 postXmlParams error##url##"+url);
		 	logger.error(uuid+"##okhttp3 postXmlParams error##param##"+xml);
		 	logger.error(uuid+"##okhttp3 postXmlParams error##"+e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}
	 
}