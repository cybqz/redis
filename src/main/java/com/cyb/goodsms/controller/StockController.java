package com.cyb.goodsms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.goodsms.common.Constant;
import com.cyb.goodsms.dao.StockMapper;
import com.cyb.goodsms.domain.Stock;
import com.cyb.goodsms.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 库存管理Controller
 */
@Controller
@RequestMapping(value = "/"+Constant.STOCK)
public class StockController extends BaseController {

	private final static String MODEL_NAME = "库存管理";

	@Resource
	private StockMapper stockMapper;

	@GetMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("model", MODEL_NAME);
		request.setAttribute("title", MODEL_NAME +"-列表");
		return Constant.DEFAULT_PAGE_STOCK;
	}

	@RequestMapping(Constant.DEFAULT_ADD)
	public String add(HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-新增");
		return Constant.DEFAULT_PAGE_PREFIX +Constant.STOCK + Constant.DEFAULT_ADD;
	}

	@PostMapping(Constant.DEFAULT_SAVE)
	@ResponseBody
	public Tips save(Stock stock) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("新增失败", true, false);
			stock.setId(MyUtils.getPrimaryKey());
			stock.setCreateDateTime(new Date());
			int count = stockMapper.insert(stock);
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

				int count = stockMapper.deleteById(id);
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
		return Constant.DEFAULT_PAGE_PREFIX +Constant.STOCK + Constant.DEFAULT_UPDATE;
	}

	@PostMapping("/doupdate")
	@ResponseBody
	public Tips doupdate(Stock stock) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(stock.getId())){
				int count = stockMapper.updateById(stock);
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
				Stock stock = stockMapper.selectById(id);
				if(null != stock){
					tips = new Tips("查询成功",  true, stock);
				}
			}
		}
		return tips;
	}

	@PostMapping(Constant.DEFAULT_PAGE)
	@ResponseBody
	public TipsPagination<Stock> page(String param) {
		TipsPagination<Stock> tipsPagination = new TipsPagination<Stock>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
			JSONObject jsonObject = JSON.parseObject(param);
			Stock stock = jsonObject.getObject("stock", Stock.class);
			Pagination pagination = jsonObject.getObject("pagination", Pagination.class);
			int count = stockMapper.countByExample(stock);
			if(count > 0) {
				List<Stock> list = stockMapper.selectByExample(stock, pagination);
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
	public Tips count(Stock stock) {
		super.validLogined();
		if(isLogined) {
			int count = stockMapper.countByExample(stock);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
