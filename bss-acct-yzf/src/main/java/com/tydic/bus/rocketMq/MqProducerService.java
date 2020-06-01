package com.tydic.bus.rocketMq;

import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ctg.mq.api.CTGMQFactory;
import com.ctg.mq.api.IMQProducer;
import com.ctg.mq.api.bean.MQMessage;
import com.ctg.mq.api.bean.MQSendResult;
import com.ctg.mq.api.exception.MQException;
import com.ctg.mq.api.impl.MQProducerImpl;
import com.tydic.common.utils.Tools;


/**
 * 异步日志消息写入
 * @author shiyj
 *
 */
@Service
public class MqProducerService {
	
	private static Logger logger = LoggerFactory.getLogger(MqProducerService.class);
	
	@Value("${mq.namesrvAddr}")
	private String namesrvAddr;
	
	@Value("${mq.authId}")
	private String authId;
	
	@Value("${mq.authPwd}")
	private String authPwd;
	
	@Value("${mq.clusterName}")
	private String clusterName;
	
	@Value("${mq.tenantID}")
	private String tenantID;
	
	@Value("${mq.apiGroup}")
	private String apiGroup;
	
	@Value("${mq.apiTopic}")
	private String apiTopic;
		
	private IMQProducer producer = null;
	
	public IMQProducer getInstance() {
		if (!Tools.isNull(producer)) {
			return producer;
		}
		synchronized (MQProducerImpl.class) {
			if (!Tools.isNull(producer)) {
				return producer;
			}
			Properties properties = new Properties();
			properties.setProperty("producerGroupName", "bss-acct-zyf-" + System.currentTimeMillis());
			properties.setProperty("instanceName", Long.toString(System.currentTimeMillis()));
			properties.setProperty("namesrvAddr", namesrvAddr);
			properties.setProperty("authId", authId);
			properties.setProperty("authPwd", authPwd);
			properties.setProperty("clusterName", clusterName);
			properties.setProperty("tenantID", tenantID);
			properties.setProperty("maxMessageSize", "5242880");
			properties.setProperty("sendMaxRetryTimes", "5");
			properties.setProperty("sendMsgTimeout", String.valueOf(3000));
			properties.setProperty("compressMsgBodyOverHowmuch", String.valueOf(4096));

			producer = CTGMQFactory.createProducer(properties);
			int result = 0;
			try {
				result = producer.connect();
				if (result == 0) {
					logger.info("producer init success.");
					return producer;
				}

				logger.info("producer.connect return: " + result);
				return null;
			} catch (MQException e) {
				e.printStackTrace();
				logger.info("producer.connect exception, desc: " + e.getExpDesc());
				return null;
			}
		}
	}
	
	public MQSendResult sendMsg(String topic,String key,String tag,String msg,String groupId) throws Exception {
		producer = getInstance();
		if (Tools.isNull(producer)) {
			logger.info("producer.is null ");
			return null;
		}
		MQMessage message = new MQMessage(topic,key,tag,msg.toString().getBytes(),groupId);
		 
		MQSendResult sendResult = null;
		try {
			logger.info("发送消息开始,topic=" + topic + "," + new Date().toLocaleString());
			
			sendResult = producer.send(message);
			
			logger.info("sendResult.send success , messageID:" + sendResult.getMessageID());
			logger.info("发送消息结束,topic=" + topic + "," + new Date().toLocaleString());
		} catch (MQException e) {
			e.printStackTrace();
			logger.info("producer.send exception, desc: " + e.getExpDesc());
			throw new Exception(e);
		}
		return sendResult;
	}
	
	public MQSendResult sendMsg(String topic,String key,String tag,String msg) {
		producer = getInstance();
		if (Tools.isNull(producer)) {
			logger.info("producer.is null ");
			return null;
		}
		MQMessage message = new MQMessage(topic,key,tag,msg.toString().getBytes());
		
		MQSendResult sendResult = null;
		try {
			logger.info("发送消息开始,topic=" + topic + "," + new Date().toLocaleString());
			
			sendResult = producer.send(message);
			
			logger.info("sendResult.send success , messageID:" + sendResult.getMessageID());
			logger.info("发送消息结束,topic=" + topic + "," + new Date().toLocaleString());
		} catch (MQException e) {
			e.printStackTrace();
			logger.info("producer.send exception, desc: " + e.getExpDesc());
		}
		
		return sendResult;
	}
	
	public void sendApiLog(String msg){
		try {
			MQSendResult sendResult = this.sendMsg(apiTopic, "", "2", msg, apiGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MqProducerService mqProducerService = new MqProducerService();
		//mQProducer.sendMsg("PLCA_RC_BalanceTopic", "", "25", "456456","PLCA_RC_BalanceGroup");
	}
}
