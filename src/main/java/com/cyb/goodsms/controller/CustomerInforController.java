package com.cyb.goodsms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.goodsms.common.Constant;
import com.cyb.goodsms.dao.CustomerInfoMapper;
import com.cyb.goodsms.domain.CustomerInfo;
import com.cyb.goodsms.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 客户信息管理Controller
 */
@Controller
@RequestMapping(value= "/"+Constant.CUSTOMER_INFO)
public class CustomerInforController extends BaseController {

	private final static String MODEL_NAME = "客户信息管理";

	@Resource
	private CustomerInfoMapper customerInfoMapper;

	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("model", MODEL_NAME);
		request.setAttribute("title", MODEL_NAME +"-列表");
		return Constant.DEFAULT_PAGE_CUSTOMER_INFO;
	}

	@RequestMapping(Constant.DEFAULT_ADD)
	public String add(HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-新增");
		return Constant.DEFAULT_PAGE_PREFIX +Constant.CUSTOMER_INFO + Constant.DEFAULT_ADD;
	}

	@PostMapping(Constant.DEFAULT_SAVE)
	@ResponseBody
	public Tips save(CustomerInfo customerInfo) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("新增失败", true, false);
			customerInfo.setId(MyUtils.getPrimaryKey());
			customerInfo.setCreateDateTime(new Date());
			int count = customerInfoMapper.insert(customerInfo);
			if(count > 0){
				tips = new Tips("新增成功", true, true);
			}
		}
		return tips;
	}

	@PostMapping(Constant.DEFAULT_DELETE)
	@ResponseBody
	public Tips delete(String id) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("删除失败", true, false);
			if(StringUtils.isNotEmpty(id)){
				int count = customerInfoMapper.deleteById(id);
				if(count > 0){
					tips = new Tips("删除成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@RequestMapping(Constant.DEFAULT_UPDATE)
	public String update(String id, HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-更新");
		request.setAttribute("opreationId", id);
		return Constant.DEFAULT_PAGE_PREFIX +Constant.CUSTOMER_INFO + Constant.DEFAULT_UPDATE;
	}

	@PostMapping("/doupdate")
	@ResponseBody
	public Tips doupdate(CustomerInfo customerInfo) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(customerInfo.getId())){
				int count = customerInfoMapper.updateById(customerInfo);
				if(count > 0){
					tips = new Tips("更新成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@PostMapping(Constant.DEFAULT_DETAIL)
	@ResponseBody
	public Tips detail(String id) {

		super.validLogined();
		if(isLogined) {
			tips.setMsg("查询失败");
			if(StringUtils.isNotEmpty(id)){
				CustomerInfo customerInfo = customerInfoMapper.selectById(id);
				if(null != customerInfo){
					tips = new Tips("查询成功",  true, customerInfo);
				}
			}
		}
		return tips;
	}

	@PostMapping(value = Constant.DEFAULT_PAGE)
	@ResponseBody
	public TipsPagination<CustomerInfo> page(String param) {
		TipsPagination<CustomerInfo> tipsPagination = new TipsPagination<CustomerInfo>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
			JSONObject jsonObject = JSON.parseObject(param);
			CustomerInfo customerInfo = jsonObject.getObject("customerInfo", CustomerInfo.class);
			Pagination pagination = jsonObject.getObject("pagination", Pagination.class);
			int count = customerInfoMapper.countByExample(customerInfo);
			if(count > 0){
				List<CustomerInfo> list = customerInfoMapper.selectByExample(customerInfo, pagination);
				pagination.setDatas(list);
				pagination.setTotal(count);
				tipsPagination.setPagination(pagination);
				tipsPagination.setValidate(true);
				tipsPagination.setMsg("查询成功");
			}
		}
		return tipsPagination;
	}

	@PostMapping(Constant.DEFAULT_COUNT)
	@ResponseBody
	public Tips count(CustomerInfo customerInfo) {
		super.validLogined();
		if(isLogined) {
			int count = customerInfoMapper.countByExample(customerInfo);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
