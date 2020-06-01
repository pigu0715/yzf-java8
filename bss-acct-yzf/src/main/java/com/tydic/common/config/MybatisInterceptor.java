package com.tydic.common.config;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

@Component
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MybatisInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
				SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		// 先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		// id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
		String sqlId = mappedStatement.getId();
		// sql语句类型 select、delete、insert、update
		BoundSql boundSql = statementHandler.getBoundSql();

		// 获取到原始sql语句
		String sql = boundSql.getSql();
		StringBuilder sqlSb = new StringBuilder(sql);// 构造一个StringBuilder对象
		sqlSb.insert(0," /* [\"sqlId\":\""+sqlId+"\",\"appName\":\"bss-acct-yzf\",\"Mode\":\"运维\"] */   ");
		// 通过反射修改sql语句
		Field field = boundSql.getClass().getDeclaredField("sql");
		field.setAccessible(true);
		field.set(boundSql, sqlSb.toString());
		return invocation.proceed();

	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}

	}

	@Override
	public void setProperties(Properties properties) {

	}

}
