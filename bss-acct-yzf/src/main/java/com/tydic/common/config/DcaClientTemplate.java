package com.tydic.common.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.tydic.common.utils.DesTool;
import com.tydic.dca.DCAClient;
import com.tydic.dca.config.DCAMConfig;
import com.tydic.dca.connection.DCAConnector;

@Configuration
public class DcaClientTemplate {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	ThreadLocal<DCAClient> curDcaPool = new ThreadLocal<DCAClient>();

	@Autowired
	DcaConfig dcaConfig;

	@Value("${dca.flag}")
	private String dcaFlag;

	private  DCAClient dcaClient() {
		DCAClient con = null;
		try {
			con = curDcaPool.get();
			if (con != null) {
				return con;
			} else {
					DCAMConfig dcamConfig = new DCAMConfig(
							dcaConfig.getIp(), 
							dcaConfig.getPort(), 
							dcaConfig.getSlaveIp(),
							dcaConfig.getSlavePort(), 
							dcaConfig.getAcctID(), 
							dcaConfig.getUserName(),
							DesTool.dec(dcaConfig.getPassWord())
							);
					DCAConnector connector = new DCAConnector(dcamConfig);
					con = connector.connect();
					curDcaPool.set(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/***
	 * 底层存放的是list
	 */
	public List<String> lrange(String key) {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		logger.debug("start get dca key:" + key);
		List<String> result = null;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();

			result = dcaClient().lrange(key, 0, -1);
			endTime = System.currentTimeMillis();
			logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + ",dca key[" + key + "],result:" + result);
			if (result != null && result.size() == 0) {
				result = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Thread.currentThread().getId()+":dca get key[" + key + "] value error " + e.getMessage());
		}

		return result;
	}
 
	public Long lrem(String key, long count, String value) throws Exception {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		long ret = 0;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();

			ret = dcaClient().lrem(key, count, value);

			endTime = System.currentTimeMillis();
			logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + " dca lrem key[" + key + "],value[" + value
					+ "],dca ret:" + ret);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Thread.currentThread().getId()+"dca lrem key[" + key + "],value[" + value + "]  error " + e.getMessage());
		}
		return ret;
	}

	public Long lpush(String key, String value) throws Exception {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		long ret = 0;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();

			ret = dcaClient().lpush(key, value);

			endTime = System.currentTimeMillis();
			logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + " dca lpush key[" + key + "],value[" + value
					+ "],dca ret:" + ret);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Thread.currentThread().getId()+"dca lpush key[" + key + "],value[" + value + "]  error " + e.getMessage());
		}
		return ret;
	}

	public Long incr(String key) throws Exception {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		Long ret = 0L;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();

			ret = dcaClient().incr(key);

			endTime = System.currentTimeMillis();
			
			logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + ", dca incr key[" + key + "] ret:" + ret);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getId()+" dca incr key[" + key + "]  value error :" + e.getMessage());
		}

		return ret;
	}
	
	public Long hset(String key,String field, String value) throws Exception {
		long ret = 0;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();

			ret = dcaClient().hset(key, field, value);

			endTime = System.currentTimeMillis();
			logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + " dca hset key[" + key + "],value[" + value
					+ "],dca ret:" + ret);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getId()+"dca hset key[" + key + "],value[" + value + "]  error " + e.getMessage());
		}
		return ret;
	}
	
	/***
	 * 底层存放的是list
	 */
	public List<String> hvals(String key) {
		List<String> result = null;
		long startTime = 0L;
		long endTime = 0L;
		if(logger.isDebugEnabled()) {
			logger.debug("start hvals dca key:" + key);
		}
		try {
			startTime = System.currentTimeMillis();

			result = dcaClient().hvals(key);
			endTime = System.currentTimeMillis();
			if(logger.isDebugEnabled()) {
				logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + ",dca hvals key[" + key + "],result:" + result);
			}
			if (result != null && result.size() == 0) {
				result = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Thread.currentThread().getId()+":dca hvals key[" + key + "] value error " + e.getMessage());
		}

		return result;
	}
	
	public String hget(String key,String field) {
		String result = null;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();

			result = dcaClient().hget(key,field);
			endTime = System.currentTimeMillis();
			logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + ",dca hget key[" + key + "]  field[" + field + "],result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Thread.currentThread().getId()+":dca hget  key[" + key + "]  field[" + field + "] value error " + e.getMessage());
		}

		return result;
	}
	
	public Long hdel(String key,String field) {
		Long result = null;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();

			result = dcaClient().hdel(key,field);
			endTime = System.currentTimeMillis();
			logger.debug(Thread.currentThread().getId()+"time cost: " + (endTime - startTime) + ",dca hdel key[" + key + "]  field[" + field + "],result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Thread.currentThread().getId()+":dca hdel  key[" + key + "]  field[" + field + "] value error " + e.getMessage());
		}

		return result;
	}
	
	
}
