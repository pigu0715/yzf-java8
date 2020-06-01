package com.tydic.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;



@Configuration	
//开启定时任务多线程执行
public class ScheduleConfig implements SchedulingConfigurer {
  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
      taskRegistrar.setScheduler(setTaskExecutors());
  }
  
  @Bean(destroyMethod="shutdown")
  public Executor setTaskExecutors(){
      return Executors.newScheduledThreadPool(50);
  }
}
