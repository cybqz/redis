package com.cyb.redisclient.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyb.redisclient.service.SetService;
import com.cyb.redisclient.util.Constant;
import com.cyb.redisclient.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.redisclient.workcenter.response.WorkcenterResponseBodyJson;

@Controller
@RequestMapping("/set")
public class SetConroller extends RedisConfig implements Constant {
	
	@Autowired
	private SetService setService;
	
	@RequestMapping(value="/delSetValue", method=RequestMethod.POST)
	@ResponseBody
	public Object delSetValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam String value) {
		
		setService.delSetValue(serverName, dbIndex, key, value);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
	@RequestMapping(value="/updateSetValue", method=RequestMethod.POST)
	@ResponseBody
	public Object updateSetValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam String value) {
		
		setService.updateSetValue(serverName, dbIndex, key, value);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
}
