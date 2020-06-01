package com.tydic.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class DcaConfig {
	
	@Value("${dca.ip}")
	private String ip;
	
	@Value("${dca.port}")
	private int port;
	
	@Value("${dca.slaveIp}")
	private String slaveIp;
	
	@Value("${dca.slavePort}")
	private int slavePort;
	
	@Value("${dca.acctID}")
	private String acctID;
	
	@Value("${dca.userName}")
	private String userName;
	
	@Value("${dca.passWord}")
	private String passWord;
	
	@Value("${dca.pid}")
	private int pid;
	
	@Value("${dca.processName}")
	private String processName = "Java";
	
	@Value("${dca.dcasConnTimeOut}")
	private int dcasConnTimeOut;
	
	@Value("${dca.dcasSoTimeOut}")
	private int dcasSoTimeOut;
	
	@Value("${dca.dcasAgainNumber}")
	private int dcasAgainNumber;
	
	 
}
