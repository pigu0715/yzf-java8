package com.tydic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tydic.common.config.TaskThreadPoolConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@SpringBootApplication
@EnableTransactionManagement
@EnableEncryptableProperties
@EnableAsync
@EnableScheduling //开启定时任务
@EnableAutoConfiguration(exclude = { 
					DataSourceAutoConfiguration.class
					,MultipartAutoConfiguration.class
					})
@EnableConfigurationProperties({TaskThreadPoolConfig.class} ) // 开启配置属性支持
@PropertySource(value = {
				"file:conf_bssAcctYzf/application.properties"
				,"file:conf_bssAcctYzf/jdbc.properties"
				,"file:conf_bssAcctYzf/DCACluster.properties"
				}
				,ignoreResourceNotFound = false
				,encoding = "UTF-8")
public class SpringbootApplication {

	public static void main(String[] args) {
		System.setProperty("rocketmq.client.log.loadconfig","false");
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
