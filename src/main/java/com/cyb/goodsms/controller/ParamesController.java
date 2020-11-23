package com.cyb.goodsms.controller;

import com.cyb.common.tips.Tips;
import com.cyb.goodsms.common.Constant;
import com.cyb.goodsms.domain.Parames;
import com.cyb.goodsms.service.ParamesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/parames")
public class ParamesController {

	@Autowired
	private ParamesServices paramesServices;
}
