package com.cyb.redisclient.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyb.redisclient.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController implements Constant {

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public Object index(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/redis";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Object home(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/redis";
	}
}
