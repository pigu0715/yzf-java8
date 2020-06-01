package com.tydic.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tydic.common.utils.Tools;

@Component("commonDaos")
public class CommonDaosImpl implements CommonDaos {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	};
	
	@Override
	public SqlSessionFactory getSqlSessionFactory() throws Exception {
		return getSqlSessionTemplate().getSqlSessionFactory();
	}
	
	@Override
	public void add(String nameSpace, Object obj, String dbType) throws Exception {
		DBContextHolder.setDBType(dbType);
		sqlSessionTemplate.insert(nameSpace, obj);
	}

	@Override
	public int save(String nameSpace, Object obj, String dbType) throws Exception {
		DBContextHolder.setDBType(dbType);
		return sqlSessionTemplate.insert(nameSpace, obj);
	}

	@Override
	public void delete(String nameSpace, Object obj, String dbType) throws Exception {
		DBContextHolder.setDBType(dbType);
		sqlSessionTemplate.delete(nameSpace, obj);
	}

	@Override
	public void update(String nameSpace, Object obj, String dbType) throws Exception {
		DBContextHolder.setDBType(dbType);
		sqlSessionTemplate.update(nameSpace, obj);
	}

	@Override
	public Object selectOne(String nameSpace, Object obj, String dbType) throws Exception {
		DBContextHolder.setDBType(dbType);
		return sqlSessionTemplate.selectOne(nameSpace, obj);
	}

	@Override
	public <T> List<T> selectList(String nameSpace, Object obj, String dbType) throws Exception {
		DBContextHolder.setDBType(dbType);
		return sqlSessionTemplate.selectList(nameSpace, obj);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectListByPage(String nameSpace, Object obj,String dbType) throws Exception {
		DBContextHolder.setDBType(dbType);
		Map<String,Object> resultMapPage = new HashMap<String,Object>();
		List<Map<String,Object>> resultSet = null;
		Map<String,Object> pageMap = (Map<String, Object>) obj;
		if (Tools.isNull(pageMap.get("pageIndex"))) {
			pageMap.put("pageIndex", 0);
		}
		if (Tools.isNull(pageMap.get("pageSize"))) {
			pageMap.put("pageSize", 10);
		}
		Integer pageIndex =Integer.valueOf(String.valueOf(pageMap.get("pageIndex")));
		Integer pageSize =Integer.valueOf(String.valueOf( pageMap.get("pageSize")));
		PageHelper.startPage(pageIndex, pageSize);
		resultSet = sqlSessionTemplate.selectList(nameSpace, pageMap);
		PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(resultSet);
		resultMapPage.put("totalSize" , page.getTotal());
	    resultMapPage.put("pageIndex" , page.getPageNum());
	    resultMapPage.put("pageSize" ,  page.getPageSize());
	    resultMapPage.put("endRow" , page.getEndRow());
	    resultMapPage.put("pages" , page.getPages());
	    resultMapPage.put("startRow" , page.getStartRow());
		resultMapPage.put("pageData" , page.getList());
		return resultMapPage;
	}

}
