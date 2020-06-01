package com.tydic.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = {"classpath:dubbo-config/dubbo.consumer.xml"})
public class DubboConfig {

}
