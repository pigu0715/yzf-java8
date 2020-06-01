package com.tydic.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class InterceptorConfig implements HandlerInterceptor{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	/**
	 * 进入controller层之前拦截请求   
     * @param httpServletRequest   
     * @param httpServletResponse   
     * @param object   
     * @return   
     * @throws Exception   
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("---------------------开始进入请求地址拦截----------------------------");    
		  
		  
		return true;
	}    
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
		logger.debug("--------------处理请求完成后视图渲染之前的处理操作---------------");    
		
	}

	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub
		logger.debug("---------------视图渲染之后的操作-------------------------");    
		
	}

}
