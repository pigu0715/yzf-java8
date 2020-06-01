package com.tydic.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.tydic.common.dao.DynamicDataSource;


@Configuration
public class MybatisConfig {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String MYBATIS_CONFIG = "mybatis/mybatis-config.xml";
	private static final String MAPPER_PATH = "classpath:mybatis/mapper/*/*.xml";

	@Autowired
	private DynamicDataSource dynamicDataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dynamicDataSource);
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(MAPPER_PATH);
		sqlSessionFactoryBean.setMapperLocations(resources);
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
		return new SqlSessionTemplate(factory);
	}

}
