package com.tydic.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

public interface CommonDaos {

	SqlSessionFactory getSqlSessionFactory() throws Exception;
	
	SqlSessionTemplate getSqlSessionTemplate() throws Exception;
	
	void add(String nameSpace, Object obj, String dbType) throws Exception;
	
	int save(String nameSpace, Object obj, String dbType) throws Exception;

	void delete(String nameSpace, Object obj, String dbType) throws Exception;

	void update(String nameSpace, Object obj, String dbType) throws Exception;

	Object selectOne(String nameSpace, Object obj, String dbType) throws Exception;

	<T> List<T> selectList(String nameSpace, Object obj, String dbType) throws Exception;

	Map<String, Object> selectListByPage(String nameSpace, Object obj,String dbType) throws Exception;

}
