package com.cyb.redisclient.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyb.redisclient.service.StringService;
import com.cyb.redisclient.util.Constant;
import com.cyb.redisclient.util.RedisApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.redisclient.workcenter.response.WorkcenterResponseBodyJson;

@Controller
@RequestMapping("/string")
public class StringConroller extends RedisApplication implements Constant {
	
	@Autowired
	private StringService stringService;
	
	@RequestMapping(value="/delValue", method=RequestMethod.POST)
	@ResponseBody
	public Object delValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType) {
		
		stringService.delValue(serverName, dbIndex, key);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
	@RequestMapping(value="/updateValue", method=RequestMethod.POST)
	@ResponseBody
	public Object updateValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam String value) {
		
		stringService.updateValue(serverName, dbIndex, key, value);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
}
