package com.tydic.bus.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {
    
	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public String hello() throws UnknownHostException {
		
		InetAddress ip=InetAddress.getLocalHost();
		System.out.println(ip.getHostAddress());
		
		return ip.getHostAddress();
	}
}
