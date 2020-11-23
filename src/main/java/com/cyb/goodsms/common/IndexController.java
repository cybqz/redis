package com.cyb.goodsms.common;

import com.cyb.authority.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/")
public class IndexController extends BaseController {

	private static final String DEFAULT_PAGE = "login";
	private static final String SYSTEM_TITLE = "商品管理系统";

	@GetMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("title", SYSTEM_TITLE);
		return DEFAULT_PAGE;
	}

	@GetMapping("/index")
	public String toIndex(HttpServletRequest request) {
		request.setAttribute("title", SYSTEM_TITLE);
		return DEFAULT_PAGE;
	}
}
