package com.tydic.common.config;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.tydic.common.dao.DynamicDataSource;

@Configuration
public class DataSourceConfigurer {
	
	 //非常简单的配置druid数据库连接池
    @Bean("acct")
    @ConfigurationProperties(prefix = "spring.datasource.acct")
    public DataSource acct(){
        return  new DruidDataSource();
    }

    //非常简单的配置druid数据库连接池
    @Bean("cust")
    @ConfigurationProperties(prefix = "spring.datasource.cust")
    public DataSource cust(){
        return  new DruidDataSource();
    }
    
    
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(
    			@Qualifier("acct")DataSource acct
    			,@Qualifier("cust")DataSource cust
    			) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("acct",acct);
        targetDataSources.put("cust",cust);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(acct);
        return dataSource;
    }
	

}