package com.tydic.bus.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.bus.service.CommonService;

@RestController
@RequestMapping("common")
public class CommonController {

	@Autowired
	private CommonService commonService;
    
//	@ResponseBody
//	@RequestMapping(value = "commonOper", method = RequestMethod.POST)
//	public String commonOper(@RequestBody Map<String, Object> vo) throws Exception{
//		return commonService.commonOper(vo);
//	}

	@ResponseBody
	@RequestMapping(value = "autoTest", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> autoTest() {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap = commonService.autoTest(null);
		return resultMap;
	}
}
