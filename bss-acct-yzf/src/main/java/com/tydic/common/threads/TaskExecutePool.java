package com.tydic.common.threads;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.tydic.common.config.TaskThreadPoolConfig;

@Configuration
@EnableAsync
public class TaskExecutePool  implements AsyncConfigurer {
    @Autowired
    private TaskThreadPoolConfig config;
   
	@Override
	@Bean(name = "myAsyncTaskExecutor")
	public Executor getAsyncExecutor() {
		  ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        //核心线程池大小
	        executor.setCorePoolSize(config.getCorePoolSize());
	        //最大线程数
	        executor.setMaxPoolSize(config.getMaxPoolSize());
	        //队列容量
	        executor.setQueueCapacity(config.getQueueCapacity());
	        //活跃时间
	        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
	        //线程名字前缀
	        executor.setThreadNamePrefix("MyExecutor-");

	        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
	        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
	        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	        executor.initialize();
	        return executor;
	}

	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return null;
	}
}
